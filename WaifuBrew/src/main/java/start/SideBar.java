package start;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideBar extends InteractiveObjects implements ActionListener {

    private int x = (WaifuBrew.getInstance().getRes()[1].x / 3) * 2;
    private int y = 0;
    private int length = WaifuBrew.getInstance().getRes()[1].x / 3;
    private int height = WaifuBrew.getInstance().getRes()[1].y;
    private boolean isActive = false;
    private javaxt.io.Image imageBlock;
    private MathClass mathClass = new MathClass();

    private int[] movement;

    public SideBar () {
        imageBlock = new javaxt.io.Image (WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        imageBlock.resize(length, height);
        imageBlock.setBackgroundColor(128,128,128);
        movement = mathClass.easeOut(0, 1, WaifuBrew.getInstance().getRes()[1].x, x);
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean activate) {
        isActive = activate;
    }

    @Override
    public MasterHandlerClass retrieveMouseHandler() {
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        if(isActive) {
/*

            for(int slidingCounter = 0; slidingCounter < movement.length; slidingCounter++) {
                g.drawImage(imageBlock.getBufferedImage(), movement[slidingCounter], y, this);
                try {
                    Thread.sleep(100);
                } catch(InterruptedException e) {

                }
            }
*/

            g.drawImage(imageBlock.getBufferedImage(), x, y, this);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
