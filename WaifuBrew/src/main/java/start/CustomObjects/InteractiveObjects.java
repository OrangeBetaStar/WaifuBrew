package start.CustomObjects;

import javax.swing.*;

public abstract class InteractiveObjects extends JPanel {

    public abstract Origin getOrigin();

    public abstract int getActivePosX();

    public abstract int getActivePosY();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getX();

    public abstract int getY();

    public abstract MasterHandlerClass retrieveMouseHandler();
}
