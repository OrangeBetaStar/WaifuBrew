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

    public void setWaifu(Waifu waifu) {
        if (waifu != null) {
            this.currentWaifu = waifu;
            displayImage(waifu.name, waifu.getMood());
        }
    }

    private void displayImage(String name, Mood mood) {
        System.out.println("C:\\Users\\BetaStar\\IdeaProjects\\WaifuBrew\\WaifuBrew\\photos\\" +
                name.toLowerCase() + "-" + mood.toString().toLowerCase() + ".jpg");
        ImageIcon image = new ImageIcon("C:\\Users\\BetaStar\\IdeaProjects\\WaifuBrew\\WaifuBrew\\photos\\" +
                name.toLowerCase() + "-" + mood.toString().toLowerCase() + ".jpg");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(WIDTH/2, HEIGHT/2, WIDTH, HEIGHT);
        add(imageLabel);
        imageLabel.setVisible(true);
        setLayout(new FlowLayout());
    }

    private boolean isValidClicc(MouseEvent e) {
        return e.getX() >= 0 && e.getX() < this.WIDTH && e.getY() >= 0 && e.getY() < this.HEIGHT;
    }

    private void runNext(MouseEvent e) {
        if (isValidClicc(e)) {
            setWaifu(this.currentWaifu);
            // change pic
        }
    }
    // Main application
    public static void main(String args[]) {
        new WaifuGUI();
        WaifuGUI test = new WaifuGUI();
        Waifu asdf = new Waifu("nico",45, 140, new int[] {74, 60, 71}, Mood.HAPPY);
        test.displayImage(asdf.name, asdf.getMood());

        asdf.setMood(Mood.ANGRY);
        test.setWaifu(asdf);
        test.displayImage(asdf.name, asdf.getMood());
    }

}
