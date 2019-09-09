package net.optimalsolutionshub.csvfileconsumer.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDBFactory {

    private static Connection connection;
    private static Statement statement;
    private static String dbName = "customerXDatabase.db";
    private static String dbPath = "C:/sqlite/db/";
    private static String tableName = "customerXTable";

    public static void createCustomerXTable(String[] columnHeaders) throws SQLException, IOException {
        createConnectionToCustomerXDataBase();
        String createTableQuery = buildCreateTableQuery(columnHeaders);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection createConnectionToCustomerXDataBase() throws IOException {
        createDataBaseDirectory();
        String dbUrl = "jdbc:sqlite:" + dbPath + dbName;
        try {
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    private static void createDataBaseDirectory() throws IOException {
        Path CSVFileLocation = Files.createDirectories(Paths.get(dbPath));
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
