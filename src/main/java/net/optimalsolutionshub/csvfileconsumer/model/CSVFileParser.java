package net.optimalsolutionshub.csvfileconsumer.model;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class CSVFileParser {
    private static String CSVfilePath = "D:/Java/Test/Interview-task-data-osh.csv";

    /*public static void parseCSVFile() throws IOException, SQLException {
        CSVReader reader = new CSVReader(new FileReader(CSVfilePath));

        String[] nextLine = reader.readNext();
        SQLiteDataBaseFactory.createCustomerXTable(nextLine);
    }
*/
    /*public static void main(String[] args) {
        try {
            CSVFileParser.parseCSVFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
