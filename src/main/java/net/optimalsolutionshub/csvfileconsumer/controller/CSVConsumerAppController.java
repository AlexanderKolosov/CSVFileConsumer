/*
 * @application CSV files consuming application
 *
 * @date 09/2019
 * @author Kolosov Alexander
 *
 * (This field is filling according to company demands)
 * */
package net.optimalsolutionshub.csvfileconsumer.controller;

import net.optimalsolutionshub.csvfileconsumer.model.*;
import net.optimalsolutionshub.csvfileconsumer.view.UIFrame;

/*
* Application controller
* */
public class CSVConsumerAppController {
    private UIFrame appFrame;
    private CSVFileParser csvFileParser;
    private CSVFileReader csvFileReader;
    private CSVFileWriter csvFileWriter;
    private LogFileFactory logFileFactory;
    private SQLiteDataBaseFactory sqLiteDataBaseFactory;

    CSVConsumerAppController() {
        csvFileParser = new CSVFileParser(this);
        csvFileReader = new CSVFileReader(this);
        csvFileWriter = new CSVFileWriter(this);
        logFileFactory = new LogFileFactory(this);
        sqLiteDataBaseFactory = new SQLiteDataBaseFactory(this);
    }

    void start() {
        appFrame = new UIFrame(this);
    }

    public UIFrame getAppFrame() {
        return appFrame;
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

    public SQLiteDataBaseFactory getSQLiteDataBaseFactory() {
        return sqLiteDataBaseFactory;
    }

}
