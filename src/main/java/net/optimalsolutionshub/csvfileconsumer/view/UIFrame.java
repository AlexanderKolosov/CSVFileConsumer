package net.optimalsolutionshub.csvfileconsumer.view;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import javax.swing.*;

public class UIFrame extends JFrame {
    private UIPanel appPanel;

    public UIFrame(CSVConsumerAppController csvConsumerApp) {
        appPanel = new UIPanel(csvConsumerApp);
        setUpFrame();
    }

    public UIPanel getAppPanel() {
        return appPanel;
    }

    private void setUpFrame() {
        this.setTitle("CSVFileConsumer");
        this.setContentPane(appPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,300);
        this.setVisible(true);
    }
}
