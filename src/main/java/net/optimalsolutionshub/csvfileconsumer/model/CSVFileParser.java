package net.optimalsolutionshub.csvfileconsumer.model;

import au.com.bytecode.opencsv.CSVReader;
import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CSVFileParser {
    private CSVConsumerAppController csvConsumerApp;
    private List<String[]> badStrings = new LinkedList<>();

    public CSVFileParser(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    public void parseCSVFile(CSVReader reader) throws IOException, SQLException {
        String[] nextLine = reader.readNext();
        csvConsumerApp.getSqLiteDataBaseFactory().createCustomerXTable(nextLine);
        /*while (nextLine != null)*/ for (int i = 0; i < 10; i++) {
            nextLine = reader.readNext();
            if (Arrays.toString(nextLine).contains(",,") || nextLine.length < 10) {
                badStrings.add(nextLine);
                if (badStrings.size() == 100000) {
                    getCsvFileWriter().writeValuesToCSVFile(badStrings);
                    badStrings = new ArrayList<>();
                }
            } else {
                getSQLiteDataBaseFactory().insertValuesIntoTable(nextLine);
            }
        }
        if (!badStrings.isEmpty()) {
            getCsvFileWriter().writeValuesToCSVFile(badStrings);
            badStrings = new ArrayList<>();
            getSQLiteDataBaseFactory().closeConnection();
        }
    }

    public CSVFileReader getCSVFileReader() {
        return csvConsumerApp.getCSVFileReader();
    }

    public CSVFileWriter getCsvFileWriter() {
        return csvConsumerApp.getCsvFileWriter();
    }

    public SQLiteDataBaseFactory getSQLiteDataBaseFactory() {
        return csvConsumerApp.getSqLiteDataBaseFactory();
    }


}
