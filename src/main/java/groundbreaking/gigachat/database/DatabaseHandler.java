package groundbreaking.gigachat.database;

import groundbreaking.gigachat.GigaChat;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseHandler {

    private final GigaChat plugin;

    @Getter(AccessLevel.PACKAGE)
    private static Connection connection;

    public DatabaseHandler(final GigaChat plugin) {
        this.plugin = plugin;
    }

    public void createConnection() {
        final File dbFile = this.loadDatabaseFile();
        final String url = "jdbc:sqlite:" + dbFile;

        try {
            connection = DriverManager.getConnection(url);
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
    }

    private File loadDatabaseFile() {
        final File dbFile = new File(this.plugin.getDataFolder() + File.separator + "database.db");
        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return dbFile;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
    }
}
