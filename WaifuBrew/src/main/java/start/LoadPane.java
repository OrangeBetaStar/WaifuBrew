package start;

// Same looking as SaveLoadPane, but clicking on SaveLoadBox

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class LoadPane extends JPanel {

    private Handlerclass handler = new Handlerclass();
    private SaveLoadBox saveLoadBox = new SaveLoadBox();
    private boolean frameRateDisable = false;
    private HashMap<String, CustomButton> loadPaneButtons = new HashMap<>(4);
    private SideBar configBar = new SideBar();
    private javaxt.io.Image backgroundImage;

    // [1] is resolution of program window
    private Point windowSize = WaifuBrew.getInstance().getRes()[1];

    LoadPane() {
        init();

        addMouseListener(handler);
        addMouseMotionListener(handler);

        this.loadPaneButtons.put("config", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "options_button", Origin.MIDDLE_TOP, 60, true));
        this.loadPaneButtons.put("back", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "back_button", Origin.MIDDLE_TOP, 60, true));
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private class Handlerclass extends MasterHandlerClass {
        public void mouseReleased(MouseEvent event) {
            if (inBound(event, loadPaneButtons.get("config"), true)) {
                configBar.setActive(true);
                WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                System.out.println("Pressed config");
            }
            else if (inBound(event, loadPaneButtons.get("back"), true)) {
                configBar.setActive(false);
                System.out.println("Pressed back");
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getBufferedImage(), 0, 0, this);
        saveLoadBox.paintComponent(g);
        configBar.paintComponent(g);

        for (Map.Entry<String, CustomButton> entry : this.loadPaneButtons.entrySet()) {
            if (configBar.isActive()) {
                if (entry.getKey().equals("back")) {
                    entry.getValue().paintComponent(g);
                }

            } else {
                if (entry.getKey().equals("config")) {
                    entry.getValue().paintComponent(g);
                }
            }
        }
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

        // -----------------------------


        for (Map.Entry<String, CustomButton> entry : this.loadPaneButtons.entrySet()) {
            CustomButton button = entry.getValue();
            addMouseListener(button.retrieveMouseHandler());
            addMouseMotionListener(button.retrieveMouseHandler());
        }

        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backgroundImage.resize(windowSize.x, windowSize.y);
        backgroundImage.setBackgroundColor(64, 64, 64);
    }
}
