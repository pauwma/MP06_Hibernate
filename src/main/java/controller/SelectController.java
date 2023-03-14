package controller;

import model.Launch;

import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class SelectController {

    private Connection connection;
    private EntityManagerFactory entityManagerFactory;
    private AgencyController agencyController;
    private LaunchController launchController;
    private LocationController locationController;
    private MissionController missionController;
    private RocketController rocketController;

    /**
     * Constructor de la clase SelectController.
     *
     * @param connection Conexión a la base de datos.
     * @param entityManagerFactory Fabrica de EntityManager para realizar operaciones con la base de datos.
     * @param agencyController Controlador para realizar operaciones con la tabla de agencias.
     * @param launchController Controlador para realizar operaciones con la tabla de lanzamientos.
     * @param locationController Controlador para realizar operaciones con la tabla de ubicaciones.
     * @param missionController Controlador para realizar operaciones con la tabla de misiones.
     * @param rocketController Controlador para realizar operaciones con la tabla de cohetes.
     */
    public SelectController(Connection connection, EntityManagerFactory entityManagerFactory, AgencyController agencyController, LaunchController launchController, LocationController locationController, MissionController missionController, RocketController rocketController) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
        this.agencyController =  agencyController;
        this.launchController =  launchController;
        this.locationController =  locationController;
        this.missionController =  missionController;
        this.rocketController =  rocketController;
    }

    /**
     * Método principal para listar registros de la base de datos.
     */
    public void selectMain() {
        int option = scannerInt("Ingrese en que tabla quiere buscar (1: launch, 2: rocket, 3: agency, 4: location, 5: mission): ",1,5);

        try {
            switch (option) {
                case 1:
                    launchController.printLaunch(launchController.listLaunch());
                    break;
                case 2:
                    rocketController.printRocket(rocketController.listRocket());
                    break;
                case 3:
                    agencyController.printAgency(agencyController.listAgency());
                    break;
                case 4:
                    locationController.printLocation(locationController.listLocation());
                    break;
                case 5:
                    missionController.printMission(missionController.listMission());
                    break;
                default:
                    System.out.println("ERROR - Opción no válida.");
            }
        } catch (Exception e){
            System.out.println("ERROR - No se ha podido listar la tabla.");
        }
    }

    /**
     Esta función permite seleccionar una tabla de la base de datos y buscar un texto en ella.
     La función solicita al usuario que ingrese el número de la tabla que desea buscar y el texto que desea buscar.
     Luego, realiza una búsqueda en la tabla seleccionada utilizando el controlador correspondiente.
     Si se encuentra un resultado, se imprime en la consola utilizando el método "print" del controlador correspondiente.
     Si no se encuentra ningún resultado, se imprime un mensaje de error.

     @throws Exception Si no se encuentra ningún resultado.
     */
    public void selectText(){
        // Solicita al usuario que ingrese el número de la tabla que desea buscar
        int option = scannerInt("Ingrese en que tabla quiere buscar (1: launch, 2: rocket, 3: agency, 4: location, 5: mission): ",1,5);
        // Solicita al usuario que ingrese el texto que desea buscar
        String searchText = scannerString("Ingrese el texto de búsqueda: ");
        try {
            // Realiza una búsqueda en la tabla seleccionada utilizando el controlador correspondiente.
            switch (option) {
                case 1:
                    launchController.printLaunch(launchController.searchLaunch(searchText));
                    break;
                case 2:
                    rocketController.printRocket(rocketController.searchRocket(searchText));
                    break;
                case 3:
                    agencyController.printAgency(agencyController.searchAgency(searchText));
                    break;
                case 4:
                    locationController.printLocation(locationController.searchLocation(searchText));
                    break;
                case 5:
                    missionController.printMission(missionController.searchMission(searchText));
                    break;
                default:
                    System.out.println("ERROR - Opción no válida.");
            }
        } catch (Exception e){
            // Si no se encuentra ningún resultado, se imprime un mensaje de error.
            System.out.println("ERROR - No se ha encontado nada con \"" + searchText + "\".");
        }
    }

    /**
     Esta función permite seleccionar una tabla de la base de datos y buscar un texto en ella.

     @param searchText Texto el cual buscar en la tabla.
     @throws Exception Si no se encuentra ningún resultado.
     */
    public List<Launch> selectTextParam(String searchText){
        try {
            return launchController.searchLaunch(searchText);
        } catch (Exception e){
            // Si no se encuentra ningún resultado, se imprime un mensaje de error.
            System.out.println("ERROR - No se ha encontado nada con \"" + searchText + "\".");
            return null;
        }
    }

    /**
     * Método para preguntar al usuario por un String con excepciones.
     *
     * @param pregunta Pregunta para mostrar por pantalla.
     */
    public static String scannerString(String pregunta) {
        Scanner sc = new Scanner(System.in);
        String userInput = "";
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print(pregunta);
                userInput = sc.nextLine();
                if (userInput.trim().isEmpty()){
                    System.out.println("No puedes introducir una cadena vacía.");
                    isValid = false;
                } else {
                    isValid = true;
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error al leer la entrada. Por favor, inténtalo de nuevo.");
                sc.next();
            }
        }

        return userInput;
    }

    /**
     * Método para preguntar al usuario por un Integer con excepciones.
     * @param pregunta Pregunta para mostrar por pantalla.
     * @param min Número mínimo.
     * @param max Número máximo.
     */
    public static int scannerInt(String pregunta, int min, int max) {
        Scanner sc = new Scanner(System.in);
        int userInput = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print(pregunta);
                userInput = sc.nextInt();
                if (userInput >= min && userInput <= max) {
                    isValid = true;
                } else {
                    System.out.println("El número debe estar entre " + min + " y " + max + ".");
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error al leer la entrada. Por favor, inténtalo de nuevo con un número entero.");
                sc.next();
            }
        }
        return userInput;
    }

}