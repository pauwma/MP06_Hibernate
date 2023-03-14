package controller;

import model.*;

import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class UpdateController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;
    private SelectController selectController;
    private AgencyController agencyController;
    private LaunchController launchController;
    private LocationController locationController;
    private MissionController missionController;
    private RocketController rocketController;

    /**
     * Constructor de la clase DeleteController.
     *
     * @param connection Conexión a la base de datos.
     * @param entityManagerFactory Fabrica de EntityManager para realizar operaciones con la base de datos.
     * @param selectController Controlador para listar registros de la base de datos.
     * @param agencyController Controlador para realizar operaciones con la tabla de agencias.
     * @param launchController Controlador para realizar operaciones con la tabla de lanzamientos.
     * @param locationController Controlador para realizar operaciones con la tabla de ubicaciones.
     * @param missionController Controlador para realizar operaciones con la tabla de misiones.
     * @param rocketController Controlador para realizar operaciones con la tabla de cohetes.
     */
    public UpdateController(Connection connection, EntityManagerFactory entityManagerFactory, SelectController selectController, AgencyController agencyController, LaunchController launchController, LocationController locationController, MissionController missionController, RocketController rocketController) {
        this.connection = connection;
        this.selectController = selectController;
        this.entityManagerFactory = entityManagerFactory;
        this.agencyController =  agencyController;
        this.launchController =  launchController;
        this.locationController =  locationController;
        this.missionController =  missionController;
        this.rocketController =  rocketController;
    }

    /**
     Actualiza las condiciones de lanzamiento seleccionadas por el usuario a través de una serie de opciones de menú.
     El usuario introduce el tipo de condición (cohete, agencia, ubicación o misión) y luego selecciona una opción de una lista
     de elementos correspondientes al tipo de condición. Luego, se le pide al usuario que seleccione un elemento para actualizar a partir de una lista
     y se actualiza cada lanzamiento asociado con el elemento seleccionado en la base de datos con el nuevo elemento seleccionado.
     Si el usuario introduce una opción inválida, se muestra un mensaje de error.
     @throws Exception si no se puede editar la información de lanzamiento.
     */
    public void updateCondition(){
        Scanner scanner = new Scanner(System.in);
        int option = scannerInt("Introduce el tipo de condición (1: rocket, 2: agency, 3: location, 4: mission): ",0,4);

        try {
            switch (option) {
                case 1:
                    List<Rocket> rocketList = rocketController.listRocket();
                    rocketController.printRocket(rocketList);
                    int rocketOption = scannerInt("\nEditar lanzamientos con cohete: (1-" + rocketList.size() + "): ", 1, rocketList.size());
                    Rocket rocketToUpdate = rocketList.get(rocketOption-1);
                    int rocketOption2 = scannerInt("Cambiar a cohete: (1-" + rocketList.size() + "): ", 1, rocketList.size());
                    Rocket rocketUpdated = rocketList.get(rocketOption2-1);
                    List<Launch> launchesToEditRocket = selectController.selectTextParam(rocketToUpdate.getRocket_name());
                    for (Launch l : launchesToEditRocket){
                        l.setRocket(rocketUpdated);
                        launchController.updateLaunch(l);
                    }
                    break;
                case 2:
                    List<Agency> agencyList = agencyController.listAgency();
                    agencyController.printAgency(agencyList);
                    int agencyOption = scannerInt("\nEditar lanzamientos con agencia: (1-" + agencyList.size() + "): ", 1, agencyList.size());
                    Agency agencyToUpdate = agencyList.get(agencyOption-1);
                    int agencyOption2 = scannerInt("Cambiar a agencia: (1-" + agencyList.size() + "): ", 1, agencyList.size());
                    Agency agencyUpdated = agencyList.get(agencyOption2-1);
                    List<Launch> launchesToEditAgency = selectController.selectTextParam(agencyToUpdate.getAgency_name());
                    for (Launch l : launchesToEditAgency){
                        l.setAgency(agencyUpdated);
                        launchController.updateLaunch(l);
                    }
                    break;
                case 3:
                    List<Location> locationList = locationController.listLocation();
                    locationController.printLocation(locationList);
                    int locationOption = scannerInt("\nEditar lanzamientos con ubicación: (1-" + locationList.size() + "): ", 1, locationList.size());
                    Location locationToUpdate = locationList.get(locationOption-1);
                    int locationOption2 = scannerInt("Cambiar ubicación a: (1-" + locationList.size() + "): ", 1, locationList.size());
                    Location locationUpdated = locationList.get(locationOption2-1);
                    List<Launch> launchesToEditLocation = selectController.selectTextParam(locationToUpdate.getLocationName());
                    for (Launch l : launchesToEditLocation){
                        l.setLocation(locationUpdated);
                        launchController.updateLaunch(l);
                    }
                    break;
                case 4:
                    List<Mission> missionList = missionController.listMission();
                    missionController.printMission(missionList);
                    int missionOption = scannerInt("\nEditar lanzamientos con misión: (1-" + missionList.size() + "): ", 1, missionList.size());
                    Mission missionToUpdate = missionList.get(missionOption-1);
                    int missionOption2 = scannerInt("Cambiar misión a: (1-" + missionList.size() + "): ", 1, missionList.size());
                    Mission missionUpdated = missionList.get(missionOption2-1);
                    List<Launch> launchesToEditMission = selectController.selectTextParam(missionToUpdate.getMission_name());
                    for (Launch l : launchesToEditMission){
                        l.setMission(missionUpdated);
                        launchController.updateLaunch(l);
                    }
                    break;
                case 0:
                    System.out.println("INFO - No se ha editado nada.");
                    break;
                default:
                    System.out.println("ERROR - Opción no válida.");
            }
        } catch (Exception e){
            System.out.println("ERROR - No se ha podido editar.");
        }
    }

    /**
     * Método principal para editar registros de la base de datos.
     */
    public void updateMain() {
        Scanner scanner = new Scanner(System.in);
        int option = scannerInt("Ingrese en qué tabla desea eliminar (1: launch, 2: rocket, 3: agency, 4: location, 5: mission): ",0,5);

        try {
            switch (option) {
                case 1:
                    List<Launch> launchList = launchController.listLaunch();
                    launchController.printLaunch(launchList);
                    int launchOption = scannerInt("¿Qué lanzamiento desea editar? (1-" + launchList.size() + "): ", 1, launchList.size());
                    Launch launchToUpdate = launchList.get(launchOption-1);
                    modifyObject(launchToUpdate);
                    launchController.updateLaunch(launchToUpdate);
                    break;
                case 2:
                    List<Rocket> rocketList = rocketController.listRocket();
                    rocketController.printRocket(rocketList);
                    int rocketOption = scannerInt("¿Qué cohete desea editar? (1-" + rocketList.size() + "): ", 1, rocketList.size());
                    Rocket rocketToUpdate = rocketList.get(rocketOption-1);
                    modifyObject(rocketToUpdate);
                    rocketController.updateRocket(rocketToUpdate);
                    break;
                case 3:
                    List<Agency> agencyList = agencyController.listAgency();
                    agencyController.printAgency(agencyList);
                    int agencyOption = scannerInt("¿Qué agencia desea editar? (1-" + agencyList.size() + "): ", 1, agencyList.size());
                    Agency agencyToUpdate = agencyList.get(agencyOption-1);
                    modifyObject(agencyToUpdate);
                    agencyController.updateAgency(agencyToUpdate);
                    break;
                case 4:
                    List<Location> locationList = locationController.listLocation();
                    locationController.printLocation(locationList);
                    int locationOption = scannerInt("¿Qué ubicación desea editar? (1-" + locationList.size() + "): ", 1, locationList.size());
                    Location locationToUpdate = locationList.get(locationOption-1);
                    modifyObject(locationToUpdate);
                    locationController.updateLocation(locationToUpdate);
                    break;
                case 5:
                    List<Mission> missionList = missionController.listMission();
                    missionController.printMission(missionList);
                    int missionOption = scannerInt("¿Qué misión desea editar? (1-" + missionList.size() + "): ", 1, missionList.size());
                    Mission missionToUpdate = missionList.get(missionOption-1);
                    modifyObject(missionToUpdate);
                    missionController.updateMission(missionToUpdate);
                    break;
                case 0:
                    System.out.println("INFO - No se ha editado nada.");
                    break;
                default:
                    System.out.println("ERROR - Opción no válida.");
            }
        } catch (Exception e){
            System.out.println("ERROR - No se ha podido editar.");
        }
    }

    /**
     Modifica un objeto mediante la entrada de datos del usuario utilizando el escáner.
     Se recorren los campos del objeto y, según el tipo de datos, se solicita la entrada de datos al usuario.
     Si el tipo de dato es int, se convierte la entrada de usuario en un int y se asigna al campo.
     Si el tipo de dato es double, se convierte la entrada de usuario en un double y se asigna al campo.
     Si el tipo de dato es String, se solicita la entrada de usuario y se asigna al campo.
     Si el tipo de dato es Location, se muestran las opciones disponibles de ubicaciones mediante el LocationController,
     se solicita al usuario que seleccione una ubicación y se asigna al campo.
     Si el tipo de dato es Agency, se muestran las opciones disponibles de agencias mediante el AgencyController,
     se solicita al usuario que seleccione una agencia y se asigna al campo.
     Si el tipo de dato es Rocket, se muestran las opciones disponibles de cohetes mediante el RocketController,
     se solicita al usuario que seleccione un cohete y se asigna al campo.
     Si el tipo de dato es Mission, se muestran las opciones disponibles de misiones mediante el MissionController,
     se solicita al usuario que seleccione una misión y se asigna al campo.
     Si el tipo de dato no es soportado, se muestra un mensaje indicando que el tipo de dato no es soportado.
     @param obj objeto a modificar.
     */
    public void modifyObject(Object obj) {
        Scanner scanner = new Scanner(System.in);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Field field = fields[i];
            try {
                field.setAccessible(true);
                if (field.getType() == int.class) {
                    System.out.print("Ingrese el valor para " + field.getName() + ": ");
                    String input = scanner.nextLine();
                    field.setInt(obj, Integer.parseInt(input));
                } else if (field.getType() == double.class) {
                    System.out.print("Ingrese el valor para " + field.getName() + ": ");
                    String input = scanner.nextLine();
                    field.setDouble(obj, Double.parseDouble(input));
                } else if (field.getType() == String.class) {
                    System.out.print("Ingrese el valor para " + field.getName() + ": ");
                    String input = scanner.nextLine();
                    field.set(obj, input);
                } else if (field.getType() == Location.class) {
                    List<Location> locationList = locationController.listLocation();
                    locationController.printLocation(locationList);
                    int locationOption = scannerInt("¿Qué ubicación desea asignar al campo " + field.getName() + "? (1-" + locationList.size() + "): ", 1, locationList.size());
                    Location location = locationList.get(locationOption-1);
                    field.set(obj, location);
                    System.out.println("Ubicación asignada: " + location.getLocationName());

                } else if (field.getType() == Agency.class) {
                    List<Agency> agencyList = agencyController.listAgency();
                    agencyController.printAgency(agencyList);
                    int agencyOption = scannerInt("¿Qué agencia desea asignar al campo " + field.getName() + "? (1-" + agencyList.size() + "): ", 1, agencyList.size());
                    Agency agency = agencyList.get(agencyOption-1);
                    field.set(obj, agency);
                    System.out.println("Agencia asignada: " + agency.getAgency_name());
                } else if (field.getType() == Rocket.class) {
                    List<Rocket> rocketList = rocketController.listRocket();
                    rocketController.printRocket(rocketList);
                    int rocketOption = scannerInt("¿Qué cohete desea asignar al campo " + field.getName() + "? (1-" + rocketList.size() + "): ", 1, rocketList.size());
                    Rocket rocket = rocketList.get(rocketOption-1);
                    field.set(obj, rocket);
                    System.out.println("Cohete asignado: " + rocket.getRocket_name());
                } else if (field.getType() == Mission.class) {
                    List<Mission> missionList = missionController.listMission();
                    missionController.printMission(missionList);
                    int missionOption = scannerInt("¿Qué misión desea asignar al campo " + field.getName() + "? (1-" + missionList.size() + "): ", 1, missionList.size());
                    Mission mission = missionList.get(missionOption-1);
                    field.set(obj, mission);
                    System.out.println("Misión asignada: " + mission.getMission_name());
                } else {
                    System.out.println("Tipo de dato no soportado: " + field.getType());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
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