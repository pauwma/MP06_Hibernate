package view;

import controller.DatabaseController;
import controller.DeleteController;
import controller.SelectController;
import controller.UpdateController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    private int option;
    private boolean close;


    public Menu() {
        super();
    }

    /**
     * Muestra el menú principal de la aplicación.
     *
     * @return La opción elegida por el usuario.
     */
    public int mainMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean validOption = false;
        do {
            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃       MENU PRINCIPAL      ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃  1 -  Base de datos  - 1  ┃");
            System.out.println("┃  2 -     Selects     - 2  ┃");
            System.out.println("┃  3 -     Updates     - 3  ┃");
            System.out.println("┃  4 -     Deletes     - 4  ┃");
            System.out.println("┃  0 -      Salir      - 0  ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            try {
                option = scannerInt("Elige una opción: ",0,4);
                validOption = true;
            } catch (Exception e) {
                System.out.println("ERROR - Opción no válida, por favor introduce un número entero");
            }

        } while (!validOption);
        return option;
    }

    /**
     * Muestra el menú del control de la base de datos y permite elegir una opción.
     *
     * @return La opción elegida por el usuario.
     */
    public int menuDatabase(DatabaseController databaseController) throws SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int option = 0;
        boolean cerrarSubmenu = false;
        while (!cerrarSubmenu) {
            System.out.println("\n┌───────────────────────────┐");
            System.out.println("│       MENU DATABASE       │");
            System.out.println("├───────────────────────────┤");
            System.out.println("│  1 -      Crear      - 1  │");
            System.out.println("│  2 -      Borrar     - 2  │");
            System.out.println("│  3 -     Rellenar    - 3  │");
            System.out.println("│  0 -      Cerrar     - 0  │");
            System.out.println("└───────────────────────────┘");
            try {
                option = scannerInt("Elige una opción: ",0,3);
                if (option >= 0 && option <= 3) {
                    switch (option){
                        case 1:
                            databaseController.createAllTables();
                            break;
                        case 2:
                            databaseController.deleteAllTables();
                            break;
                        case 3:
                            databaseController.insertAllData();
                            break;
                        case 0:
                            cerrarSubmenu = true;
                            break;
                    }
                } else {
                    System.out.println("Opción inválida, por favor ingrese una opción válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error en el formato, por favor ingrese una opción válida.");
            } catch (IOException e) {
                System.out.println("Error de entrada/salida.");
            }
        }
        return option;
    }

    /**
     * Muestra el menú del control de las funciones de selects y permite elegir una opción.
     *
     * @return La opción elegida por el usuario.
     */
    public int menuSelects(SelectController selectController) throws SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int option = 0;
        boolean cerrarSubmenu = false;
        while (!cerrarSubmenu) {
            System.out.println("\n┌───────────────────────────┐");
            System.out.println("│       MENU  SELECTS       │");
            System.out.println("├───────────────────────────┤");
            System.out.println("│  1 -      Tablas     - 1  │");
            System.out.println("│  2 -    Por texto    - 2  │");
            System.out.println("│  3 -   Lanzamiento   - 3  │");
            System.out.println("│  0 -      Cerrar     - 0  │");
            System.out.println("└───────────────────────────┘");
            try {
                option = scannerInt("Elige una opción: ",0,3);
                if (option >= 0 && option <= 3) {
                    switch (option){
                        case 1:
                            selectController.selectMain();
                            break;
                        case 2:
                            selectController.selectText();
                            break;
                        case 3:
                            break;
                        case 0:
                            cerrarSubmenu = true;
                            break;
                    }
                } else {
                    System.out.println("Opción inválida, por favor ingrese una opción válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error en el formato, por favor ingrese una opción válida.");
            }
        }
        return option;
    }

    /**
     * Muestra el menú del control de las funciones de updates y permite elegir una opción.
     *
     * @return La opción elegida por el usuario.
     */
    public int menuUpdates(UpdateController updateController) throws SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int option = 0;
        boolean cerrarSubmenu = false;
        while (!cerrarSubmenu) {

            System.out.println("\n┌───────────────────────────┐");
            System.out.println("│       MENU  UPDATES       │");
            System.out.println("├───────────────────────────┤");
            System.out.println("│  1 -      Tablas     - 1  │");
            System.out.println("│  2 -   Condiciones   - 2  │");
            System.out.println("│  0 -      Cerrar     - 0  │");
            System.out.println("└───────────────────────────┘");
            try {
                option = scannerInt("Elige una opción: ",0,2);
                if (option >= 0 && option <= 2) {
                    switch (option){
                        case 1:
                            updateController.updateMain();
                            break;
                        case 2:
                            updateController.updateCondition();
                            break;
                        case 0:
                            cerrarSubmenu = true;
                            break;
                    }
                } else {
                    System.out.println("Opción inválida, por favor ingrese una opción válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error en el formato, por favor ingrese una opción válida.");
            }
        }
        return option;
    }

    /**
     * Muestra el menú del control de las funciones de deletes y permite elegir una opción.
     *
     * @return La opción elegida por el usuario.
     */
    public int menuDeletes(DeleteController deleteController) throws SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int option = 0;
        boolean cerrarSubmenu = false;
        while (!cerrarSubmenu) {

            System.out.println("\n┌───────────────────────────┐");
            System.out.println("│       MENU  DELETES       │");
            System.out.println("├───────────────────────────┤");
            System.out.println("│  1 -      Tablas     - 1  │");
            System.out.println("│  2 -   Condiciones   - 2  │");
            System.out.println("│  0 -      Cerrar     - 0  │");
            System.out.println("└───────────────────────────┘");
            try {
                option = scannerInt("Elige una opción: ",0,2);
                if (option >= 0 && option <= 2) {
                    switch (option){
                        case 1:
                            deleteController.deleteMain();
                            break;
                        case 2:
                            deleteController.deleteCondition();
                            break;
                        case 0:
                            cerrarSubmenu = true;
                            break;
                    }
                } else {
                    System.out.println("Opción inválida, por favor ingrese una opción válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error en el formato, por favor ingrese una opción válida.");
            }
        }
        return option;
    }

    /**
     * Función para cerrar el menú principal
     */
    public void close(){
        close = true;
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