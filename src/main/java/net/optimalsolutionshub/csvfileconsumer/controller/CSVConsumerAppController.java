package net.optimalsolutionshub.csvfileconsumer.controller;

import net.optimalsolutionshub.csvfileconsumer.model.CSVFileParser;
import net.optimalsolutionshub.csvfileconsumer.model.CSVFileReader;
import net.optimalsolutionshub.csvfileconsumer.model.CSVFileWriter;
import net.optimalsolutionshub.csvfileconsumer.model.SQLiteDataBaseFactory;
import net.optimalsolutionshub.csvfileconsumer.view.UIFrame;

public class CSVConsumerAppController {
    private UIFrame appFrame;
    private SQLiteDataBaseFactory sqLiteDataBaseFactory;
    private CSVFileParser csvFileParser;
    private CSVFileReader csvFileReader;
    private CSVFileWriter csvFileWriter;

    public UIFrame getAppFrame() {
        return appFrame;
    }

    public SQLiteDataBaseFactory getSqLiteDataBaseFactory() {
        return sqLiteDataBaseFactory;
    }

    public CSVFileReader getCSVFileReader() {
        return csvFileReader;
    }

    public CSVFileParser getCSVFileParser() {
        return csvFileParser;
    }

    public CSVFileWriter getCsvFileWriter() {
        return csvFileWriter;
    }

    public CSVConsumerAppController() {
        sqLiteDataBaseFactory = new SQLiteDataBaseFactory(this);
        csvFileParser = new CSVFileParser(this);
        csvFileReader = new CSVFileReader(this);
        csvFileWriter = new CSVFileWriter(this);
    }

    public void start() {
        appFrame = new UIFrame(this);
    }
}
