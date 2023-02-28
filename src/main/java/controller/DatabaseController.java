package controller;

import database.ConnectionFactory;
import model.Agency;

import javax.persistence.EntityManagerFactory;
import javax.xml.stream.Location;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;
    private AgencyController agencyController;
    private LaunchController launchController;
    private LocationController locationController;
    private MissionController missionController;
    private RocketController rocketController;

    public DatabaseController(Connection connection, EntityManagerFactory entityManagerFactory, AgencyController agencyController, LaunchController launchController, LocationController locationController, MissionController missionController, RocketController rocketController) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
        this.agencyController =  agencyController;
        this.launchController =  launchController;
        this.locationController =  locationController;
        this.missionController =  missionController;
        this.rocketController =  rocketController;
    }

    /**
     * Elimina todas las tablas en una base de datos relacional.
     */
    public void deleteAllTables() throws SQLException {
        Statement st = connection.createStatement();
        try {
            st.execute("DROP TABLE location CASCADE;" +
                    "DROP TABLE launch;" +
                    "DROP TABLE agency CASCADE;" +
                    "DROP TABLE mission;" +
                    "DROP TABLE rocket;");
            System.out.println("INFO - Tablas eliminadas.");
        } catch (SQLException e) {
            System.out.println("INFO - Las tablas ya estaban eliminadas.");
        }
    }

    /**
     * Crea todas las tablas en la base de datos.
     * @throws IOException si hay un error al leer el archivo "src/schema.sql".
     * @throws SQLException si hay un error al ejecutar la sentencia SQL en la base de datos.
     */
    public void createAllTables() throws IOException, SQLException {
        Statement st = connection.createStatement();
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/schema.sql"))) {
            st.execute(br.lines().collect(Collectors.joining(" \n")));
            System.out.println("INFO - Tablas creadas.");
        } catch (Exception e){
            System.out.println("INFO - Las tablas ya estaban creadas.");
        }
    }

    /**
     * Inserta todos los registros de los csv en sus respectivas tablas de la base de datos.
     *
     * @throws SQLException Si ocurre algún error en la ejecución de la consulta SQL.
     */
    public static void insertAllData() throws SQLException {
        try {

            System.out.println("INFO - Base de datos rellenada");
        } catch (Exception e){
            System.out.println("INFO - No se ha podido rellenar la base de datos.");
        }
    }

}