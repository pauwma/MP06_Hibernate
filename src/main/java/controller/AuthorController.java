package controller;

import model.Author;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AuthorController {

    private Connection connection;

    public AuthorController(Connection connection) {
        this.connection = connection;
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


}
