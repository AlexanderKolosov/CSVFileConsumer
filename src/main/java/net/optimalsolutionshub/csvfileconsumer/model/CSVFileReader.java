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

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

/*
* Receives CSV file, opens input stream and give possibility to CSV file parser to retrieve
* information for further operations.
* */
public class CSVFileReader {
    private CSVConsumerAppController csvConsumerApp;
    private String CSVFilePath;

    public CSVFileReader(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    public void readCSVFile() throws IOException, SQLException {
        try (CSVReader reader = new CSVReader(new FileReader(getCSVFilePath()))) {
                getCSVFileParser().parseCSVFile(reader);
        }
    }

    private String getCSVFilePath() {
        return CSVFilePath;
    }

    private CSVFileParser getCSVFileParser() {
        return csvConsumerApp.getCSVFileParser();
    }

    public void setCSVFilePath(String CSVfilePath) {
        this.CSVFilePath = CSVfilePath;
    }
}
