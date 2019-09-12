/*
 * @application CSV files consuming application
 *
 * @date 09/2019
 * @author Kolosov Alexander
 *
 * (This field is filling according to company demands)
 * */
package net.optimalsolutionshub.csvfileconsumer.model;

import au.com.bytecode.opencsv.CSVReader;
import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/*
* Parse CSV file content and transmit collected data to SQLiteDB operator and CSV file writer
* */
public class CSVFileParser {
    private CSVConsumerAppController csvConsumerApp;
    private int numberOfBadRecords;
    private int numberOfReceivedRecords;
    private int numberOfSuccessfulRecords;
    private List<String[]> badStrings = new LinkedList<>();
    private List<String[]> successfulStrings = new LinkedList<>();

    public CSVFileParser(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    void parseCSVFile(CSVReader reader) throws IOException, SQLException {
        String[] nextLine = reader.readNext();
        csvConsumerApp.getSQLiteDataBaseFactory().createTableQuery(nextLine);

        while (nextLine != null) {

            for ( int i = 0; i < 1000; i++ ) {
                nextLine = reader.readNext();
                if (nextLine != null) {
                    if (Arrays.asList(nextLine)
                            .contains("") || nextLine.length != 10) {
                        badStrings.add(nextLine);
                    } else {
                        successfulStrings.add(nextLine);
                    }
                }
            }
            if (!badStrings.isEmpty()) {
                numberOfBadRecords += badStrings.size();
                getCsvFileWriter().writeDataToCSVFile(badStrings);
            }
            if (!successfulStrings.isEmpty()) {
                numberOfSuccessfulRecords += successfulStrings.size();
                getSQLiteDataBaseFactory().insertValuesIntoTableQuery(successfulStrings);
            }
        }
        numberOfReceivedRecords = numberOfBadRecords + numberOfSuccessfulRecords;

        getSQLiteDataBaseFactory().closeConnection();
    }

    private SQLiteDataBaseFactory getSQLiteDataBaseFactory() {
        return csvConsumerApp.getSQLiteDataBaseFactory();
    }

    private CSVFileWriter getCsvFileWriter() {
        return csvConsumerApp.getCSVFileWriter();
    }

    public int getNumberOfReceivedRecords() {
        return numberOfReceivedRecords;
    }

    public int getNumberOfSuccessfulRecords() {
        return numberOfSuccessfulRecords;
    }

    public int getNumberOfBadRecords() {
        return numberOfBadRecords;
    }

    void setBadStrings(List<String[]> badStrings) {
        this.badStrings = badStrings;
    }

    void setSuccessfulStrings(List<String[]> successfulStrings) {
        this.successfulStrings = successfulStrings;
    }

    void setNumberOfReceivedRecords() {
        this.numberOfReceivedRecords = 0;
    }

    void setNumberOfSuccessfulRecords() {
        this.numberOfSuccessfulRecords = 0;
    }

    void setNumberOfBadRecords() {
        this.numberOfBadRecords = 0;
    }
}
