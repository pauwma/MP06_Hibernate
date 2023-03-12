import java.sql.Connection;
import java.sql.SQLException;
import controller.*;
import database.ConnectionFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

  static SessionFactory sessionFactoryObj;

  private static SessionFactory buildSessionFactory() {
    try {
      StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
          .configure("hibernate.cfg.xml").build();
      Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
      return metadata.getSessionFactoryBuilder().build();

    } catch (HibernateException he) {
      System.out.println("Session Factory creation failure");
      throw he;
    }
  }

  public static EntityManagerFactory createEntityManagerFactory(){
    EntityManagerFactory emf;
    try {
      emf = Persistence.createEntityManagerFactory("JPAMagazines");
    } catch (Throwable ex) {
      System.err.println("Failed to create EntityManagerFactory object."+ ex);
      throw new ExceptionInInitializerError(ex);
    }
    return emf;
  }

  public static void main(String[] args) throws SQLException {
    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    Connection c = connectionFactory.connect();

    EntityManagerFactory entityManagerFactory = createEntityManagerFactory();

    AgencyController agencyController = new AgencyController(c, entityManagerFactory);
    LaunchController launchController = new LaunchController(c, entityManagerFactory);
    LocationController locationController = new LocationController(c, entityManagerFactory);
    MissionController missionController = new MissionController(c, entityManagerFactory);
    RocketController rocketController = new RocketController(c, entityManagerFactory);
    DatabaseController databaseController = new DatabaseController(c, entityManagerFactory, agencyController, launchController, locationController, missionController, rocketController);
    SelectController selectController = new SelectController(c, entityManagerFactory, agencyController, launchController, locationController, missionController, rocketController);
    DeleteController deleteController = new DeleteController(c, entityManagerFactory, selectController ,agencyController, launchController, locationController, missionController, rocketController);

    Menu menu = new Menu();
    int option;
    option = menu.mainMenu();

    while (option > 0 && option <= 4) {
      switch (option) {
        case 1:
          menu.menuDatabase(databaseController);
          break;
        case 2:
          menu.menuSelects(selectController);
          break;
        case 3:
          menu.menuUpdates();
          break;
        case 4:
          menu.menuDeletes(deleteController);
          break;
        case 0:
          System.out.println("Adiós... \uD83D\uDE80");
          menu.close();
          break;
      }
      option = menu.mainMenu();
    }
  }
}