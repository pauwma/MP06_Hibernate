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

/**
 Esta clase es el controlador para la entidad de Rocket en el sistema.
 Maneja la lectura, adición, actualización y eliminación de Rocket.
 También proporciona funciones de búsqueda y listado de todos los Rocket.
 */
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

  /**
   Constructor de la clase.
   Crea un nuevo objeto de controlador de Rocket con una conexión y un EntityManagerFactory dados.
   @param connection La conexión a la base de datos que se utilizará.
   @param entityManagerFactory El EntityManagerFactory que se utilizará para crear EntityManagers.
   */
  public RocketController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
    this.agencyController = new AgencyController(connection, entityManagerFactory);
  }

  /**
   Lee un archivo de texto que contiene datos de Rocket y devuelve una lista de Rocket.
   @param filename El nombre del archivo de texto que se va a leer.
   @return Una lista de Rocket leídas del archivo de texto.
   @throws IOException Si ocurre un error de entrada/salida al leer el archivo.
   */
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

  /**
   Imprime una lista de Rocket dada en la consola.
   @param rocketList La lista de Rocket que se va a imprimir.
   */
  public void printRocket(List<Rocket> rocketList) {
    for (int i = 0; i < rocketList.size(); i++) {
      System.out.println((i+1)+ " " +rocketList.get(i).toString());
    }
  }

  /**
   Agrega un Rocket a la base de datos.
   @param rocket objeto de la clase Rocket que representa el Rocket a agregar
   */
  public void addRocket(Rocket rocket) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(rocket);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Retorna una lista con todos los Rocket almacenadas en la base de datos.
   @return lista de objetos de la clase Rocket que representan todos los Rocket almacenados
   */
  public List<Rocket> listRocket() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Rocket> result = em.createQuery("from Rocket", Rocket.class).getResultList();
    em.getTransaction().commit();
    em.close();

    return result;
  }

  /**
   Actualiza un Rocket ya almacenado en la base de datos.
   @param rocket objeto de la clase Rocket que representa el Rocket a actualizar
   */
  public void updateRocket(Rocket rocket) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(rocket);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Elimina un Rocket de la base de datos.
   @param rocket_name el nombre del Rocket a eliminar
   */
  public void deleteRocket(String rocket_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Rocket rocket = (Rocket) em.find(Rocket.class, rocket_name);
    em.remove(rocket);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Lee un archivo de texto que contiene información de Rocket y los agrega a la base de datos.
   @throws IOException si ocurre un error al leer el archivo
   */
  public void insertRocketList() throws IOException {
    List<Rocket> rocketList = readRocketFile("src/main/resources/rocket.txt");
    for (Rocket rocket : rocketList){
      addRocket(rocket);
    }
  }

  /**
   Busca todos los Rocket que contengan un texto dado en alguno de sus atributos y las retorna en una lista.
   @param searchText el texto a buscar en los atributos de los Rocket
   @return lista de objetos de la clase Rocket que representan los Rocket encontrados
   */
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