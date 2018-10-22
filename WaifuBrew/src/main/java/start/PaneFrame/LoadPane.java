package start.PaneFrame;

import start.CustomObjects.CustomButton;
import start.CustomObjects.MasterHandlerClass;
import start.CustomObjects.SideBar;
import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class LoadPane extends JPanel {

    private Handlerclass handler = new Handlerclass();
    private CustomButton.SaveLoadBox saveLoadBox = new CustomButton.SaveLoadBox();
    private boolean frameRateDisable = false;
    private HashMap<String, CustomButton> loadPaneButtons = new HashMap<>(4);
    private SideBar configBar = new SideBar();
    private javaxt.io.Image backgroundImage;

    // [1] is resolution of program window
    private Point windowSize = WaifuBrew.getInstance().getRes()[1];

    LoadPane() {
        initFPS();
        initImage();
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private class Handlerclass extends MasterHandlerClass {
        public void mouseReleased(MouseEvent event) {
            if (inBound(event, loadPaneButtons.get("options"), true) && loadPaneButtons.get("options").getActiveButtonState()) {
                configBar.setActive(true);
                loadPaneButtons.get("options").setActiveButtonState(false);
                loadPaneButtons.get("back").setActiveButtonState(true);
                System.out.println("Pressed options");
            } else if (inBound(event, loadPaneButtons.get("back"), true) && loadPaneButtons.get("back").getActiveButtonState()) {
                WaifuBrew.getInstance().setStage(0);
                configBar.setActive(false);
                loadPaneButtons.get("back").setActiveButtonState(false);
                loadPaneButtons.get("options").setActiveButtonState(true);
                System.out.println("Pressed back");
                WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
            } else {
                if (configBar.isActive()) {
                    configBar.setActive(false);
                    loadPaneButtons.get("options").setActiveButtonState(true);
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

        for (Map.Entry<String, CustomButton> entry : this.loadPaneButtons.entrySet()) {
            CustomButton button = entry.getValue();
            if (configBar.isActive()) {
                if (entry.getKey().equals("back")) {
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
        /*
        double interpolation = 0;
        final int TICKS_PER_SECOND = 25;
        final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
        final int MAX_FRAMESKIP = 5;

        @Override
        public void run() {
            double next_game_tick = System.currentTimeMillis();
            int loops;

            while (true) {
                loops = 0;
                while (System.currentTimeMillis() > next_game_tick
                        && loops < MAX_FRAMESKIP) {

                    update_game();

                    next_game_tick += SKIP_TICKS;
                    loops++;
                }

                interpolation = (System.currentTimeMillis() + SKIP_TICKS - next_game_tick
                        / (double) SKIP_TICKS);
                display_game(interpolation);
            }
        }
        */

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

        this.loadPaneButtons.put("options", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "options_button", CustomButton.Origin.MIDDLE_TOP, 60, true));
        // this.loadPaneButtons.put("", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "options_button", CustomButton.Origin.MIDDLE_TOP, 60, true));
        this.loadPaneButtons.put("back", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "back_button", CustomButton.Origin.MIDDLE_TOP, 60, true));


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
