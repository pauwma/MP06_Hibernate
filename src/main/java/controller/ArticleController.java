package controller;

import model.Article;
import model.Author;
import model.Magazine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ArticleController {

    private Connection connection;
    private MagazineController magazineController = new MagazineController(connection);
    private AuthorController authorController = new AuthorController(connection);

    public ArticleController(Connection connection) {
        this.connection = connection;
    }


    /**
     * @param articlesFile Aquest String correspon amb l'arxiu on s'emmagatzemen les
     *                     dades de les isntancies de Revista
     * @return ArrayList d'objectes Revista, amb els seus articles i la
     * informació de l'autor
     * @throws IOException <dt><b>Preconditions:</b>
     *                     <dd>
     *                     filename<>nil </br> llistaRevistes<>nil </br>
     *                     llistaRevistes.getRevista(i).getArticles()== nil</br>
     *                     <dt><b>Postconditions:</b>
     *                     <dd>
     *                     llistaRevistes.getRevistes()<>nil</br>
     *                     llistaRevistes.getRevista(i).getArticles()<>nil</br>
     *                     llistaRevistes.getRevista(i).getArticle(j)<>nil</br>
     *                     llistaRevistes
     *                     .getRevista(i).getArticle(j).getAutor()<>nil</br>
     */
    public ArrayList<Magazine> readArticlesFile(String articlesFile, String magazinesFile, String authorsFile)
            throws IOException {
        int articleId, magazineId, authorId;
        String title;
        Date creationDate;
        boolean publishable;
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

        BufferedReader br = new BufferedReader(new FileReader(articlesFile));
        String linea = "";

        ArrayList<Magazine> magazinesList = magazineController.readMagazinesFile(magazinesFile);
        ArrayList<Author> authorList = authorController.readAuthorsFile(authorsFile);

        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            articleId = Integer.parseInt(str.nextToken());
            magazineId = Integer.parseInt(str.nextToken());
            authorId = Integer.parseInt(str.nextToken());
            title = str.nextToken();

            try {
                creationDate = dateFormat.parse(str.nextToken());
                publishable = Boolean.parseBoolean(str.nextToken());
                // System.out.println(idArticle + ", idrev" + idRevista + ", idaut" + idAutor + ", titol " + titol 	+ data_creacio);
                magazinesList.get(magazineId - 1).addArticle(new Article(articleId, title, creationDate, publishable, authorList.get(authorId - 1)));
                /**
                 System.out.println(llistaRevistes.get(idRevista-1).toString());
                 for (int i = 0; i < llistaRevistes.get(idRevista-1).getArticles()
                 .size(); i++) {

                 System.out.println(llistaRevistes.get(idRevista-1)
                 .getArticle(i).toString());
                 System.out.println(llistaRevistes.get(idRevista-1).getArticle(i).getAutor().toString());

                 }
                 **/
                // llistaAutors.add(new Autor(id, name, country, year, active));
            } catch (ParseException e) {

                e.printStackTrace();
            }

        }
        br.close();


        return magazinesList;
        // TODO
    }


}
