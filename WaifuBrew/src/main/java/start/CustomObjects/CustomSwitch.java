package start.CustomObjects;

// This class will enhouse on/off boolean with more graphical interface.

import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class CustomSwitch extends InteractiveObjects implements ActionListener {

    // position
    private int x;
    private int y;
    private int activePosX;
    private int activePosY;
    private Origin origin;

    // display image
    private javaxt.io.Image backgroundImage;
    private javaxt.io.Image knob;

    // trait
    private String switchDesc;
    private boolean value;
    private int bigBox = 40;
    private int smallBox = 20;

    // mouse handler
    private Handlerclass miniHandler = new Handlerclass();

    // listener
    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    public CustomSwitch(int x, int y, boolean value, Origin origin) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.origin = origin;
        this.switchDesc = "";

        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backgroundImage.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        backgroundImage.resize(bigBox, bigBox);
        knob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "blackbox"));
        knob.resize(smallBox, smallBox); // I meant to do that, intellij.

        initPositionCalc();
    }

    public CustomSwitch(int x, int y, boolean value, Origin origin, String switchDesc) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.origin = origin;
        this.switchDesc = switchDesc;

        // Change the image later. This is just a sample
        backgroundImage = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backgroundImage.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        backgroundImage.resize(bigBox, bigBox);
        knob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "blackbox"));
        knob.resize(smallBox / 2, smallBox / 2); // I meant to do that, intellij.

        initPositionCalc();
    }

    private void initPositionCalc() {
        // calculate active x
        if (origin.getValue() == 0 || origin.getValue() == 3 || origin.getValue() == 6) {
            activePosX = x;
        } else if (origin.getValue() == 1 || origin.getValue() == 4 || origin.getValue() == 7) {
            activePosX = x - (backgroundImage.getWidth() / 2);
        } else if (origin.getValue() == 2 || origin.getValue() == 5 || origin.getValue() == 8) {
            activePosX = x - (backgroundImage.getWidth());
        }

        // calculate active y
        if (origin.getValue() == 0 || origin.getValue() == 1 || origin.getValue() == 2) {
            activePosY = y;
        } else if (origin.getValue() == 3 || origin.getValue() == 4 || origin.getValue() == 5) {
            activePosY = y - (backgroundImage.getHeight() / 2);
        } else if (origin.getValue() == 6 || origin.getValue() == 7 || origin.getValue() == 8) {
            activePosY = y - (backgroundImage.getHeight());
        }
    }

    public void paintComponent(Graphics g) {
        // gui
        g.drawImage(backgroundImage.getBufferedImage(), activePosX, activePosY, that);
        if (value) {
            g.drawImage(knob.getBufferedImage(), activePosX + (backgroundImage.getWidth() / 2) - (knob.getWidth() / 2), activePosY + (backgroundImage.getHeight() / 2) - (knob.getHeight() / 2), that);
        }
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
    public Origin getOrigin() {
        return origin;
    }

    @Override
    public int getActivePosX() {
        return activePosX;
    }

    @Override
    public int getActivePosY() {
        return activePosY;
    }

    @Override
    public int getWidth() {
        return bigBox;
    }

    @Override
    public int getHeight() {
        return bigBox;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getSwitchDesc() {
        return switchDesc;
    }

    @Override
    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    private class Handlerclass extends MasterHandlerClass {
        public void mouseClicked(MouseEvent e) {

            if ((e.getX() > activePosX) &&
                    (e.getX() <= activePosX + bigBox) &&
                    (e.getY() > activePosY) &&
                    (e.getY() <= activePosY + bigBox)) {
                if (value) {
                    value = false;
                } else {
                    value = true;
                }
            }
        }
    }
}
