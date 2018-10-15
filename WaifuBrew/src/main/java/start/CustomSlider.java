package start;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class CustomSlider extends InteractiveObjects implements ActionListener {
    private javaxt.io.Image sliderBackground; // sliderTrack
    private javaxt.io.Image sliderLeveler; // dialogueTransparencySlider
    private javaxt.io.Image sliderKnob; // dialogueTransparencyKnob
    private int dialogueKnobX; // dialogueTransparencyKnobX
    private int dialogueKnobY; // dialogueTransparencyKnobY
    private int level; // dialogueTransparency
    private boolean sliderActive = false; //dialogueTransparencyMove
    private int x; // dialogueX
    private int y; // dialogueTransparencyY
    private int length = 200;
    private int height = 20;

    private boolean initStage = true;

    // this that this that left right left right up down up down
    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private Handlerclass miniHandler = new Handlerclass();

    public CustomSlider(int x, int y, int level) {
        this.x = x;
        this.y = y;

        sliderBackground = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        sliderLeveler = sliderBackground.copy();
        sliderKnob = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "slider_knob"));
        this.level = level;
    }

    // TODO: Haven't full implemented custom knob slider yet
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

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (initStage) {

            // TODO: May be fix this? Why would I need to get WHITE.getRed and stuff???
            sliderBackground.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
            sliderLeveler.setBackgroundColor(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue());
            // sliderKnob.setBackgroundColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());

            // SLIDER TRACK
            sliderBackground.resize(length, height, false);
            sliderKnob.resize(sliderBackground.getHeight(), sliderBackground.getHeight(), false);
            dialogueKnobX = x + (int) (sliderBackground.getWidth() * (level / 100.0));
            dialogueKnobY = y;

            initStage = false;
        }

        // TODO: Calculate the following (x, y) to relative to resolution (current defaults to mid way of knob)
        g.drawImage(sliderBackground.getBufferedImage(), x, dialogueKnobY, sliderBackground.getWidth() + sliderKnob.getWidth(), sliderBackground.getHeight(), that);
        g.drawImage(sliderLeveler.getBufferedImage(), x, dialogueKnobY, (int) (sliderBackground.getWidth() * (level / 100.0)) + (int) (sliderKnob.getWidth() * 0.5), sliderBackground.getHeight(), that);
        g.drawImage(sliderKnob.getBufferedImage(), (int) (x + ((level / 100.0) * length)), dialogueKnobY, that);
        // dialogueKnobX = (int)(x + ((level/100.0) * length));

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

    @Override
    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    private class Handlerclass extends MasterHandlerClass {
        // TODO: FINE TUNE THE KNOBS SO THAT IT KEEPS THE ORIGINAL POSITION OF CLICK POINT OF SQUARE (CURRENT IS MIDDLE)
        public void mousePressed(MouseEvent event) {
            if (event.getX() >= (int) (x + ((level / 100.0) * length)) && event.getX() <= (int) (x + ((level / 100.0) * length)) + sliderKnob.getWidth() && event.getY() >= dialogueKnobY && event.getY() <= dialogueKnobY + sliderKnob.getHeight()) {
                sliderActive = true;
            }
        }

        public void mouseDragged(MouseEvent event) {
            if (sliderActive) {
                if (event.getX() >= x + (int) (sliderKnob.getWidth() * 0.5) && event.getX() <= x + sliderLeveler.getWidth() + (int) (sliderKnob.getWidth() * 0.5)) {
                    dialogueKnobX = (int) (event.getX() - (sliderKnob.getWidth() * 0.5));
                    level = (int) (((dialogueKnobX - x) / (double) sliderBackground.getWidth()) * 100);

                    if (level <= 1) {
                        // <= 1 because 1 == true and program thinks it wants to be opaque
                        level = 0;
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
