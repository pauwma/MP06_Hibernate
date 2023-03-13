package controller;

import model.Agency;
import model.Location;
import model.Mission;
import model.Rocket;
import org.hibernate.Session;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MissionController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  private RocketController rocketController = new RocketController(connection, entityManagerFactory);

  public MissionController(Connection connection) {
    this.connection = connection;
  }

  public MissionController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
    this.rocketController = new RocketController(connection, entityManagerFactory);
  }

  public List<Mission> readMissionFile(String filename) throws IOException {
    String mission_name;
    String mission_type;
    String mission_launch_cost;
    String mission_description;
    String rocket_name;
    Rocket rocket;

    List<Rocket> rocketList = rocketController.listRocket();
    List<Mission> missionList = new ArrayList();

    BufferedReader br = new BufferedReader(new FileReader(filename));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      rocket_name = str.nextToken();
      mission_name = str.nextToken();
      mission_type = str.nextToken();
      mission_launch_cost = str.nextToken();
      mission_description = str.nextToken();
      rocket = null;
      for (Rocket r : rocketList) {
        if (r.getRocket_name().equals(rocket_name)) {
          rocket = r;
          Mission mission = new Mission(mission_name, mission_type, mission_launch_cost, mission_description, rocket);
          missionList.add(mission);
          break;
        }
      }
    }
    br.close();
    return missionList;
  }

  public void printMission(List<Mission> locationList) {
    for (int i = 0; i < locationList.size(); i++) {
      System.out.println((i+1) + " " + locationList.get(i).toString());
    }
  }

  public void addMission(Mission mission) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(mission);
    em.getTransaction().commit();
    em.close();
  }

  public List<Mission> listMission() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Mission> result = em.createQuery("from Mission", Mission.class).getResultList();
    em.getTransaction().commit();
    em.close();

    return result;
  }

  public void updateMission(Mission mission) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(mission);
    em.getTransaction().commit();
    em.close();
  }

  public void deleteMission(String location_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Location location = (Location) em.find(Location.class, location_name);
    em.remove(location);
    em.getTransaction().commit();
    em.close();
  }

  public void insertMissionList() throws IOException {
    List<Mission> missionList = readMissionFile("src/main/resources/mission.txt");
    for (Mission mission : missionList){
      addMission(mission);
    }
  }

  public List<Mission> searchMission(String searchText) {
    try {
      EntityManager em = entityManagerFactory.createEntityManager();
      em.getTransaction().begin();
      TypedQuery<Mission> query = em.createQuery("FROM Mission m WHERE m.mission_name LIKE :searchText OR mission_type LIKE :searchText OR mission_launch_cost LIKE :searchText OR mission_description LIKE :searchText OR rocket_name LIKE :searchText", Mission.class);
      query.setParameter("searchText", "%" + searchText + "%");
      List<Mission> results = query.getResultList();
      em.getTransaction().commit();
      em.close();
      return results;
    } catch (Exception e){}
    return null;
  }
}