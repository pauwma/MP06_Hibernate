package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Magazine implements Serializable {
    private int magazineId;
    private String title;
    private Date publicationDate;

    private List<Article> articlesList = new ArrayList<Article> ();


    public Magazine(int magazineId, String title, Date publicationDate) {
        super();
        this.title = title;
        this.publicationDate = publicationDate;
        this.magazineId = magazineId;
    }
    public Magazine() {
        super();
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
    public int getMagazineId() {
        return magazineId;
    }
    public void setMagazineId(int magazineId) {
        this.magazineId = magazineId;
    }
    public void addArticle(Article art){
        articlesList.add(art);
    }
    public Article getArticle(int i){

        return articlesList.get(i);

    }
    public List<Article> getArticlesList() {
        return articlesList;
    }
    public void setArticlesList(List<Article> articlesList) {
        this.articlesList = articlesList;
    }


    @Override
    public String toString() {
        String result = "Revista [id_revista=" + magazineId +",titol=" + title + ", data_publicacio="
                + publicationDate.toString() + "]";

        result += "\n Llista d'articles: [ \n";

        for (Article a : articlesList) {
            result += "\t";
            result += a.toString();
            result += "\n";
        }

        result += "] \n";

        return result;
    }

}
