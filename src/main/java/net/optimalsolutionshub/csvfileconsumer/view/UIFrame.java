package net.optimalsolutionshub.csvfileconsumer.view;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import javax.swing.*;

public class UIFrame extends JFrame {
        private UIPanel uiPanel;

    public UIFrame(CSVConsumerAppController csvConsumerApp) {
        uiPanel = new UIPanel(csvConsumerApp);
        setUpFrame();
    }

    private void setUpFrame() {
        this.setTitle("CSVFileConsumer");
        this.setContentPane(uiPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,230);
        this.setVisible(true);
    }
}
