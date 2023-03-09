package controller;

import model.Agency;
import model.Location;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

  public void printAgency(ArrayList<Agency> agencyList) {
    for (int i = 0; i < agencyList.size(); i++) {
      System.out.println(agencyList.get(i).toString());
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
    List<Agency> result = em.createQuery("from agency", Agency.class).getResultList();

    for (Agency agency : result) {
      System.out.println(agency.toString());
    }
    em.getTransaction().commit();
    em.close();

    return result;
  }

  public void updateAgency(String agency_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Agency agency = (Agency) em.find(Agency.class, agency_name);
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

  // TODO
  public static void modifyObject(Object obj) {
    System.out.println(obj.toString());
    Scanner scanner = new Scanner(System.in);
    Field[] fields = obj.getClass().getDeclaredFields();
    for (Field field : fields) {
      System.out.print("Ingrese el valor para " + field.getName() + ": ");
      String input = scanner.nextLine();
      try {
        field.setAccessible(true);
        if (field.getType() == int.class) {
          field.setInt(obj, Integer.parseInt(input));
        } else if (field.getType() == double.class) {
          field.setDouble(obj, Double.parseDouble(input));
        } else if (field.getType() == String.class) {
          field.set(obj, input);
        } else {
          System.out.println("Tipo de dato no soportado: " + field.getType());
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    System.out.println(obj.toString());
  }
}