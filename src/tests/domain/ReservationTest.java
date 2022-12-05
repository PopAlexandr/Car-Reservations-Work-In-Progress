package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    Car c1;

    Reservation r1;


    @BeforeEach
    void setUp() {
        c1=new Car("1","Dacia","2002");
        r1=new Reservation(c1,"Alex");


    }

    @Test
    void build() {
        Reservation r2=new Reservation();
        assert r2.getID().equals("");
        r2=Reservation.build("test","test","test");
        assert r2.getID().equals("test");
    }

    @Test
    void setID() {

        r1.setID("2");
        assert r1.getID().equals("2");
    }

    @Test
    void getID() {
        assert r1.getID().equals("1Alex");
    }

    @Test
    void getCarID() {
        assert r1.getCarID().equals("1");
    }

    @Test
    void getName() {
        assert r1.getName().equals("Alex");
    }



    @Test
    void testToString() {
        assert r1.toString().equals("[1Alex] 1 Alex");
    }

    @Test
    void testEquals() {
        Reservation r2=new Reservation(c1,"Alex");
        assert r1.equals(r2);
    }
}