package repo.database;

import domain.Reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class ReservationRepoDB extends DatabaseRepository<String, Reservation> {

    public ReservationRepoDB(String url) {
        super(url);
    }

    @Override
    protected void createTable() {
        try {
            openConnection();
            final Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS reservations(" +
                            "ReservationID VARCHAR(80) PRIMARY KEY, " +
                            "CarID VARCHAR(25) NOT NULL , " +
                            "Name VARCHAR(50) NOT NULL );");
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
            exit(1);
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean add(String id, Reservation reservation) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO reservations VALUES (?, ?, ?);");
            statement.setString(1, reservation.getID());
            statement.setString(2, reservation.getCarID());
            statement.setString(3, reservation.getName());

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
                    "DELETE FROM reservations WHERE ReservationID=?;");
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
    public boolean update(String id, Reservation app) {
        try {
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE reservations " +
                            "SET ReservationID = ?, CarID = ?, Name = ? " +
                            "WHERE ReservationID = ?;");
            statement.setString(1, app.getID());
            statement.setString(2, app.getCarID());
            statement.setString(3, app.getName());

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
    public Reservation findById(String id) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from reservations as a WHERE a.ReservationID = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            Reservation a = null;
            int counter = 0;
            if (rs.next()) {
                a = Reservation.build(
                        rs.getString("ReservationID"),
                        rs.getString("CarID"),
                        rs.getString("Name"));
                counter++;
            }

            return a;
        } catch (SQLException ex) {

            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<Reservation> getAll() {
        var apps = new ArrayList<Reservation>();
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from reservations;");
            ResultSet rs = statement.executeQuery();
            int counter = 0;
            while (rs.next()) {
                var a = Reservation.build(
                        rs.getString("ReservationID"),
                        rs.getString("CarID"),
                        rs.getString("Name"));
                apps.add(a);
                counter++;
            }

            return apps;
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
                    "SELECT COUNT(*) from reservations;");
            ResultSet rs = statement.executeQuery();

            if (rs.next())
                return rs.getInt(1);
            statement.close();
            return 0;
        } catch (SQLException ex) {

            return 0;
        } finally {
            closeConnection();
        }
    }
}
