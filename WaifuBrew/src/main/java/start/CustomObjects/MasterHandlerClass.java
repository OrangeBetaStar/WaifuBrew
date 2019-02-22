package start.CustomObjects;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MasterHandlerClass implements MouseListener, MouseMotionListener {

    public MasterHandlerClass() {

    }

    public boolean inBound(MouseEvent event, InteractiveObjects interactiveObject) {
        System.out.println("Mouse activity logging in MasterHandlerClass");
        System.out.println("Event mouse - X:" + event.getX() +  " [" + interactiveObject.getActivePosX() + " ~ " + (interactiveObject.getActivePosX() + interactiveObject.getWidth()) + "] : " + "Y:" + event.getY() +  " [" + interactiveObject.getActivePosY() + " ~ " + (interactiveObject.getActivePosY() + interactiveObject.getHeight()) + "]");

        return event.getX() >= interactiveObject.getActivePosX() &&
        event.getX() <= interactiveObject.getActivePosX() + interactiveObject.getWidth() &&
        event.getY() >= interactiveObject.getActivePosY() &&
        event.getY() <= interactiveObject.getActivePosY() + interactiveObject.getHeight();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
