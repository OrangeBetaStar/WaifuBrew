package start.PaneFrame;

// Same looking as SaveLoadPane, but clicking on SaveLoadBox

import start.CustomObjects.*;
import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class SavePane extends JPanel {

    private Handlerclass handler = new Handlerclass();
    private SaveLoadBox saveLoadBox = new SaveLoadBox();
    private boolean frameRateDisable = false;
    private HashMap<String, CustomButton> savePaneButtons = new HashMap<>(4);
    private SideBar configBar = new SideBar();
    private javaxt.io.Image backgroundImage;

    // [1] is resolution of program window
    private Point windowSize = WaifuBrew.getInstance().getRes()[1];

    SavePane() {
        initFPS();
        initImage();
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private class Handlerclass extends MasterHandlerClass {
        public void mouseReleased(MouseEvent event) {
            if (inBound(event, savePaneButtons.get("options"), true) && savePaneButtons.get("options").getActiveButtonState()) {
                configBar.setActive(true);
                savePaneButtons.get("options").setActiveButtonState(false);
                savePaneButtons.get("back").setActiveButtonState(true);
                System.out.println("Pressed options");
            } else if (inBound(event, savePaneButtons.get("back"), true) && savePaneButtons.get("back").getActiveButtonState()) {
                WaifuBrew.getInstance().setStage(0);
                configBar.setActive(false);
                savePaneButtons.get("back").setActiveButtonState(false);
                savePaneButtons.get("options").setActiveButtonState(true);
                System.out.println("Pressed back");
                WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
            } else {
                if (configBar.isActive()) {
                    configBar.setActive(false);
                    savePaneButtons.get("options").setActiveButtonState(true);
                } else {
                    // Normal behavior
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getBufferedImage(), 0, 0, this);
        saveLoadBox.paintComponent(g);
        configBar.paintComponent(g);

        for (Map.Entry<String, CustomButton> entry : this.savePaneButtons.entrySet()) {
            CustomButton button = entry.getValue();
            if (configBar.isActive()) {
                if (entry.getKey().equals("back") && !configBar.isMoving()) {
                    button.paintComponent(g);
                }

            } else {
                if (entry.getKey().equals("options")) {
                    button.paintComponent(g);
                }
            }
        }
    }

    public void initFPS() {
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

    private void initImage() {
        // Internal handler
        addMouseListener(handler);
        addMouseMotionListener(handler);

        this.savePaneButtons.put("options", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "options_button", Origin.MIDDLE_TOP, 60, true));
        this.savePaneButtons.put("back", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "back_button", Origin.MIDDLE_TOP, 60, true));


        for (Map.Entry<String, CustomButton> entry : savePaneButtons.entrySet()) {
            CustomButton button = entry.getValue();
            addMouseListener(button.retrieveMouseHandler());
            addMouseMotionListener(button.retrieveMouseHandler());
        }

        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backgroundImage.resize(windowSize.x, windowSize.y);
        backgroundImage.setBackgroundColor(64, 64, 64);
    }
}
