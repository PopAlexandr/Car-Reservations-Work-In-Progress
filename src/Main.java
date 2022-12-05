import service.RepoLoader;
import service.*;
import ui.UI;

public class Main {

    public static void main(String[] args) {

        RepoLoader repoLoader = new RepoLoader();
        CarService carService = new CarService(repoLoader.initCarsRepo());

        ReservationService reservationService = new ReservationService(repoLoader.initReservationsRepo());

        Controller controller = new Controller(reservationService, carService);

        UI ui = new UI(controller);
        ui.go();

    }
}