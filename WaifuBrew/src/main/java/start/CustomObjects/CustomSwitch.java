package start.CustomObjects;

// This class will enhouse on/off boolean with more graphical interface.

import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

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
    private String switchDesc;
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
        knob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "blackbox"));
        knob.resize(slidingPathHeight, slidingPathHeight); // I meant to do that, intellij.
    }

    public CustomSwitch(int x, int y, boolean value, boolean centered, String switchDesc) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.centered = centered;
        this.switchDesc = switchDesc;

        // Change the image later. This is just a sample
        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backgroundImage.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        backgroundImage.resize(slidingPathHeight, slidingPathHeight);
        knob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "blackbox"));
        knob.resize(slidingPathHeight / 2, slidingPathHeight / 2); // I meant to do that, intellij.

        /*
        left.resize((int)(left.getWidth() * ((double)slidingPathHeight/left.getHeight())), slidingPathHeight, true);
        body.resize((int)(left.getWidth() * ((double)slidingPathHeight/left.getHeight())), slidingPathHeight, true);
        right.resize((int)(left.getWidth() * ((double)slidingPathHeight/left.getHeight())), slidingPathHeight, true);
        */

    }

    public void paintComponent(Graphics g) {
        // This is centred
        if (centered) {
            g.drawImage(backgroundImage.getBufferedImage(), x - (backgroundImage.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);
            if (value) {
                g.drawImage(knob.getBufferedImage(), x - (knob.getWidth() / 2), y - (knob.getHeight() / 2), that);
            }
        }
        else {
            g.drawImage(backgroundImage.getBufferedImage(), x, y, that);
            if (value) {
                g.drawImage(knob.getBufferedImage(), x + ((backgroundImage.getWidth() / 2) - (knob.getWidth() / 2)), y + ((backgroundImage.getHeight() / 2) - (knob.getHeight() / 2)), that);
            }
        }
        /*
        g.drawImage(backgroundImage.getBufferedImage(), x - (backgroundImage.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);
        if (value) {
            g.drawImage(knob.getBufferedImage(), x - (backgroundImage.getWidth() / 2), y - (backgroundImage.getHeight() / 2), that);
        } else {
            g.drawImage(knob.getBufferedImage(), x + (backgroundImage.getWidth() / 2) - knob.getWidth(), y - (backgroundImage.getHeight() / 2), that);
        }
        */



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
    public int getAbsoluteX() {
        return this.x + this.slidingPathWidth / 2;
    }

    @Override
    public int getAbsoluteY() {
        return this.y + this.slidingPathHeight / 2;
    }

    @Override
    public int getWidth() {
        return slidingPathWidth;
    }

    @Override
    public int getHeight() {
        return slidingPathHeight;
    }

    public String getSwitchDesc() { return switchDesc; }

    @Override
    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    private class Handlerclass extends MasterHandlerClass {
        public void mouseClicked(MouseEvent e) {
            if (centered) {
                if ((e.getX() > (x - (slidingPathHeight / 2))) &&
                    (e.getX() <= (x + (slidingPathHeight / 2))) &&
                    (e.getY() > (y - (slidingPathHeight / 2))) &&
                    (e.getY() <= (y + (slidingPathHeight / 2)))) {
                    if (value) {
                        value = false;
                    } else {
                        value = true;
                    }
                }
            } else {
                if ((e.getX() > x) &&
                    (e.getX() <= x + slidingPathHeight) &&
                    (e.getY() > y) &&
                    (e.getY() <= y + slidingPathHeight)) { // TODO: Check if this is working.
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
