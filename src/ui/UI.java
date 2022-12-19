//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import domain.Car;
import domain.Reservation;
import exception.RepoException;
import service.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class UI {
    Controller controller;
    Scanner console;

    public UI(Controller controller) {
        this.console=new Scanner(System.in);
        this.controller=controller;

    }
    public void go()  {

        System.out.println("AFISARE MASINI FOLOSIND CONSUMER FUNCTIONAL INTERFACE");
        Consumer<Car> show= System.out::println;

        for(int i=0;i<this.controller.carService.size();i++){
            try {
                show.accept(this.controller.getCar(String.valueOf(i)));
            } catch (RepoException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("AFISARE REZERVARI FOLOSIND CONSUMER FUNCTIONAL INTERFACE");
        Consumer<Reservation> show2= System.out::println;
        ArrayList<Reservation> reservations=this.controller.getReservations();
        for(int i=0;i<this.controller.appService.size();i++){
            show2.accept(reservations.get(i));
        }

        
        System.out.println("\n AFISARE MASINI MAI VECHI DE 2005");
        List<Car> predicatecars =this.controller.getCars();

        Predicate<Car> carPredicate=n->  Integer.parseInt( n.getYear()) <2005;
        predicatecars.stream().filter(carPredicate).forEach(System.out::println);


        System.out.println("\n FILTER MASINI MAI NOI DE 2005");
        List<Car>carFilter=this.controller.getCars();
        carFilter.stream()
                .filter(y->Integer.parseInt( y.getYear())>2005)
                .sorted(Comparator.comparing(Car::getYear))
                .forEach(System.out::println);


        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1:CRUD FOR CARS");
            System.out.println("2:CRUD FOR RESERVATIONS");
            System.out.println("3:Exit");

            System.out.println("4:Reports");
            System.out.println("Enter Option:");
            String option=this.console.nextLine();
            switch (option.charAt(0)){
                case '1'-> {
                    try {
                        new CarsUI().run();
                    } catch (RepoException e) {
                        throw new RuntimeException(e);
                    }
                }
                case'2'-> {
                    try {
                        new ReservationsUI().run();
                    } catch (RepoException e) {
                        throw new RuntimeException(e);
                    }
                }
                case'3'-> {System.out.println("BYE BYE");
                    return;}
                case '4'->{try{new Reports().run();}
                catch (RepoException e){throw new RuntimeException(e);}}

                default->System.out.println("???");

            }

        }
    }
    class CarsUI{
        public void run() throws RepoException {
            while (true){
                System.out.println("1:ADD CAR");
                System.out.println("2:PRINT ALL CARS ");
                System.out.println("3:DELETE CAR");
                System.out.println("4:UPDATE CAR");
                System.out.println("5:GO BACK TO MAIN MENU");
                System.out.println("Enter option:");
                char option=UI.this.console.nextLine().charAt(0);
                switch (option){
                    case '1'->{
                        System.out.println("Enter car ID:");
                        String id=UI.this.console.nextLine();
                        System.out.println("Car name:");
                        String name=UI.this.console.nextLine();
                        System.out.println("Enter year of fabrication:");
                        String year=UI.this.console.nextLine();
                        try{
                            UI.this.controller.addCars(new Car(id,name,year));
                        } catch (RepoException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case '2'->{
                        for (Car c: UI.this.controller.getCars()){
                            System.out.print(c);

                            System.out.println();
                        }
                    }
                    case '4'->{
                        System.out.println("Car ID:");
                        String id= UI.this.console.nextLine();
                        System.out.println("New car name:");
                        String name=UI.this.console.nextLine();
                        System.out.println("New year of fabrication:");
                        String year=UI.this.console.nextLine();
                        Car car=new Car(id,name,year);
                        try{
                            UI.this.controller.updateCar(car);
                        }
                        catch (RepoException e){
                            System.out.println(e.getMessage());
                        }
                    }

                    case '3'->{
                        System.out.print("Enter car ID: ");
                        String id = UI.this.console.nextLine();
                        try {
                            UI.this.controller.removeCars(new Car(id, null, null));

                        } catch (Exception e) {
                            System.out.println( e.getMessage());
                        }
                    }
                    case '5'-> {return;}
                    default -> System.out.println("????");
                }
            }
        }
    }

    class Reports{
        public void run()throws RepoException{
            while (true){
                System.out.println("1:the name of the persons who booked a certain car");
                System.out.println("2:all cars rented by a certain person");
                System.out.println("3:Cars older than a certain year");
                System.out.println("4:Cars newer than a certain year");
                System.out.println("5:all cars that have a certain letter in their name");
                System.out.println("6:Back");
                System.out.print("Enter option: ");
                char option=UI.this.console.nextLine().charAt(0);
                switch (option){
                    case '1'->{System.out.println("Enter car ID:");
                        String idCar=UI.this.console.nextLine();
                        List<Reservation>reservationFilter=UI.this.controller.getReservations();
                        reservationFilter.stream().filter(y->y.carID.equals(idCar)).forEach(System.out::println);

                    }
                    case '2'->{System.out.println("Enter person name:");
                                String name= UI.this.console.nextLine();
                                List<Reservation>reservationList=UI.this.controller.getReservations();
                                reservationList.stream().filter(y->y.name.equals(name)).forEach(reservation -> System.out.println(reservation.getCarID()));


                    }
                    case '3'->{
                        System.out.println("Enter Year:");
                        String year=UI.this.console.nextLine();
                        List<Car>carFilter=UI.this.controller.getCars();
                        carFilter.stream()
                                .filter(y->Integer.parseInt( y.getYear())<Integer.parseInt(year))
                                .sorted(Comparator.comparing(Car::getYear))
                                .forEach(System.out::println);}
                    case '4'->{System.out.println("Enter Year:");
                        String year=UI.this.console.nextLine();
                        List<Car>carFilter=UI.this.controller.getCars();
                        carFilter.stream()
                                .filter(y->Integer.parseInt( y.getYear())>Integer.parseInt(year))
                                .sorted(Comparator.comparing(Car::getYear))
                                .forEach(System.out::println);}
                    case '5'->{System.out.println("Letter:");
                        String letter=UI.this.console.nextLine();
                        List<Car>carList=UI.this.controller.getCars();
                        carList.stream().filter(y->y.getName().contains(letter)).forEach(System.out::println);

                    }
                    case '6'->{return;}

                }
            }
        }
    }
    class ReservationsUI{
        public void run() throws RepoException {
            while (true){
                System.out.println("1: Create reservation");
                System.out.println("2: Print all reservations");
                System.out.println("3: Update reservation");
                System.out.println("4: Delete reservation");

                System.out.println("6: Back");
                System.out.print("Enter option: ");
                char option=UI.this.console.nextLine().charAt(0);
                switch (option){
                    case '1'->{

                        System.out.println("Enter car ID:");
                        String idCar=UI.this.console.nextLine();
                        Car car=UI.this.controller.getCar(idCar);
                        System.out.println("Enter the name of the person:");
                        String name=UI.this.console.nextLine();

                        try{
                            UI.this.controller.bookReservation(car,name);
                        } catch (Exception e){System.out.println(e.getMessage());}

                    }
                    case '2'->{
                        System.out.println("Hey");
                        for(Reservation r:UI.this.controller.getReservations()){ System.out.println(r);

                        }
                    }
                    case '3'->{
                        for (int i = 0; i < UI.this.controller.getNumberOfReservations(); i++)
                            System.out.println("[" + (i + 1) + "] " + UI.this.controller.getReservations().get(i));
                        System.out.print("Index of the desired reservation:");
                        int index;
                        try{
                            index=Integer.parseInt(UI.this.console.nextLine());
                            if (index<1 || index>UI.this.controller.getNumberOfReservations())
                                throw new Exception("??");

                        } catch (Exception e)  {
                            System.out.println(e.getMessage());
                            return;
                        }
                        System.out.println("Enter new car:");
                        String carID=UI.this.console.nextLine();
                        Car car= UI.this.controller.getCar(carID);
                        System.out.println(car);
                        System.out.println("Enter new name");
                        String name= UI.this.console.nextLine();

                        Reservation r=UI.this.controller.getReservations().get(index-1);
                        r.setName(name);
                        r.setCarID(carID);

                        System.out.println(r);
                        try{
                            UI.this.controller.updateReservation(r);


                        }
                        catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                    case '4'->{
                        for(int i=0;i<UI.this.controller.getNumberOfReservations();i++)
                            System.out.println("["+(i+1)+"]"+UI.this.controller.getReservations().get(i));
                        System.out.print("Chose the reservation index:");
                            int index;
                            try{
                                index = Integer.parseInt(UI.this.console.nextLine());
                                if (index < 1 || index > UI.this.controller.getNumberOfReservations()) {
                                    throw new Exception("Invalid index");
                                }
                                UI.this.controller.cancelReservation(UI.this.controller.getReservations().get(index-1));

                            }
                            catch (Exception e){
                                System.out.println(e.getMessage());
                                return;
                            }


                    }
                    case '6'->{return;}
                }
            }




        }

    }

}
