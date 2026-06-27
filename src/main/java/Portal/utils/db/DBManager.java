package Portal.utils.db;

import Portal.utils.dataReader.PropertyReader;
import Portal.utils.logs.LogsManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBManager — Singleton class responsible for managing the MySQL database connection.
 *
 * Reads all config from src/main/resources/db.properties via PropertyReader.
 *
 * Usage:
 *   Connection conn = DBManager.getInstance().getConnection();
 *   DBManager.getInstance().closeConnection();
 */
public class DBManager {

    private static DBManager instance;
    private Connection connection;

    // Private constructor — Singleton pattern
    private DBManager() {
        // Load all .properties files into System.properties (including db.properties)
        PropertyReader.loadProperties();
    }

    /** Returns the single instance of DBManager */
    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    /**
     * Opens a connection to MySQL if not already open.
     * Reads db.url, db.user, db.password from db.properties
     * @return active Connection object
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url      = PropertyReader.getProperty("db.url");
                String user     = PropertyReader.getProperty("db.user");
                String password = PropertyReader.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
                LogsManager.info("✅ DB Connection established:", PropertyReader.getProperty("db.url"));
            }
        } catch (SQLException e) {
            LogsManager.error("❌ Failed to connect to DB:", e.getMessage());
            throw new RuntimeException("Cannot establish DB connection: " + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * Closes the current database connection.
     * Call this in @AfterClass or @AfterSuite.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    LogsManager.info("🔌 DB Connection closed.");
                }
            } catch (SQLException e) {
                LogsManager.error("❌ Failed to close DB connection:", e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
