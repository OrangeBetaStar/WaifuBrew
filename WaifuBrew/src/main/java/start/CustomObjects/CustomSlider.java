package start.CustomObjects;

import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class CustomSlider extends InteractiveObjects implements ActionListener {

    // image to draw
    private javaxt.io.Image sliderBackground; // shows max / min
    private javaxt.io.Image sliderLeveler; // changing colour with value
    private javaxt.io.Image sliderKnob; // the moveable knob

    // attributes
    private int level; // value
    private boolean sliderActive = false;
    private Origin origin;

    // position
    private int x; // absolute location of X
    private int y; // absolute location of Y
    private int activePosX; // calculated position respect to origin
    private int activePosY; // calculated position respect to origin

    // dimension of slider
    private int length = 200;
    private int height = 20;

    // description of slider (shown)
    private String sliderDesc;

    // this that this that left right left right up down up down
    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    // Mouse listener
    private Handlerclass miniHandler = new Handlerclass();

    public CustomSlider(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.sliderDesc = "";

        sliderBackground = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        sliderLeveler = sliderBackground.copy();
        sliderKnob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "slider_knob"));
        this.level = level;

        sliderBackground.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        sliderBackground.resize(length, height, false);
        sliderKnob.resize(sliderBackground.getHeight() * 2, sliderBackground.getHeight() * 2, false);

        initPositionCalc();
    }

    public CustomSlider(int x, int y, int level, String sliderDesc) {
        this.x = x;
        this.y = y;
        this.origin = Origin.LEFT_TOP; // default
        this.sliderDesc = sliderDesc;

        sliderBackground = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        sliderLeveler = sliderBackground.copy();
        sliderKnob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "slider_knob"));
        this.level = level;

        sliderBackground.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        sliderBackground.resize(length, height, false);
        sliderKnob.resize(sliderBackground.getHeight() * 2, sliderBackground.getHeight() * 2, false);

        initPositionCalc();
    }

    public CustomSlider(int x, int y, int level, Origin origin, String sliderDesc) {
        this.x = x;
        this.y = y;
        this.origin = origin;
        this.sliderDesc = sliderDesc;

        sliderBackground = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        sliderLeveler = sliderBackground.copy();
        sliderKnob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "slider_knob"));
        this.level = level;

        sliderBackground.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        sliderBackground.resize(length, height, false);
        sliderKnob.resize(sliderBackground.getHeight() * 2, sliderBackground.getHeight() * 2, false);

        initPositionCalc();
    }

    public CustomSlider(int x, int y, int level, Origin origin, String sliderDesc, String customSliderName) {
        this.x = x;
        this.y = y;
        this.origin = origin;
        this.sliderDesc = sliderDesc;

        sliderBackground = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        sliderLeveler = sliderBackground.copy();
        sliderKnob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, customSliderName));
        this.level = level;

        sliderBackground.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        sliderBackground.resize(length, height, false);
        sliderKnob.resize(sliderBackground.getHeight() * 2, sliderBackground.getHeight() * 2, false);

        initPositionCalc();
    }

    private void initPositionCalc() {
        // calculate the numbering for x
        if (origin.getValue() == 0 || origin.getValue() == 3 || origin.getValue() == 6) {
            activePosX = x;
        } else if (origin.getValue() == 1 || origin.getValue() == 4 || origin.getValue() == 7) {
            activePosX = x - (sliderBackground.getWidth() / 2);
        } else if (origin.getValue() == 2 || origin.getValue() == 5 || origin.getValue() == 8) {
            activePosX = x - sliderBackground.getWidth();
        }

        // calculate the numbering for y
        if (origin.getValue() == 0 || origin.getValue() == 1 || origin.getValue() == 2) {
            activePosY = y;
        } else if (origin.getValue() == 3 || origin.getValue() == 4 || origin.getValue() == 5) {
            activePosY = y - (sliderBackground.getHeight() / 2);
        } else if (origin.getValue() == 6 || origin.getValue() == 7 || origin.getValue() == 8) {
            activePosY = y - sliderBackground.getHeight();
        }
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        // super.paintComponent(g); // This is disabled so that weird square doesn't print on top left of the setting page

        // Changing sliderLeveler colour
        javaxt.io.Image tempSliderLeveler = sliderLeveler.copy();

        // Changing colours
        tempSliderLeveler.setBackgroundColor(255 - ((int) (255 * ((double) level) / 100.0)), 255 - ((int) (255 * ((double) level) / 100.0)), 255 - ((int) (255 * ((double) level) / 100.0)));

        // gui
        g.drawImage(sliderBackground.getBufferedImage(), activePosX, activePosY, sliderBackground.getWidth(), sliderBackground.getHeight(), that);
        g.drawImage(tempSliderLeveler.getBufferedImage(), activePosX, activePosY, (int) (sliderBackground.getWidth() * (level / 100.0)), sliderBackground.getHeight(), that);
        g.drawImage(sliderKnob.getBufferedImage(), activePosX + (int) (sliderBackground.getWidth() * (level / 100.0)) - (sliderKnob.getWidth() / 2), activePosY - (sliderBackground.getHeight() / 2), that);
        // setting up font with proper sizing
        g.setFont(WaifuBrew.getInstance().getSystemFont(WaifuBrew.getInstance().getSystemFontName()).deriveFont(Font.BOLD, WaifuBrew.getInstance().getSystemFontSize()));
        // using the font metrics to center the text above the slider knob
        g.drawString(Integer.toString(level), activePosX + (int) (sliderBackground.getWidth() * (level / 100.0)) - (g.getFontMetrics().stringWidth(Integer.toString(level)) / 2), activePosY - (sliderBackground.getHeight() / 2) - (WaifuBrew.getInstance().getSystemFontSize() / 2));
    }

    public boolean isSliderActive() {
        return sliderActive;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        return length;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public String getSliderDesc() {
        return sliderDesc;
    }

    @Override
    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    private class Handlerclass extends MasterHandlerClass {
        public void mousePressed(MouseEvent event) {

            if (event.getX() >= (activePosX - sliderKnob.getWidth() / 2) &&
                    event.getX() <= (activePosX + sliderKnob.getWidth() / 2) + sliderBackground.getWidth() &&
                    event.getY() >= activePosY - (sliderKnob.getHeight() / 4) &&
                    event.getY() <= activePosY + ((sliderKnob.getHeight() / 4) * 3)) {
                sliderActive = true;
                level = (int) (((event.getX() - activePosX) / (double) sliderBackground.getWidth()) * 100.0);
            }

            // fixes range issue
            if (level <= 0) {
                level = 0;
            } else if (level >= 100) {
                level = 100;
            }
        }

        public void mouseDragged(MouseEvent event) {
            if (sliderActive) {

                // having just the x will have active area limitless to y axis
                level = (int) (((event.getX() - activePosX) / (double) sliderBackground.getWidth()) * 100.0);

                // fixes range issue
                if (level <= 0) {
                    level = 0;
                } else if (level >= 100) {
                    level = 100;
                }
            }
        }

        public void mouseReleased(MouseEvent event) {
            if (sliderActive) {
                sliderActive = false;
            }
        }
    }
}
