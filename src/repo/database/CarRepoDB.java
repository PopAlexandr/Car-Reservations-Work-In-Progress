package repo.database;

import domain.Car;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class CarRepoDB extends DatabaseRepository<String, Car> {

    public CarRepoDB(String url) {
        super(url);
    }

    @Override
    protected void createTable() {
        try {
            openConnection();
            final Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS cars(" +
                            "CarID VARCHAR(25) PRIMARY KEY, " +
                            "Name VARCHAR(200) NOT NULL , " +
                            "Year VARCHAR(200) NOT NULL );");
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
            exit(1);
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean add(String id, Car car) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO cars VALUES (?, ?, ?);");
            statement.setString(1, car.getID());
            statement.setString(2, car.getName());
            statement.setString(3, car.getYear());

            int ra = statement.executeUpdate();

            return true;
        } catch (SQLException e) {

            return false;
        } finally {
            closeConnection();
        }
    }



    @Override
    public boolean remove(String id) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM cars WHERE CarID=?;");
            statement.setString(1, id);
            int ra = statement.executeUpdate();

            return true;
        } catch (SQLException e) {

            return false;
        } finally {
            closeConnection();
        }
    }


    @Override
    public boolean update(String id, Car car) {
        try {
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE cars " +
                            "SET CarID = ?, Name = ?, Year = ? " +
                            "WHERE CarID = ?;");
            statement.setString(1, car.getID());
            statement.setString(2, car.getName());
            statement.setString(3, car.getYear());

            statement.setString(4, id);
            int ra = statement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException ex) {

            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public Car findById(String id) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from cars as p WHERE p.CarID = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            Car p = null;
            int count = 0;
            if (rs.next()) {
                p = new Car(
                        rs.getString("CarID"),
                        rs.getString("Name"),
                        rs.getString("Year"));
                count++;
            }

            return p;
        } catch (SQLException ex) {

            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<Car> getAll() {
        var cars = new ArrayList<Car>();
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from cars;");
            ResultSet rs = statement.executeQuery();
            int count = 0;
            while (rs.next()) {
                var p = new Car(
                        rs.getString("CarID"),
                        rs.getString("Name"),
                        rs.getString("Year"));
                cars.add(p);
                count++;
            }

            return cars;
        } catch (SQLException ex) {

            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public Integer size() {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) from cars;");
            ResultSet rs = statement.executeQuery();

            if (rs.next())
                return rs.getInt(1);
            return 0;
        } catch (SQLException ex) {

            return 0;
        } finally {
            closeConnection();
        }
    }
}
