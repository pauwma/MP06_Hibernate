package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "location")
public class Location implements Serializable{
    @Id
    @Column(name = "location_name")
    String location_name;
    @Column(name = "location_location")
    String location_location;
    @Column(name = "rockets_launched")
    int rockets_launched;

    public Location(String locationName, String locationLocation, int rocketsLaunched){
        super();
        this.location_name = locationName;
        this.location_location = locationLocation;
        this.rockets_launched = rocketsLaunched;
    }

    public Location(){
        super();
    }

    public String getLocationName() {
        return location_name;
    }

    public void setLocationName(String locationName) {
        this.location_name = locationName;
    }

    public String getLocationLocation() {
        return location_location;
    }

    public void setLocationLocation(String locationLocation) {
        this.location_location = locationLocation;
    }

    public int getRocketsLaunched() {
        return rockets_launched;
    }

    public void setRocketsLaunched(int rocketsLaunched) {
        this.rockets_launched = rocketsLaunched;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationName='" + location_name + '\'' +
                ", locationLocation='" + location_location + '\'' +
                ", rocketsLaunched=" + rockets_launched +
                '}';
    }
}