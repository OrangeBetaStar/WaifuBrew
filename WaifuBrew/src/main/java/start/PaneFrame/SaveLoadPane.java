package start.PaneFrame;

import start.Containers.LoadSaveWrapper;
import start.CustomObjects.*;
import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaveLoadPane extends JPanel {

    // Containers
    private ArrayList<SaveLoadBox> saveLoadBox = new ArrayList<>();
    private HashMap<String, CustomButton> loadPaneButtons = new HashMap<>(4);
    private SideBar configBar = new SideBar();

    private javaxt.io.Image backgroundImage;

    private int scrubPage; // use this when scrubbing through panels
    private CustomSlider panelScrubThrough;

    // [1] is resolution of program window
    private Point windowSize = WaifuBrew.getInstance().getRes()[1];

    // Misc
    private boolean frameRateDisable = false;

    // MouseHandler
    private Handlerclass handler = new Handlerclass();

    // To have this pane behave as load or save. (for mouse handler)
    boolean clickLoad = true;

    public SaveLoadPane() {
        initFPS();
        initImage();
        initPanelBlocks();
        initInteractiveUI();
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private class Handlerclass extends MasterHandlerClass {

        public void mouseReleased(MouseEvent event) {

            if (event.getButton() == MouseEvent.BUTTON1) {
                for (int boundCheck = 0; boundCheck < saveLoadBox.size(); boundCheck++) {
                    if (inBound(event, saveLoadBox.get(boundCheck), false)) {

                        // If save has a valid date, then it's good to load.
                        if (saveLoadBox.get(boundCheck).isValidSaveDate()) {
                            WaifuBrew.getInstance().setCurrentSave(boundCheck + 1);
                            WaifuBrew.getInstance().setStage(1);
                            WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                        }
                    }
                }

                // "Option is clicked while also option is viewable"
                if (inBound(event, loadPaneButtons.get("options"), true) && loadPaneButtons.get("options").getActiveButtonState()) {
                    configBar.setActive(true);
                    loadPaneButtons.get("options").setActiveButtonState(false);
                    loadPaneButtons.get("back").setActiveButtonState(true);
                    // System.out.println("Pressed options");

                    // "Back is clicked when back is also viewable"
                } else if (inBound(event, loadPaneButtons.get("back"), true) && loadPaneButtons.get("back").getActiveButtonState()) {
                    WaifuBrew.getInstance().setStage(0);
                    configBar.setActive(false);
                    loadPaneButtons.get("back").setActiveButtonState(false);
                    loadPaneButtons.get("options").setActiveButtonState(true);
                    // System.out.println("Pressed back");
                    WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                } else {
                    // Clicked somewhere else to disable config bar.
                    if (configBar.isActive()) {
                        configBar.setActive(false);
                        loadPaneButtons.get("options").setActiveButtonState(true);
                    } else {
                        // Normal behavior
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getBufferedImage(), 0, 0, this);

        for (SaveLoadBox a : saveLoadBox) {
            a.paintComponent(g);
        }

        configBar.paintComponent(g);
        panelScrubThrough.paintComponent(g);

        for (Map.Entry<String, CustomButton> entry : this.loadPaneButtons.entrySet()) {
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

    public void stageChange(String mode) {
        if (mode.equals("load")) {
            clickLoad = true;
        } else {
            clickLoad = false;
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

        this.loadPaneButtons.put("options", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "options_button", Origin.MIDDLE_TOP, 60, true));
        this.loadPaneButtons.put("back", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "back_button", Origin.MIDDLE_TOP, 60, true));

        for (Map.Entry<String, CustomButton> entry : this.loadPaneButtons.entrySet()) {
            CustomButton button = entry.getValue();
            addMouseListener(button.retrieveMouseHandler());
            addMouseMotionListener(button.retrieveMouseHandler());
        }

        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backgroundImage.resize(windowSize.x, windowSize.y);
        backgroundImage.setBackgroundColor(64, 64, 64);
    }

    private void initPanelBlocks() {
        // Fetching loaded saves from file

        for (LoadSaveWrapper a : WaifuBrew.getInstance().getLoadSaveContainers()) {
            if (a.getSaveDate() != null) {
                if ((a.getPanelLocation() % 2) == 0) {
                    // System.out.println("Panel location (right) " + a.getPanelLocation());
                    saveLoadBox.add(new SaveLoadBox(((windowSize.x / 5) * 3) + (windowSize.y / 10), (windowSize.y / 6 * ((a.getPanelLocation() / 2))) + (windowSize.y / 10), Origin.MIDDLE_CENTRE, a.getRouteStory(), a.getSaveDate().toString(), a.getThumbnailFile()));
                    // System.out.println("X: " + (windowSize.x / 3) * 2 + ", Y: " + windowSize.y / 4 * ((a.getPanelLocation() / 2) + 1));

                } else {
                    // System.out.println("Panel location (left) " + a.getPanelLocation());
                    saveLoadBox.add(new SaveLoadBox((windowSize.x / 6) + (windowSize.y / 10), (windowSize.y / 6 * ((a.getPanelLocation() / 2) + 1)) + (windowSize.y / 10), Origin.MIDDLE_CENTRE, a.getRouteStory(), a.getSaveDate().toString(), a.getThumbnailFile()));
                    // System.out.println("X: " + windowSize.x / 3 + ", Y: " + windowSize.y / 4 * ((a.getPanelLocation() / 2) + 1));

                }
            }
        }
        // mousehandler for boxes
        for (SaveLoadBox addMouseHandler : saveLoadBox) {
            addMouseListener(addMouseHandler.retrieveMouseHandler());
            addMouseMotionListener(addMouseHandler.retrieveMouseHandler());
        }
    }

    private void initInteractiveUI() {
        // initializing a slider that slides the save/load panels & add mouse listeners
        panelScrubThrough = new CustomSlider((windowSize.x / 2), (windowSize.y / 15) * 14, scrubPage, Origin.RIGHT_TOP, "");
        addMouseListener(panelScrubThrough.retrieveMouseHandler());
        addMouseMotionListener(panelScrubThrough.retrieveMouseHandler());
    }
}
