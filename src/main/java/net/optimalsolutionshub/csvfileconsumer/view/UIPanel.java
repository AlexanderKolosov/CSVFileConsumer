package net.optimalsolutionshub.csvfileconsumer.view;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;
import net.optimalsolutionshub.csvfileconsumer.model.CSVFileParser;
import net.optimalsolutionshub.csvfileconsumer.model.SQLiteDataBaseFactory;

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
    private JLabel processNotification;
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
        processNotification = new JLabel();
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
        this.setSize(500,300);
        this.setLayout(baseLayout);
        this.add(dataBaseLabel);
        this.add(dataBaseAbsolutePath);
        this.add(createDataBase);
        this.add(selectCSVFile);
        this.add(selectCSVFileLabel);
        this.add(exit);
        this.add(processNotification);
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
        baseLayout.putConstraint(SpringLayout.WEST, processNotification,
                10, SpringLayout.WEST, this);
        baseLayout.putConstraint(SpringLayout.NORTH, processNotification,
                95, SpringLayout.NORTH, selectCSVFileLabel);
    }

    private void setUpListeners() {

        createDataBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataBaseAbsolutePathText = dataBaseAbsolutePath.getText();
                getSqLiteDataBaseFactory().setDataBaseAbsolutePath(dataBaseAbsolutePathText);
                getSqLiteDataBaseFactory().createConnectionToCustomerXDataBase();
                getSqLiteDataBaseFactory().setDatabaseIsCreated(true);
                getSqLiteDataBaseFactory().startOperation();
            }
        });

        selectCSVFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Select");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File csvFile = fileopen.getSelectedFile();
                    String absolutePath = csvFile.getAbsolutePath();
                    if (absolutePath.endsWith(".csv")) {
                        selectCSVFileLabel.setText(absolutePath);
                        getCSVFileParser().setCSVfilePath(absolutePath);
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
                    getCSVFileParser().parseCSVFile();
                    processNotification.setText("Application in process. Please, wait.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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
                "Wrong directory selected! Please, verify available disc name and syntax " +
                        "like on the example below.",
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
                "",
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
        baseLayout.putConstraint(SpringLayout.WEST, startOperation,
                322, SpringLayout.WEST, this);
        baseLayout.putConstraint(SpringLayout.NORTH, startOperation,
                25, SpringLayout.NORTH, selectCSVFileLabel);
    }

    public SQLiteDataBaseFactory getSqLiteDataBaseFactory() {
        return csvConsumerApp.getSqLiteDataBaseFactory();
    }

    public CSVFileParser getCSVFileParser() {
        return csvConsumerApp.getCSVFileParser();
    }
}
