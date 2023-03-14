package controller;

import model.Agency;
import model.Location;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

/**
 Esta clase es el controlador para la entidad de Location en el sistema.
 Maneja la lectura, adición, actualización y eliminación de Location.
 También proporciona funciones de búsqueda y listado de todas las Location.
 */
public class LocationController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  /**
   Constructor de la clase.
   Crea un nuevo objeto de controlador de Location con una conexión dada.
   @param connection La conexión a la base de datos que se utilizará.
   */
  public LocationController(Connection connection) {
    this.connection = connection;
  }

  /**
   Constructor de la clase.
   Crea un nuevo objeto de controlador de Location con una conexión y un EntityManagerFactory dados.
   @param connection La conexión a la base de datos que se utilizará.
   @param entityManagerFactory El EntityManagerFactory que se utilizará para crear EntityManagers.
   */
  public LocationController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   Lee un archivo de texto que contiene datos de Location y devuelve una lista de Location.
   @param filename El nombre del archivo de texto que se va a leer.
   @return Una lista de Location leídas del archivo de texto.
   @throws IOException Si ocurre un error de entrada/salida al leer el archivo.
   */
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

  /**
   Imprime una lista de Location dada en la consola.
   @param locationList La lista de Location que se va a imprimir.
   */
  public void printLocation(List<Location> locationList) {
    for (int i = 0; i < locationList.size(); i++) {
      System.out.println((i+1) + " " + locationList.get(i).toString());
    }
  }

  /**
   Agrega una Location a la base de datos.
   @param location objeto de la clase Location que representa la Location a agregar
   */
  public void addLocation(Location location) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(location);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Retorna una lista con todas las Location almacenadas en la base de datos.
   @return lista de objetos de la clase Location que representan todas las Location almacenadas
   */
  public List<Location> listLocation() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Location> result = em.createQuery("from Location", Location.class).getResultList();
    em.getTransaction().commit();
    em.close();

    return result;
  }

  /**
   Actualiza una Location ya almacenada en la base de datos.
   @param location objeto de la clase Location que representa la Location a actualizar
   */
  public void updateLocation(Location location) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(location);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Elimina una Location de la base de datos.
   @param location_name el nombre de la Location a eliminar
   */
  public void deleteLocation(String location_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Location location = (Location) em.find(Location.class, location_name);
    em.remove(location);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Lee un archivo de texto que contiene información de Location y las agrega a la base de datos.
   @throws IOException si ocurre un error al leer el archivo
   */
  public void insertLocationList() throws IOException {
    List<Location> locationList = readLocationFile("src/main/resources/location.txt");
    for (Location location : locationList){
      addLocation(location);
    }
  }

  /**
   Busca todas las Location que contengan un texto dado en alguno de sus atributos y las retorna en una lista.
   @param searchText el texto a buscar en los atributos de las Location
   @return lista de objetos de la clase Location que representan las Location encontradas
   */
  public List<Location> searchLocation(String searchText) {
    try {
      EntityManager em = entityManagerFactory.createEntityManager();
      em.getTransaction().begin();
      TypedQuery<Location> query = em.createQuery("FROM Location l WHERE l.location_name LIKE :searchText OR l.location_location LIKE :searchText OR l.rockets_launched LIKE :searchText", Location.class);
      query.setParameter("searchText", "%" + searchText + "%");
      List<Location> results = query.getResultList();
      em.getTransaction().commit();
      em.close();
      return results;
    } catch (Exception e){}
    return null;
  }

}