package start.PaneFrame;

import start.CustomObjects.CustomButton;
import start.CustomObjects.MasterHandlerClass;
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

        /*Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(image, x, y, width, height, this);
        */

        
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
            // 4 - Exit

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
                    } else if (entry.getKey().equals("exit")) {
                        System.exit(0);
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


        // This is fit
        /*
        if(((double)backgroundPicture.getWidth() / backgroundPicture.getHeight()) <= ((double)windowSize.x / windowSize.y)) {
            backgroundPicture.resize((int)(windowSize.y * ((double)backgroundPicture.getWidth() / backgroundPicture.getHeight())), windowSize.y);
        } else {
            backgroundPicture.resize(windowSize.x, (int)(windowSize.x * ((double)backgroundPicture.getWidth() / backgroundPicture.getHeight())));
        }
        */

        /*
        if (backgroundPicture.getWidth() < backgroundPicture.getHeight() && backgroundPicture.getHeight() > windowSize.x) {
            backgroundPicture.resize(backgroundPicture.getWidth() * windowSize.x / backgroundPicture.getHeight(), windowSize.x);
        } else if (backgroundPicture.getWidth() > backgroundPicture.getHeight() && backgroundPicture.getWidth() > windowSize.x) {
            backgroundPicture.resize(windowSize.x, backgroundPicture.getHeight() * windowSize.x / backgroundPicture.getWidth());
        } else {
            // no change
        }
        */


        startScreenButtons.put("start", new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 5), (windowSize.y / 6) * 5, "start_button", CustomButton.Origin.MIDDLE_CENTRE));
        startScreenButtons.put("load", new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 5) * 2, (windowSize.y / 6) * 5, "load_button", CustomButton.Origin.MIDDLE_CENTRE));
        startScreenButtons.put("config", new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 5) * 3, (windowSize.y / 6) * 5, "config_button", CustomButton.Origin.MIDDLE_CENTRE));
        startScreenButtons.put("exit", new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 5) * 4, (windowSize.y / 6) * 5, "exit_button", CustomButton.Origin.MIDDLE_CENTRE));

        for (Map.Entry<String, CustomButton> entry : this.startScreenButtons.entrySet()) {
            CustomButton button = entry.getValue();
            addMouseListener(button.retrieveMouseHandler());
            addMouseMotionListener(button.retrieveMouseHandler());
        }
    }
}