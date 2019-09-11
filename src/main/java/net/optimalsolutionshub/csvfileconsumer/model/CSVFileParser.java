package net.optimalsolutionshub.csvfileconsumer.model;

import au.com.bytecode.opencsv.CSVReader;
import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;
import net.optimalsolutionshub.csvfileconsumer.view.UIPanel;
import org.sqlite.SQLiteException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CSVFileParser {
    private CSVConsumerAppController csvConsumerApp;
    private List<String[]> badStrings = new LinkedList<>();
    private List<String[]> successfulStrings = new LinkedList<>();
    private int numberOfReceivedRecords;
    private int numberOfSuccessfulRecords;
    private int numberOfBadRecords;

    public CSVFileParser(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    public void parseCSVFile(CSVReader reader) throws IOException, SQLException {
        String[] nextLine = reader.readNext();
        csvConsumerApp.getSQLiteDataBaseFactory().createTable(nextLine);

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
                getCsvFileWriter().writeValuesToCSVFile(badStrings);
            }
            if (!successfulStrings.isEmpty()) {
                numberOfSuccessfulRecords += successfulStrings.size();
                getSQLiteDataBaseFactory().insertValuesIntoTable(successfulStrings);
            }
        }
        numberOfReceivedRecords = numberOfBadRecords + numberOfSuccessfulRecords;

        getSQLiteDataBaseFactory().closeConnection();
    }

    public CSVFileReader getCSVFileReader() {
        return csvConsumerApp.getCSVFileReader();
    }

    public CSVFileWriter getCsvFileWriter() {
        return csvConsumerApp.getCSVFileWriter();
    }

    public SQLiteDataBaseFactory getSQLiteDataBaseFactory() {
        return csvConsumerApp.getSQLiteDataBaseFactory();
    }

    public void setBadStrings(List<String[]> badStrings) {
        this.badStrings = badStrings;
    }

    public void setSuccessfulStrings(List<String[]> successfulStrings) {
        this.successfulStrings = successfulStrings;
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

    private LogFileFactory getLogFile() {
        return csvConsumerApp.getLogFileFactory();
    }

    public void setNumberOfReceivedRecords(int numberOfReceivedRecords) {
        this.numberOfReceivedRecords = numberOfReceivedRecords;
    }

    public void setNumberOfSuccessfulRecords(int numberOfSuccessfulRecords) {
        this.numberOfSuccessfulRecords = numberOfSuccessfulRecords;
    }

    public void setNumberOfBadRecords(int numberOfBadRecords) {
        this.numberOfBadRecords = numberOfBadRecords;
    }

    private UIPanel getAppPanel() {
        return csvConsumerApp.getAppFrame().getAppPanel();
    }
}
