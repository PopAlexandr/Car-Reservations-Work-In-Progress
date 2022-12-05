//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import domain.Car;
import domain.Reservation;
import exception.RepoException;
import service.Controller;

import java.util.Scanner;

public class UI {
    Controller controller;
    Scanner console;

    public UI(Controller controller) {
        this.console=new Scanner(System.in);
        this.controller=controller;

    }
    public void go()  {
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1:CRUD FOR CARS");
            System.out.println("2:CRUD FOR RESERVATIONS");
            System.out.println("3:Exit");
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

    class ReservationsUI{
        public void run() throws RepoException {
            while (true){
                System.out.println("1: Create reservation");
                System.out.println("2: Print all reservations");
                System.out.println("3: Update reservation");
                System.out.println("4: Delete reservation");
                System.out.println("5: Statistics");
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
