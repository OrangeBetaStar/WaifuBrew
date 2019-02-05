package start.CustomObjects;

import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This is the element used in Save/Load Pane as progress snapshot.
public class SaveLoadBox extends InteractiveObjects implements ActionListener {

    Point sizeCalc = WaifuBrew.getInstance().getRes()[1];
    private int x = (sizeCalc.x / 2);
    private int y = (sizeCalc.y / 2);
    private int length = sizeCalc.x / 3;
    private int height = sizeCalc.y / 5;
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

        g.drawImage(new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.THUMBNAILS, "1")).getBufferedImage(), x, y, x / 2, y/2, this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}