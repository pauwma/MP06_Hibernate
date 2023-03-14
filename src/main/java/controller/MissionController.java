package controller;

import model.Agency;
import model.Location;
import model.Mission;
import model.Rocket;
import org.hibernate.Session;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 Esta clase es el controlador para la entidad de Mission en el sistema.
 Maneja la lectura, adición, actualización y eliminación de Mission.
 También proporciona funciones de búsqueda y listado de todas las Mission.
 */
public class MissionController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  private RocketController rocketController = new RocketController(connection, entityManagerFactory);

  /**
   Constructor de la clase.
   Crea un nuevo objeto de controlador de Mission con una conexión dada.
   @param connection La conexión a la base de datos que se utilizará.
   */
  public MissionController(Connection connection) {
    this.connection = connection;
  }

  /**
   Constructor de la clase.
   Crea un nuevo objeto de controlador de Location con una conexión y un EntityManagerFactory dados.
   @param connection La conexión a la base de datos que se utilizará.
   @param entityManagerFactory El EntityManagerFactory que se utilizará para crear EntityManagers.
   */
  public MissionController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
    this.rocketController = new RocketController(connection, entityManagerFactory);
  }

  /**
   Lee un archivo de texto que contiene datos de Mission y devuelve una lista de Mission.
   @param filename El nombre del archivo de texto que se va a leer.
   @return Una lista de Mission leídas del archivo de texto.
   @throws IOException Si ocurre un error de entrada/salida al leer el archivo.
   */
  public List<Mission> readMissionFile(String filename) throws IOException {
    String mission_name;
    String mission_type;
    String mission_launch_cost;
    String mission_description;
    String rocket_name;
    Rocket rocket;

    List<Rocket> rocketList = rocketController.listRocket();
    List<Mission> missionList = new ArrayList();

    BufferedReader br = new BufferedReader(new FileReader(filename));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      rocket_name = str.nextToken();
      mission_name = str.nextToken();
      mission_type = str.nextToken();
      mission_launch_cost = str.nextToken();
      mission_description = str.nextToken();
      rocket = null;
      for (Rocket r : rocketList) {
        if (r.getRocket_name().equals(rocket_name)) {
          rocket = r;
          Mission mission = new Mission(mission_name, mission_type, mission_launch_cost, mission_description, rocket);
          missionList.add(mission);
          break;
        }
      }
    }
    br.close();
    return missionList;
  }

  /**
   Imprime una lista de Mission dada en la consola.
   @param missionList La lista de Mission que se va a imprimir.
   */
  public void printMission(List<Mission> missionList) {
    for (int i = 0; i < missionList.size(); i++) {
      System.out.println((i+1) + " " + missionList.get(i).toString());
    }
  }

  /**
   Agrega una Mission a la base de datos.
   @param mission objeto de la clase Mission que representa la agencia a Mission
   */
  public void addMission(Mission mission) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(mission);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Retorna una lista con todas las Mission almacenadas en la base de datos.
   @return lista de objetos de la clase Mission que representan todas las Mission almacenadas
   */
  public List<Mission> listMission() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Mission> result = em.createQuery("from Mission", Mission.class).getResultList();
    em.getTransaction().commit();
    em.close();

    return result;
  }

  /**
   Actualiza una Mission ya almacenada en la base de datos.
   @param mission objeto de la clase Mission que representa la Mission a actualizar
   */
  public void updateMission(Mission mission) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(mission);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Elimina una Mission de la base de datos.
   @param location_name el nombre de la Mission a eliminar
   */
  public void deleteMission(String location_name) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Location location = (Location) em.find(Location.class, location_name);
    em.remove(location);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Lee un archivo de texto que contiene información de Mission y las agrega a la base de datos.
   @throws IOException si ocurre un error al leer el archivo
   */
  public void insertMissionList() throws IOException {
    List<Mission> missionList = readMissionFile("src/main/resources/mission.txt");
    for (Mission mission : missionList){
      addMission(mission);
    }
  }

  /**
   Busca todas las Mission que contengan un texto dado en alguno de sus atributos y las retorna en una lista.
   @param searchText el texto a buscar en los atributos de las Mission
   @return lista de objetos de la clase Mission que representan las Mission encontradas
   */
  public List<Mission> searchMission(String searchText) {
    try {
      EntityManager em = entityManagerFactory.createEntityManager();
      em.getTransaction().begin();
      TypedQuery<Mission> query = em.createQuery("FROM Mission m WHERE m.mission_name LIKE :searchText OR mission_type LIKE :searchText OR mission_launch_cost LIKE :searchText OR mission_description LIKE :searchText OR rocket_name LIKE :searchText", Mission.class);
      query.setParameter("searchText", "%" + searchText + "%");
      List<Mission> results = query.getResultList();
      em.getTransaction().commit();
      em.close();
      return results;
    } catch (Exception e){}
    return null;
  }
}