package controller;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

  public void printLaunch(ArrayList<Location> locationList) {
    for (int i = 0; i < locationList.size(); i++) {
      System.out.println(locationList.get(i).toString());
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

  public void updateLaunch(String location_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Location location = (Location) em.find(Location.class, location_name);
    em.merge(location);
    em.getTransaction().commit();
    em.close();
  }

  public void deleteLaunch(String location_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Location location = (Location) em.find(Location.class, location_name);
    em.remove(location);
    em.getTransaction().commit();
    em.close();
  }

    public void insertLaunchList() throws IOException {
      List<Launch> launchList = readLaunchFile("src/main/resources/launch.txt");
      for (Launch launch : launchList){
        addLaunch(launch);
      }
    }
}