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

        // TODO: if any goes 100, kill
        if(level >= 100) {
            tempSliderLeveler.setBackgroundColor(255, 255, 255);
        }
        else if(level <= 0) {
            tempSliderLeveler.setBackgroundColor(0, 0, 0);
        }
        else {
            tempSliderLeveler.setBackgroundColor(255 - ((int) (255 * ((double) level) / 100.0)), 255 - ((int) (255 * ((double) level) / 100.0)), 255 - ((int) (255 * ((double) level) / 100.0)));

        }

        // calculate x
        if(origin.getValue() == 0 || origin.getValue() == 3 || origin.getValue() == 6) {
            knobX = x;
        }
        else if(origin.getValue() == 1 || origin.getValue() == 4 || origin.getValue() == 7) {
            knobX = x - (sliderBackground.getWidth() / 2);
        }
        else if(origin.getValue() == 2 || origin.getValue() == 5 || origin.getValue() == 8) {
            knobX = x - sliderBackground.getWidth();
        }

        // calculate y
        if(origin.getValue() == 0 || origin.getValue() == 1 || origin.getValue() == 2) {
            knobY = y;
        }
        else if(origin.getValue() == 3 || origin.getValue() == 4 || origin.getValue() == 5) {
            knobY = y - (sliderBackground.getHeight() / 2);
        }
        else if(origin.getValue() == 6 || origin.getValue() == 7 || origin.getValue() == 8) {
            knobY = y - sliderBackground.getHeight();
        }
            g.drawImage(sliderBackground.getBufferedImage(), x, y, sliderBackground.getWidth(), sliderBackground.getHeight(), that);
            g.drawImage(tempSliderLeveler.getBufferedImage(), x, y, (int) (sliderBackground.getWidth() * (level / 100.0)), sliderBackground.getHeight(), that);
            g.drawImage(sliderKnob.getBufferedImage(), x + (int) (sliderBackground.getWidth() * (level / 100.0)) - (sliderKnob.getWidth() / 2), y - (sliderBackground.getHeight() / 2), that);

            g.setFont(WaifuBrew.getInstance().getSystemFont(WaifuBrew.getInstance().getSystemFontName()).deriveFont(Font.BOLD, WaifuBrew.getInstance().getSystemFontSize()));
            g.drawString(Integer.toString(level), x + (int) (sliderBackground.getWidth() * (level / 100.0)) - (sliderKnob.getWidth() / 2), y - (sliderBackground.getHeight() / 2) - (WaifuBrew.getInstance().getSystemFontSize() / 2));

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
        // TODO: FINE TUNE THE KNOBS SO THAT IT KEEPS THE ORIGINAL POSITION OF CLICK POINT OF SQUARE (CURRENT IS MIDDLE)
        public void mousePressed(MouseEvent event) {

            /*
            if (event.getX() >= x &&
                event.getX() <= x + sliderBackground.getWidth() &&
                event.getY() >= y - (sliderKnob.getHeight() / 4) && // One fourth way up above x (because button is twice as big
                event.getY() <= y + ((sliderKnob.getHeight() / 4) * 3)) { // Three fourth way down below x (because button is twice as big)
                sliderActive = true;

                // First press: move knob (Runs only once)
                level = (int)(((event.getX() - x) / (double)sliderBackground.getWidth()) * 100.0);
                // dialogueKnobX = x + ((int) (sliderBackground.getWidth() * (level / 100.0)) - sliderBackground.getHeight());
            }
             */

            if (origin.getValue() == 0) {
                if (event.getX() >= (x - (sliderKnob.getWidth() / 2)) &&
                        event.getX() <= x + sliderBackground.getWidth() + (sliderKnob.getWidth() / 2) &&
                        event.getY() >= y - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= y + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - x) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 1) {
                if (event.getX() >= ((x - (sliderKnob.getWidth() / 2)) - (sliderBackground.getWidth() / 2)) &&
                        event.getX() <= (x - (sliderBackground.getWidth() / 2)) + sliderBackground.getWidth() + (sliderKnob.getWidth() / 2) &&
                        event.getY() >= y - (sliderBackground.getHeight() / 4) &&
                        event.getY() <= y + ((sliderBackground.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - (sliderBackground.getWidth() / 2))) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 2) {
                if (event.getX() >= ((x - (sliderKnob.getWidth() / 2)) - sliderBackground.getWidth()) &&
                        event.getX() <= (x - sliderBackground.getWidth()) + sliderBackground.getWidth() + (sliderKnob.getWidth() / 2) &&
                        event.getY() >= y - (sliderBackground.getHeight() / 4) &&
                        event.getY() <= y + ((sliderBackground.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - sliderBackground.getWidth())) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 3) {
                if (event.getX() >= (x - (sliderKnob.getWidth() / 2)) &&
                        event.getX() <= x + sliderBackground.getWidth() + (sliderKnob.getWidth() / 2) &&
                        event.getY() >= (y - (sliderBackground.getHeight() / 2)) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight() / 2)) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - x) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 4) {
                if (event.getX() >= ((x - (sliderKnob.getWidth() / 2)) - (sliderBackground.getWidth() / 2)) &&
                        event.getX() <= (x - (sliderBackground.getWidth() / 2)) + sliderBackground.getWidth() + (sliderKnob.getWidth() / 2) &&
                        event.getY() >= (y - (sliderBackground.getHeight() / 2)) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight() / 2)) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - (sliderBackground.getWidth() / 2))) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 5) {
                if (event.getX() >= ((x - (sliderKnob.getWidth() / 2)) - sliderBackground.getWidth()) &&
                        event.getX() <= (x - sliderBackground.getWidth()) + sliderBackground.getWidth() + (sliderKnob.getWidth() / 2) &&
                        event.getY() >= (y - (sliderBackground.getHeight() / 2)) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight() / 2)) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - sliderBackground.getWidth())) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 6) {
                if (event.getX() >= (x - (sliderKnob.getWidth() / 2)) &&
                        event.getX() <= x + sliderBackground.getWidth() + (sliderKnob.getWidth() / 2) &&
                        event.getY() >= (y - (sliderBackground.getHeight()) - (sliderKnob.getWidth() / 2)) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight())) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - x) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 7) {
                if (event.getX() >= ((x - (sliderKnob.getWidth() / 2)) - (sliderBackground.getWidth() / 2)) &&
                        event.getX() <= (x - (sliderBackground.getWidth() / 2)) + sliderBackground.getWidth() + (sliderKnob.getWidth() / 2) &&
                        event.getY() >= (y - (sliderBackground.getHeight())) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight())) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - (sliderBackground.getWidth() / 2))) / (double) sliderBackground.getWidth()) * 100.0);
                }
            } else if (origin.getValue() == 8) {
                if (event.getX() >= ((x - (sliderKnob.getWidth() / 2)) - sliderBackground.getWidth()) &&
                        event.getX() <= (x - sliderBackground.getWidth()) + sliderBackground.getWidth() + (sliderKnob.getWidth() / 2) &&
                        event.getY() >= (y - (sliderBackground.getHeight())) - (sliderKnob.getHeight() / 4) &&
                        event.getY() <= (y - (sliderBackground.getHeight())) + ((sliderKnob.getHeight() / 4) * 3)) {
                    sliderActive = true;
                    level = (int) (((event.getX() - (x - sliderBackground.getWidth())) / (double) sliderBackground.getWidth()) * 100.0);
                }
            }


        }

        public void mouseDragged(MouseEvent event) {
            if (sliderActive) {
                /*
                if (event.getX() <= x) {
                    level = 0;
                }
                else if (event.getX() >= (x + sliderBackground.getWidth())) {
                    level = 100;
                }
                else {
                    level = (int)(((event.getX() - x) / (double)sliderBackground.getWidth()) * 100.0);
                    // dialogueKnobX = x + ((int) (sliderBackground.getWidth() * (level / 100.0)) - sliderBackground.getHeight());
                }
                 */

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
