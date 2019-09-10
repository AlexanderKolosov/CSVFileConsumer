package net.optimalsolutionshub.csvfileconsumer.model;

import au.com.bytecode.opencsv.CSVWriter;
import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CSVFileWriter {
    private CSVConsumerAppController csvConsumerApp;
    private Path badDataFile;

    public CSVFileWriter(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    public void writeValuesToCSVFile(List<String[]> badStrings) throws IOException {
        if (Files.notExists(badDataFile)) {
            createBadDataFile();
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(badDataFile.toString(), true))) {
            writer.writeAll(badStrings);
        }

    }

    private void createBadDataFile() {
        try {
            badDataFile = Files.createFile(Paths.get(createAbsoluteFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createAbsoluteFilePath() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss",Locale.ENGLISH);

        String dataBasePath = getSqLiteDataBaseFactory().getDataBasePath();
        String badDataFilesDirectory = dataBasePath.substring(0,dataBasePath.lastIndexOf("/")) +
                "/" + "bad-data-files";
        Path directories = Files.createDirectories(Paths.get(badDataFilesDirectory));

        Date currentDate = new Date();
        String badDataFileName = sdf.format(currentDate) + ".csv";

        String absolutePathToBadDataFile = badDataFilesDirectory + "/" + badDataFileName;

        return absolutePathToBadDataFile;
    }

    public SQLiteDataBaseFactory getSqLiteDataBaseFactory() {
        return csvConsumerApp.getSqLiteDataBaseFactory();
    }
}
