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

    private int knobX;
    private int knobY;

    // dimension
    private int length = 200;
    private int height = 20;

    // description of slider (shown)
    private String sliderDesc;

    private boolean initStage = true;

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
    }

    // TODO: Haven't full implemented custom knob slider yet
    /*
    public CustomSlider(int x, int y, int level, String fileName) {
        this.x = x;
        this.y = y;

        sliderBackground = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        sliderLeveler = sliderBackground.copy();
        // Find a better way to implement fileName as it uses IO
        sliderKnob = new javaxt.io.Image(RESOURCE_PATH + fileName);
        // sliderKnob = sliderBackground.copy();
        this.level = level;
    }
    */

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        // super.paintComponent(g); // This is disabled so that weird square doesn't print on top left of the setting page
        if (initStage) {

            sliderBackground.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
            // sliderLeveler.setBackgroundColor((int)(255/(double)level), (int)(255/(double)level), (int)(255/(double)level));
            // sliderKnob.setBackgroundColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());

            // SLIDER TRACK
            sliderBackground.resize(length, height, false);
            sliderKnob.resize(sliderBackground.getHeight() * 2, sliderBackground.getHeight() * 2, false);

            initStage = false;
        }
        // Changing sliderLeveler colour
        javaxt.io.Image tempSliderLeveler = sliderLeveler.copy();

        // Changing colours
        tempSliderLeveler.setBackgroundColor(255 - ((int) (255 * ((double) level) / 100.0)), 255 - ((int) (255 * ((double) level) / 100.0)), 255 - ((int) (255 * ((double) level) / 100.0)));

        // calculate the numbering for x
        if(origin.getValue() == 0 || origin.getValue() == 3 || origin.getValue() == 6) {
            knobX = x;
        } else if(origin.getValue() == 1 || origin.getValue() == 4 || origin.getValue() == 7) {
            knobX = x - (sliderBackground.getWidth() / 2);
        } else if(origin.getValue() == 2 || origin.getValue() == 5 || origin.getValue() == 8) {
            knobX = (x - sliderBackground.getWidth());
        }

        // calculate the numbering for y
        if(origin.getValue() == 0 || origin.getValue() == 1 || origin.getValue() == 2) {
            knobY = y;
        } else if(origin.getValue() == 3 || origin.getValue() == 4 || origin.getValue() == 5) {
            knobY = y - (sliderBackground.getHeight() / 2);
        } else if(origin.getValue() == 6 || origin.getValue() == 7 || origin.getValue() == 8) {
            knobY = y - sliderBackground.getHeight();
        }

        g.drawImage(sliderBackground.getBufferedImage(), knobX, knobY, sliderBackground.getWidth(), sliderBackground.getHeight(), that);
        g.drawImage(tempSliderLeveler.getBufferedImage(), knobX, knobY, (int) (sliderBackground.getWidth() * (level / 100.0)), sliderBackground.getHeight(), that);
        g.drawImage(sliderKnob.getBufferedImage(), knobX + (int) (sliderBackground.getWidth() * (level / 100.0)) - (sliderKnob.getWidth() / 2), knobY - (sliderBackground.getHeight() / 2), that);
        // setting up font with proper sizing
        g.setFont(WaifuBrew.getInstance().getSystemFont(WaifuBrew.getInstance().getSystemFontName()).deriveFont(Font.BOLD, WaifuBrew.getInstance().getSystemFontSize()));
        // using the font metrics to center the text above the slider knob
        g.drawString(Integer.toString(level), knobX + (int) (sliderBackground.getWidth() * (level / 100.0)) - (g.getFontMetrics().stringWidth(Integer.toString(level)) / 2), knobY - (sliderBackground.getHeight() / 2) - (WaifuBrew.getInstance().getSystemFontSize() / 2));
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
    public int getAbsoluteX() {
        return this.x + this.length / 2;
    }

    @Override
    public int getAbsoluteY() {
        return this.y + this.height / 2;
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

            // includes "(x - (sliderKnob.getWidth() / 2))" for x since I want to include knob to be responsive that are out of slider range aka 0
            // also (x + (sliderKnob.getWidth() / 2))" for similar effect as above but on other spectrum aka 100
            if (origin.getValue() == 0) {
                if (event.getX() >= (x - sliderKnob.getWidth() / 2) &&
                        event.getX() <= (x + sliderKnob.getWidth() / 2) + sliderBackground.getWidth() &&
                        event.getY() >= y - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= y + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - x) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 1) {
                if (event.getX() >= ((x - sliderKnob.getWidth() / 2) - (sliderBackground.getWidth() / 2)) &&
                        event.getX() <= ((x + sliderKnob.getWidth() / 2) - (sliderBackground.getWidth() / 2)) + sliderBackground.getWidth() &&
                        event.getY() >= y - (sliderBackground.getHeight() / 4) &&
                        event.getY() <= y + ((sliderBackground.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - (sliderBackground.getWidth() / 2))) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 2) {
                if (event.getX() >= ((x - sliderKnob.getWidth() / 2) - sliderBackground.getWidth()) &&
                        event.getX() <= ((x + sliderKnob.getWidth() / 2) - sliderBackground.getWidth()) + sliderBackground.getWidth() &&
                        event.getY() >= y - (sliderBackground.getHeight() / 4) &&
                        event.getY() <= y + ((sliderBackground.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - sliderBackground.getWidth())) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 3) {
                if (event.getX() >= (x - sliderKnob.getWidth() / 2) &&
                        event.getX() <= (x + sliderKnob.getWidth() / 2) + sliderBackground.getWidth() &&
                        event.getY() >= (y - (sliderBackground.getHeight() / 2)) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight() / 2)) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - x) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 4) {
                if (event.getX() >= ((x - sliderKnob.getWidth() / 2) - (sliderBackground.getWidth() / 2)) &&
                        event.getX() <= ((x + sliderKnob.getWidth() / 2) - (sliderBackground.getWidth() / 2)) + sliderBackground.getWidth() &&
                        event.getY() >= (y - (sliderBackground.getHeight() / 2)) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight() / 2)) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - (sliderBackground.getWidth() / 2))) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 5) {
                if (event.getX() >= ((x - sliderKnob.getWidth() / 2) - sliderBackground.getWidth()) &&
                        event.getX() <= ((x + sliderKnob.getWidth() / 2) - sliderBackground.getWidth()) + sliderBackground.getWidth() &&
                        event.getY() >= (y - (sliderBackground.getHeight() / 2)) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight() / 2)) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - sliderBackground.getWidth())) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 6) {
                if (event.getX() >= (x - sliderKnob.getWidth() / 2) &&
                        event.getX() <= (x + sliderKnob.getWidth() / 2) + sliderBackground.getWidth() &&
                        event.getY() >= (y - (sliderBackground.getHeight()) - (sliderKnob.getWidth() / 2)) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight())) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - x) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 7) {
                if (event.getX() >= ((x - sliderKnob.getWidth() / 2) - (sliderBackground.getWidth() / 2)) &&
                        event.getX() <= ((x + sliderKnob.getWidth() / 2) - (sliderBackground.getWidth() / 2)) + sliderBackground.getWidth() &&
                        event.getY() >= (y - (sliderBackground.getHeight())) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight())) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - (sliderBackground.getWidth() / 2))) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 8) {
                if (event.getX() >= ((x - sliderKnob.getWidth() / 2) - sliderBackground.getWidth()) &&
                        event.getX() <= ((x + sliderKnob.getWidth() / 2) - sliderBackground.getWidth()) + sliderBackground.getWidth() &&
                        event.getY() >= (y - (sliderBackground.getHeight())) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight())) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - sliderBackground.getWidth())) / (double) sliderBackground.getWidth()) * 100.0);
                }
            }

            // fixes range issue
            if(level <= 0) {
                level = 0;
            }
            else if(level >= 100) {
                level = 100;
            }
        }

        public void mouseDragged(MouseEvent event) {
            if (sliderActive) {

                if (origin.getValue() == 0 || origin.getValue() == 3 || origin.getValue() == 6) {
                    if (event.getX() <= x) {
                        level = 0;
                    } else if (event.getX() >= (x + sliderBackground.getWidth())) {
                        level = 100;
                    } else {
                        level = (int) (((event.getX() - x) / (double) sliderBackground.getWidth()) * 100.0);
                    }
                } else if (origin.getValue() == 1 || origin.getValue() == 4 || origin.getValue() == 7) {
                    if (event.getX() <= (x - (sliderBackground.getWidth() / 2))) {
                        level = 0;
                    } else if (event.getX() >= ((x - (sliderBackground.getWidth() / 2)) + sliderBackground.getWidth())) {
                        level = 100;
                    } else {
                        level = (int) (((event.getX() - (x - (sliderBackground.getWidth() / 2))) / (double) sliderBackground.getWidth()) * 100.0);
                    }
                } else if (origin.getValue() == 2 || origin.getValue() == 5 || origin.getValue() == 8) {
                    if (event.getX() <= (x - sliderBackground.getWidth())) {
                        level = 0;
                    } else if (event.getX() >= ((x - sliderBackground.getWidth()) + sliderBackground.getWidth())) {
                        level = 100;
                    } else {
                        level = (int) (((event.getX() - (x - sliderBackground.getWidth())) / (double) sliderBackground.getWidth()) * 100.0);
                    }
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
