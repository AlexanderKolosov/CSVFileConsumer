/*
 * @application CSV files consuming application
 *
 * @date 09/2019
 * @author Kolosov Alexander
 *
 * (This field is filling according to company demands)
 * */
package net.optimalsolutionshub.csvfileconsumer.model;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;
import net.optimalsolutionshub.csvfileconsumer.view.UIPanel;

import java.io.IOException;
import java.nio.file.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
* SQLite data base operator. Receives commands from user interface panel also, receives data from
* CSV file parser. All necessary communication with data base happens here.
* */
public class SQLiteDataBaseFactory {
    private CSVConsumerAppController csvConsumerApp;
    private Connection connection;
    private String tableName;
    private String dataBaseName;
    private String dataBasePath;
    private String dataBaseAbsolutePath;
    private String tableColumnHeaders = "";
    private boolean tableExists = false;
    private boolean databaseIsCreated = false;
    private boolean csvFileIsSelected = false;

    private static final String[] testHeader = new String[]{"A"};
    private static final String[] testValue = new String[]{"A"};
    private static final List<String[]> testListOfValues = new ArrayList<>();

    static {
        testListOfValues.add(testValue);
    }

    public SQLiteDataBaseFactory(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    public void createConnectionToDataBase() {
        createDataBaseDirectory(dataBaseAbsolutePath);
        setDataBaseName(dataBaseAbsolutePath);

        if (dataBaseName.endsWith(".db")) {
            databaseIsCreated = true;
            String dbUrl = "jdbc:sqlite:" + dataBasePath + "/" + dataBaseName;

            try {
                connection = DriverManager.getConnection(dbUrl);
                startOperation();
                getAppPanel().dataBaseSuccessfulCreatedNotification();
                getAppPanel().updateUI();
            } catch (SQLException e) {
                e.getMessage();
            }
        } else {
            getAppPanel().badDataBaseExtensionNotification();
        }
    }

    private void createDataBaseDirectory(String dataBaseAbsolutePath) {
        try {
            Path dataBaseDirectory = Files
                    .createDirectories(Paths
                            .get(dataBaseAbsolutePath
                                    .substring(0, dataBaseAbsolutePath.lastIndexOf("/"))));
            dataBasePath = dataBaseDirectory.toString();
        } catch (IOException e) {
            getAppPanel().badDirectoryNotification();
        }
    }

    public void startOperation() {
        if (databaseIsCreated && csvFileIsSelected) {
            getAppPanel().showStartOperationButton();
        }
    }

    public void verifyIfTableExists() throws SQLException {
        getAppPanel().insertTableNameProposition();

        int value = countRowsInTheTableQuery();
        if (value > 0) {
            tableExists = true;
            getAppPanel().tableExistsNotification();
        } else {
            dropTableQuery();
            createTableQuery(testHeader);
            insertValuesIntoTableQuery(testListOfValues);
            value = countRowsInTheTableQuery();
            if (value > 0) {
                dropTableQuery();
                tableExists = true;
            }
            tableColumnHeaders = "";
        }
    }

    private int countRowsInTheTableQuery() throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName + ";";

        Statement statement = null;
        int value = 0;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            value = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
        return value;
    }

    private void closeStatement(Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    private void dropTableQuery() throws SQLException {
        String query = "DROP TABLE IF EXISTS " + tableName + ";";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    void createTableQuery(String[] columnHeaders) throws SQLException {
        String createTableQuery = buildCreateTableQuery(columnHeaders);

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    private String buildCreateTableQuery(String[] columnHeaders) {
        StringBuilder queryBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS "+tableName+"(");

        for ( String columnHeader : columnHeaders ) {
            tableColumnHeaders += columnHeader + ",";
            queryBuilder.append(columnHeader)
                    .append(" VARCHAR(255) NOT NULL, ");
        }

        tableColumnHeaders = tableColumnHeaders.substring(0, tableColumnHeaders.length() - 1);

        String queryResult = queryBuilder.toString();

        return queryResult.substring(0,queryResult.length()-2) + ");";
    }

    void insertValuesIntoTableQuery(List<String[]> goodStrings) throws SQLException {
        String query = createInsertValuesIntoTableQuery(goodStrings);

        Statement statement = null;
        if (!connection.isClosed()) {
            try {
                statement = connection.createStatement();
                statement.executeUpdate(query);
                getCSVFleParser().setSuccessfulStrings(new ArrayList<String[]>());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeStatement(statement);
            }
        }
    }

    private String createInsertValuesIntoTableQuery(List<String[]> values) {
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
        String startOfTheValue;
        String endOfTheValue;
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

    void closeConnection() throws SQLException, IOException {
        if (connection != null) {
            connection.close();
        }
        getLogFileFactory().insertInformationToLogFile();
        getAppPanel().showFinalInformation();
        prepareAppForFurtherWork();
    }

    private void prepareAppForFurtherWork() {
        tableColumnHeaders = "";
        tableName = "";
        databaseIsCreated = false;
        csvFileIsSelected = false;
        tableExists = false;
        getCSVFileReader().setCSVFilePath("");
        getCSVFleParser().setNumberOfBadRecords();
        getCSVFleParser().setNumberOfReceivedRecords();
        getCSVFleParser().setNumberOfSuccessfulRecords();
        getCSVFileWriter().setBadDataFile();
    }

    private UIPanel getAppPanel() {
        return csvConsumerApp.getAppFrame().getAppPanel();
    }

    String getDataBasePath() {
        return dataBasePath;
    }

    private CSVFileParser getCSVFleParser() {
        return csvConsumerApp.getCSVFileParser();
    }

    private CSVFileReader getCSVFileReader() {
        return csvConsumerApp.getCSVFileReader();
    }

    private LogFileFactory getLogFileFactory() {
        return csvConsumerApp.getLogFileFactory();
    }

    public boolean isTableExists() {
        return tableExists;
    }

    public String getTableName() {
        return tableName;
    }

    private CSVFileWriter getCSVFileWriter() {
        return csvConsumerApp.getCSVFileWriter();
    }
    private void setDataBaseName(String dataBaseAbsolutePath) {
        dataBaseName = dataBaseAbsolutePath.substring(dataBaseAbsolutePath.lastIndexOf("/"));
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
}
