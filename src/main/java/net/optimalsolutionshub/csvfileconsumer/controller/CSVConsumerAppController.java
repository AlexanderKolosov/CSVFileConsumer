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

    public CSVFileParser getCSVFileParser() {
        return csvFileParser;
    }

    public CSVConsumerAppController() {
        sqLiteDataBaseFactory = new SQLiteDataBaseFactory(this);
        csvFileParser = new CSVFileParser(this);
    }

    public void start() {
        appFrame = new UIFrame(this);
    }
}
