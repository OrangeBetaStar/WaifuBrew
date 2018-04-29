package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class StartScreen extends JPanel implements ActionListener {

    private javaxt.io.Image backgroundPicture;
    private javaxt.io.Image start_button;
    private javaxt.io.Image config_button;
    private javaxt.io.Image exit_button;
    private javaxt.io.Image load_button;
    private Point startButtonP;
    private Point configButtonP;
    private Point exitButtonP;
    private Point loadButtonP;
    private Handlerclass handler = new Handlerclass();
    boolean startButtonUI = false;
    boolean configButtonUI = false;
    boolean exitButtonUI = false;
    boolean loadButtonUI = false;

    private int buttonY = 600;
    private int brightness = 20;

    private final String RESOURCE_PATH = "src/main/java/resources/";

    public void actionPerformed(ActionEvent e) {
        repaint();
        System.out.println("Hi");
    }

    public StartScreen() {
        addMouseListener(handler);
        addMouseMotionListener(handler);
        backgroundPicture = new javaxt.io.Image(RESOURCE_PATH + "start.png");
        start_button = new javaxt.io.Image(RESOURCE_PATH + "startscreen_start_button.png");
        config_button = new javaxt.io.Image(RESOURCE_PATH + "startscreen_config_button.png");
        load_button = new javaxt.io.Image(RESOURCE_PATH + "startscreen_load_button.png");
        exit_button = new javaxt.io.Image(RESOURCE_PATH + "startscreen_exit_button.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundPicture != null) {
            // I want to centre the image that is 960:640 to widescreen format, but do not want to stretch. I will zoom in!

            // Not centered tho... lo
            if(getPreferredSize(backgroundPicture.getBufferedImage()).width < getSize().width) {
                if((getSize().width/(double)getPreferredSize(backgroundPicture.getBufferedImage()).width) * getPreferredSize(backgroundPicture.getBufferedImage()).height < getSize().height) {
                    // TODO: Change x0, y0 if picture is changed.
                    g.drawImage(backgroundPicture.getBufferedImage(),0,0,(int)Math.round(getSize().height / (double)getPreferredSize(backgroundPicture.getBufferedImage()).height * getPreferredSize(backgroundPicture.getBufferedImage()).width), getSize().height, this);
                }
                else {
                    g.drawImage(backgroundPicture.getBufferedImage(), 0, 0, getSize().width,(int)Math.round(getSize().width / (double)getPreferredSize(backgroundPicture.getBufferedImage()).width * getPreferredSize(backgroundPicture.getBufferedImage()).height), this);
                }
            }

            // TODO: Add rest of the menu elements
            startButtonP = new Point((getSize().width / 5) - (getPreferredSize(start_button.getBufferedImage()).width / 2), buttonY - (getPreferredSize(start_button.getBufferedImage()).height / 2));
            loadButtonP = new Point((getSize().width / 5) * 2 - (getPreferredSize(load_button.getBufferedImage()).width / 2), buttonY - (getPreferredSize(load_button.getBufferedImage()).height / 2));
            configButtonP = new Point((getSize().width / 5) * 3 - (getPreferredSize(config_button.getBufferedImage()).width / 2), buttonY - (getPreferredSize(config_button.getBufferedImage()).height / 2));
            exitButtonP = new Point((getSize().width / 5) * 4 - (getPreferredSize(exit_button.getBufferedImage()).width / 2), buttonY - (getPreferredSize(exit_button.getBufferedImage()).height / 2));

            // Copy the original cuz the javaxt edits from original (quality loss)
            javaxt.io.Image tempStartButton = start_button.copy();
            javaxt.io.Image tempConfigButton = config_button.copy();
            javaxt.io.Image tempExitButton = exit_button.copy();
            javaxt.io.Image tempLoadButton = load_button.copy();
            if(startButtonUI) {
                tempStartButton.setOpacity(100);
            }
            else {
                tempStartButton.setOpacity(brightness);
            }
            if(configButtonUI) {
                tempConfigButton.setOpacity(100);
            }
            else {
                tempConfigButton.setOpacity(brightness);
            }
            if(loadButtonUI) {
                tempLoadButton.setOpacity(100);
            }
            else {
                tempLoadButton.setOpacity(brightness);
            }
            if(exitButtonUI) {
                tempExitButton.setOpacity(100);
            }
            else {
                tempExitButton.setOpacity(brightness);
            }
            g.drawImage(tempStartButton.getBufferedImage(), startButtonP.x, startButtonP.y, getPreferredSize(start_button.getBufferedImage()).width, getPreferredSize(start_button.getBufferedImage()).height,this);
            g.drawImage(tempConfigButton.getBufferedImage(),configButtonP.x, configButtonP.y, getPreferredSize(config_button.getBufferedImage()).width, getPreferredSize(config_button.getBufferedImage()).height,  this);
            g.drawImage(tempExitButton.getBufferedImage(), exitButtonP.x, exitButtonP.y, getPreferredSize(exit_button.getBufferedImage()).width, getPreferredSize(exit_button.getBufferedImage()).height,  this);
            g.drawImage(tempLoadButton.getBufferedImage(), loadButtonP.x, loadButtonP.y, getPreferredSize(load_button.getBufferedImage()).width, getPreferredSize(load_button.getBufferedImage()).height, this);
            repaint();
        }
    }

    private class Handlerclass implements MouseListener, MouseMotionListener {

        public void mouseClicked(MouseEvent event) {
        }

        public void mousePressed(MouseEvent event) {
        }

        public void mouseReleased(MouseEvent event) {
        }

        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mouseDragged(MouseEvent event) {
        }

        public void mouseMoved(MouseEvent event) {
            if(isInArea(event, start_button, startButtonP)) {
                startButtonUI = true;
            }
            else {
                startButtonUI = false;
            }
            if(isInArea(event, config_button, configButtonP)) {
                configButtonUI = true;
            }
            else {
                configButtonUI = false;
            }
            if(isInArea(event, load_button, loadButtonP)) {
                loadButtonUI = true;
            }
            else {
                loadButtonUI = false;
            }
            if(isInArea(event, exit_button, exitButtonP)) {
                exitButtonUI = true;
            }
            else {
                exitButtonUI = false;
            }
        }

        private boolean isInArea(MouseEvent event, javaxt.io.Image button, Point area) {
            return event.getX() > area.x && event.getX() < (area.x + button.getWidth()) && event.getY() > area.y && event.getY() < (area.y + button.getHeight());
        }
    }

    public Dimension getPreferredSize(BufferedImage a) {
        if (a != null) {
            int width = a.getWidth();
            int height = a.getHeight();
            return new Dimension(width, height);
        }
        return super.getPreferredSize();
    }
}