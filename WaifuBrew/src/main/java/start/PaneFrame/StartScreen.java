package start.PaneFrame;

import start.CustomObjects.CustomButton;
import start.CustomObjects.MasterHandlerClass;
import start.CustomObjects.Origin;
import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class StartScreen extends JPanel implements ActionListener {

    private javaxt.io.Image backgroundPicture;
    private HashMap<String, CustomButton> startScreenButtons = new HashMap<>(4);
    private Handlerclass handler = new Handlerclass();
    private final Point windowSize = WaifuBrew.getInstance().getRes()[1];
    private boolean stopPainting = false;

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public StartScreen() {
        initFPS();
        initImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundPicture.getBufferedImage(), (windowSize.x / 2) - (backgroundPicture.getWidth() / 2), (windowSize.y / 2) - (backgroundPicture.getHeight() / 2), this);

        for (Map.Entry<String, CustomButton> entry : this.startScreenButtons.entrySet()) {
            entry.getValue().paintComponent(g);
        }
    }

    private class Handlerclass extends MasterHandlerClass {

        public void mouseClicked(MouseEvent event) {

            // 1 - AnimationPane
            // 2 - ConfigPane
            // 3 - Load
            // 4 - Quit
            if(event.getButton() == MouseEvent.BUTTON1) {
                for (Map.Entry<String, CustomButton> entry : startScreenButtons.entrySet()) {
                    if (inBound(event, entry.getValue(), true)) {
                        if (entry.getKey().equals("start")) {
                            WaifuBrew.getInstance().setStage(1);
                            WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                        } else if (entry.getKey().equals("config")) {
                            WaifuBrew.getInstance().setStage(2);
                            WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                        } else if (entry.getKey().equals("load")) {
                            WaifuBrew.getInstance().setStage(3);
                            WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                        } else if (entry.getKey().equals("quit")) {
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }

    private void initFPS() {
        Timer t = new Timer((int) (1000 / WaifuBrew.getInstance().getFrameRate()), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!stopPainting) {
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
        addMouseListener(handler);
        addMouseMotionListener(handler);
        backgroundPicture = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BACKGROUND, "bg_start.png"));

        // Calculates the scaling needed to fit the screen. Any ratio will work.
        double scale = Math.max(((double)windowSize.x / backgroundPicture.getWidth()), ((double)windowSize.y / backgroundPicture.getHeight()));
        backgroundPicture.resize((int)((scale) * backgroundPicture.getWidth()), (int)((scale) * backgroundPicture.getHeight()));

        startScreenButtons.put("start", new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 5), (windowSize.y / 6) * 5, "start_button", Origin.MIDDLE_CENTRE));
        startScreenButtons.put("load", new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 5) * 2, (windowSize.y / 6) * 5, "load_button", Origin.MIDDLE_CENTRE));
        startScreenButtons.put("config", new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 5) * 3, (windowSize.y / 6) * 5, "config_button", Origin.MIDDLE_CENTRE));
        startScreenButtons.put("quit", new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 5) * 4, (windowSize.y / 6) * 5, "quit_button", Origin.MIDDLE_CENTRE));

        for (Map.Entry<String, CustomButton> entry : this.startScreenButtons.entrySet()) {
            CustomButton button = entry.getValue();
            addMouseListener(button.retrieveMouseHandler());
            addMouseMotionListener(button.retrieveMouseHandler());
        }
    }
}