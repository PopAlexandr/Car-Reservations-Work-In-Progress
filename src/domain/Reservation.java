package domain;

import java.io.Serializable;
import java.util.Objects;

public class Reservation implements Identifiable<String>, Serializable {

    public static final long serialVersionUID = 1L;

    public String carID;

    public String id;

    public String name;

    public Reservation(Car car, String name) {
        this.id = car.getID() + name;
        this.carID=car.getID();

        this.name = name;
    }

    public Reservation() {
        this.id = "";
        this.carID = "";


        this.name = "";
    }

    public static Reservation build(String id, String carID, String name) {
        Reservation a = new Reservation();
        a.id = id;
        a.carID = carID;

        a.name = name;
        return a;
    }

    public void setID(String id) {
        this.id = id;
    }
    public void setCarID(String carID){this.carID=carID;}
    public void setName(String name){this.name=name;}
    public String getID() {
        return id;
    }

    public String getCarID() {
        return carID;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + this.getID() + "] " + this.carID +  " " + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation app = (Reservation) o;
        return Objects.equals(id, app.id);
    }
}
