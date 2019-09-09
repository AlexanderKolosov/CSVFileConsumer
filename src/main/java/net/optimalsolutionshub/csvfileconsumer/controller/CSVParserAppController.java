package net.optimalsolutionshub.csvfileconsumer.controller;

import net.optimalsolutionshub.csvfileconsumer.model.CSVFileParser;
import net.optimalsolutionshub.csvfileconsumer.model.SQLiteDBFactory;
import net.optimalsolutionshub.csvfileconsumer.view.UIFrame;

public class CSVParserAppController {
    private UIFrame appFrame;
    private SQLiteDBFactory sqLiteDBFactory;
    private CSVFileParser csvFileParser;

    public UIFrame getAppFrame() {
        return appFrame;
    }

    public SQLiteDBFactory getSqLiteDBFactory() {
        return sqLiteDBFactory;
    }

    public CSVFileParser getCsvFileParser() {
        return csvFileParser;
    }

    public CSVParserAppController() {
        sqLiteDBFactory = new SQLiteDBFactory();
        csvFileParser = new CSVFileParser();
    }

    public void start() {
        appFrame = new UIFrame(this);
    }
}
