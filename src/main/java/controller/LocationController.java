package controller;

import model.Location;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

public class LocationController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  public LocationController(Connection connection) {
    this.connection = connection;
  }

  public LocationController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  public List<Location> readLocationFile(String filename) throws IOException {
    String location_name;
    String location_location;
    int rockets_launched;
    List<Location> locationList = new ArrayList();

    BufferedReader br = new BufferedReader(new FileReader(filename));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      location_name = str.nextToken();
      location_location = str.nextToken();
      rockets_launched = Integer.parseInt(str.nextToken());
      Location location = new Location(location_name, location_location, rockets_launched);
      locationList.add(location);
    }
    br.close();
    return locationList;
  }

  public void printLocation(List<Location> locationList) {
    for (int i = 0; i < locationList.size(); i++) {
      System.out.println((i+1) + " " + locationList.get(i).toString());
    }
  }

  /* Method to CREATE a Magazine  in the database */
  public void addLocation(Location location) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(location);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to READ all Magazines */
  public List<Location> listLocation() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Location> result = em.createQuery("from Location", Location.class).getResultList();
    em.getTransaction().commit();
    em.close();

    return result;
  }

  /* Method to UPDATE activity for an Magazine */
  public void updateLocation(String location_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Location location = (Location) em.find(Location.class, location_name);
    em.merge(location);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to DELETE an Magazine from the records */
  public void deleteLocation(String location_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Location location = (Location) em.find(Location.class, location_name);
    em.remove(location);
    em.getTransaction().commit();
    em.close();
  }

  public void insertLocationList() throws IOException {
    List<Location> locationList = readLocationFile("src/main/resources/location.txt");
    for (Location location : locationList){
      addLocation(location);
    }
  }

}