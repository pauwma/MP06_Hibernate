package model;

import java.io.Serializable;

public class Author implements Serializable {
    int authorId;
    String name, naionality, birthYear;
    boolean active;


    public Author(int authorId, String name, String naionality, String birthYear,
                  boolean active) {
        super();
        this.birthYear = birthYear;
        this.name = name;
        this.naionality = naionality;
        this.active = active;
        this.authorId = authorId;
    }

    public Author() {

    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNaionality() {
        return naionality;
    }

    public void setNaionality(String naionality) {
        this.naionality = naionality;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Autor [id_autor=" + authorId + ", nom=" + name + ", any_naixement=" + birthYear
                + ", nacionalitat=" + naionality + ", actiu=" + active
                + "]";
    }


}
