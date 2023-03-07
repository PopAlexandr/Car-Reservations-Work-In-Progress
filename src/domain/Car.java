package domain;

import java.io.Serializable;
import java.util.Objects;


public class Car implements Identifiable<String>, Serializable {

    public static final long serialVersionUID = 1L;
    private String name;
    private String year;

    private String id;

    public Car(String id, String name, String year) {
        this.id = id;
        this.name = name;
        this.year=year;
    }

    @Override
    public void setID(String s) {
        this.id = s;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "[" + this.getID() + "] " + this.name + " " + this.year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

}

