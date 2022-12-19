package service;

import domain.Car;
import domain.Reservation;
import exception.InvalidParametersException;
import exception.RepoException;

import java.util.ArrayList;

final public class Controller {

    public final ReservationService appService;
    public final CarService carService;


    public Controller(ReservationService reservationService, CarService carService) {
        this.appService = reservationService;
        this.carService = carService;

    }

    public void bookReservation(Car car, String name) throws RepoException, InvalidParametersException {
        if (!carService.contains(car.getID()) )
            throw new RepoException("Car not found");
        Reservation reservation = new Reservation(car,name);

        appService.add(reservation);
    }

    public void cancelReservation(Reservation reservation) throws RepoException {
        appService.remove(reservation);
    }

    public void addCars(Car... cars) throws RepoException, InvalidParametersException {
        for (Car car : cars) {

            carService.add(car);
        }
    }



    public void removeCars(Car... cars) throws RepoException {
        for (Car d : cars) {
            carService.remove(d);

        }
    }


    public void updateCar(Car car) throws RepoException, InvalidParametersException {

        carService.update(car);
    }


    public void updateReservation(Reservation reservation) throws RepoException, InvalidParametersException {

        appService.update(reservation);
    }

    public Car getCar(String id) throws RepoException {
        return carService.get(id);
    }

    public ArrayList<Car> getCars() {
        return carService.get();
    }



    public ArrayList<Reservation> getReservations() {
        return appService.get();
    }


    public Integer getNumberOfReservations() {
        return appService.size();
    }


    public Reservation getReservation(String id) throws RepoException {
        return appService.get(id);
    }
}
