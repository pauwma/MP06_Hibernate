package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "agency")
public class Agency implements Serializable{
    @Id
    @Column(name = "agency_name")
    String agency_name;
    @Column(name = "agency_type")
    String agency_type;
    @Column(name = "agency_abbreviation")
    String agency_abbreviation;
    @Column(name = "agency_administration")
    String agency_administration;
    @Column(name = "agency_founded")
    int agency_founded;
    @Column(name = "agency_country")
    String agency_country;
    @Column(name = "agency_spacecraft")
    String agency_spacecraft;
    @Column(name = "agency_launchers")
    String agency_launchers;
    @Column(name = "agency_description")
    String agency_description;

    public Agency(String agency_name, String agency_type, String agency_abbreviation, String agency_administration, int agency_founded, String agency_country, String agency_spacecraft, String agency_launchers, String agency_description) {
        super();
        this.agency_name = agency_name;
        this.agency_type = agency_type;
        this.agency_abbreviation = agency_abbreviation;
        this.agency_administration = agency_administration;
        this.agency_founded = agency_founded;
        this.agency_country = agency_country;
        this.agency_spacecraft = agency_spacecraft;
        this.agency_launchers = agency_launchers;
        this.agency_description = agency_description;
    }

    public Agency(){
        super();
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getAgency_type() {
        return agency_type;
    }

    public void setAgency_type(String agency_type) {
        this.agency_type = agency_type;
    }

    public String getAgency_abbreviation() {
        return agency_abbreviation;
    }

    public void setAgency_abbreviation(String agency_abbreviation) {
        this.agency_abbreviation = agency_abbreviation;
    }

    public String getAgency_administration() {
        return agency_administration;
    }

    public void setAgency_administration(String agency_administration) {
        this.agency_administration = agency_administration;
    }

    public int getAgency_founded() {
        return agency_founded;
    }

    public void setAgency_founded(int agency_founded) {
        this.agency_founded = agency_founded;
    }

    public String getAgency_country() {
        return agency_country;
    }

    public void setAgency_country(String agency_country) {
        this.agency_country = agency_country;
    }

    public String getAgency_spacecraft() {
        return agency_spacecraft;
    }

    public void setAgency_spacecraft(String agency_spacecraft) {
        this.agency_spacecraft = agency_spacecraft;
    }

    public String getAgency_launchers() {
        return agency_launchers;
    }

    public void setAgency_launchers(String agency_launchers) {
        this.agency_launchers = agency_launchers;
    }

    public String getAgency_description() {
        return agency_description;
    }

    public void setAgency_description(String agency_description) {
        this.agency_description = agency_description;
    }

    @Override
    public String toString() {
        return  "| " + agency_name + " | " + agency_type + " | " + agency_abbreviation +  " | " + agency_administration +  " | " + agency_founded +
                " | " + agency_country +  " | " + agency_spacecraft +  " | " + agency_launchers +  " | " + agency_description + " |";
    }
}