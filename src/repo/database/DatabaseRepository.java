package repo.database;

import org.sqlite.SQLiteDataSource;
import repo.IRepository;

import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.System.exit;

public abstract class DatabaseRepository<ID, T> implements IRepository<ID, T> {
    protected String url;
    protected Connection connection;

    public DatabaseRepository(String url) {
        this.url = "jdbc:sqlite:" + url;
        createTable();
    }

    protected void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(this.url);
            if (connection == null || connection.isClosed())
                connection = ds.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    protected void closeConnection() {
        try {
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    abstract protected void createTable();
}
