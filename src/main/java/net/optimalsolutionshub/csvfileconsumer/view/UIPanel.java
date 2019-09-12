package net.optimalsolutionshub.csvfileconsumer.view;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;
import net.optimalsolutionshub.csvfileconsumer.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class UIPanel extends JPanel{
    private CSVConsumerAppController csvConsumerApp;
    private JLabel dataBaseLabel;
    private JLabel selectCSVFileLabel;
    private JTextField dataBaseAbsolutePath;
    private JButton createDataBase;
    private JButton selectCSVFile;
    private JButton exit;
    private JButton startOperation;
    private SpringLayout baseLayout;
    private SQLiteDataBaseFactory sqLiteDataBaseFactory;

    public UIPanel(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;

        dataBaseLabel = new JLabel("Insert database full path or leave default value");
        selectCSVFileLabel = new JLabel("CSV file NOT selected");
        dataBaseAbsolutePath = new JTextField("C:/sqlite/db/customerXDatabase.db", 30);
        createDataBase = new JButton("Create database");
        selectCSVFile = new JButton("Select CSV file");
        exit = new JButton("EXIT");
        startOperation = new JButton("START OPERATION");

        baseLayout = new SpringLayout();

        setUpPanel();
        setUpLayout();
        setUpListeners();
    }

    private void setUpPanel() {
        this.setSize(this.getWidth(),this.getHeight());
        this.setLayout(baseLayout);
        this.add(dataBaseLabel);
        this.add(dataBaseAbsolutePath);
        this.add(createDataBase);
        this.add(selectCSVFile);
        this.add(selectCSVFileLabel);
        this.add(exit);
    }

    private void setUpLayout() {
        baseLayout.putConstraint(SpringLayout.WEST, dataBaseAbsolutePath,
                10, SpringLayout.WEST, this);
        baseLayout.putConstraint(SpringLayout.NORTH, dataBaseAbsolutePath,
                25, SpringLayout.NORTH, this);
        baseLayout.putConstraint(SpringLayout.WEST, dataBaseLabel,
                10, SpringLayout.WEST, this);
        baseLayout.putConstraint(SpringLayout.NORTH, dataBaseLabel,
                25, SpringLayout.NORTH, dataBaseAbsolutePath);
        baseLayout.putConstraint(SpringLayout.EAST, createDataBase,
                470, SpringLayout.NORTH, this);
        baseLayout.putConstraint(SpringLayout.NORTH, createDataBase,
                20, SpringLayout.NORTH, this);
        baseLayout.putConstraint(SpringLayout.WEST, selectCSVFileLabel,
                10, SpringLayout.WEST, this);
        baseLayout.putConstraint(SpringLayout.NORTH, selectCSVFileLabel,
                70, SpringLayout.NORTH, dataBaseLabel);
        baseLayout.putConstraint(SpringLayout.WEST, selectCSVFile,
                10, SpringLayout.WEST, this);
        baseLayout.putConstraint(SpringLayout.NORTH, selectCSVFile,
                25, SpringLayout.NORTH, selectCSVFileLabel);
        baseLayout.putConstraint(SpringLayout.WEST, exit,
                410, SpringLayout.WEST, this);
        baseLayout.putConstraint(SpringLayout.NORTH, exit,
                100, SpringLayout.NORTH, selectCSVFileLabel);
    }

    private void setUpListeners() {

        createDataBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataBaseAbsolutePathText = dataBaseAbsolutePath.getText();
                getSqLiteDataBaseFactory().setDataBaseAbsolutePath(dataBaseAbsolutePathText);
                getSqLiteDataBaseFactory().createConnectionToDataBase();
                getSqLiteDataBaseFactory().setDatabaseIsCreated(true);
                getSqLiteDataBaseFactory().startOperation();
            }
        });

        selectCSVFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int ret = fileChooser.showDialog(null, "Select");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File csvFile = fileChooser.getSelectedFile();
                    String absolutePath = csvFile.getAbsolutePath();
                    if (absolutePath.endsWith(".csv")) {
                        selectCSVFileLabel.setText(absolutePath);
                        getCSVFileReader().setCSVFilePath(absolutePath);
                        getSqLiteDataBaseFactory().setCsvFileIsSelected(true);
                        getSqLiteDataBaseFactory().startOperation();
                    } else {
                        this.badFileSelectedNotification();
                    }
                }
            }

            private void badFileSelectedNotification() {
                JOptionPane.showMessageDialog(
                        csvConsumerApp.getAppFrame().getAppPanel(),
                        "Wrong file selected! Please, verify extension of the selected" +
                                "file. Extension should be '.csv'",
                        "Bad operation notification",
                        JOptionPane.DEFAULT_OPTION
                );
            }
        });

        startOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getSqLiteDataBaseFactory().vrifyIfTableExists();
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                }
                if (getSqLiteDataBaseFactory().isTableExists()) {
                    try {
                        getCSVFileReader().readCSVFile();
                    } catch (IOException | SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    this.badTableNameNotification();
                }
            }

            public void badTableNameNotification() {
                JOptionPane.showMessageDialog(
                        csvConsumerApp.getAppFrame().getAppPanel(),
                        "Please insert again table name. With letters, only.",
                        "WARNING!",
                        JOptionPane.DEFAULT_OPTION
                );
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                csvConsumerApp.getAppFrame().setVisible(false);
                System.exit(0);
            }
        });
    }

    public void badDirectoryNotification() {
        JOptionPane.showMessageDialog(
                this,
                "Wrong directory selected! Please, verify available disc name or syntax.",
                "Bad operation notification",
                JOptionPane.DEFAULT_OPTION
        );
    }

    public void badDataBaseExtensionNotification() {
        JOptionPane.showMessageDialog(
                this,
                "Wrong data base extension! Extension should be '.db'",
                "Bad operation notification",
                JOptionPane.DEFAULT_OPTION
        );
    }

    public void dataBaseSuccessfullyCreationNotification() {
        JOptionPane.showMessageDialog(
                this,
                "Data base created successfully!",
                "Report",
                JOptionPane.DEFAULT_OPTION
        );
    }

    public void insertTableName() {
        Object result = JOptionPane.showInputDialog(this,
                "Insert table name",
                "",
                JOptionPane.DEFAULT_OPTION
        );
        if (result != null) {
            String tableName = result.toString();
            getSqLiteDataBaseFactory().setTableName(tableName);
        }
    }

    public void showStartOperationButton() {
        this.add(startOperation);
        startOperation.setVisible(true);
        baseLayout.putConstraint(SpringLayout.WEST, startOperation,
                322, SpringLayout.WEST, this);
        baseLayout.putConstraint(SpringLayout.NORTH, startOperation,
                25, SpringLayout.NORTH, selectCSVFileLabel);
    }

    public SQLiteDataBaseFactory getSqLiteDataBaseFactory() {
        return csvConsumerApp.getSQLiteDataBaseFactory();
    }

    public CSVFileReader getCSVFileReader() {
        return csvConsumerApp.getCSVFileReader();
    }

    public CSVFileWriter getCSVFileWriter() {
        return csvConsumerApp.getCSVFileWriter();
    }

    public CSVFileParser getCSVFileParser() {
        return  csvConsumerApp.getCSVFileParser();
    }

    public LogFileFactory getLogFileFactory() {
        return csvConsumerApp.getLogFileFactory();
    }

    public void showFinalInformation() {
        selectCSVFileLabel.setText("CSV file NOT selected");
        startOperation.setVisible(false);
        String badDataFileLocation = getCSVFileWriter().getAbsolutePathToBadDataFile();
        String logFileLocation = getLogFileFactory().getAbsolutePathToLogFile();
        JOptionPane.showMessageDialog(this,
                new String[] {"1. Number of records received - " +
                        getCSVFileParser().getNumberOfReceivedRecords(),
                "2. Number of records successful - " +
                        getCSVFileParser().getNumberOfSuccessfulRecords(),
                "3. Number of records failed - " +
                        getCSVFileParser().getNumberOfBadRecords(),
                "Bad-data file location : " + badDataFileLocation,
                "Log file location : " + logFileLocation},
                "Report",
                JOptionPane.DEFAULT_OPTION);
    }

    public void existTableNotification() {
        Object result = JOptionPane.showInputDialog(this,
                new String[] {"The table '" + getSqLiteDataBaseFactory().getTableName() +
                        "' is already exists. ",
                        "If you are shure that data from your ",
                        "CSV file is compatible with this table ",
                        "please, insert the table name again ",
                        "or choose the other one. ",
                        "Letters only preferable."},
                "WARNING!",
                JOptionPane.DEFAULT_OPTION
        );
        if (result != null) {
            String tableName = result.toString();
            getSqLiteDataBaseFactory().setTableName(tableName);
        }
    }
}
