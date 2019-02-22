package start.CustomObjects;

import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

// This is the element used in Save/Load Pane as progress snapshot.
public class SaveLoadBox extends InteractiveObjects implements ActionListener {

    Point sizeCalc = WaifuBrew.getInstance().getRes()[1];

    // TODO: Action listener (add mouse listener)

    // Position and size
    private int x = 0;
    private int y = 0;
    private int activePosX = 0;
    private int activePosY = 0;

    // Trait
    private int length = (int) (sizeCalc.x / 2.5);
    private int height = sizeCalc.y / 7;
    private int padding = 0; // will be overwritten
    private int thumbnailScaling = 0;

    // Data
    private Origin origin;
    private boolean mouseOver = false;
    private boolean wasPressed = false;
    private boolean activeButton = true;
    private int advancerPosition;

    // Viewable
    private javaxt.io.Image imageBlock;
    private javaxt.io.Image imageBlockMouseOver;
    private javaxt.io.Image thumbnail;
    private String route;
    private String saveDate;

    // Misc
    private Handlerclass miniHandler = new Handlerclass();
    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();
    private Font sysFont = WaifuBrew.getInstance().getSystemFont(WaifuBrew.getInstance().getSystemFontName());


    public SaveLoadBox(int x, int y, Origin origin, String route, String saveDate, String thumbnailName) {
        imageBlock = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        imageBlock.resize(length, height);
        imageBlockMouseOver = imageBlock.copy();

        imageBlock.setBackgroundColor(128, 128, 128);
        imageBlockMouseOver.setBackgroundColor(200, 200, 200);

        this.x = x;
        this.y = y;
        this.origin = origin;
        this.route = route;
        this.saveDate = saveDate;
        this.thumbnail = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.THUMBNAILS, thumbnailName));

        initPositionCalc();
    }

    private void initPositionCalc() {
        // calculating active x value
        if (origin.getValue() == 0 || origin.getValue() == 3 || origin.getValue() == 6) {
            activePosX = x;
        }

        else if (origin.getValue() == 1 || origin.getValue() == 4 || origin.getValue() == 7) {
            activePosX = x - (imageBlock.getWidth() / 2);
        }

        else if (origin.getValue() == 2 || origin.getValue() == 5 || origin.getValue() == 8) {
            activePosX = x - (imageBlock.getWidth());
        }

        // calculating active y value
        if (origin.getValue() == 0 || origin.getValue() == 1 || origin.getValue() == 2) {
            activePosY = y;
        }

        else if (origin.getValue() == 3 || origin.getValue() == 4 || origin.getValue() == 5) {
            activePosY = y - (imageBlock.getHeight() / 2);
        }

        else if (origin.getValue() == 6 || origin.getValue() == 7 || origin.getValue() == 8) {
            activePosY = y - (imageBlock.getHeight());
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getActivePosX() {
        return activePosX;
    }

    @Override
    public int getActivePosY() {
        return activePosY;
    }

    @Override
    public Origin getOrigin() {
        return origin;
    }

    @Override
    public int getWidth() {
        return length;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public boolean isValidSaveDate() {
        return !saveDate.equals("Empty");
    }

    @Override
    public MasterHandlerClass retrieveMouseHandler() {
        return miniHandler;
    }

    private class Handlerclass extends MasterHandlerClass {

        public void mouseMoved(MouseEvent event) {
            if (activeButton) {

                if (event.getX() >= activePosX &&
                        event.getX() <= activePosX + imageBlock.getWidth() &&
                        event.getY() >= activePosY &&
                        event.getY() <= activePosY + imageBlock.getHeight()) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
        }

        public void mouseClicked(MouseEvent event) {
            if (activeButton) {
                if (event.getX() >= activePosX &&
                        event.getX() <= activePosX + imageBlock.getWidth() &&
                        event.getY() >= activePosY &&
                        event.getY() <= activePosY + imageBlock.getHeight()) {
                    wasPressed = true;
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

    @Override
    public void paintComponent(Graphics g) {

        // calculate first for easier implementation
        padding = (int) (imageBlock.getHeight() * 0.1);
        thumbnailScaling = (int) (imageBlock.getHeight() * 1.6);

        if (activeButton) {

            // gui
            if (!mouseOver) {
                g.drawImage(imageBlock.getBufferedImage(), activePosX, activePosY, imageBlock.getWidth(), imageBlock.getHeight(), that);
            } else {
                g.drawImage(imageBlockMouseOver.getBufferedImage(), activePosX, activePosY, imageBlockMouseOver.getWidth(), imageBlockMouseOver.getHeight(), that);
            }
            g.drawImage(thumbnail.getBufferedImage(), (activePosX + padding), (activePosY + padding), (thumbnailScaling), (int) (((thumbnailScaling) / 16.0) * 8.0), that);
            g.setFont(WaifuBrew.getInstance().getSystemFont(WaifuBrew.getInstance().getSystemFontName()).deriveFont(Font.PLAIN, WaifuBrew.getInstance().getSystemFontSize()));
            g.drawString("Save Date: " + saveDate, activePosX + ((padding * 2) + (thumbnailScaling)), activePosY + padding * 2);
            g.drawString("Route: " + route, activePosX + ((padding * 2) + (thumbnailScaling)), activePosY + padding * 4);
        }
    }

    public boolean isPressed() {
        return wasPressed;
    } // Doesn't work reliably to use it. Yet.

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}