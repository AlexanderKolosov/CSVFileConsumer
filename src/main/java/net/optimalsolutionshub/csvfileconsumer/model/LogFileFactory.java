package net.optimalsolutionshub.csvfileconsumer.model;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogFileFactory {
    private static final String stringHandlers = "handlers = java.util.logging.FileHandler";
    private static final String stringLevel = "java.util.logging.FileHandler.level = INFO";
    private static final String stringAppend = "java.util.logging.FileHandler.append = true";
    private static final String stringFormatter =
            "java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter";

    private Logger LOGGER;
    private Path logFile;
    private CSVConsumerAppController csvConsumerApp;
    private Path configFile;
    private Path filesDirectory;
    private static final String configFileName = "log.config";
    private static final String logFileName = "log_file.txt";
    private String absolutePathToConfigFile;
    private String absolutePathToLogFile;
    private String logFilesDirectory;

    public LogFileFactory(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;
    }

    public void insertInformationToLogFile() throws IOException {
        if (configFile == null) {
            createFile(configFileName);
        }
        if (logFile == null) {
            createFile(logFileName);
        }
        fillConfigFile();

        insertInformation();
    }

    public void createFile(String fileName) throws IOException {
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
        logFilesDirectory=dataBasePath.substring(0,dataBasePath.lastIndexOf("\\"))+
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
            LOGGER = Logger.getLogger(LogFileFactory.class.getName());
            FileHandler fileHandler = new FileHandler(logFile.toString());
            LOGGER.addHandler(fileHandler);
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    private void insertInformation() {
        LOGGER.log(Level.INFO," Number of records received - " +
                getCSVFileParser().getNumberOfReceivedRecords());
        LOGGER.log(Level.INFO," Number of records successful - " +
                getCSVFileParser().getNumberOfSuccessfulRecords());
        LOGGER.log(Level.INFO," Number of records failed - " +
                getCSVFileParser().getNumberOfBadRecords());
    }


    private SQLiteDataBaseFactory getSqLiteDataBaseFactory() {
        return csvConsumerApp.getSQLiteDataBaseFactory();
    }

    public Logger getLOGGER() {
        return LOGGER;
    }

    public String getAbsolutePathToLogFile() {
        return absolutePathToLogFile;
    }

    private CSVFileParser getCSVFileParser() {
        return csvConsumerApp.getCSVFileParser();
    }

    private CSVFileWriter getCSVFileWriter() {
        return csvConsumerApp.getCSVFileWriter();
    }
}