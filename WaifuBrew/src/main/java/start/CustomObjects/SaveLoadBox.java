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

    // Position and size
    private int x = 0;
    private int y = 0;
    private int length = sizeCalc.x / 4;
    private int height = sizeCalc.y / 6;

    // Data
    private Origin origin;
    private boolean mouseOver = false;
    private boolean wasPressed = false;
    private int advancerPosition;

    // Viewable
    private javaxt.io.Image imageBlock;
    private javaxt.io.Image imageBlockMouseOver;
    private javaxt.io.Image thumbnail;
    private String route;
    private String saveDate;

    private Handlerclass miniHandler = new Handlerclass();

    // this that this that left right left right up down up down
    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();
/*

    public SaveLoadBox() {
        imageBlock = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        imageBlock.resize(length, height);
        imageBlockMouseOver = imageBlock.copy();

        imageBlock.setBackgroundColor(128, 128, 128);
        imageBlockMouseOver.setBackgroundColor(200, 200, 200);
    }
*/

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
    public int getAbsoluteX() {
        return x + (length / 2);
    }

    @Override
    public int getAbsoluteY() {
        return y + (height / 2);
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
    public MasterHandlerClass retrieveMouseHandler() {
        return miniHandler;
    }

    private class Handlerclass extends MasterHandlerClass {

        public void mouseMoved(MouseEvent event) {
            if (origin.getValue() == 0) {
                if (event.getX() > x &&
                        event.getX() < (x + imageBlock.getWidth()) &&
                        event.getY() > y &&
                        event.getY() < (y + imageBlock.getHeight())) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if (origin.getValue() == 1) {
                if (event.getX() > x - ((imageBlock.getWidth()) / 2) &&
                        event.getX() < (x + imageBlock.getWidth() / 2) &&
                        event.getY() > y &&
                        event.getY() < (y + imageBlock.getHeight())) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if (origin.getValue() == 2) {
                if (event.getX() > (x - imageBlock.getWidth()) &&
                        event.getX() < x &&
                        event.getY() > y &&
                        event.getY() < (y + imageBlock.getHeight())) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if (origin.getValue() == 3) {
                if (event.getX() > x &&
                        event.getX() < (x + imageBlock.getWidth()) &&
                        event.getY() > y - ((imageBlock.getHeight()) / 2) &&
                        event.getY() < (y + imageBlock.getHeight() / 2)) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if (origin.getValue() == 4) {
                if (event.getX() > x - ((imageBlock.getWidth()) / 2) &&
                        event.getX() < (x + imageBlock.getWidth() / 2) &&
                        event.getY() > y - ((imageBlock.getHeight()) / 2) &&
                        event.getY() < (y + imageBlock.getHeight() / 2)) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if (origin.getValue() == 5) {
                if (event.getX() > (x - imageBlock.getWidth()) &&
                        event.getX() < x &&
                        event.getY() > y - ((imageBlock.getHeight()) / 2) &&
                        event.getY() < (y + imageBlock.getHeight() / 2)) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if (origin.getValue() == 6) {
                if (event.getX() > x &&
                        event.getX() < (x + imageBlock.getWidth()) &&
                        event.getY() > y - (imageBlock.getHeight()) &&
                        event.getY() < y) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if (origin.getValue() == 7) {
                if (event.getX() > x - ((imageBlock.getWidth()) / 2) &&
                        event.getX() < (x + imageBlock.getWidth() / 2) &&
                        event.getY() > y - (imageBlock.getHeight()) &&
                        event.getY() < y) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
            if (origin.getValue() == 8) {
                if (event.getX() > (x - imageBlock.getWidth()) &&
                        event.getX() < x &&
                        event.getY() > y - (imageBlock.getHeight()) &&
                        event.getY() < y) {
                    mouseOver = true;
                } else {
                    mouseOver = false;
                }
            }
        }

        public void mouseClicked(MouseEvent event) {
            if (origin.getValue() == 0) {
                if (event.getX() > x &&
                        event.getX() < (x + imageBlock.getWidth()) &&
                        event.getY() > y &&
                        event.getY() < (y + imageBlock.getHeight())) {
                    wasPressed = true;
                }
            }
            if (origin.getValue() == 1) {
                if (event.getX() > x - ((imageBlock.getWidth()) / 2) &&
                        event.getX() < (x + imageBlock.getWidth() / 2) &&
                        event.getY() > y &&
                        event.getY() < (y + imageBlock.getHeight())) {
                    wasPressed = true;
                }
            }
            if (origin.getValue() == 2) {
                if (event.getX() > (x - imageBlock.getWidth()) &&
                        event.getX() < x &&
                        event.getY() > y &&
                        event.getY() < (y + imageBlock.getHeight())) {
                    wasPressed = true;
                }
            }
            if (origin.getValue() == 3) {
                if (event.getX() > x &&
                        event.getX() < (x + imageBlock.getWidth()) &&
                        event.getY() > y - ((imageBlock.getHeight()) / 2) &&
                        event.getY() < (y + imageBlock.getHeight() / 2)) {
                    wasPressed = true;
                }
            }
            if (origin.getValue() == 4) {
                if (event.getX() > x - ((imageBlock.getWidth()) / 2) &&
                        event.getX() < (x + imageBlock.getWidth() / 2) &&
                        event.getY() > y - ((imageBlock.getHeight()) / 2) &&
                        event.getY() < (y + imageBlock.getHeight() / 2)) {
                    wasPressed = true;
                }
            }
            if (origin.getValue() == 5) {
                if (event.getX() > (x - imageBlock.getWidth()) &&
                        event.getX() < x &&
                        event.getY() > y - ((imageBlock.getHeight()) / 2) &&
                        event.getY() < (y + imageBlock.getHeight() / 2)) {
                    wasPressed = true;
                }
            }
            if (origin.getValue() == 6) {
                if (event.getX() > x &&
                        event.getX() < (x + imageBlock.getWidth()) &&
                        event.getY() > y - (imageBlock.getHeight()) &&
                        event.getY() < y) {
                    wasPressed = true;
                }
            }
            if (origin.getValue() == 7) {
                if (event.getX() > x - ((imageBlock.getWidth()) / 2) &&
                        event.getX() < (x + imageBlock.getWidth() / 2) &&
                        event.getY() > y - (imageBlock.getHeight()) &&
                        event.getY() < y) {
                    wasPressed = true;
                }
            }
            if (origin.getValue() == 8) {
                if (event.getX() > (x - imageBlock.getWidth()) &&
                        event.getX() < x &&
                        event.getY() > y - (imageBlock.getHeight()) &&
                        event.getY() < y) {
                    wasPressed = true;
                }
            }
        }

        public void mouseReleased(MouseEvent event) {
            // TODO: If state wasn't changed, do not change the boolean to false
            if (mouseOver)
                mouseOver = false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {

/*        g.drawImage(imageBlock.getBufferedImage(), x, y, this);

        g.drawImage(thumbnail.getBufferedImage(), x, y, x / 2, y / 2, this);*/

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
                g.drawImage(imageBlock.getBufferedImage(), x, y, imageBlock.getWidth(), imageBlock.getHeight(), that);
            } else {
                g.drawImage(imageBlockMouseOver.getBufferedImage(), x, y, imageBlockMouseOver.getWidth(), imageBlockMouseOver.getHeight(), that);
            }
        }

        if (origin.getValue() == 1) {
            if (!mouseOver) {
                g.drawImage(imageBlock.getBufferedImage(), x - (imageBlock.getWidth() / 2), y, imageBlock.getWidth(), imageBlock.getHeight(), that);
            } else {
                g.drawImage(imageBlockMouseOver.getBufferedImage(), x - (imageBlockMouseOver.getWidth() / 2), y, imageBlockMouseOver.getWidth(), imageBlockMouseOver.getHeight(), that);
            }
        }

        if (origin.getValue() == 2) {
            if (!mouseOver) {
                g.drawImage(imageBlock.getBufferedImage(), x - (imageBlock.getWidth()), y, imageBlock.getWidth(), imageBlock.getHeight(), that);
            } else {
                g.drawImage(imageBlockMouseOver.getBufferedImage(), x - (imageBlockMouseOver.getWidth()), y, imageBlockMouseOver.getWidth(), imageBlockMouseOver.getHeight(), that);
            }
        }

        if (origin.getValue() == 3) {
            if (!mouseOver) {
                g.drawImage(imageBlock.getBufferedImage(), x, y - (imageBlock.getHeight() / 2), imageBlock.getWidth(), imageBlock.getHeight(), that);
            } else {
                g.drawImage(imageBlockMouseOver.getBufferedImage(), x, y - (imageBlockMouseOver.getHeight() / 2), imageBlockMouseOver.getWidth(), imageBlockMouseOver.getHeight(), that);
            }
        }

        if (origin.getValue() == 4) {
            if (!mouseOver) {
                g.drawImage(imageBlock.getBufferedImage(), x - (imageBlock.getWidth() / 2), y - (imageBlock.getHeight() / 2), imageBlock.getWidth(), imageBlock.getHeight(), that);
            } else {
                g.drawImage(imageBlockMouseOver.getBufferedImage(), x - (imageBlockMouseOver.getWidth() / 2), y - (imageBlockMouseOver.getHeight() / 2), imageBlockMouseOver.getWidth(), imageBlockMouseOver.getHeight(), that);
            }
        }

        if (origin.getValue() == 5) {
            if (!mouseOver) {
                g.drawImage(imageBlock.getBufferedImage(), x - (imageBlock.getWidth()), y - (imageBlock.getHeight() / 2), imageBlock.getWidth(), imageBlock.getHeight(), that);
            } else {
                g.drawImage(imageBlockMouseOver.getBufferedImage(), x - (imageBlockMouseOver.getWidth()), y - (imageBlockMouseOver.getHeight() / 2), imageBlockMouseOver.getWidth(), imageBlockMouseOver.getHeight(), that);
            }
        }

        if (origin.getValue() == 6) {
            if (!mouseOver) {
                g.drawImage(imageBlock.getBufferedImage(), x, y - (imageBlock.getHeight()), imageBlock.getWidth(), imageBlock.getHeight(), that);
            } else {
                g.drawImage(imageBlockMouseOver.getBufferedImage(), x, y - (imageBlockMouseOver.getHeight()), imageBlockMouseOver.getWidth(), imageBlockMouseOver.getHeight(), that);
            }
        }

        if (origin.getValue() == 7) {
            if (!mouseOver) {
                g.drawImage(imageBlock.getBufferedImage(), x - (imageBlock.getWidth() / 2), y - (imageBlock.getHeight()), imageBlock.getWidth(), imageBlock.getHeight(), that);
            } else {
                g.drawImage(imageBlockMouseOver.getBufferedImage(), x - (imageBlockMouseOver.getWidth() / 2), y - (imageBlockMouseOver.getHeight()), imageBlockMouseOver.getWidth(), imageBlockMouseOver.getHeight(), that);
            }
        }

        if (origin.getValue() == 8) {
            if (!mouseOver) {
                g.drawImage(imageBlock.getBufferedImage(), x - (imageBlock.getWidth()), y - (imageBlock.getHeight()), imageBlock.getWidth(), imageBlock.getHeight(), that);
            } else {
                g.drawImage(imageBlockMouseOver.getBufferedImage(), x - (imageBlockMouseOver.getWidth()), y - (imageBlockMouseOver.getHeight()), imageBlockMouseOver.getWidth(), imageBlockMouseOver.getHeight(), that);
            }
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