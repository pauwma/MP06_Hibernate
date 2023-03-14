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

/**
 Esta clase es el controlador para la entidad de Agencia en el sistema.
 Maneja la lectura, adición, actualización y eliminación de Agencias.
 También proporciona funciones de búsqueda y listado de todas las Agencias.
 */
public class AgencyController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  /**
   Constructor de la clase.
   Crea un nuevo objeto de controlador de Agency con una conexión dada.
   @param connection La conexión a la base de datos que se utilizará.
   */
  public AgencyController(Connection connection) {
    this.connection = connection;
  }

  /**
   Constructor de la clase.
   Crea un nuevo objeto de controlador de Agencia con una conexión y un EntityManagerFactory dados.
   @param connection La conexión a la base de datos que se utilizará.
   @param entityManagerFactory El EntityManagerFactory que se utilizará para crear EntityManagers.
   */
  public AgencyController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   Lee un archivo de texto que contiene datos de Agencias y devuelve una lista de Agencias.
   @param filename El nombre del archivo de texto que se va a leer.
   @return Una lista de Agencias leídas del archivo de texto.
   @throws IOException Si ocurre un error de entrada/salida al leer el archivo.
   */
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

  /**
   Imprime una lista de Agencias dada en la consola.
   @param agencyList La lista de Agencias que se va a imprimir.
   */
  public void printAgency(List<Agency> agencyList) {
    for (int i = 0; i < agencyList.size(); i++) {
      System.out.println((i+1) +  " " + agencyList.get(i).toString());
    }
  }

  /**
   Agrega una agencia a la base de datos.
   @param agency objeto de la clase Agency que representa la agencia a agregar
   */
  public void addAgency(Agency agency) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(agency);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Retorna una lista con todas las agencias almacenadas en la base de datos.
   @return lista de objetos de la clase Agency que representan todas las agencias almacenadas
   */
  public List<Agency> listAgency() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Agency> result = em.createQuery("from Agency", Agency.class).getResultList();
    em.getTransaction().commit();
    em.close();

    return result;
  }

  /**
   Actualiza una agencia ya almacenada en la base de datos.
   @param agency objeto de la clase Agency que representa la agencia a actualizar
   */
  public void updateAgency(Agency agency) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(agency);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Elimina una agencia de la base de datos.
   @param agency_name el nombre de la agencia a eliminar
   */
  public void deleteAgency(String agency_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Agency agency = (Agency) em.find(Agency.class, agency_name);
    em.remove(agency);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Lee un archivo de texto que contiene información de agencias y las agrega a la base de datos.
   @throws IOException si ocurre un error al leer el archivo
   */
  public void insertAgencyList() throws IOException {
    List<Agency> agencyList = readAgencyFile("src/main/resources/agency.txt");
    for (Agency agency : agencyList){
      addAgency(agency);
    }
  }

  /**
   Busca todas las agencias que contengan un texto dado en alguno de sus atributos y las retorna en una lista.
   @param searchText el texto a buscar en los atributos de las agencias
   @return lista de objetos de la clase Agency que representan las agencias encontradas
   */
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