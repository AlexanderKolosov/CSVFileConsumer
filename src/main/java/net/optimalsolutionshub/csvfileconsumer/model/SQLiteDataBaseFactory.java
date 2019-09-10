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
    private String tableName;
    private String dataBaseAbsolutePath;
    private boolean databaseIsCreated = false;
    private boolean csvFileIsSelected = false;
    private CSVConsumerAppController csvConsumerApp;

    public SQLiteDataBaseFactory(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    public void createCustomerXTable(String[] columnHeaders) throws SQLException, IOException {
        getAppPanel().insertTableName();
        String createTableQuery = buildCreateTableQuery(columnHeaders);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createConnectionToCustomerXDataBase() {
        createDataBaseDirectory(dataBaseAbsolutePath);
        setDataBaseName(dataBaseAbsolutePath);
        if (dataBaseName.endsWith(".db")) {
            databaseIsCreated = true;
            String dbUrl = "jdbc:sqlite:" + dataBasePath + "/" + dataBaseName;
            try {
                connection = DriverManager.getConnection(dbUrl);
                startOperation();
                getAppPanel().dataBaseSuccessfullyCreationNotification();
                getAppPanel().updateUI();
            } catch (SQLException e) {
                e.getMessage();
            }
        } else {
            getAppPanel().badDataBaseExtensionNotification();
        }
    }

    private void createDataBaseDirectory(String dataBaseAbsolutePath) {
        Path dataBaseDirectory = null;
        try {
            dataBaseDirectory = Files
                    .createDirectories(Paths
                            .get(dataBaseAbsolutePath
                                    .substring(0, dataBaseAbsolutePath.lastIndexOf("/"))));
        } catch (IOException e) {
            getAppPanel().badDirectoryNotification();
        }
        dataBasePath = dataBaseDirectory.toString();
    }

    public void setDataBaseName(String dataBaseAbsolutePath) {
        dataBaseName = dataBaseAbsolutePath.substring(dataBaseAbsolutePath.lastIndexOf("/"));
    }

    public void startOperation() {
        if (databaseIsCreated && csvFileIsSelected) {
            getAppPanel().showStartOperationButton();
        }
    }

    private String buildCreateTableQuery(String[] columnHeaders) {
        StringBuilder queryBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS "+tableName+"(");
        for (int i = 0; i < columnHeaders.length; i++) {
            queryBuilder.append(columnHeaders[i])
                    .append(" VARCHAR(255) NOT NULL, ");
        }
        String result = queryBuilder.toString();
        String query = result.substring(0,result.length()-2) + ");";

        return query;
    }

    public void setDataBaseAbsolutePath(String dataBaseAbsolutePath) {
        this.dataBaseAbsolutePath = dataBaseAbsolutePath;
    }

    public void setCsvFileIsSelected(boolean csvFileIsSelected) {
        this.csvFileIsSelected = csvFileIsSelected;
    }

    public void setDatabaseIsCreated(boolean databaseIsCreated) {
        this.databaseIsCreated = databaseIsCreated;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public UIPanel getAppPanel() {
        return csvConsumerApp.getAppFrame().getAppPanel();
    }
}
