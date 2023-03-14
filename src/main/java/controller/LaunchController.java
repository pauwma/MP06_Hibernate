package controller;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 Esta clase es el controlador para la entidad de Launch en el sistema.
 Maneja la lectura, adición, actualización y eliminación de Launchs.
 También proporciona funciones de búsqueda y listado de todos los Launch.
 */
public class LaunchController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;
  private AgencyController agencyController;
  private LocationController locationController;
  private MissionController missionController;
  private RocketController rocketController;

  /**
   Constructor de la clase.
   Crea un nuevo objeto de controlador de Launch con una conexión dada.
   @param connection La conexión a la base de datos que se utilizará.
   */
  public LaunchController(Connection connection) {
    this.connection = connection;
  }

  /**
   Constructor de la clase.
   Crea un nuevo objeto de controlador de Launch con una conexión y un EntityManagerFactory dados.
   @param connection La conexión a la base de datos que se utilizará.
   @param entityManagerFactory El EntityManagerFactory que se utilizará para crear EntityManagers.
   */
  public LaunchController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
    this.agencyController = new AgencyController(connection, entityManagerFactory);
    this.locationController = new LocationController(connection, entityManagerFactory);
    this.missionController = new MissionController(connection, entityManagerFactory);
    this.rocketController = new RocketController(connection, entityManagerFactory);

  }

  /**
   Lee un archivo de texto que contiene datos de Laucnh y devuelve una lista de Launch.
   @param filename El nombre del archivo de texto que se va a leer.
   @return Una lista de Launch leídas del archivo de texto.
   @throws IOException Si ocurre un error de entrada/salida al leer el archivo.
   */
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

  /**
   Imprime una lista de Launch dada en la consola.
   @param launchList La lista de Launch que se va a imprimir.
   */
  public void printLaunch(List<Launch> launchList) {
    for (int i = 0; i < launchList.size(); i++) {
      System.out.println((i+1)+ " " +launchList.get(i).toString());
    }
  }

  /**
   Agrega un Launch a la base de datos.
   @param launch objeto de la clase Launch que representa el Launch a agregar
   */
  public void addLaunch(Launch launch) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(launch);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Retorna una lista con todos los Launch almacenados en la base de datos.
   @return lista de objetos de la clase Launch que representan todos los Launch almacenados
   */
  public List<Launch> listLaunch() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Launch> result = em.createQuery("from Launch", Launch.class).getResultList();
    em.getTransaction().commit();
    em.close();
    return result;
  }

  /**
   Actualiza un Launch ya almacenada en la base de datos.
   @param launch objeto de la clase Launch que representa el Launch a actualizar
   */
  public void updateLaunch(Launch launch) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(launch);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Elimina un Launch de la base de datos.
   @param launch_title el nombre del Launch a eliminar
   */
  public void deleteLaunch(String launch_title) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Launch launch = (Launch) em.find(Launch.class, launch_title);
    em.remove(launch);
    em.getTransaction().commit();
    em.close();
  }

  /**
   Lee un archivo de texto que contiene información de Launch y los agrega a la base de datos.
   @throws IOException si ocurre un error al leer el archivo
   */
  public void insertLaunchList() throws IOException {
      List<Launch> launchList = readLaunchFile("src/main/resources/launch.txt");
      for (Launch launch : launchList){
        addLaunch(launch);
      }
    }

  /**
   Busca todos los Launch que contengan un texto dado en alguno de sus atributos y las retorna en una lista.
   @param searchText el texto a buscar en los atributos de los Launch
   @return lista de objetos de la clase Launch que representan los Launch encontrados
   */
  public List<Launch> searchLaunch(String searchText) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    TypedQuery<Launch> query = em.createQuery("FROM Launch l WHERE l.rocket.rocket_name LIKE :searchText OR l.agency.agency_name LIKE :searchText OR l.location.location_name LIKE :searchText OR l.mission.mission_name LIKE :searchText", Launch.class);
    query.setParameter("searchText", "%" + searchText + "%");
    List<Launch> results = query.getResultList();
    em.getTransaction().commit();
    em.close();
    return results;
  }
}