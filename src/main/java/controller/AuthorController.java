package controller;

import model.Author;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.hibernate.SessionFactory;

public class AuthorController {

  private Connection connection;
  private SessionFactory sessionFactory;

  public AuthorController(Connection connection, SessionFactory sessionFactory) {
    this.connection = connection;
    this.sessionFactory = sessionFactory;
  }

  public ArrayList<Author> readAuthorsFile(String filename) throws IOException {
    int id;
    String name, year, country;
    boolean active;
    ArrayList<Author> authorsList = new ArrayList<Author>();

    BufferedReader br = new BufferedReader(new FileReader(filename));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      id = Integer.parseInt(str.nextToken());
      name = str.nextToken();
      year = str.nextToken();
      country = str.nextToken();
      active = Boolean.parseBoolean(str.nextToken());
      // System.out.println(id + name + country + year + active);
      authorsList.add(new Author(id, name, country, year, active));

    }
    br.close();

    return authorsList;
  }

  public void printAutors(ArrayList<Author> authorsList) {
    for (int i = 0; i < authorsList.size(); i++) {
      System.out.println(authorsList.get(i).toString());
    }
  }


  /* Method to CREATE an Autor in the database */
  public void addAuthor(Author author) {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    Integer AuthorID = null;
    try {
      tx = session.beginTransaction();
      AuthorID = (Integer) session.save(author);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }

  }


}
