package start;

// This class will enhouse on/off boolean with more graphical interface.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomOnOffButton extends JPanel implements ActionListener {

    private Handlerclass miniHandler = new Handlerclass();
    private boolean value;
    private int x;
    private int y;
    private boolean centered;
    private javaxt.io.Image backgroundImage;
    private javaxt.io.Image knob;

    private int slidingPathWidth = 100;
    private int slidingPathHeight = 30;

    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    public CustomOnOffButton(int x, int y, boolean value) {
        this.x = x;
        this.y = y;
        this.value = value;
        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        knob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "slider_knob.jpg")); // TODO: Change to circle and png later
    }

    public CustomOnOffButton(int x, int y, boolean value, boolean centered) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.centered = centered;
        // Change the image later. This is just a sample
        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backgroundImage.resize(slidingPathWidth, slidingPathHeight);
        // Shit implementation (Will replace later)
        /*
        for(int widthX = 0; widthX < backgroundImage.getWidth(); widthX++) {
            for(int heightY = 0; heightY < backgroundImage.getHeight(); heightY++) {
                backgroundImage.setColor(x, y, Color.ORANGE);
            }
        }
        */

        knob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "slider_knob")); // TODO: Change to circle and png later
        knob.resize(slidingPathHeight, slidingPathHeight);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage.getBufferedImage(), x - (backgroundImage.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);
        if(value) {
            g.drawImage(knob.getBufferedImage(), x - (backgroundImage.getWidth() / 2) + knob.getWidth(), y - (backgroundImage.getHeight() / 2), that);
        }
        else {
            g.drawImage(knob.getBufferedImage(), x + (backgroundImage.getWidth() / 2) - knob.getWidth(), y - (backgroundImage.getHeight() / 2), that);
        }
    }

    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public boolean getValue() {
        return value;
    }
    private class Handlerclass implements MouseListener, MouseMotionListener {
        public void mouseClicked(MouseEvent e) {
            if(centered) {
                if((e.getX() > (x - (slidingPathWidth / 2))) && (e.getX() <= (x + (slidingPathWidth / 2))) && (e.getY() > (y - (slidingPathHeight / 2))) && (e.getY() <= (y + (slidingPathHeight / 2)))) {
                    if(value) {
                        value = false;
                    }
                    else {
                        value = true;
                    }
                }
            }
            else {
                // TODO: Non centred position
            }

            /*
            System.out.println("x " + (x - (slidingPathWidth / 2)) + " and " + (x + (slidingPathWidth / 2)) + " t/f for x: " + (e.getX() > (x - (slidingPathWidth / 2))) + " and " + (e.getX() < (x + (slidingPathWidth / 2))));
            System.out.println("y " + (y - (slidingPathHeight / 2)) + " and " + (y + (slidingPathHeight / 2)) + " t/f for y: " + (e.getY() > (y - (slidingPathHeight / 2))) + " and " + (e.getY() < (y + (slidingPathHeight / 2))));
            System.out.println("x cursor " + e.getX());
            System.out.println("y cursor " + e.getY());
            System.out.println("--------------------");
            */


        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseMoved(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseDragged(MouseEvent e) {

        }
        public void mouseExited(MouseEvent e) {

        }

    }
}
