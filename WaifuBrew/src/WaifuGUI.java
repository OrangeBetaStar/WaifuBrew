import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class WaifuGUI extends JFrame {
    //private variables
    private static int WIDTH = 500;
    private static int HEIGHT = 500;

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

        /*
        JMenuItem drawingItem = new JMenuItem("New");
        drawingItem.addActionListener(new NewDrawingAction());
        drawingMenu.add(drawingItem);
        menuBar.add(drawingMenu);
        this.setJMenuBar(menuBar);
        */
    }



    // Main application
    public static void main(String args[]) {
        new WaifuGUI();
    }

}
