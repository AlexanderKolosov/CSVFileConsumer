package net.optimalsolutionshub.csvfileconsumer.model;

import au.com.bytecode.opencsv.CSVReader;
import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class CSVFileReader {
    private String CSVFilePath;
    private CSVConsumerAppController csvConsumerApp;


    public CSVFileReader(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    public void readCSVFile() throws IOException, SQLException {
        try (CSVReader reader = new CSVReader(new FileReader(getCSVFilePath()))) {
                getCSVFileParser().parseCSVFile(reader);
        }
    }

    public void setCSVFilePath(String CSVfilePath) {
        this.CSVFilePath = CSVfilePath;
    }

    public String getCSVFilePath() {
        return CSVFilePath;
    }

    public CSVFileParser getCSVFileParser() {
        return csvConsumerApp.getCSVFileParser();
    }
}
