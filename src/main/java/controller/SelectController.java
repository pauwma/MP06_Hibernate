package controller;

import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.util.Scanner;

public class SelectController {

    private Connection connection;
    private EntityManagerFactory entityManagerFactory;
    private AgencyController agencyController;
    private LaunchController launchController;
    private LocationController locationController;
    private MissionController missionController;
    private RocketController rocketController;

    public SelectController(Connection connection, EntityManagerFactory entityManagerFactory, AgencyController agencyController, LaunchController launchController, LocationController locationController, MissionController missionController, RocketController rocketController) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
        this.agencyController =  agencyController;
        this.launchController =  launchController;
        this.locationController =  locationController;
        this.missionController =  missionController;
        this.rocketController =  rocketController;
    }

    public void selectMain() {
        Scanner scanner = new Scanner(System.in);
        int option = scannerInt("Ingrese en que tabla quiere buscar (1: launch, 2: rocket, 3: agency, 4: location, 5: mission): ",1,5);

        try {
            switch (option) {
                case 1:
                    agencyController.listAgency();
                    break;
                case 2:
                    launchController.listLaunch();
                    break;
                case 3:
                    locationController.listLocation();
                    break;
                case 4:
                    missionController.listMission();
                    break;
                case 5:
                    rocketController.listRocket();
                    break;
                default:
                    System.out.println("ERROR - Opción no válida.");
            }
        } catch (Exception e){
            System.out.println("ERROR - No se ha podido listar la tabla.");
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
