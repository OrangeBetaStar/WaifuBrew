package start;

import javax.swing.*;

public abstract class InteractiveObjects extends JPanel {

    private javaxt.io.Image image;
    private int x;
    private int y;

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getX();

    public abstract int getY();

    public abstract MasterHandlerClass retrieveMouseHandler();
}
