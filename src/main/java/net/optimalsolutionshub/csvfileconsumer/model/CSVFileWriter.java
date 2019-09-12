/*
 * @application CSV files consuming application
 *
 * @date 09/2019
 * @author Kolosov Alexander
 *
 * (This field is filling according to company demands)
 * */
package net.optimalsolutionshub.csvfileconsumer.model;

import au.com.bytecode.opencsv.CSVWriter;
import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

/*
* Receives list of bad data creates .csv file and write bad data to created .csv file
* */
public class CSVFileWriter {
    private CSVConsumerAppController csvConsumerApp;
    private Path badDataFile;
    private String absolutePathToBadDataFile;

    public CSVFileWriter(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    void writeDataToCSVFile(List<String[]> badData) throws IOException {
        if (badDataFile == null) {
            createBadDataFile();
        }
        try (CSVWriter writer = new CSVWriter(
                new FileWriter(badDataFile.toString(), true),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.NO_ESCAPE_CHARACTER))
        {
            formatStrings(badData);
            writer.writeAll(badData);
        }
        getCSVFileParser().setBadStrings(new ArrayList<String[]>());
    }

    private void createBadDataFile() {
        try {
            badDataFile = Files.createFile(Paths.get(createAbsoluteFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createAbsoluteFilePath() throws IOException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd_'at'_HH-mm-ss",Locale.ENGLISH);

        String dataBasePath = getSqLiteDataBaseFactory().getDataBasePath();
        String badDataFilesDirectory=dataBasePath.substring(0,dataBasePath.lastIndexOf("\\"))+
                "\\" + "bad-data-files";
        Files.createDirectories(Paths.get(badDataFilesDirectory));

        Date currentDate = new Date();
        String badDataFileName = "bad-data-" + sdf.format(currentDate) + ".csv";

        absolutePathToBadDataFile = badDataFilesDirectory + "/" + badDataFileName;

        return absolutePathToBadDataFile;
    }

    private void formatStrings(List<String[]> badStrings) {
        for (String[] values : badStrings) {
            for (int i = 1; i < values.length; i++) {
                if (i == 4 && !values[i].equals("")) {
                    values[i] = String.format("\"%s\"",values[i]);
                }
            }
        }
    }

    private CSVFileParser getCSVFileParser() {
        return csvConsumerApp.getCSVFileParser();
    }

    private SQLiteDataBaseFactory getSqLiteDataBaseFactory() {
        return csvConsumerApp.getSQLiteDataBaseFactory();
    }

    public String getAbsolutePathToBadDataFile() {
        return absolutePathToBadDataFile;
    }

    void setBadDataFile() {
        this.badDataFile = null;
    }
}
