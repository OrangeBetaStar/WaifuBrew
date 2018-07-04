package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomSlider extends JPanel implements ActionListener {
    private javaxt.io.Image slider_background;  // sliderTrack
    private javaxt.io.Image slider_leveler; // dialogueTransparencySlider
    private javaxt.io.Image slider_knob; // dialogueTransparencyKnob
    private int dialogueKnobX; // dialogueTransparencyKnobX
    private int dialogueKnobY; // dialogueTransparencyKnobY
    private int level; // dialogueTransparency
    private boolean sliderActive = false; //dialogueTransparencyMove
    private int x; // dialogueX
    private int y; // dialogueTransparencyY
    private boolean initStage = true;

    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private Handlerclass miniHandler = new Handlerclass();

    public CustomSlider(int x, int y, int level) {
        this.x = x;
        this.y = y;
        slider_background = new javaxt.io.Image(RESOURCE_PATH + "white.png");
        slider_leveler = new javaxt.io.Image(RESOURCE_PATH + "white.png");
        slider_knob = new javaxt.io.Image(RESOURCE_PATH + "white.png");

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
            // TODO: FIX THIS. SHIT IMPLEMENTATION AS TEMP (CHANGING EACH PIXEL WTF)
            for(int a=0; a <= slider_knob.getWidth(); a++) {
                for(int b=0; b <= slider_leveler.getHeight(); b++) {
                    slider_leveler.setColor(a, b, Color.CYAN);
                    slider_knob.setColor(a, b, Color.ORANGE);
                }
            }

            // SLIDER TRACK
            slider_background.resize(200, 20, false);

            // slider_leveler.setBackgroundColor(90, 90, 90);
            slider_knob.resize(slider_background.getHeight(), slider_background.getHeight(), false);
            dialogueKnobX = x + (int)(slider_background.getWidth() * (level/100.0));
            dialogueKnobY = y;

            initStage = false;
        }

        // TODO: Calculate the following (x, y) to relative to resolution (current defaults to mid way of knob)
        // +20 on width so knob fits
        g.drawImage(slider_background.getBufferedImage(), x, dialogueKnobY, slider_background.getWidth() + 20, slider_background.getHeight(), that);
        g.drawImage(slider_leveler.getBufferedImage(), x, dialogueKnobY, (int)(slider_background.getWidth() * (level/100.0)) + (int)(slider_knob.getWidth() * 0.5), slider_background.getHeight(), that);
        g.drawImage(slider_knob.getBufferedImage(), dialogueKnobX, dialogueKnobY, that);

        // DISPLAY EXAMPLE
        // TODO: STEP 1
        /*
        if(showExample) {
            javaxt.io.Image dialogueBox = new javaxt.io.Image(RESOURCE_PATH + "dialogbar.png");
            dialogueBox.resize((int) (dialogueBox.getWidth() * 0.9), (int) (dialogueBox.getHeight() * 0.9));
            dialogueBox.setOpacity(dialogueTransparency);
            g.drawImage(dialogueBox.getBufferedImage(), WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2, WaifuBrew.getInstance().getRes()[1].y - dialogueBox.getHeight() - (WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2), this);
        }
        */
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
                WaifuBrew.getInstance().setDialogueTransparency(level);
            }
        }
        public void mouseExited (MouseEvent event) {
        }
    }

}
