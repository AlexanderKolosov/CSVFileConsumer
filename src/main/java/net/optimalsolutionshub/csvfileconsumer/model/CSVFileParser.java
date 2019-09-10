package net.optimalsolutionshub.csvfileconsumer.model;

import au.com.bytecode.opencsv.CSVReader;
import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class CSVFileParser {
    private static String CSVfilePath;
    private CSVConsumerAppController csvConsumerApp;

    public CSVFileParser(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    public void parseCSVFile() throws IOException, SQLException {
        CSVReader reader = new CSVReader(new FileReader(CSVfilePath));

        String[] nextLine = reader.readNext();
        csvConsumerApp.getSqLiteDataBaseFactory().createCustomerXTable(nextLine);
    }

    public void setCSVfilePath(String CSVfilePath) {
        CSVFileParser.CSVfilePath = CSVfilePath;
    }
}
