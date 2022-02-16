package controller;

import model.Magazine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class MagazineController {

    private Connection connection;

    public MagazineController(Connection connection) {
        this.connection = connection;
    }

    /**
     * @param filename Aquest String correspon amb l'arxiu on s'emmagatzemen les
     *                 dades de les instancies de Revista
     * @throws IOException <dt><b>Preconditions:</b>
     *                     <dd>
     *                     filename<>nil </br> llistaRevistes == nil
     *                     <dt><b>Postconditions:</b>
     *                     <dd>
     *                     llistaRevistes<>nil
     */

    public ArrayList<Magazine> readMagazinesFile(String filename) throws IOException {
        int magazineId;
        String title;
        Date publicationDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        ArrayList<Magazine> magazinesList = new ArrayList();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String linea = "";
        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            magazineId = Integer.parseInt(str.nextToken());
            title = str.nextToken();

            try {
                publicationDate = dateFormat.parse(str.nextToken());
                magazinesList.add(new Magazine(magazineId, title, publicationDate));

            } catch (ParseException e) {
                System.err.println("Errada format data al fitxer");
                e.printStackTrace();
            }

        }
        br.close();
        return magazinesList;
    }

    public void printMagazines(ArrayList<Magazine> magazinesList) {
        for (int i = 0; i < magazinesList.size(); i++) {
            System.out.println(magazinesList.get(i).toString());
        }
    }


}
