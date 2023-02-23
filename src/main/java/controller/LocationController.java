package controller;

import model.Location;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

  public void printLocation(ArrayList<Location> locationList) {
    for (int i = 0; i < locationList.size(); i++) {
      System.out.println(locationList.get(i).toString());
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
  public void listLocation() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Location> result = em.createQuery("from location", Location.class).getResultList();

    for (Location location : result) {
      System.out.println(location.toString());
    }
    em.getTransaction().commit();
    em.close();
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
}