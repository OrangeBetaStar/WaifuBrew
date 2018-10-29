package start.CustomObjects;

import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This is the element used in Save/Load Pane as progress snapshot.
public class SaveLoadBox extends InteractiveObjects implements ActionListener {

    private int x = (WaifuBrew.getInstance().getRes()[1].x / 2);
    private int y = (WaifuBrew.getInstance().getRes()[1].y / 2);
    private int length = WaifuBrew.getInstance().getRes()[1].x / 4;
    private int height = WaifuBrew.getInstance().getRes()[1].y / 7;
    private javaxt.io.Image imageBlock;

    public SaveLoadBox () {
        imageBlock = new javaxt.io.Image (WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        imageBlock.resize(length, height);
        imageBlock.setBackgroundColor(128,128,128);
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
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(imageBlock.getBufferedImage(), x, y, this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}