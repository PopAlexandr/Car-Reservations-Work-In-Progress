package service;

import domain.Reservation;
import domain.Car;
import exception.ConfigurationException;
import repo.IRepository;
import repo.file.*;
import repo.memory.IdentifiableRepoMem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

final public class RepoLoader {
    private static final String SETTINGS_FILE = "config/settings.properties";
    private static final String PATHS_FILE = "config/files.properties";
    private String repoMode;
    private String carsPath;
    private String reservationsPath;

    public RepoLoader() {
        loadConfig(true);
    }

    public void loadConfig(boolean verbose) {
        Properties properties = new Properties();
        try (InputStream pf = new FileInputStream(SETTINGS_FILE)) {
            properties.load(pf);
            repoMode = properties.getProperty("RepositoryMode");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (InputStream pf = new FileInputStream(PATHS_FILE)) {
            properties.load(pf);
            switch (repoMode) {
                case "memory" -> {
                    carsPath = null;
                    reservationsPath = null;
                }
                case "csv" -> {
                    carsPath = properties.getProperty("CarsCSV");
                    reservationsPath = properties.getProperty("ReservationCSV");
                }
                case "binary" -> {
                    carsPath = properties.getProperty("CarsBIN");
                    reservationsPath = properties.getProperty("ReservationBIN");
                }
                default -> throw new ConfigurationException("Invalid repository mode");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (verbose) {
            System.out.println("\nRepository config loaded successfully on mode [" + repoMode + "]");
        }
    }

    public IRepository<String, Car> initCarsRepo() {
        switch (repoMode) {
            case "csv" -> {
                return new CarRepoCSV(carsPath);
            }
            case "binary" -> {
                return new CarRepoBIN(carsPath);
            }
            case "memory" -> {
                return new IdentifiableRepoMem<Car>();
            }
            default -> throw new ConfigurationException("Invalid repository type");
        }
    }

    public IRepository<String,Reservation> initReservationsRepo() {
        switch (repoMode) {
            case "csv" -> {
                return new ReservationRepoCSV(reservationsPath);
            }
            case "binary" -> {
                return new ReservationRepoBIN(reservationsPath);
            }
            case "memory" -> {
                return new IdentifiableRepoMem<Reservation>();
            }
            default -> throw new ConfigurationException("Invalid repository type");
        }
    }

}
