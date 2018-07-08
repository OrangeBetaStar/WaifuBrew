package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// TODO: Probably better to use this as async instead of putting this in graphics and check if it has been pressed?

// TODO: ^ Need change listener since button's wasPressed needs to be defaulted back to false after stage change.

// This class will handle the buttons that require transparency changes and launch actions
public class CustomButton extends JPanel implements ActionListener {

    private javaxt.io.Image originalButton;
    private javaxt.io.Image mouseoverButton;
    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private int x;
    private int y;
    private boolean wasPressed = false;
    private boolean mouseOver = false;
    private boolean centered = false;

    // this that this that left right left right up down up down
    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    private Handlerclass miniHandler = new Handlerclass();

    public CustomButton (int x, int y, String fileName) {
        this.x = x;
        this.y = y;
        // originalButton will be shown when mouse isn't above the button.
        originalButton = new javaxt.io.Image(RESOURCE_PATH + fileName);
        mouseoverButton = originalButton.copy();
        originalButton.setOpacity(20);
    }

    public CustomButton (int x, int y, String fileName, boolean centered) {
        this.x = x;
        this.y = y;
        // originalButton will be shown when mouse isn't above the button.
        originalButton = new javaxt.io.Image(RESOURCE_PATH + fileName);
        mouseoverButton = originalButton.copy();
        originalButton.setOpacity(20);
        this.centered = centered;
    }

    public CustomButton (int x, int y, String fileName_1, String fileName_2) {
        this.x = x;
        this.y = y;
        // originalButton will be shown and changed to mouseoverButton when mouse is over the pic.
        originalButton = new javaxt.io.Image(RESOURCE_PATH + fileName_1);
        mouseoverButton = new javaxt.io.Image(RESOURCE_PATH + fileName_2);
    }

    // TODO: ACCEPT A PARAMETER THAT HAS BUFFERED IMAGE / IMAGE (So that I can pass image that was cut from image sheet)

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    @Override
    public int getWidth() {
        return originalButton.getWidth();
    }

    @Override
    public int getHeight() {
        return originalButton.getHeight();
    }

    public boolean isPressed() {
        return wasPressed;
    } // Doesn't work reliably to use it. Yet.

    @Override
    public void paintComponent(Graphics g) {
        if(centered) {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x - (originalButton.getWidth() / 2), y - (originalButton.getHeight() / 2), originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x - (originalButton.getWidth() / 2), y - (originalButton.getHeight() / 2), mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }
        else {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x, y, originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x, y, mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }
    }

    private class Handlerclass implements MouseListener, MouseMotionListener {

        public void mousePressed (MouseEvent event) {

        }
        public void mouseMoved (MouseEvent event) {
            if(!centered) {
                if (event.getX() > x && event.getX() < (x + originalButton.getWidth()) && event.getY() > y && event.getY() < (y + originalButton.getHeight())) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            else {
                if (event.getX() > x - ((originalButton.getWidth()) / 2) && event.getX() < (x + originalButton.getWidth() / 2) && event.getY() > y - ((originalButton.getHeight()) / 2) && event.getY() < (y + originalButton.getHeight() / 2)) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
        }
        public void mouseDragged (MouseEvent event) {

        }
        public void mouseClicked (MouseEvent event) {
            if(event.getX() > x && event.getX() < (x + originalButton.getWidth()) && event.getY() > y && event.getY() < (y + originalButton.getHeight())) {
                wasPressed = true;
            }
        }
        public void mouseEntered (MouseEvent event) {

        }
        public void mouseReleased (MouseEvent event) {
            // TODO: If state wasn't changed, do not change the boolean to false
            if(mouseOver)
                mouseOver = false;
        }
        public void mouseExited (MouseEvent event) {

        }
    }
}
