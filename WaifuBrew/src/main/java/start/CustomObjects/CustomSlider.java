package start.CustomObjects;

import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

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
    private String sliderDesc;

    private boolean initStage = true;

    // this that this that left right left right up down up down
    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
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
            dialogueKnobX = x + ((int) (sliderBackground.getWidth() * (level / 100.0)) - sliderBackground.getHeight());
            dialogueKnobY = y;

            initStage = false;
        }
        // Changing background
        javaxt.io.Image tempSliderLeveler = sliderLeveler.copy();
        tempSliderLeveler.setBackgroundColor(255 - ((int)(255*((double)level)/100.0)), 255 - ((int)(255*((double)level)/100.0)), 255 - ((int)(255*((double)level)/100.0)));

        // Draw slider (Some of the calculations may look weird since this was also accounting for snap back after re-entry to config.
        g.drawImage(sliderBackground.getBufferedImage(), x, dialogueKnobY, sliderBackground.getWidth(), sliderBackground.getHeight(), that);
        g.drawImage(tempSliderLeveler.getBufferedImage(), x, dialogueKnobY, (int) (sliderBackground.getWidth() * (level / 100.0)), sliderBackground.getHeight(), that);
        g.drawImage(sliderKnob.getBufferedImage(), x + (int)(sliderBackground.getWidth() * (level / 100.0)) - (sliderKnob.getWidth() / 2), dialogueKnobY  - (sliderBackground.getHeight() / 2), that);
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
            if (event.getX() >= x &&
                event.getX() <= x + sliderBackground.getWidth() &&
                event.getY() >= dialogueKnobY - (sliderKnob.getHeight() / 4) && // One fourth way up above x (because button is twice as big
                event.getY() <= dialogueKnobY + ((sliderKnob.getHeight() / 4) * 3)) { // Three fourth way down below x (because button is twice as big)
                sliderActive = true;

                // First press: move knob (Runs only once)
                level = (int)(((event.getX() - x) / (double)sliderBackground.getWidth()) * 100.0);
                dialogueKnobX = x + ((int) (sliderBackground.getWidth() * (level / 100.0)) - sliderBackground.getHeight());
            }
        }

        public void mouseDragged(MouseEvent event) {
            if (sliderActive) {

                if (event.getX() <= x) {
                    level = 0;
                }
                else if (event.getX() >= (x + sliderBackground.getWidth())) {
                    level = 100;
                }
                else {
                    level = (int)(((event.getX() - x) / (double)sliderBackground.getWidth()) * 100.0);
                    dialogueKnobX = x + ((int) (sliderBackground.getWidth() * (level / 100.0)) - sliderBackground.getHeight());
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
