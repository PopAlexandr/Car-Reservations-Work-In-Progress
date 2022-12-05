package service;

import domain.Car;
import exception.RepoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import repo.memory.IdentifiableRepoMem;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ControllerTest {
    Controller ctrl;
    @BeforeEach
    void setUp(){
        var cserv=new CarService(new IdentifiableRepoMem<>());
        var rserv=new ReservationService(new IdentifiableRepoMem<>());
        ctrl=new Controller(rserv,cserv);

    }

    @Test
    void bookReservation() {
        var c=new Car("1","Dacie","2002");
        try{
            ctrl.addCars(c);
            ctrl.bookReservation(c,"Alex");
        }catch (Exception e){
            assert false;
        }
        assert ctrl.getReservations().size()==1;
        assert ctrl.getReservations().get(0).getCarID().equals("1");
    }

    @Test
    void cancelReservation() {
        var c=new Car("1","Dacie","2002");
        try{
            ctrl.addCars(c);
            ctrl.bookReservation(c,"Alex");
        }catch (Exception e){
            assert false;
        }
        try{
            ctrl.cancelReservation(ctrl.getReservations().get(0));

        } catch (Exception e){
            assert false;
        }
        assert ctrl.getReservations().size()==0;
    }

    @Test
    void addCars() {
        var c=new Car("1","Dacie","2002");
        try{
            ctrl.addCars(c);
            assert true;
        }catch (RepoException e){assert false;}
        try{
            ctrl.addCars(c);
            assert false;
        }catch (RepoException e){assert true;}

        }


    @Test
    void removeCars() {
        var c=new Car("1","Dacie","2002");
        try{
            ctrl.addCars(c);
            ctrl.removeCars(c);
            assert true;
        }catch (RepoException e){assert false;}
        assert ctrl.getCars().size()==0;
    }

    @Test
    void updateCar() {
        var c=new Car("1","Dacie","2002");
        var c2=new Car("1","Bobcat","2020");
        try{
            ctrl.addCars(c);
            ctrl.updateCar(c2);
            assert true;
        }catch (RepoException e){assert true;}
        assert ctrl.getCars().get(0).getYear().equals("2020");
    }


    @Test
    void getCar() {
        var c=new Car("1","Dacie","2002");
        try{
            ctrl.addCars(c);
            assert ctrl.getCar("1").getYear().equals("2002");
        }catch (RepoException e){assert false;}
    }




    @Test
    void getNumberOfReservations() {

        assert ctrl.getNumberOfReservations()==0;
    }

}