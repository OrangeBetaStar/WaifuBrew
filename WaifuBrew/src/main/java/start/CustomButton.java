package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

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
    private double GUIScale = (double)WaifuBrew.getInstance().getSystemGUIScale();
    private int defaultButtonHeight = 75;
    private Origin origin;

    // this that this that left right left right up down up down
    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    private Handlerclass miniHandler = new Handlerclass();

    public CustomButton (int x, int y, String fileName, Origin origin) {
        this.x = x;
        this.y = y;
        // originalButton will be shown when mouse isn't above the button.
        if(fileName.contains("start_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[0]);
        }
        else if(fileName.contains("save_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[1]);
        }
        else if(fileName.contains("load_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[2]);
        }
        else if(fileName.contains("back_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[3]);
        }
        else if(fileName.contains("config_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[4]);
        }
        else if(fileName.contains("exit_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[5]);
        }
        else {
            originalButton = new javaxt.io.Image(RESOURCE_PATH + fileName);
            System.out.println("Please fix code: I/O access detected.");
        }
        originalButton.resize((int)(originalButton.getWidth() * (GUIScale/originalButton.getHeight())), defaultButtonHeight, true);
        mouseoverButton = originalButton.copy();
        originalButton.setOpacity(20);
        this.origin = origin;
    }

    public CustomButton (int x, int y, String fileName, Origin origin, double sizeY, boolean invert) {
        this.x = x;
        this.y = y;
        // originalButton will be shown when mouse isn't above the button.
        if(fileName.contains("start_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[0]);
        }
        else if(fileName.contains("save_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[1]);
        }
        else if(fileName.contains("load_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[2]);
        }
        else if(fileName.contains("back_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[3]);
        }
        else if(fileName.contains("config_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[4]);
        }
        else if(fileName.contains("exit_button")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getSystemImage()[5]);
        }
        else {
            originalButton = new javaxt.io.Image(RESOURCE_PATH + fileName);
            System.out.println("Please fix code: I/O access detected.");
        }
        if(invert) {
            System.out.println("inverted. " + fileName);
            originalButton = new javaxt.io.Image(imageInverter(originalButton.getBufferedImage()));
        }
        if(sizeY == 0) {
            originalButton.resize((int)(originalButton.getWidth() * (GUIScale/originalButton.getHeight())), defaultButtonHeight, true);
        }
        else {
            originalButton.resize((int)(originalButton.getWidth() * (sizeY/originalButton.getHeight())), (int)sizeY, false);
        }
        mouseoverButton = originalButton.copy();
        originalButton.setOpacity(20);
        this.origin = origin;
    }

    public CustomButton (int x, int y, String fileName_1, String fileName_2) {
        this.x = x;
        this.y = y;
        // originalButton will be shown and changed to mouseoverButton when mouse is over the pic.
        originalButton = new javaxt.io.Image(RESOURCE_PATH + fileName_1);
        mouseoverButton = new javaxt.io.Image(RESOURCE_PATH + fileName_2);
    }

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

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getAbsoluteX() {
        if (origin.getValue() == 0 || origin.getValue() == 3 || origin.getValue() == 6) {
            return x;
        }

        if (origin.getValue() == 1 || origin.getValue() == 4 || origin.getValue() == 7) {
            return (x - (originalButton.getWidth() / 2));
        }

        if (origin.getValue() == 2 || origin.getValue() == 5 || origin.getValue() == 8) {
            return (x - (originalButton.getWidth()));
        }
        return x;
    }

    public int getAbsoluteY() {
        if (origin.getValue() == 0 || origin.getValue() == 1 || origin.getValue() == 2) {
            return x;
        }

        if (origin.getValue() == 3 || origin.getValue() == 4 || origin.getValue() == 5) {
            return (y - (originalButton.getHeight() / 2));
        }

        if (origin.getValue() == 6 || origin.getValue() == 7 || origin.getValue() == 8) {
            return (y - (originalButton.getHeight()));
        }
        return y;
    }

    public boolean isPressed() {
        return wasPressed;
    } // Doesn't work reliably to use it. Yet.

    // Calculating RGB to YIQ Values for possible invert the colour of buttons. (Sometimes I can't see white on white)
    public static Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return y >= 128 ? Color.black : Color.white;
    }

    @Override
    public void paintComponent(Graphics g) {

    /*  LEFT_TOP(0)
        MIDDLE_TOP(1)
        RIGHT_TOP(2)
        LEFT_CENTRE(3)
        MIDDLE_CENTRE(4)
        RIGHT_CENTRE(5)
        LEFT_BOTTOM(6)
        MIDDLE_BOTTOM(7)
        RIGHT_BOTTOM(8)
    */
        if (origin.getValue() == 0) {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x, y, originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x, y, mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }

        if (origin.getValue() == 1) {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x - (originalButton.getWidth() / 2), y, originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x - (mouseoverButton.getWidth() / 2), y, mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }

        if (origin.getValue() == 2) {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x - (originalButton.getWidth()), y, originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x - (mouseoverButton.getWidth()), y, mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }

        if (origin.getValue() == 3) {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x, y - (originalButton.getHeight() / 2), originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x, y - (mouseoverButton.getHeight() / 2), mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }

        if (origin.getValue() == 4) {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x - (originalButton.getWidth() / 2), y - (originalButton.getHeight() / 2), originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x - (mouseoverButton.getWidth() / 2), y - (mouseoverButton.getHeight() / 2), mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }

        if (origin.getValue() == 5) {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x - (originalButton.getWidth()), y - (originalButton.getHeight() / 2), originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x - (mouseoverButton.getWidth()), y - (mouseoverButton.getHeight() / 2), mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }

        if (origin.getValue() == 6) {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x, y - (originalButton.getHeight()), originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x, y - (mouseoverButton.getHeight()), mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }

        if (origin.getValue() == 7) {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x - (originalButton.getWidth() / 2), y - (originalButton.getHeight()), originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x - (mouseoverButton.getWidth() / 2), y - (mouseoverButton.getHeight()), mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }

        if (origin.getValue() == 8) {
            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), x - (originalButton.getWidth()), y - (originalButton.getHeight()), originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), x - (mouseoverButton.getWidth()), y - (mouseoverButton.getHeight()), mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }
    }
    private BufferedImage imageInverter(BufferedImage inputFile) {

        for(int ConvY = 0; ConvY < inputFile.getHeight(); ConvY++){
            for(int ConvX = 0; ConvX < inputFile.getWidth(); ConvX++){
                int p = inputFile.getRGB(ConvX, ConvY);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                p = (a<<24) | (r<<16) | (g<<8) | b;
                inputFile.setRGB(ConvX, ConvY, p);
            }
        }
        /*

        for (int ConvX = 0; ConvX < inputFile.getWidth(); ConvX++) {
            for (int ConvY = 0; ConvY < inputFile.getHeight(); ConvY++) {
                int rgba = inputFile.getRGB(ConvX, ConvY);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                        255 - col.getGreen(),
                        255 - col.getBlue());
                inputFile.setRGB(ConvX, ConvY, col.getRGB());
            }
        }
        */
        return inputFile;
    }

    private class Handlerclass extends MasterHandlerClass {

        public void mousePressed (MouseEvent event) {

        }
        public void mouseMoved (MouseEvent event) {
            if(origin.getValue() == 0) {
                if (event.getX() > x &&
                    event.getX() < (x + originalButton.getWidth()) &&
                    event.getY() > y &&
                    event.getY() < (y + originalButton.getHeight())) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if(origin.getValue() == 1) {
                if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                    event.getX() < (x + originalButton.getWidth() / 2) &&
                    event.getY() > y &&
                    event.getY() < (y + originalButton.getHeight())) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if(origin.getValue() == 2) {
                if (event.getX() > (x - originalButton.getWidth()) &&
                    event.getX() < x &&
                    event.getY() > y &&
                    event.getY() < (y + originalButton.getHeight())) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if(origin.getValue() == 3) {
                if (event.getX() > x &&
                    event.getX() < (x + originalButton.getWidth()) &&
                    event.getY() > y - ((originalButton.getHeight()) / 2) &&
                    event.getY() < (y + originalButton.getHeight() / 2)) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if(origin.getValue() == 4) {
                if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                    event.getX() < (x + originalButton.getWidth() / 2) &&
                    event.getY() > y - ((originalButton.getHeight()) / 2) &&
                    event.getY() < (y + originalButton.getHeight() / 2)) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if(origin.getValue() == 5) {
                if (event.getX() > (x - originalButton.getWidth()) &&
                    event.getX() < x &&
                    event.getY() > y - ((originalButton.getHeight()) / 2) &&
                    event.getY() < (y + originalButton.getHeight() / 2)) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if(origin.getValue() == 6) {
                if (event.getX() > x &&
                    event.getX() < (x + originalButton.getWidth()) &&
                    event.getY() > y - (originalButton.getHeight()) &&
                    event.getY() < y) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if(origin.getValue() == 7) {
                if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                    event.getX() < (x + originalButton.getWidth() / 2) &&
                    event.getY() > y - (originalButton.getHeight()) &&
                    event.getY() < y) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if(origin.getValue() == 8) {
                if (event.getX() > (x - originalButton.getWidth()) &&
                    event.getX() < x &&
                    event.getY() > y - (originalButton.getHeight()) &&
                    event.getY() < y) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
        }

        public void mouseClicked (MouseEvent event) {
            if(origin.getValue() == 0) {
                if (event.getX() > x &&
                        event.getX() < (x + originalButton.getWidth()) &&
                        event.getY() > y &&
                        event.getY() < (y + originalButton.getHeight())) {
                    wasPressed = true;
                }
            }
            if(origin.getValue() == 1) {
                if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                        event.getX() < (x + originalButton.getWidth() / 2) &&
                        event.getY() > y &&
                        event.getY() < (y + originalButton.getHeight())) {
                    wasPressed = true;
                }
            }
            if(origin.getValue() == 2) {
                if (event.getX() > (x - originalButton.getWidth()) &&
                        event.getX() < x &&
                        event.getY() > y &&
                        event.getY() < (y + originalButton.getHeight())) {
                    wasPressed = true;
                }
            }
            if(origin.getValue() == 3) {
                if (event.getX() > x &&
                        event.getX() < (x + originalButton.getWidth()) &&
                        event.getY() > y - ((originalButton.getHeight()) / 2) &&
                        event.getY() < (y + originalButton.getHeight() / 2)) {
                    wasPressed = true;
                }
            }
            if(origin.getValue() == 4) {
                if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                        event.getX() < (x + originalButton.getWidth() / 2) &&
                        event.getY() > y - ((originalButton.getHeight()) / 2) &&
                        event.getY() < (y + originalButton.getHeight() / 2)) {
                    wasPressed = true;
                }
            }
            if(origin.getValue() == 5) {
                if (event.getX() > (x - originalButton.getWidth()) &&
                        event.getX() < x &&
                        event.getY() > y - ((originalButton.getHeight()) / 2) &&
                        event.getY() < (y + originalButton.getHeight() / 2)) {
                    wasPressed = true;
                }
            }
            if(origin.getValue() == 6) {
                if (event.getX() > x &&
                        event.getX() < (x + originalButton.getWidth()) &&
                        event.getY() > y - (originalButton.getHeight()) &&
                        event.getY() < y) {
                    wasPressed = true;
                }
            }
            if(origin.getValue() == 7) {
                if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                        event.getX() < (x + originalButton.getWidth() / 2) &&
                        event.getY() > y - (originalButton.getHeight()) &&
                        event.getY() < y) {
                    wasPressed = true;
                }
            }
            if(origin.getValue() == 8) {
                if (event.getX() > (x - originalButton.getWidth()) &&
                        event.getX() < x &&
                        event.getY() > y - (originalButton.getHeight()) &&
                        event.getY() < y) {
                    wasPressed = true;
                }
            }
        }

        public void mouseReleased (MouseEvent event) {
            // TODO: If state wasn't changed, do not change the boolean to false
            if(mouseOver)
                mouseOver = false;
        }
    }
}
