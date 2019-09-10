package net.optimalsolutionshub.csvfileconsumer.model;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;
import net.optimalsolutionshub.csvfileconsumer.view.UIPanel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDataBaseFactory {

    private static Connection connection;
    private static Statement statement;
    private String dataBaseName;
    private String dataBasePath;
    private static String tableName = "customerXTable";
    private String dataBaseAbsolutePath;
    private UIPanel uiPanel;
    private CSVConsumerAppController csvConsumerApp;

    public SQLiteDataBaseFactory(CSVConsumerAppController csvConsumerApp, String dataBaseAbsolutePathText) {
        this.csvConsumerApp = csvConsumerApp;
        dataBaseAbsolutePath = dataBaseAbsolutePathText;

        createConnectionToCustomerXDataBase();
    }

    /*public static void createCustomerXTable(String[] columnHeaders) throws SQLException, IOException {
        createConnectionToCustomerXDataBase();
        String createTableQuery = buildCreateTableQuery(columnHeaders);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    private Connection createConnectionToCustomerXDataBase() {
        createDataBaseDirectory(dataBaseAbsolutePath);
        setDataBaseName(dataBaseAbsolutePath);
        if (dataBaseName.endsWith(".db")) {
            String dbUrl = "jdbc:sqlite:" + dataBasePath + "/" + dataBaseName;
            try {
                connection = DriverManager.getConnection(dbUrl);
                csvConsumerApp.getAppFrame().getAppPanel().dataBaseSuccessfullyCreationNotification();
            } catch (SQLException e) {
                e.getMessage();
            }
        } else {
            csvConsumerApp.getAppFrame().getAppPanel().badDataBaseExtensionNotification();
        }
        return connection;
    }

    private void createDataBaseDirectory(String dataBaseAbsolutePath) {
        Path dataBaseDirectory = null;
        try {
            dataBaseDirectory = Files
                    .createDirectories(Paths
                            .get(dataBaseAbsolutePath
                                    .substring(0, dataBaseAbsolutePath.lastIndexOf("/"))));
        } catch (IOException e) {
            csvConsumerApp.getAppFrame().getAppPanel().badDirectoryNotification();
        }
        dataBasePath = dataBaseDirectory.toString();
    }

    public void setDataBaseName(String dataBaseAbsolutePath) {
        dataBaseName = dataBaseAbsolutePath.substring(dataBaseAbsolutePath.lastIndexOf("/"));
    }

    private static String buildCreateTableQuery(String[] columnHeaders) {
        StringBuilder queryBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS "+tableName+"(");
        for (int i = 0; i < columnHeaders.length; i++) {
            queryBuilder.append(columnHeaders[i])
                    .append(" VARCHAR(255) NOT NULL, ");
        }
        String result = queryBuilder.toString();
        String query = result.substring(0,result.length()-2) + ");";

        return query;
    }
}
