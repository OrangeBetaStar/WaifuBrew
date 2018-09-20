import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class WaifuGUI extends JFrame {
    //private variables
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;

    private Waifu currentWaifu = null;
    private String text = "";
    private JFrame frame;
    //constructor

    public WaifuGUI() {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                runNext(me);
            }
        });

        frame = new JFrame("WaifuGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        File f = new File("WaifuBrew/res/resources/icon.jpg");
        frame.setIconImage(new ImageIcon(f.getAbsolutePath()).getImage());
    }

    public Waifu getCurrentWaifu() {
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
        File f = new File("resources/" + name.toLowerCase() + "-" + mood.toString().toLowerCase() + ".jpg");
        System.out.println(f.getAbsoluteFile());
        ImageIcon image = new ImageIcon(f.getAbsolutePath());
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(WIDTH/2, HEIGHT/2, WIDTH, HEIGHT);
        frame.add(imageLabel);
        frame.setIconImage(imageLabel);
        imageLabel.setVisible(true);

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().add(new mouseClass());
    }

    private boolean isValidClicc(MouseEvent e) {
        // System.out.println("isValidClicc");
        return e.getX() >= 0 && e.getX() < this.WIDTH &&
                e.getY() >= 0 && e.getY() < this.HEIGHT;
    }

    private void runNext(MouseEvent e) {
        System.out.println("runNext");
        //if (isValidClicc(e)) {
            if (this.currentWaifu.getMood().equals(Mood.HAPPY)) {
                this.currentWaifu.setMood(Mood.ANGRY);
                System.out.println("Pressed. Switching to Angry!");
                setWaifu(this.currentWaifu);
            }
            else {
                this.currentWaifu.setMood(Mood.HAPPY);
                System.out.println("Pressed. Switching to Happy!");
                setWaifu(this.currentWaifu);
            }

        //}
    }
    // Main application
    public static void main(String args[]) {
        new WaifuGUI();
        WaifuGUI test = new WaifuGUI();
        Waifu nicoNii = new Waifu("Nico",43, 153, new int[] {74, 57, 79}, Mood.HAPPY);
        //nicoNii.setMood(Mood.HAPPY);
        test.displayImage(nicoNii.name, nicoNii.getMood());
    }

}
