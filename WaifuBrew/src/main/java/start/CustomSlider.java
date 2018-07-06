package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomSlider extends JPanel implements ActionListener {
    private javaxt.io.Image slider_background; // sliderTrack
    private javaxt.io.Image slider_leveler; // dialogueTransparencySlider
    private javaxt.io.Image slider_knob; // dialogueTransparencyKnob
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
        slider_background = new javaxt.io.Image(RESOURCE_PATH + "white.png");
        slider_leveler = slider_background.copy();
        slider_knob = slider_background.copy();
        this.level = level;
    }

    // TODO: Haven't full implemented custom knob slider yet
    public CustomSlider(int x, int y, int level, String fileName) {
        this.x = x;
        this.y = y;
        slider_background = new javaxt.io.Image(RESOURCE_PATH + "white.png");
        slider_leveler = slider_background.copy();
        slider_knob = new javaxt.io.Image(RESOURCE_PATH + fileName);
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
        if(initStage) {

            // TODO: May be fix this? Why would I need to get WHITE.getRed and stuff???
            slider_background.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
            slider_leveler.setBackgroundColor(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue());
            slider_knob.setBackgroundColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());

            // SLIDER TRACK
            slider_background.resize(200, 20, false);
            slider_knob.resize(slider_background.getHeight(), slider_background.getHeight(), false);
            dialogueKnobX = x + (int)(slider_background.getWidth() * (level/100.0));
            dialogueKnobY = y;

            initStage = false;
        }

        // TODO: Calculate the following (x, y) to relative to resolution (current defaults to mid way of knob)
        g.drawImage(slider_background.getBufferedImage(), x, dialogueKnobY, slider_background.getWidth() + slider_knob.getWidth(), slider_background.getHeight(), that);
        g.drawImage(slider_leveler.getBufferedImage(), x, dialogueKnobY, (int)(slider_background.getWidth() * (level/100.0)) + (int)(slider_knob.getWidth() * 0.5), slider_background.getHeight(), that);
        g.drawImage(slider_knob.getBufferedImage(), dialogueKnobX, dialogueKnobY, that);

    }

    public boolean isSliderActive() {
        return sliderActive;
    }

    public int getLevel() {
        return level;
    }

    private class Handlerclass implements MouseListener, MouseMotionListener {
        // TODO: FINE TUNE THE KNOBS SO THAT IT KEEPS THE ORIGINAL POSITION OF CLICK POINT OF SQUARE (CURRENT IS MIDDLE)
        public void mousePressed (MouseEvent event) {
            if(event.getX() >= dialogueKnobX && event.getX() <= dialogueKnobX + slider_knob.getWidth() && event.getY() >= dialogueKnobY && event.getY() <= dialogueKnobY + slider_knob.getHeight()) {
                sliderActive = true;
            }
        }
        public void mouseMoved (MouseEvent event) {

        }
        public void mouseDragged (MouseEvent event) {
            if(sliderActive) {
                if(event.getX() >= x + (int)(slider_knob.getWidth() * 0.5) && event.getX() <= x + slider_leveler.getWidth() + (int)(slider_knob.getWidth() * 0.5)) {
                    dialogueKnobX = (int) (event.getX() - (slider_knob.getWidth() * 0.5));
                    level = (int)(((dialogueKnobX - x) / (double)slider_background.getWidth()) * 100);
                    if(level == 0) {
                        level = 1;
                    }
                    // System.out.println("Level: "+level);
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
