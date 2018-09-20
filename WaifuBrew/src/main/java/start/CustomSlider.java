package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomSlider extends JPanel implements ActionListener {
    private javaxt.io.Image sliderBackground; // sliderTrack
    private javaxt.io.Image sliderLeveler; // dialogueTransparencySlider
    private javaxt.io.Image sliderKnob; // dialogueTransparencyKnob
    private int dialogueKnobX; // dialogueTransparencyKnobX
    private int dialogueKnobY; // dialogueTransparencyKnobY
    private int level; // dialogueTransparency
    private boolean sliderActive = false; //dialogueTransparencyMove
    private int x; // dialogueX
    private int y; // dialogueTransparencyY

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

    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(initStage) {

            // TODO: May be fix this? Why would I need to get WHITE.getRed and stuff???
            sliderBackground.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
            sliderLeveler.setBackgroundColor(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue());
            // sliderKnob.setBackgroundColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());

            // SLIDER TRACK
            sliderBackground.resize(200, 20, false);
            sliderKnob.resize(sliderBackground.getHeight(), sliderBackground.getHeight(), false);
            dialogueKnobX = x + (int)(sliderBackground.getWidth() * (level/100.0));
            dialogueKnobY = y;

            initStage = false;
        }

        // TODO: Calculate the following (x, y) to relative to resolution (current defaults to mid way of knob)
        g.drawImage(sliderBackground.getBufferedImage(), x, dialogueKnobY, sliderBackground.getWidth() + sliderKnob.getWidth(), sliderBackground.getHeight(), that);
        g.drawImage(sliderLeveler.getBufferedImage(), x, dialogueKnobY, (int)(sliderBackground.getWidth() * (level/100.0)) + (int)(sliderKnob.getWidth() * 0.5), sliderBackground.getHeight(), that);
        g.drawImage(sliderKnob.getBufferedImage(), dialogueKnobX, dialogueKnobY, that);

    }

    public boolean isSliderActive() {
        return sliderActive;
    }

    public int getLevel() {
        return this.level;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    private class Handlerclass implements MouseListener, MouseMotionListener {
        // TODO: FINE TUNE THE KNOBS SO THAT IT KEEPS THE ORIGINAL POSITION OF CLICK POINT OF SQUARE (CURRENT IS MIDDLE)
        public void mousePressed (MouseEvent event) {
            if(event.getX() >= dialogueKnobX && event.getX() <= dialogueKnobX + sliderKnob.getWidth() && event.getY() >= dialogueKnobY && event.getY() <= dialogueKnobY + sliderKnob.getHeight()) {
                sliderActive = true;
            }
        }
        public void mouseMoved (MouseEvent event) {

        }
        public void mouseDragged (MouseEvent event) {
            if(sliderActive) {
                if(event.getX() >= x + (int)(sliderKnob.getWidth() * 0.5) && event.getX() <= x + sliderLeveler.getWidth() + (int)(sliderKnob.getWidth() * 0.5)) {
                    dialogueKnobX = (int) (event.getX() - (sliderKnob.getWidth() * 0.5));
                    level = (int)(((dialogueKnobX - x) / (double)sliderBackground.getWidth()) * 100);

                    if(level <= 1 ) {
                        // <= 1 because 1 == true and program thinks it wants to be opaque
                        level = 0;
                    }
                }
            }
        }
        public void mouseClicked (MouseEvent event) {

        }
        public void mouseEntered (MouseEvent event) {

        }
        public void mouseReleased (MouseEvent event) {
            if(sliderActive) {
                sliderActive = false;
            }
        }
        public void mouseExited (MouseEvent event) {
        }
    }

}
