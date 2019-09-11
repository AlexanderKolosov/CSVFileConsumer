package net.optimalsolutionshub.csvfileconsumer.controller;

import net.optimalsolutionshub.csvfileconsumer.model.*;
import net.optimalsolutionshub.csvfileconsumer.view.UIFrame;

public class CSVConsumerAppController {
    private UIFrame appFrame;
    private SQLiteDataBaseFactory sqLiteDataBaseFactory;
    private CSVFileParser csvFileParser;
    private CSVFileReader csvFileReader;
    private CSVFileWriter csvFileWriter;
    private LogFileFactory logFileFactory;

    public UIFrame getAppFrame() {
        return appFrame;
    }

    public SQLiteDataBaseFactory getSQLiteDataBaseFactory() {
        return sqLiteDataBaseFactory;
    }

    public CSVFileReader getCSVFileReader() {
        return csvFileReader;
    }

    public CSVFileParser getCSVFileParser() {
        return csvFileParser;
    }

    public CSVFileWriter getCSVFileWriter() {
        return csvFileWriter;
    }

    public LogFileFactory getLogFileFactory() {
        return logFileFactory;
    }

    public CSVConsumerAppController() {
        sqLiteDataBaseFactory = new SQLiteDataBaseFactory(this);
        csvFileParser = new CSVFileParser(this);
        csvFileReader = new CSVFileReader(this);
        csvFileWriter = new CSVFileWriter(this);
        logFileFactory = new LogFileFactory(this);
    }

    public void start() {
        appFrame = new UIFrame(this);
    }
}
