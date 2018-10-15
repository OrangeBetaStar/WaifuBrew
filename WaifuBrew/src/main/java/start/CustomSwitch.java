package start;

// This class will enhouse on/off boolean with more graphical interface.

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class CustomSwitch extends InteractiveObjects implements ActionListener {

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

    private int slidingPathWidth = 40;
    private int slidingPathHeight = 20;

    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    public CustomSwitch(int x, int y, boolean value) {
        this.x = x;
        this.y = y;
        this.value = value;
        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backgroundImage.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        backgroundImage.resize(slidingPathWidth, slidingPathHeight);
        knob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "slider_knob.jpg"));
        knob.resize(slidingPathHeight, slidingPathHeight); // I meant to do that, intellij.
    }

    public CustomSwitch(int x, int y, boolean value, boolean centered) {
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

        if (value) {
            g.drawImage(knob.getBufferedImage(), x - (backgroundImage.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);
        } else {
            g.drawImage(knob.getBufferedImage(), x + (backgroundImage.getWidth() / 2) - knob.getWidth(), y - (backgroundImage.getHeight() / 2), that);
        }
        // g.drawString("The value: " + value, x - (backgroundImage.getWidth() / 2), y - (backgroundImage.getHeight() / 2));
        /*
        g.drawImage(left.getBufferedImage(), x - (backgroundImage.getWidth() / 2) - (left.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);
        // TODO : add middle portion if this looks good...
        g.drawImage(right.getBufferedImage(), x + (backgroundImage.getWidth() / 2) - (right.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);
        */
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return slidingPathWidth;
    }

    @Override
    public int getHeight() {
        return slidingPathHeight;
    }

    @Override
    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    private class Handlerclass extends MasterHandlerClass {
        public void mouseClicked(MouseEvent e) {
            if (centered) {
                if ((e.getX() > (x - (slidingPathWidth / 2))) && (e.getX() <= (x + (slidingPathWidth / 2))) && (e.getY() > (y - (slidingPathHeight / 2))) && (e.getY() <= (y + (slidingPathHeight / 2)))) {
                    if (value) {
                        value = false;
                    } else {
                        value = true;
                    }
                }
            } else {
                if ((e.getX() > x) && (e.getX() <= x + slidingPathWidth) && (e.getY() > y) && (e.getY() <= y + slidingPathHeight)) { // TODO: Check if this is working.
                    if (value) {
                        value = false;
                    } else {
                        value = true;
                    }
                }
            }
        }
    }
}
