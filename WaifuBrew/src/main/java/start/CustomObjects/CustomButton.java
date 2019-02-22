package start.CustomObjects;

import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

// This class will handle the buttons that require transparency changes and launch actions
public class CustomButton extends InteractiveObjects implements ActionListener {

    // gui
    private javaxt.io.Image originalButton;
    private javaxt.io.Image mouseoverButton;
    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();

    // position
    private int x;
    private int y;
    private int activePosX;
    private int activePosY;

    // data
    private boolean wasPressed = false;
    private boolean mouseOver = false;
    private double GUIScale = (double) WaifuBrew.getInstance().getSystemGUIScale();

    // trait
    private int defaultButtonHeight = 75;
    private Origin origin;
    private boolean activeButton = true;

    // this that this that left right left right up down up down
    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    // mouse listener
    private Handlerclass miniHandler = new Handlerclass();

    public CustomButton(int x, int y, String fileName, Origin origin) {
        this.x = x;
        this.y = y;
        // originalButton will be shown when mouse isn't above the button.
        if (fileName.contains("_")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BUTTONS, fileName.toLowerCase().substring(0, fileName.indexOf("_"))));
        } else {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BUTTONS, fileName.toLowerCase()));
        }

        originalButton.resize((int) (originalButton.getWidth() * (GUIScale / originalButton.getHeight())), defaultButtonHeight, true);
        mouseoverButton = originalButton.copy();
        originalButton.setOpacity(20);
        this.origin = origin;
    }

    public CustomButton(int x, int y, String fileName, Origin origin, double sizeY, boolean invert) {
        this.x = x;
        this.y = y;
        // originalButton will be shown when mouse isn't above the button.

        // TODO: This has to be fixed in order to load the required images.
        if (fileName.contains("_")) {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BUTTONS, fileName.toLowerCase().substring(0, fileName.indexOf("_"))));
        } else {
            originalButton = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BUTTONS, fileName.toLowerCase()));
        }

        // Black to white, white to black for better viewing
        if (invert) {
            originalButton = new javaxt.io.Image(imageInverter(originalButton.getBufferedImage()));
        }

        if (sizeY == 0) {
            originalButton.resize((int) (originalButton.getWidth() * (GUIScale / originalButton.getHeight())), defaultButtonHeight, true);
        } else {
            originalButton.resize((int) (originalButton.getWidth() * (sizeY / originalButton.getHeight())), (int) sizeY, false);
        }
        mouseoverButton = originalButton.copy();
        originalButton.setOpacity(20);
        this.origin = origin;
    }

    public CustomButton(int x, int y, String fileName_1, String fileName_2) {
        this.x = x;
        this.y = y;
        // originalButton will be shown and changed to mouseoverButton when mouse is over the pic.
        originalButton = new javaxt.io.Image(RESOURCE_PATH + fileName_1);
        mouseoverButton = new javaxt.io.Image(RESOURCE_PATH + fileName_2);
    }

    public void setActiveButtonState(boolean active) {
        this.activeButton = active;
    }

    public boolean getActiveButtonState() {
        return this.activeButton;
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public int getWidth() {
        return originalButton.getWidth();
    }

    @Override
    public int getHeight() {
        return originalButton.getHeight();
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

        // calculating active x value
        if (origin.getValue() == 0 || origin.getValue() == 3 || origin.getValue() == 6) {
            activePosX = x;
        }

        if (origin.getValue() == 1 || origin.getValue() == 4 || origin.getValue() == 7) {
            activePosX = x - (originalButton.getWidth() / 2);
        }

        if (origin.getValue() == 2 || origin.getValue() == 5 || origin.getValue() == 8) {
            activePosX = x - (originalButton.getWidth());
        }

        // calculating active y value
        if (origin.getValue() == 0 || origin.getValue() == 1 || origin.getValue() == 2) {
            activePosY = y;
        }

        if (origin.getValue() == 3 || origin.getValue() == 4 || origin.getValue() == 5) {
            activePosY = y - (originalButton.getHeight() / 2);
        }

        if (origin.getValue() == 6 || origin.getValue() == 7 || origin.getValue() == 8) {
            activePosY = y - (originalButton.getHeight());
        }

        // gui
        if (activeButton) {

            if (!mouseOver) {
                g.drawImage(originalButton.getBufferedImage(), activePosX, activePosY, originalButton.getWidth(), originalButton.getHeight(), that);
            } else {
                g.drawImage(mouseoverButton.getBufferedImage(), activePosX, activePosY, mouseoverButton.getWidth(), mouseoverButton.getHeight(), that);
            }
        }
    }

    private BufferedImage imageInverter(BufferedImage inputFile) {

        for (int ConvY = 0; ConvY < inputFile.getHeight(); ConvY++) {
            for (int ConvX = 0; ConvX < inputFile.getWidth(); ConvX++) {
                int p = inputFile.getRGB(ConvX, ConvY);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                p = (a << 24) | (r << 16) | (g << 8) | b;
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

    @Override
    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    private class Handlerclass extends MasterHandlerClass {

        public void mouseMoved(MouseEvent event) {
            if (activeButton) {
                if (origin.getValue() == 0) {
                    if (event.getX() > x &&
                            event.getX() < (x + originalButton.getWidth()) &&
                            event.getY() > y &&
                            event.getY() < (y + originalButton.getHeight())) {
                        mouseOver = true;
                    } else {
                        mouseOver = false;
                    }
                } else if (origin.getValue() == 1) {
                    if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                            event.getX() < (x + originalButton.getWidth() / 2) &&
                            event.getY() > y &&
                            event.getY() < (y + originalButton.getHeight())) {
                        mouseOver = true;
                    } else {
                        mouseOver = false;
                    }
                } else if (origin.getValue() == 2) {
                    if (event.getX() > (x - originalButton.getWidth()) &&
                            event.getX() < x &&
                            event.getY() > y &&
                            event.getY() < (y + originalButton.getHeight())) {
                        mouseOver = true;
                    } else {
                        mouseOver = false;
                    }
                } else if (origin.getValue() == 3) {
                    if (event.getX() > x &&
                            event.getX() < (x + originalButton.getWidth()) &&
                            event.getY() > y - ((originalButton.getHeight()) / 2) &&
                            event.getY() < (y + originalButton.getHeight() / 2)) {
                        mouseOver = true;
                    } else {
                        mouseOver = false;
                    }
                } else if (origin.getValue() == 4) {
                    if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                            event.getX() < (x + originalButton.getWidth() / 2) &&
                            event.getY() > y - ((originalButton.getHeight()) / 2) &&
                            event.getY() < (y + originalButton.getHeight() / 2)) {
                        mouseOver = true;
                    } else {
                        mouseOver = false;
                    }
                } else if (origin.getValue() == 5) {
                    if (event.getX() > (x - originalButton.getWidth()) &&
                            event.getX() < x &&
                            event.getY() > y - ((originalButton.getHeight()) / 2) &&
                            event.getY() < (y + originalButton.getHeight() / 2)) {
                        mouseOver = true;
                    } else {
                        mouseOver = false;
                    }
                } else if (origin.getValue() == 6) {
                    if (event.getX() > x &&
                            event.getX() < (x + originalButton.getWidth()) &&
                            event.getY() > y - (originalButton.getHeight()) &&
                            event.getY() < y) {
                        mouseOver = true;
                    } else {
                        mouseOver = false;
                    }
                } else if (origin.getValue() == 7) {
                    if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                            event.getX() < (x + originalButton.getWidth() / 2) &&
                            event.getY() > y - (originalButton.getHeight()) &&
                            event.getY() < y) {
                        mouseOver = true;
                    } else {
                        mouseOver = false;
                    }
                } else if (origin.getValue() == 8) {
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
        }

        public void mouseClicked(MouseEvent event) {
            if (activeButton) {
                if (origin.getValue() == 0) {
                    if (event.getX() > x &&
                            event.getX() < (x + originalButton.getWidth()) &&
                            event.getY() > y &&
                            event.getY() < (y + originalButton.getHeight())) {
                        wasPressed = true;
                    }
                } else if (origin.getValue() == 1) {
                    if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                            event.getX() < (x + originalButton.getWidth() / 2) &&
                            event.getY() > y &&
                            event.getY() < (y + originalButton.getHeight())) {
                        wasPressed = true;
                    }
                } else if (origin.getValue() == 2) {
                    if (event.getX() > (x - originalButton.getWidth()) &&
                            event.getX() < x &&
                            event.getY() > y &&
                            event.getY() < (y + originalButton.getHeight())) {
                        wasPressed = true;
                    }
                } else if (origin.getValue() == 3) {
                    if (event.getX() > x &&
                            event.getX() < (x + originalButton.getWidth()) &&
                            event.getY() > y - ((originalButton.getHeight()) / 2) &&
                            event.getY() < (y + originalButton.getHeight() / 2)) {
                        wasPressed = true;
                    }
                } else if (origin.getValue() == 4) {
                    if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                            event.getX() < (x + originalButton.getWidth() / 2) &&
                            event.getY() > y - ((originalButton.getHeight()) / 2) &&
                            event.getY() < (y + originalButton.getHeight() / 2)) {
                        wasPressed = true;
                    }
                } else if (origin.getValue() == 5) {
                    if (event.getX() > (x - originalButton.getWidth()) &&
                            event.getX() < x &&
                            event.getY() > y - ((originalButton.getHeight()) / 2) &&
                            event.getY() < (y + originalButton.getHeight() / 2)) {
                        wasPressed = true;
                    }
                } else if (origin.getValue() == 6) {
                    if (event.getX() > x &&
                            event.getX() < (x + originalButton.getWidth()) &&
                            event.getY() > y - (originalButton.getHeight()) &&
                            event.getY() < y) {
                        wasPressed = true;
                    }
                } else if (origin.getValue() == 7) {
                    if (event.getX() > x - ((originalButton.getWidth()) / 2) &&
                            event.getX() < (x + originalButton.getWidth() / 2) &&
                            event.getY() > y - (originalButton.getHeight()) &&
                            event.getY() < y) {
                        wasPressed = true;
                    }
                } else if (origin.getValue() == 8) {
                    if (event.getX() > (x - originalButton.getWidth()) &&
                            event.getX() < x &&
                            event.getY() > y - (originalButton.getHeight()) &&
                            event.getY() < y) {
                        wasPressed = true;
                    }
                }
            }
        }

        public void mouseReleased(MouseEvent event) {
            // TODO: If state wasn't changed, do not change the boolean to false
            if (activeButton) {
                if (mouseOver)
                    mouseOver = false;
            }
        }
    }
}
