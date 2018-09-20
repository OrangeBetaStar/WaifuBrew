import java.awt.event.MouseEvent;

public class Menu {
    private String currentText;
    private WaifuBrew gui;
    private int skip;
    private int auto;
    private int next;
    private int prev;
    private int qSave;
    private int qLoad;

    public Menu() {
        this.currentText = "";
        this.gui = new WaifuBrew();
        this.skip = gui.getWidth()/4 + gui.getWidth()/20;
        this.auto = gui.getWidth()/4 + 2*gui.getWidth()/20;
        this.next = gui.getWidth()/4 + 3*gui.getWidth()/20;
        this.prev = gui.getWidth()/4 + 4*gui.getWidth()/20;
        this.qSave = gui.getWidth()/4 + 5*gui.getWidth()/20;
        this.qLoad = gui.getWidth()/4 + 6*gui.getWidth()/20;
    }

    public void runNext(MouseEvent e) {
        if (e.getX() >= 0 && e.getX() < gui.getWidth() &&
                e.getY() >= 0 && e.getY() < gui.getWidth()) {
            this.currentText = "Hello world";
        }
    }

    public void hover(MouseEvent e) {
        int positon = 0;
        if (e.getY() > gui.getWidth()/4 && e.getY() <= this.skip) {
            positon = 1;
        } else if (e.getY() <= this.auto) {
            positon = 2;
        } else if (e.getY() <= this.next) {
            positon = 3;
        } else if (e.getY() <= this.prev) {
            positon = 4;
        } else if (e.getY() <= this.qSave) {
            positon = 5;
        } else if (e.getY() <= this.qLoad) {
            positon = 6;
        }

        switch (positon) { //need to check e.getX() here;
            case 1: break;
            case 2: break;
            case 3: break;
            case 4: break;
            case 5: break;
            case 6: break;
            default: break;
        }
    }

    private void glow() {
        //display glowing effect on specified button
    }
}
