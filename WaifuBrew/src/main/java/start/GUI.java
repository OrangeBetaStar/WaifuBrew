package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private JPanel waifuPanel;

    // TODO: Why create them when I do not need it right now.
    private StartScreen startPage;
    private AnimationPane animationPane;
    private ConfigPane configPane;

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private int stage = WaifuBrew.getInstance().getStage();
    private int lastStage = 10; // Remembers last opened stage

    private boolean stop = false;

    public GUI() {
        super("ワイファブルー by Tailsoft");

        waifuPanel = new JPanel();
        waifuPanel.setBackground(Color.BLACK);
        startPage = new StartScreen();
        animationPane = new AnimationPane();
        configPane = new ConfigPane();

        // Set icon? Why not?
        // TODO: setIconImage();
        waifuPanel.setBounds(WaifuBrew.getInstance().getRes()[1].x, WaifuBrew.getInstance().getRes()[1].y, WaifuBrew.getInstance().getRes()[2].x, WaifuBrew.getInstance().getRes()[2].y);
        init();
    }

    public void revalidateGraphics() {

        stage = WaifuBrew.getInstance().getStage();

        // If stage changed
        if(lastStage != stage) {

            // Remove old pane
            if(lastStage == 1) {
                remove(animationPane);
            }
            if(lastStage == 2) {
                remove(configPane);
            }
            if(lastStage == 3) {
                // remeove load pane
            }
            if(lastStage == 0 || lastStage == 10) {
                remove(startPage);
            }

            // Add new pane as active
            if(stage == 1) {
                add(animationPane);
                animationPane.repaint();
            }
            if(stage == 2) {
                add(configPane);
                configPane.repaint();
            }
            if (stage == 3) {
                // add load pane
                // load pane repaint
            }
            if(stage == 0 || stage == 10) {
                add(startPage);
                startPage.repaint();
            }


            /*
            // Stage 0 is the main screen
            if (stage == 0) {
                if(animationPane.getParent() != null) {
                    remove(animationPane);
                }
                if(configPane.getParent() != null) {
                    remove(configPane);
                }
                add(startPage);
                // If there is no repaint(), it doesn't set chain reaction for loop.
                startPage.repaint();
            }

            // Stage 1 is the play field
            else if (stage == 1) {
                if(startPage.getParent() != null) {
                    remove(startPage);
                }
                if(configPane.getParent() != null) {
                    remove(configPane);
                }
                add(animationPane);
                animationPane.repaint();
            }

            // Stage 2 is the config field
            else if (stage == 2) {
                if(animationPane.getParent() != null) {
                    remove(animationPane);
                }
                if(startPage.getParent() != null) {
                    remove(startPage);
                }
                add(configPane);
                configPane.repaint();
            }

            // Stage 3 is the load field
            else if (stage == 3) {
                if(animationPane.getParent() != null) {
                    remove(animationPane);
                }
                if(startPage.getParent() != null) {
                    remove(startPage);
                }
                // TODO: Uncomment when this is implemented
                // add(loadPane);
                // loadPane.repaint();
            }
            */
        }

        lastStage = stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
        // Just using duplicated stage variables for Global and Private to cut down on calling?
        WaifuBrew.getInstance().setStage(stage);
        revalidateGraphics();
    }

    public void init(){
        Timer t = new Timer((int)(1000/WaifuBrew.getInstance().getFrameRate()), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!stop) {
                    revalidateGraphics();
                    revalidate();
                } else {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        t.setRepeats(true);
        t.setDelay((int)(1000/WaifuBrew.getInstance().getFrameRate()));
        t.start();
    }

}