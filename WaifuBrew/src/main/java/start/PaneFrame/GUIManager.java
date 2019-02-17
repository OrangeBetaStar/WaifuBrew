package start.PaneFrame;

import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIManager extends JFrame {

    // TODO: Why create them when I do not need it right now.
    private StartScreenPane startPage;
    private AnimationPane animationPane;
    private ConfigPane configPane;
    private SaveLoadPane saveLoadPane;

    private int stage = WaifuBrew.getInstance().getStage();
    private int lastStage = 10; // Remembers last opened stage
    private boolean framelimiterDisable = false;

    public GUIManager() {
        super("ワイファブルー by Tailsoft");
        startPage = new StartScreenPane();
        animationPane = new AnimationPane();
        configPane = new ConfigPane();
        saveLoadPane = new SaveLoadPane();
        init();
    }


    public void revalidateGraphics() {

        stage = WaifuBrew.getInstance().getStage();

        // If stage changed
        if (lastStage != stage) {

            // Remove old pane
            if (lastStage == 1) {
                remove(animationPane);
            }
            if (lastStage == 2) {
                remove(configPane);
            }
            if (lastStage == 3) {
                remove(saveLoadPane);
            }
            if (lastStage == 4) {
                remove(saveLoadPane);
            }
            if (lastStage == 0 || lastStage == 10) {
                remove(startPage);
            }

            // Add new pane as active
            if (stage == 1) {
                add(animationPane);
                animationPane.stageChange(lastStage);
                animationPane.repaint();
            }
            if (stage == 2) {
                add(configPane);
                configPane.stageChange(lastStage);
                configPane.repaint();
            }
            if (stage == 3) {
                add(saveLoadPane);
                saveLoadPane.stageChange("load");
                saveLoadPane.repaint();
            }
            if (stage == 4) {
                add(saveLoadPane);
                saveLoadPane.stageChange("save");
                saveLoadPane.repaint();
            }
            if (stage == 0 || stage == 10) {
                add(startPage);
                startPage.repaint();
            }
        }

        lastStage = stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
        // Just using duplicated stage variables for Global and Private to cut down on calling?
        WaifuBrew.getInstance().setStage(stage);
        revalidateGraphics();
    }

    public void init() {
        // Setting framerate
        Timer t = new Timer((int) (1000 / WaifuBrew.getInstance().getFrameRate()), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!framelimiterDisable) {
                    revalidateGraphics();
                    revalidate();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        t.setRepeats(true);
        t.setDelay((int) (1000 / WaifuBrew.getInstance().getFrameRate()));
        t.start();

        // Set application icon
        setIconImage(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "icon"));
    }

}