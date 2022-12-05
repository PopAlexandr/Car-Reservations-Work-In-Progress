package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    Car c1;
    Car c2;
    @BeforeEach
    void setUp(){
        c1=new Car("1","Dacie","2002");
        c2=new Car("2","Avion","2022");
    }
    @Test
    void setID() {
        c1.setID("3");
        assert c1.getID().equals("3");
    }

    @Test
    void getID() {
        assert c1.getID().equals("1");
    }

    @Test
    void getName() {
       assert c1.getName().equals("Dacie");
    }

    @Test
    void setName() {
        c1.setName("Passat");
        assert c1.getName().equals("Passat");
    }

    @Test
    void getYear() {
        assert c1.getYear().equals("2002");
    }

    @Test
    void setYear() {
        c1.setYear("2004");
        assert c1.getYear().equals("2004");
    }

    @Test
    void testToString() {
        assert c1.toString().equals("[1] Dacie 2002");
    }

    @Test
    void testEquals() {
        assert !c1.equals(c2);
        Car c3=new Car("2","Avion","2022");
        assert c2.equals(c3);
    }
}