import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import controller.ArticleController;
import controller.AuthorController;
import controller.MagazineController;
import database.ConnectionFactory;
import model.*;
import view.Menu;


public class Main {

  public static void main(String[] args) {
    ArrayList<Magazine> revistes = new ArrayList();

    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    Connection c = connectionFactory.connect();

    ArticleController articleController = new ArticleController(c);
    AuthorController authorController = new AuthorController(c);
    MagazineController magazineController = new MagazineController(c);

    Menu menu = new Menu();
    int opcio;
    opcio = menu.mainMenu();

    switch (opcio) {

      case 1:

        System.out.println("1!!");
        try {

          // authorController.printAutors(authorController.readAuthorsFile("src/main/resources/autors.txt"));
          // magazineController.printMagazines(magazineController.readMagazinesFile("src/main/resources/revistes.txt"));
          magazineController.printMagazines(articleController.readArticlesFile("src/main/resources/articles.txt", "src/main/resources/revistes.txt", "src/main/resources/autors.txt"));

        } catch (NumberFormatException | IOException e) {

          e.printStackTrace();
        }
        break;

      default:
        System.out.println("Adeu!!");
        System.exit(1);
        break;

    }
  }
}
