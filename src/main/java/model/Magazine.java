package model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Magazine implements Serializable {
    @Id
    private int magazineId;
    private String title;
    private Date publicationDate;
    @OneToMany
    @JoinColumn(name="id_revista", referencedColumnName="id_revista")
    @Cascade(CascadeType.ALL)
    private List<Article> articles = new ArrayList<Article> ();

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
      articles.add(art);
     }
     public Article getArticle(int i){
      return articles.get(i);
     }

    public List<Article> getArticles() {
        return articles;
    }
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }


    @Override
    public String toString() {
        String result = "Revista [id_revista=" + magazineId +",titol=" + title + ", data_publicacio="
                + publicationDate.toString() + "]";

        result += "\n Llista d'articles: [ \n";

        for (Article a : articles) {
            result += "\t";
            result += a.toString();
            result += "\n";
        }

        result += "] \n";

        return result;
    }

}
