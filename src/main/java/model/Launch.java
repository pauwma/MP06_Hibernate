package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "launch")
public class Launch implements Serializable{
    @Id
    @Column(name = "launch_title")
    String launch_title;
    @Column(name = "launch_status")
    String launch_status;
    @Column(name = "launch_date")
    String launch_date;
    @Column(name = "rocket_name")
    Rocket rocket;
    @Column(name = "agency_name")
    Agency agency;
    @Column(name = "mission_name")
    Location location;
    @Column(name = "mission_name")
    Mission mission;

    public Launch(String launch_title, String launch_status, String launch_date, Rocket rocket, Agency agency, Location location, Mission mission) {
        super();
        this.launch_title = launch_title;
        this.launch_status = launch_status;
        this.launch_date = launch_date;
        this.rocket = rocket;
        this.agency = agency;
        this.location = location;
        this.mission = mission;
    }

    public Launch(){
        super();
    }

    public String getLaunch_title() {
        return launch_title;
    }

    public void setLaunch_title(String launch_title) {
        this.launch_title = launch_title;
    }

    public String getLaunch_status() {
        return launch_status;
    }

    public void setLaunch_status(String launch_status) {
        this.launch_status = launch_status;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(String launch_date) {
        this.launch_date = launch_date;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    @Override
    public String toString() {
        return "Launch{" +
                "launch_title='" + launch_title + '\'' +
                ", launch_status='" + launch_status + '\'' +
                ", launch_date='" + launch_date + '\'' +
                ", rocket=" + rocket.rocket_name +
                ", agency=" + agency.agency_name +
                ", location=" + location.location_name +
                ", mission=" + mission.mission_name +
                '}';
    }
}