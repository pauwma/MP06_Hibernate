package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "rocket")
public class Rocket implements Serializable{
    @Id
    String rocket_name;
    String rocket_family;
    String rocket_length;
    String rocket_diameter;
    String rocket_launch_mass;
    String rocket_low_earth_orbit_capacity;
    String rocket_description;
    Agency agency;

    public Rocket(String rocket_name, String rocket_family, String rocket_length, String rocket_diameter, String rocket_launch_mass, String rocket_low_earth_orbit_capacity, String rocket_description, Agency agency) {
        super();
        this.rocket_name = rocket_name;
        this.rocket_family = rocket_family;
        this.rocket_length = rocket_length;
        this.rocket_diameter = rocket_diameter;
        this.rocket_launch_mass = rocket_launch_mass;
        this.rocket_low_earth_orbit_capacity = rocket_low_earth_orbit_capacity;
        this.rocket_description = rocket_description;
        this.agency = agency;
    }

    public Rocket(){
        super();
    }

    public String getRocket_name() {
        return rocket_name;
    }

    public void setRocket_name(String rocket_name) {
        this.rocket_name = rocket_name;
    }

    public String getRocket_family() {
        return rocket_family;
    }

    public void setRocket_family(String rocket_family) {
        this.rocket_family = rocket_family;
    }

    public String getRocket_length() {
        return rocket_length;
    }

    public void setRocket_length(String rocket_length) {
        this.rocket_length = rocket_length;
    }

    public String getRocket_diameter() {
        return rocket_diameter;
    }

    public void setRocket_diameter(String rocket_diameter) {
        this.rocket_diameter = rocket_diameter;
    }

    public String getRocket_launch_mass() {
        return rocket_launch_mass;
    }

    public void setRocket_launch_mass(String rocket_launch_mass) {
        this.rocket_launch_mass = rocket_launch_mass;
    }

    public String getRocket_low_earth_orbit_capacity() {
        return rocket_low_earth_orbit_capacity;
    }

    public void setRocket_low_earth_orbit_capacity(String rocket_low_earth_orbit_capacity) {
        this.rocket_low_earth_orbit_capacity = rocket_low_earth_orbit_capacity;
    }

    public String getRocket_description() {
        return rocket_description;
    }

    public void setRocket_description(String rocket_description) {
        this.rocket_description = rocket_description;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency_name(Agency agency) {
        this.agency = agency;
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "rocket_name='" + rocket_name + '\'' +
                ", rocket_family='" + rocket_family + '\'' +
                ", rocket_length='" + rocket_length + '\'' +
                ", rocket_diameter='" + rocket_diameter + '\'' +
                ", rocket_launch_mass='" + rocket_launch_mass + '\'' +
                ", rocket_low_earth_orbit_capacity='" + rocket_low_earth_orbit_capacity + '\'' +
                ", rocket_description='" + rocket_description + '\'' +
                ", agency=" + agency.agency_name +
                '}';
    }
}