package controller;

import model.Location;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MissionController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  public MissionController(Connection connection) {
    this.connection = connection;
  }

  public MissionController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  public List<Location> readMissionFile(String filename) throws IOException {
    String location_name;
    String location_location;
    int rockets_launched;
    List<Location> locationList = new ArrayList();

    BufferedReader br = new BufferedReader(new FileReader(filename));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      String[] parts = linea.split(",");
      location_name = parts[0];
      location_location = parts[1];
      rockets_launched = Integer.parseInt(parts[2]);
      Location location = new Location(location_name, location_location, rockets_launched);
      locationList.add(location);
    }
    br.close();
    return locationList;
  }

  public void printMission(ArrayList<Location> locationList) {
    for (int i = 0; i < locationList.size(); i++) {
      System.out.println(locationList.get(i).toString());
    }
  }

  public void addMission(Location location) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(location);

    em.getTransaction().commit();
    em.close();
  }

  public void listMission() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Location> result = em.createQuery("from location", Location.class).getResultList();

    for (Location location : result) {
      System.out.println(location.toString());
    }
    em.getTransaction().commit();
    em.close();
  }

  public void updateMission(String location_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Location location = (Location) em.find(Location.class, location_name);
    em.merge(location);
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
}