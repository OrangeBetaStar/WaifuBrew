package start;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MasterHandlerClass implements MouseListener, MouseMotionListener {

    MasterHandlerClass() {

    }

    public boolean inBound(MouseEvent event, InteractiveObjects interactiveObject, boolean absoluteTrack) {
        if(absoluteTrack) {
            System.out.println("Event mouse - X:" + event.getX() +  " [" + interactiveObject.getX() + " ~ " + (interactiveObject.getX() + interactiveObject.getWidth()) + "] : " +
                    "Y:" + event.getY() +  " [" + interactiveObject.getY() + " ~ " + (interactiveObject.getY() + interactiveObject.getHeight()) + "]");
        }
        return (absoluteTrack ?
                event.getX() > interactiveObject.getAbsoluteX() &&
                        event.getX() < interactiveObject.getAbsoluteX() + interactiveObject.getWidth() &&
                        event.getY() > interactiveObject.getAbsoluteY() &&
                        event.getY() < interactiveObject.getAbsoluteY() + interactiveObject.getHeight() :
                event.getX() >= interactiveObject.getX() - interactiveObject.getWidth() / 2 &&
                        event.getY() >= interactiveObject.getY() - interactiveObject.getHeight() / 2 &&
                        event.getX() <= interactiveObject.getX() + interactiveObject.getWidth() / 2 &&
                        event.getY() <= interactiveObject.getY() + interactiveObject.getHeight() / 2

        );
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
