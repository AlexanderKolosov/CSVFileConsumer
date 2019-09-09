package net.optimalsolutionshub.csvfileconsumer.view;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVParserAppController;

import javax.swing.*;

public class UIFrame extends JFrame {
    private UIPanel uiPanel;

    public UIFrame(CSVParserAppController csvParserApp) {
        uiPanel = new UIPanel(csvParserApp);
        setUpFrame();
    }

    private void setUpFrame() {
        this.setContentPane(uiPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setVisible(true);
    }
}
