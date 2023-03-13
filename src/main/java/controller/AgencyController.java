package controller;

import model.Agency;
import model.Location;
import model.Rocket;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AgencyController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  public AgencyController(Connection connection) {
    this.connection = connection;
  }

  public AgencyController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  public List<Agency> readAgencyFile(String filename) throws IOException {
    String agency_name;
    String agency_type;
    String agency_abbreviation;
    String agency_administration;
    int agency_founded;
    String agency_country;
    String agency_spacecraft;
    String agency_launchers;
    String agency_description;
    List<Agency> agencyList = new ArrayList();

    BufferedReader br = new BufferedReader(new FileReader(filename));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      String[] parts = linea.split(",");
      agency_name = parts[0];
      agency_type = parts[1];
      agency_abbreviation = parts[2];
      agency_administration = parts[3];
      agency_founded = Integer.parseInt(parts[4]);
      agency_country = parts[5];
      agency_spacecraft = parts[6];
      agency_launchers = parts[7];
      agency_description = parts[8];
      Agency agency = new Agency(agency_name, agency_type, agency_abbreviation, agency_administration, agency_founded, agency_country, agency_spacecraft, agency_launchers, agency_description);
      agencyList.add(agency);
    }
    br.close();
    return agencyList;
  }

  public void printAgency(List<Agency> agencyList) {
    for (int i = 0; i < agencyList.size(); i++) {
      System.out.println((i+1) +  " " + agencyList.get(i).toString());
    }
  }

  public void addAgency(Agency agency) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(agency);
    em.getTransaction().commit();
    em.close();
  }

  public List<Agency> listAgency() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Agency> result = em.createQuery("from Agency", Agency.class).getResultList();
    em.getTransaction().commit();
    em.close();

    return result;
  }

  public void updateAgency(Agency agency) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(agency);
    em.getTransaction().commit();
    em.close();
  }

  public void deleteAgency(String agency_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Agency agency = (Agency) em.find(Agency.class, agency_name);
    em.remove(agency);
    em.getTransaction().commit();
    em.close();
  }

  public void insertAgencyList() throws IOException {
    List<Agency> agencyList = readAgencyFile("src/main/resources/agency.txt");
    for (Agency agency : agencyList){
      addAgency(agency);
    }
  }

  public List<Agency> searchAgency(String searchText) {
    try {
      EntityManager em = entityManagerFactory.createEntityManager();
      em.getTransaction().begin();
      TypedQuery<Agency> query = em.createQuery("FROM Agency a WHERE a.agency_name LIKE :searchText OR a.agency_type LIKE :searchText OR a.agency_abbreviation LIKE :searchText OR a.agency_administration LIKE :searchText OR a.agency_founded LIKE :searchText OR a.agency_country LIKE :searchText OR a.agency_spacecraft LIKE :searchText OR a.agency_launchers LIKE :searchText OR a.agency_description LIKE :searchText", Agency.class);
      query.setParameter("searchText", "%" + searchText + "%");
      List<Agency> results = query.getResultList();
      em.getTransaction().commit();
      em.close();
      return results;
    } catch (Exception e){}
    return null;
  }
}