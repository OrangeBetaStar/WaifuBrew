import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class WaifuGUI extends JFrame {
    //private variables
    private static int WIDTH = 1280;
    private static int HEIGHT = 720;

    private Waifu currentWaifu;
    private String text;

    //constructor
    public WaifuGUI() {
        super("WaifuGUI");

        //initialize fields
        this.currentWaifu = null;
        this.text = "";

        //initialize gfx
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createMenus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public  Waifu getCurrentWaifu() {
        return this.currentWaifu;
    }
    public void setText(String s) {
        this.text = s;
    }

    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu();

        JMenuItem items = new JMenuItem("New");
        //drawingItem.addActionListener(new NewDrawingAction());
        menu.add(items);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }

    private void setWaifu(Waifu waifu) {
        if (waifu != null && this.currentWaifu != null && this.currentWaifu.equals(waifu) == false) {
            this.currentWaifu = waifu;
        }
    }

    private boolean isValidClicc(MouseEvent e) {
        return e.getX() >= 0 && e.getX() <= this.WIDTH &&
                e.getY() >= 0 && e.getY() <= this.HEIGHT;
    }

    // Main application
    public static void main(String args[]) {
        new WaifuGUI();
    }

}
