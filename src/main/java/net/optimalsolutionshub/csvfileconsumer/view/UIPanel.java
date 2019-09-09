package net.optimalsolutionshub.csvfileconsumer.view;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import javax.swing.*;

public class UIPanel extends JPanel{
    private CSVConsumerAppController csvConsumerApp;
    private JLabel dataBaseLabel;
    private JLabel selectCSVFileLabel;
    private JTextField dataBaseFullPath;
    private JButton createDataBase;
    private JButton selectCSVFile;
    private JButton exit;
    private SpringLayout baseLayout;

    public UIPanel(CSVConsumerAppController csvConsumerApp) {
        this.csvConsumerApp = csvConsumerApp;

        dataBaseLabel = new JLabel("Insert database full path or leave default value");
        selectCSVFileLabel = new JLabel("CSV file NOT selected");
        dataBaseFullPath = new JTextField("C:/sqlite/db/customerXDatabase.db", 30);
        createDataBase = new JButton("Create database");
        selectCSVFile = new JButton("Select CSV file");
        exit = new JButton("EXIT");

        baseLayout = new SpringLayout();

        setUpPanel();
        setUpLayout();
        setUpListeners();
    }

    private void setUpPanel() {
        this.setSize(500,230);
        this.setLayout(baseLayout);
        this.add(dataBaseLabel);
        this.add(dataBaseFullPath);
        this.add(createDataBase);
        this.add(selectCSVFile);
        this.add(selectCSVFileLabel);
        this.add(exit);
    }

    private void setUpLayout() {
        baseLayout.putConstraint(SpringLayout.WEST, dataBaseFullPath,
                10, SpringLayout.WEST, this);
        baseLayout.putConstraint(SpringLayout.NORTH, dataBaseFullPath,
                25, SpringLayout.NORTH, this);
        baseLayout.putConstraint(SpringLayout.WEST, dataBaseLabel,
                10, SpringLayout.WEST, this);
        baseLayout.putConstraint(SpringLayout.NORTH, dataBaseLabel,
                25, SpringLayout.NORTH, dataBaseFullPath);
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
                25, SpringLayout.NORTH, selectCSVFileLabel);
    }

    private void setUpListeners() {
    }
}
