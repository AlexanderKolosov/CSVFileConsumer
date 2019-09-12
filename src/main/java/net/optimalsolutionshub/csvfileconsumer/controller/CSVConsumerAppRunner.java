/*
 * @application CSV files consuming application
 *
 * @date 09/2019
 * @author Kolosov Alexander
 *
 * (This field is filling according to company demands)
 * */
package net.optimalsolutionshub.csvfileconsumer.controller;

/*
* Application start point
* CSV files consuming application destination is to retrieve information from CSV files, to verify
* its state, to insert good data to SQLite data base and badData to .csv file.
* */
public class CSVConsumerAppRunner {

    public static void main(String[] args) {
        CSVConsumerAppController csvConsumerApp = new CSVConsumerAppController();
        csvConsumerApp.start();
    }
}
