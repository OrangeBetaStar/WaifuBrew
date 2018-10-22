package start.PaneFrame;

// Same looking as SaveLoadPane, but clicking on SaveLoadBox

import start.CustomObjects.CustomButton;
import start.CustomObjects.MasterHandlerClass;
import start.Loader.WaifuBrew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SavePane extends JPanel {

    private CustomButton.SaveLoadBox saveLoadBox = new CustomButton.SaveLoadBox();
    private boolean frameRateDisable = false;

    SavePane() {
        // super();
        init();
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private class Handlerclass extends MasterHandlerClass {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        saveLoadBox.paintComponent(g);

    }

    public void init() {
        Timer t = new Timer((int) (1000 / WaifuBrew.getInstance().getFrameRate()), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!frameRateDisable) {
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        t.setRepeats(true);
        t.setDelay((int) (1000 / WaifuBrew.getInstance().getFrameRate()));
        t.start();
    }
}