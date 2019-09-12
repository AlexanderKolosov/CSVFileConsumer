/*
 * @application CSV files consuming application
 *
 * @date 09/2019
 * @author Kolosov Alexander
 *
 * (This field is filling according to company demands)
 * */
package net.optimalsolutionshub.csvfileconsumer.view;

import net.optimalsolutionshub.csvfileconsumer.controller.CSVConsumerAppController;

import javax.swing.*;

/*
* Application user interface frame
* */
public class UIFrame extends JFrame {
    private UIPanel appPanel;

    public UIFrame(CSVConsumerAppController csvConsumerApp) {
        appPanel = new UIPanel(csvConsumerApp);
        setUpFrame();
    }

    private void setUpFrame() {
        this.setTitle("CSVFileConsumer");
        this.setContentPane(appPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(500,300);
        this.setVisible(true);
    }

    public UIPanel getAppPanel() {
        return appPanel;
    }
}
