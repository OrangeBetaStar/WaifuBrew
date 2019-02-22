package start.CustomObjects;

import start.Loader.EasingSelector;
import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideBar extends InteractiveObjects implements ActionListener {

    private int x = (WaifuBrew.getInstance().getRes()[1].x / 4) * 3;
    private int y = 0;
    private int length = WaifuBrew.getInstance().getRes()[1].x / 4;
    private int height = WaifuBrew.getInstance().getRes()[1].y;
    private boolean isActive = false;
    private boolean movementState = false;
    private javaxt.io.Image imageBlock;


    private double[] movement;
    private int movementCounter = 0;

    public SideBar() {
        imageBlock = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        imageBlock.resize(length, height);
        imageBlock.setBackgroundColor(128, 128, 128);
        imageBlock.setOpacity(75);
        movement = WaifuBrew.getInstance().getMovement(EasingSelector.OUT_SINE);
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
    public int getWidth() {
        return length;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getActivePosX() {
        return x;
    }

    @Override
    public int getActivePosY() {
        return y;
    }

    @Override
    public Origin getOrigin() {
        return Origin.MIDDLE_CENTRE;
    }

    public boolean isMoving() {
        return movementState;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean activate) {
        if (this.isActive != activate) {
            movementState = true;
        }
        isActive = activate;
    }

    @Override
    public MasterHandlerClass retrieveMouseHandler() {
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        // Open
        if (isActive && movementState) {
            try {
                g.drawImage(imageBlock.getBufferedImage(), (x + length) - (int)(movement[movementCounter] * length), y, this);
                movementCounter++;
            } catch (IndexOutOfBoundsException e) {
                movementCounter = 0;
                movementState = false;
                g.drawImage(imageBlock.getBufferedImage(), x, y, this);
            }
        }

        // Close
        else if (!isActive && movementState) {
            try {
                g.drawImage(imageBlock.getBufferedImage(), x + (int)(movement[movementCounter] * length), y, this);
                movementCounter++;
            } catch (IndexOutOfBoundsException e) {
                movementCounter = 0;
                movementState = false;
            }
        }
        // Static
        else if (isActive) {
            g.drawImage(imageBlock.getBufferedImage(), x, y, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
