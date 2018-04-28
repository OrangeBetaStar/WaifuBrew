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
    private Point startButtonP;
    private Point configButtonP;
    private Point exitButtonP;
    private Handlerclass handler = new Handlerclass();
    boolean startButtonUI = false;
    boolean configButtonUI = false;
    boolean exitButtonUI = false;

    private int buttonY = 600;

    private final String RESOURCE_PATH = "src/main/java/resources/";

    public void actionPerformed(ActionEvent e) {
        repaint();
        System.out.println("Hi");
    }

    public StartScreen() {
        addMouseListener(handler);
        addMouseMotionListener(handler);
        try {
            backgroundPicture = new javaxt.io.Image(RESOURCE_PATH + "start.png");
            start_button = new javaxt.io.Image(RESOURCE_PATH + "startscreen_start_button.png");
            config_button = new javaxt.io.Image(RESOURCE_PATH + "startscreen_config_button.png");
            exit_button = new javaxt.io.Image(RESOURCE_PATH + "startscreen_exit_button.png");
        } catch (Exception e) { // Javaxt doesn't throw IOException?
            System.out.println("File failure in StartScreen class");
            e.printStackTrace(); // Wall of error
            System.exit(-1);
        }
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
            startButtonP = new Point((getSize().width / 4) - (getPreferredSize(start_button.getBufferedImage()).width / 2), buttonY - (getPreferredSize(start_button.getBufferedImage()).height / 2));
            configButtonP = new Point((getSize().width / 4) * 2 - (getPreferredSize(config_button.getBufferedImage()).width / 2), buttonY - (getPreferredSize(config_button.getBufferedImage()).height / 2));
            exitButtonP = new Point((getSize().width / 4) * 3 - (getPreferredSize(exit_button.getBufferedImage()).width / 2), buttonY - (getPreferredSize(exit_button.getBufferedImage()).height / 2));

            javaxt.io.Image tempStartButton = start_button.copy();
            javaxt.io.Image tempConfigButton = config_button.copy();
            javaxt.io.Image tempExitButton = exit_button.copy();
            if(startButtonUI) {
                tempStartButton.setOpacity(100);
            }
            else {
                tempStartButton.setOpacity(20);
            }
            if(configButtonUI) {
                tempConfigButton.setOpacity(100);
            }
            else {
                tempConfigButton.setOpacity(20);
            }
            if(exitButtonUI) {
                tempExitButton.setOpacity(100);
            }
            else {
                tempExitButton.setOpacity(20);
            }
            g.drawImage(tempStartButton.getBufferedImage(), startButtonP.x, startButtonP.y, getPreferredSize(start_button.getBufferedImage()).width, getPreferredSize(start_button.getBufferedImage()).height,this);
            g.drawImage(tempConfigButton.getBufferedImage(),configButtonP.x, configButtonP.y, getPreferredSize(config_button.getBufferedImage()).width, getPreferredSize(config_button.getBufferedImage()).height,  this);
            g.drawImage(tempExitButton.getBufferedImage(), exitButtonP.x, exitButtonP.y, getPreferredSize(exit_button.getBufferedImage()).width, getPreferredSize(exit_button.getBufferedImage()).height,  this);
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

        // these are mouse motion event
        public void mouseDragged(MouseEvent event) {
        }

        public void mouseMoved(MouseEvent event) {
            if(event.getX() > startButtonP.x && event.getX() < (startButtonP.x + start_button.getWidth()) && event.getY() > startButtonP.y && event.getY() < (startButtonP.y + start_button.getHeight())) {
                startButtonUI = true;
            }
            else {
                startButtonUI = false;
            }
            if(event.getX() > configButtonP.x && event.getX() < (configButtonP.x + config_button.getWidth()) && event.getY() > configButtonP.y && event.getY() < (configButtonP.y + config_button.getHeight())) {
                configButtonUI = true;
            }
            else {
                configButtonUI = false;
            }
            if(event.getX() > exitButtonP.x && event.getX() < (exitButtonP.x + exit_button.getWidth()) && event.getY() > exitButtonP.y && event.getY() < (exitButtonP.y + exit_button.getHeight())) {
                exitButtonUI = true;
            }
            else {
                exitButtonUI = false;
            }
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