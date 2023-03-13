package controller;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LaunchController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;
  private AgencyController agencyController;
  private LocationController locationController;
  private MissionController missionController;
  private RocketController rocketController;



  public LaunchController(Connection connection) {
    this.connection = connection;
  }

  public LaunchController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
    this.agencyController = new AgencyController(connection, entityManagerFactory);
    this.locationController = new LocationController(connection, entityManagerFactory);
    this.missionController = new MissionController(connection, entityManagerFactory);
    this.rocketController = new RocketController(connection, entityManagerFactory);

  }

  public List<Launch> readLaunchFile(String filename) throws IOException {
    String launch_title = "";
    String launch_status = "";
    String launch_date = "";
    Agency agency = null;
    String agency_name = "";
    Location location = null;
    String location_name = "";
    Mission mission = null;
    String mission_name = "";
    Rocket rocket = null;
    String rocket_name = "";

    List<Agency> agencyList = agencyController.listAgency();
    List<Location> locationList = locationController.listLocation();
    List<Mission> missionList = missionController.listMission();
    List<Rocket> rocketList = rocketController.listRocket();
    List<Launch> launchList = new ArrayList();

    BufferedReader br = new BufferedReader(new FileReader(filename));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      launch_title = str.nextToken();
      launch_status = str.nextToken();
      launch_date = str.nextToken();
      rocket_name = str.nextToken();
      agency_name = str.nextToken();
      location_name = str.nextToken();
      mission_name = str.nextToken();

      for (Rocket r : rocketList) {
        if (r.getRocket_name().equals(rocket_name)) {
          rocket = r;
          break;
        }
      }
      for (Agency a : agencyList) {
        if (a.getAgency_name().equals(agency_name)) {
          agency = a;
          break;
        }
      }
      for (Location l : locationList) {
        if (l.getLocationName().equals(location_name)) {
          location = l;
          break;
        }
      }
      for (Mission m : missionList) {
        if (m.getMission_name().equals(mission_name)) {
          mission = m;
          break;
        }
      }
      launchList.add(new Launch(launch_title, launch_status, launch_date, rocket, agency, location, mission));
    }
    br.close();
    return launchList;
  }

  public void printLaunch(List<Launch> launchList) {
    for (int i = 0; i < launchList.size(); i++) {
      System.out.println((i+1)+ " " +launchList.get(i).toString());
    }
  }

  public void addLaunch(Launch launch) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(launch);
    em.getTransaction().commit();
    em.close();
  }

  public List<Launch> listLaunch() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Launch> result = em.createQuery("from Launch", Launch.class).getResultList();
    em.getTransaction().commit();
    em.close();
    return result;
  }

  public void updateLaunch(Launch launch) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(launch);
    em.getTransaction().commit();
    em.close();
  }

  public void deleteLaunch(String launch_title) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Launch launch = (Launch) em.find(Launch.class, launch_title);
    em.remove(launch);
    em.getTransaction().commit();
    em.close();
  }

    public void insertLaunchList() throws IOException {
      List<Launch> launchList = readLaunchFile("src/main/resources/launch.txt");
      for (Launch launch : launchList){
        addLaunch(launch);
      }
    }

  public List<Launch> searchLaunch(String searchText) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    TypedQuery<Launch> query = em.createQuery("FROM Launch l WHERE l.launch_title LIKE :searchText OR l.launch_status LIKE :searchText OR l.launch_date LIKE :searchText OR l.rocket.rocket_name LIKE :searchText OR l.agency.agency_name LIKE :searchText OR l.location.location_name LIKE :searchText OR l.mission.mission_name LIKE :searchText", Launch.class);
    query.setParameter("searchText", "%" + searchText + "%");
    List<Launch> results = query.getResultList();
    em.getTransaction().commit();
    em.close();
    return results;
  }
}