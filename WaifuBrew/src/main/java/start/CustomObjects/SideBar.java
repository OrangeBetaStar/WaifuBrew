package start.CustomObjects;

import start.Loader.ImageSelector;
import start.Calculation.MathClass;
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
    private MathClass mathClass = new MathClass();

    private int[] movement;
    private int movementCounter = 0;

    public SideBar() {
        imageBlock = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        imageBlock.resize(length, height);
        imageBlock.setBackgroundColor(128, 128, 128);
        imageBlock.setOpacity(75);
        movement = mathClass.easeOut(0.0, 0.5, 0, (WaifuBrew.getInstance().getRes()[1].x / 4));
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
        if (isActive && movementState) {
            try {
                System.out.println(movement[movementCounter]);
                g.drawImage(imageBlock.getBufferedImage(), (x + length) - movement[movementCounter], y, this);
                movementCounter++;
            } catch (IndexOutOfBoundsException e) {
                movementCounter = 0;
                movementState = false;
                g.drawImage(imageBlock.getBufferedImage(), x, y, this);
            }
        }
        else if (!isActive && movementState) {
            try {
                System.out.println(movement[movementCounter]);
                g.drawImage(imageBlock.getBufferedImage(), x + movement[movementCounter], y, this);
                movementCounter++;
            } catch (IndexOutOfBoundsException e) {
                movementCounter = 0;
                movementState = false;
            }
        } else if (isActive) {
            g.drawImage(imageBlock.getBufferedImage(), x, y, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
