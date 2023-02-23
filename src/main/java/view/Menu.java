package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    private int option;

    public Menu() {
        super();
    }

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

            System.out.print("Elige una opción: ");
            try {
                option = Integer.parseInt(br.readLine());
                if(option >= 0 && option <= 4) {
                    validOption = true;
                } else {
                    System.out.println("ERROR - Opción no válida, por favor selecciona una opción entre 0 y 4");
                }
            } catch (Exception e) {
                System.out.println("ERROR - Opción no válida, por favor introduce un número entero");
            }

        } while (!validOption);
        return option;
    }
}