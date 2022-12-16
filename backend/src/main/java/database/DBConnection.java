package database;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;

/**
 * Manages the connection to the SQLite database.
 */
public class DBConnection {
    // Singleton instance of DBConnection
    public static DBConnection instance = null;

    //  Name of the database file
    public final String dbname;

    // File which stores the database
    public File dbfile;

    // Connection to the database
    private java.sql.Connection connection;

    // Whether instance is a test (true) or a main instance (false)
    public final boolean isTest;

    /**
     * Obtains and returns the singleton instance of DBConnection
     *
     * @param test: Whether the instance should be a test instance or not
     * @return instance of DBConnection
     */
    public static DBConnection getInstance(boolean test) {
        if (instance == null) {
            instance = new DBConnection(test);
        }
        return instance;
    }

    /**
     * Obtains and returns the singleton instance of DBConnection. If not already
     * instantiated, this function will set the instance to a non-test instance
     *
     * @return instance of DBConnection
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection(false);
        }
        return instance;
    }

    /**
     * Private constructor of DatabaseConnection.
     * <p>
     * DatabaseConnection can only be used from in a static context.
     */
    private DBConnection(boolean test) {
        isTest = test;
        if (test) {
            dbname = "test";
            dbfile = new File("src/test/resources", dbname + ".db");
        } else {
            dbname = "main";
            dbfile = new File("src/main/resources", dbname + ".db");
        }
    }

    /**
     * Deletes the file storing the database.
     *
     * @return true if successfully deleted or did not exist, otherwise false
     */
    public boolean deleteDBFile() {
        if (dbfile.exists()) {
            long time = Instant.now().toEpochMilli();
            String parent = dbfile.getParent();
            File f1 = new File(parent + "/old", dbname + "_old_" + time);
            boolean renamed = dbfile.renameTo(f1);
            boolean deleted = dbfile.delete();
            dbfile = new File(parent, dbname + ".db");

            return renamed || deleted;
        }
        return true;
    }

    /**
     * Creates the file which the database will be stored on
     *
     * @return true if successfully created or already exists, otherwise false
     */
    public boolean createDBFile() throws IOException {
        boolean exists = dbfile.exists();
        if (!exists) { // create database file if it does not exist
            return dbfile.createNewFile();
        }
        return true;
    }

    /**
     * Returns true if the connection is closed, otherwise false
     *
     * @return true if connection is null, otherwise false.
     */
    public boolean isClosed() {
        return connection == null;
    }

    /**
     * Returns a connection to the SQLite database.
     *
     * @return database connection, or null if an error is caught.
     */
    public java.sql.Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
        createDBFile();
        // return connection if it's already established
        if (connection != null && !connection.isClosed()) return connection;

        // Ensure the SQLite library is loaded
        Class.forName("org.sqlite.JDBC");

        // get connection
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbfile);
        connection.setAutoCommit(false);
        return connection;
    }

    /**
     * Closes the current connection to the database
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }

    /**
     * Commits pending changes to the database.
     */
    public void commitChanges() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}