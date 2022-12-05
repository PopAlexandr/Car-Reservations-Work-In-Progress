package service;

import domain.Car;
import domain.Reservation;
import exception.RepoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import repo.memory.IdentifiableRepoMem;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class ServiceTest {
    CarService carService;
    ReservationService reservationService;
    Car c1;
    Reservation r1;

    @BeforeEach
    void reset(){
        var carRepo=new IdentifiableRepoMem<Car>();
        c1=new Car("1","Dacie","2002");
        var c2=new Car("2","Bobcat","2015");
        carRepo.add(c1.getID(), c1);
        carRepo.add(c2.getID(),c2);
        carService=new CarService(carRepo);
        var resRepo=new IdentifiableRepoMem<Reservation>();
        r1=new Reservation(c1,"Axi");
        var r2=new Reservation(c2,"Octa");
        resRepo.add(r1.getID(),r1);
        resRepo.add(r2.getID(), r2);
        reservationService=new ReservationService(resRepo);
    }

    @Test
    void add(){
        var c=new Car("X","X","X");
        var r=new Reservation(c,"X");
        try{
            carService.add(c);
            reservationService.add(r);
            assert true;
        }
        catch (RepoException e){assert false;}
        try {
            carService.add(c);
            assert false;
        }catch (RepoException e){assert true;}
        assert carService.size()==3;
        assert reservationService.size()==3;


    }
    @Test
    void remove(){
        try{carService.remove(c1);
            reservationService.remove(r1);
            assert true;
    } catch (RepoException e){
        assert false;}

    }

    @Test
    void update(){
        var c=new Car("a","Passat","2018");
        try {
            carService.update(c);
            assert true;
        }catch (RepoException e){
            assert false;
        }

    }

    @Test
    void get(){
        try{
            var c=carService.get(c1.getID());
            var r=reservationService.get(r1.getID());
            assert c.equals(c1);
            assert r.equals(r1);
        }catch (RepoException e){assert false;}

        try {
            var c=carService.get("!");
            assert false;
        }catch (RepoException e){assert true;}

        var x=carService.get();
        assert x.size()==2;
    }

    @Test
    void contains(){
        assert carService.contains(c1.getID());
        assert reservationService.contains(r1.getID());
        assert !carService.contains("!");
    }

    @Test
    void size(){
        assert carService.size()==2;
        assert reservationService.size()==2;
    }

}
