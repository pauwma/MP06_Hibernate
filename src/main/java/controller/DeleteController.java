package controller;

import model.*;

import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class DeleteController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;
    private SelectController selectController;
    private AgencyController agencyController;
    private LaunchController launchController;
    private LocationController locationController;
    private MissionController missionController;
    private RocketController rocketController;

    public DeleteController(Connection connection, EntityManagerFactory entityManagerFactory, SelectController selectController, AgencyController agencyController, LaunchController launchController, LocationController locationController, MissionController missionController, RocketController rocketController) {
        this.connection = connection;
        this.selectController = selectController;
        this.entityManagerFactory = entityManagerFactory;
        this.agencyController =  agencyController;
        this.launchController =  launchController;
        this.locationController =  locationController;
        this.missionController =  missionController;
        this.rocketController =  rocketController;
    }

    public void deleteMain() {
        Scanner scanner = new Scanner(System.in);
        int option = scannerInt("Ingrese en qué tabla desea eliminar (1: launch, 2: rocket, 3: agency, 4: location, 5: mission): ",1,5);

        try {
            switch (option) {
                case 1:
                    List<Launch> launchList = launchController.listLaunch();
                    launchController.printLaunch(launchList);
                    int launchOption = scannerInt("¿Qué lanzamiento desea eliminar? (1-" + launchList.size() + "): ", 1, launchList.size());
                    Launch launchToDelete = launchList.get(launchOption-1);
                    launchController.deleteLaunch(launchToDelete.getLaunch_title());
                    break;
                case 2:
                    List<Rocket> rocketList = rocketController.listRocket();
                    rocketController.printRocket(rocketList);
                    int rocketOption = scannerInt("¿Qué cohete desea eliminar? (1-" + rocketList.size() + "): ", 1, rocketList.size());
                    Rocket rocketToDelete = rocketList.get(rocketOption-1);
                    rocketController.deleteRocket(rocketToDelete.getRocket_name());
                    break;
                case 3:
                    List<Agency> agencyList = agencyController.listAgency();
                    agencyController.printAgency(agencyList);
                    int agencyOption = scannerInt("¿Qué agencia desea eliminar? (1-" + agencyList.size() + "): ", 1, agencyList.size());
                    Agency agencyToDelete = agencyList.get(agencyOption-1);
                    agencyController.deleteAgency(agencyToDelete.getAgency_name());
                    break;
                case 4:
                    List<Location> locationList = locationController.listLocation();
                    locationController.printLocation(locationList);
                    int locationOption = scannerInt("¿Qué ubicación desea eliminar? (1-" + locationList.size() + "): ", 1, locationList.size());
                    Location locationToDelete = locationList.get(locationOption-1);
                    locationController.deleteLocation(locationToDelete.getLocationName());
                    break;
                case 5:
                    List<Mission> missionList = missionController.listMission();
                    missionController.printMission(missionList);
                    int missionOption = scannerInt("¿Qué misión desea eliminar? (1-" + missionList.size() + "): ", 1, missionList.size());
                    Mission missionToDelete = missionList.get(missionOption-1);
                    missionController.deleteMission(missionToDelete.getMission_name());
                    break;
                default:
                    System.out.println("ERROR - Opción no válida.");
            }
        } catch (Exception e){
            System.out.println("ERROR - No se ha podido eliminar.");
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