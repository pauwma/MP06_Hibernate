package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "mission")
public class Mission implements Serializable{
    @Id
    @Column(name = "mission_name")
    String mission_name;
    @Column(name = "mission_type")
    String mission_type;
    @Column(name = "mission_launch_cost")
    String mission_launch_cost;
    @Column(name = "mission_description")
    String mission_description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rocket_name")
    Rocket rocket;

    public Mission(String mission_name, String mission_type, String mission_launch_cost, String mission_description, Rocket rocket) {
        this.mission_name = mission_name;
        this.mission_type = mission_type;
        this.mission_launch_cost = mission_launch_cost;
        this.mission_description = mission_description;
        this.rocket = rocket;
    }

    public Mission(){
        super();
    }


    public String getMission_name() {
        return mission_name;
    }

    public void setMission_name(String mission_name) {
        this.mission_name = mission_name;
    }

    public String getMission_type() {
        return mission_type;
    }

    public void setMission_type(String mission_type) {
        this.mission_type = mission_type;
    }

    public String getMission_launch_cost() {
        return mission_launch_cost;
    }

    public void setMission_launch_cost(String mission_launch_cost) {
        this.mission_launch_cost = mission_launch_cost;
    }

    public String getMission_description() {
        return mission_description;
    }

    public void setMission_description(String mission_description) {
        this.mission_description = mission_description;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    @Override
    public String toString() {
        return  "| " + mission_name +  " | " + mission_type +  " | " + mission_launch_cost +  " | " + mission_description + " | " + rocket.getRocket_name() +  " |";
    }
}