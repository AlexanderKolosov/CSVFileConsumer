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
import java.util.List;

public class SQLiteDataBaseFactory {

    private static Connection connection;
    private String dataBaseName;
    private String dataBasePath;
    private String tableName;
    private String dataBaseAbsolutePath;
    private String tableColumnHeaders = "";
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
            Statement statement = connection.createStatement();
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
            tableColumnHeaders += columnHeaders[i] + ",";
            queryBuilder.append(columnHeaders[i])
                    .append(" VARCHAR(255) NOT NULL, ");
        }
        tableColumnHeaders = tableColumnHeaders.substring(0, tableColumnHeaders.length() - 1);
        String queryResult = queryBuilder.toString();

        return queryResult.substring(0,queryResult.length()-2) + ");";
    }

    public void insertValuesIntoTable(List<String[]> goodStrings) {
        String query = createInsertQuery(goodStrings);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String createInsertQuery(List<String[]> values) {
        StringBuilder queryBuilder = new StringBuilder(
                "INSERT INTO " + tableName + "(" + tableColumnHeaders + ") VALUES");
        for (String[] value : values) {
            queryBuilder.append("(");
            for ( int i = 0; i < value.length; i++ ) {
                if (i == 4) {
                    queryBuilder.append("'\"")
                            .append(value[i])
                            .append("\"',");
                } else {
                    if (value[i].contains("'")) {
                        int index = value[i].indexOf("'");
                        value[i] = buildValidValue(value[i], index);
                    }
                    queryBuilder.append("'")
                            .append(value[i])
                            .append("',");
                    value[i] = "";
                }
            }
            queryBuilder.deleteCharAt(queryBuilder.length()-1);
            queryBuilder.append("), ");
        }

        return queryBuilder.substring(0, queryBuilder.length()-2) + ";";
    }

    private String buildValidValue(String value, int index) {
        String startOfTheValue = "";
        String endOfTheValue = "";
        if (index == 0) {
            endOfTheValue = value;
            value = "'" + endOfTheValue;
            if (value.substring(index + 2)
                    .contains("'")) {
                value = buildValidValue(value, index + 2);
            }
        } else if (index == value.length() - 1) {
            startOfTheValue = value;
            value = startOfTheValue + "'";
        } else {
            index = value.indexOf("'");
            startOfTheValue = value.substring(0, index);
            endOfTheValue = value.substring(index);
            value = startOfTheValue + "'" + endOfTheValue;
            if (value.substring(index + 2)
                    .contains("'")) {
                value = buildValidValue(value, index + 2);
            }
        }

        return value;
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

    public String getDataBasePath() {
        return dataBasePath;
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        getAppPanel().showFinalInformation();
        prepareAppForFurtherWork();
    }

    private void prepareAppForFurtherWork() {
        tableColumnHeaders = "";
        databaseIsCreated = false;
        csvFileIsSelected = false;
        getCSVFileReader().setCSVFilePath("");
    }

    private CSVFileReader getCSVFileReader() {
        return csvConsumerApp.getCSVFileReader();
    }
}
