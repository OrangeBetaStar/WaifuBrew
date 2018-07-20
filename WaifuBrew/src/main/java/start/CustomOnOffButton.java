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

    /*
    private javaxt.io.Image left = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "toggle_housing-0"));
    private javaxt.io.Image body = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "toggle_housing-2"));
    private javaxt.io.Image right = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "toggle_housing-1"));
    */

    private int slidingPathWidth = 80;
    private int slidingPathHeight = 30;

    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    public CustomOnOffButton(int x, int y, boolean value) {
        this.x = x;
        this.y = y;
        this.value = value;
        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backgroundImage.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        backgroundImage.resize(slidingPathWidth, slidingPathHeight);
        knob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "slider_knob.jpg"));
        knob.resize(slidingPathHeight, slidingPathHeight);
    }

    public CustomOnOffButton(int x, int y, boolean value, boolean centered) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.centered = centered;
        // Change the image later. This is just a sample
        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backgroundImage.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        backgroundImage.resize(slidingPathWidth, slidingPathHeight);
        knob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "slider_knob"));
        knob.resize(slidingPathHeight, slidingPathHeight); // I meant to do that, intellij.

        /*
        left.resize((int)(left.getWidth() * ((double)slidingPathHeight/left.getHeight())), slidingPathHeight, true);
        body.resize((int)(left.getWidth() * ((double)slidingPathHeight/left.getHeight())), slidingPathHeight, true);
        right.resize((int)(left.getWidth() * ((double)slidingPathHeight/left.getHeight())), slidingPathHeight, true);
        */

    }

    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage.getBufferedImage(), x - (backgroundImage.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);

        if(value) {
            g.drawImage(knob.getBufferedImage(), x - (backgroundImage.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);
        }
        else {
            g.drawImage(knob.getBufferedImage(), x + (backgroundImage.getWidth() / 2) - knob.getWidth(), y - (backgroundImage.getHeight() / 2), that);
        }
        /*
        g.drawImage(left.getBufferedImage(), x - (backgroundImage.getWidth() / 2) - (left.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);
        // TODO : add middle portion if this looks good...
        g.drawImage(right.getBufferedImage(), x + (backgroundImage.getWidth() / 2) - (right.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);
        */
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
                if((e.getX() > x) && (e.getX() <= x + slidingPathWidth) && (e.getY() > y) && (e.getY() <= y + slidingPathHeight)) { // TODO: Check if this is working.
                    if(value) {
                        value = false;
                    }
                    else {
                        value = true;
                    }
                }
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
