package controller;

import model.Agency;
import model.Location;
import model.Mission;
import model.Rocket;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class RocketController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  private AgencyController agencyController;

  /**
   Constructor de la clase.
   Crea un nuevo objeto de controlador de Rocket con una conexión dada.
   @param connection La conexión a la base de datos que se utilizará.
   */
  public RocketController(Connection connection) {
    this.connection = connection;
  }

  public RocketController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
    this.agencyController = new AgencyController(connection, entityManagerFactory);
  }

  public List<Rocket> readRocketFile(String filename) throws IOException {
    String rocket_name = "";
    String rocket_family = "";
    String rocket_length = "";
    String rocket_diameter = "";
    String rocket_launch_mass = "";
    String rocket_low_earth_orbit_capacity = "";
    String rocket_description = "";
    String agency_name = "";
    Agency agency = null;

    List<Agency> agencyList = agencyController.listAgency();
    List<Rocket> rocketList = new ArrayList();

    BufferedReader br = new BufferedReader(new FileReader(filename));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      agency_name = str.nextToken();
      rocket_name = str.nextToken();
      rocket_family = str.nextToken();
      rocket_length = str.nextToken();
      rocket_diameter = str.nextToken();
      rocket_launch_mass = str.nextToken();
      rocket_low_earth_orbit_capacity = str.nextToken();
      rocket_description = str.nextToken();
      for (Agency a : agencyList) {
        if (a.getAgency_name().equals(agency_name)) {
          agency = a;
          Rocket rocket = new Rocket(rocket_name, rocket_family, rocket_length, rocket_diameter, rocket_launch_mass, rocket_low_earth_orbit_capacity, rocket_description, agency);
          rocketList.add(rocket);
          break;
        }
      }
    }
    br.close();
    return rocketList;
  }

  public void printRocket(List<Rocket> rocketList) {
    for (int i = 0; i < rocketList.size(); i++) {
      System.out.println((i+1)+ " " +rocketList.get(i).toString());
    }
  }

  public void addRocket(Rocket rocket) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(rocket);
    em.getTransaction().commit();
    em.close();
  }

  public List<Rocket> listRocket() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Rocket> result = em.createQuery("from Rocket", Rocket.class).getResultList();
    em.getTransaction().commit();
    em.close();

    return result;
  }

  public void updateRocket(Rocket rocket) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(rocket);
    em.getTransaction().commit();
    em.close();
  }

  public void deleteRocket(String rocket_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Rocket rocket = (Rocket) em.find(Rocket.class, rocket_name);
    em.remove(rocket);
    em.getTransaction().commit();
    em.close();
  }

  public void insertRocketList() throws IOException {
    List<Rocket> rocketList = readRocketFile("src/main/resources/rocket.txt");
    for (Rocket rocket : rocketList){
      addRocket(rocket);
    }
  }

  public List<Rocket> searchRocket(String searchText) {
    try {
      EntityManager em = entityManagerFactory.createEntityManager();
      em.getTransaction().begin();
      TypedQuery<Rocket> query = em.createQuery("FROM Rocket r WHERE r.rocket_name LIKE :searchText OR r.rocket_family LIKE :searchText OR r.rocket_length LIKE :searchText OR r.rocket_diameter LIKE :searchText OR r.rocket_launch_mass LIKE :searchText OR r.rocket_low_earth_orbit_capacity LIKE :searchText OR r.rocket_description LIKE :searchText", Rocket.class);
      query.setParameter("searchText", "%" + searchText + "%");
      List<Rocket> results = query.getResultList();
      em.getTransaction().commit();
      em.close();
      return results;
    } catch (Exception e){}
    return null;
  }
}