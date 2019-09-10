package net.optimalsolutionshub.csvfileconsumer.controller;

import net.optimalsolutionshub.csvfileconsumer.model.CSVFileParser;
import net.optimalsolutionshub.csvfileconsumer.model.SQLiteDataBaseFactory;
import net.optimalsolutionshub.csvfileconsumer.view.UIFrame;

public class CSVConsumerAppController {
    private UIFrame appFrame;
    private SQLiteDataBaseFactory sqLiteDataBaseFactory;
    private CSVFileParser csvFileParser;

    public UIFrame getAppFrame() {
        return appFrame;
    }

    public SQLiteDataBaseFactory getSqLiteDataBaseFactory() {
        return sqLiteDataBaseFactory;
    }

    public CSVFileParser getCsvFileParser() {
        return csvFileParser;
    }

    public void setSqLiteDataBaseFactory(SQLiteDataBaseFactory sqLiteDataBaseFactory) {
        this.sqLiteDataBaseFactory = sqLiteDataBaseFactory;
    }

    public CSVConsumerAppController() {
        csvFileParser = new CSVFileParser();
    }

    public void start() {
        appFrame = new UIFrame(this);
    }
}
