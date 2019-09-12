/*
 * @application CSV files consuming application
 *
 * @date 09/2019
 * @author Kolosov Alexander
 *
 * (This field is filling according to company demands)
 * */
package net.optimalsolutionshub.csvfileconsumer.model;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import java.io.*;
import java.nio.file.*;
import java.util.logging.*;

/*
* Creates log.config file and .log file. Writes final report in .log file
* */
public class LogFileFactory {
    private CSVConsumerAppController csvConsumerApp;
    private Logger logger;
    private Path logFile;
    private Path configFile;
    private Path filesDirectory;
    private String logFilesDirectory;
    private String absolutePathToLogFile;
    private String absolutePathToConfigFile;

    private static final String configFileName = "log.config";
    private static final String logFileName = "log_file.txt";
    private static final String stringHandlers = "handlers = java.util.logging.FileHandler";
    private static final String stringLevel = "java.util.logging.FileHandler.level = INFO";
    private static final String stringAppend = "java.util.logging.FileHandler.append = true";
    private static final String stringFormatter =
            "java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter";

    public LogFileFactory(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    void insertInformationToLogFile() throws IOException {
        if (configFile == null) {
            createFile(configFileName);
        }
        if (logFile == null) {
            createFile(logFileName);
        }
        fillConfigFile();

        insertInformation();
    }

    private void createFile(String fileName) throws IOException {
        if (filesDirectory == null) {
            createDirectory();
        }

        if (fileName.contains("config")) {
            absolutePathToConfigFile = logFilesDirectory + "/" + fileName;
            if (Files.notExists(Paths.get(absolutePathToConfigFile))) {
                configFile = Files.createFile(Paths.get(absolutePathToConfigFile));
            } else {
                configFile = Paths.get(absolutePathToConfigFile);
            }

        } else {
            absolutePathToLogFile = logFilesDirectory + "/" + fileName;
            if (Files.notExists(Paths.get(absolutePathToLogFile))) {
                logFile = Files.createFile(Paths.get(absolutePathToLogFile));
            } else {
                logFile = Paths.get(absolutePathToLogFile);
            }
        }
    }

    private void createDirectory() throws IOException {
        String dataBasePath = getSqLiteDataBaseFactory().getDataBasePath();

        logFilesDirectory = dataBasePath.substring(0,dataBasePath.lastIndexOf("\\"))+
                "/" + "log-files";
        if (Files.notExists(Paths.get(logFilesDirectory))) {
            filesDirectory = Files.createDirectories(Paths.get(logFilesDirectory));
        } else {
            filesDirectory = Paths.get(logFilesDirectory);
        }
    }

    private void fillConfigFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile.toString()))) {
            writer.write(stringHandlers);
            writer.newLine();
            writer.write(stringLevel);
            writer.newLine();
            writer.write(stringAppend);
            writer.newLine();
            writer.write(stringFormatter);
            writer.newLine();
        }

        loggerGetConfigs();
    }

    private void loggerGetConfigs() {
        try(FileInputStream ins = new FileInputStream(absolutePathToConfigFile)){
            LogManager.getLogManager().readConfiguration(ins);

            logger = Logger.getLogger(LogFileFactory.class.getName());

            FileHandler fileHandler = new FileHandler(logFile.toString());

            logger.addHandler(fileHandler);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void insertInformation() {
        logger.log(Level.INFO," Number of records received - " +
                getCSVFileParser().getNumberOfReceivedRecords());
        logger.log(Level.INFO," Number of records successful - " +
                getCSVFileParser().getNumberOfSuccessfulRecords());
        logger.log(Level.INFO," Number of records failed - " +
                getCSVFileParser().getNumberOfBadRecords());
    }

    private SQLiteDataBaseFactory getSqLiteDataBaseFactory() {
        return csvConsumerApp.getSQLiteDataBaseFactory();
    }

    public String getAbsolutePathToLogFile() {
        return absolutePathToLogFile;
    }

    private CSVFileParser getCSVFileParser() {
        return csvConsumerApp.getCSVFileParser();
    }
}