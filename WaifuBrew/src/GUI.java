import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {
    private JPanel waifuPanel;
    private JLabel waifuLabel;
    private static int WIDTH = 1920;
    private static int HEIGHT = 1080;

    WaifuBrew newWaifu = new WaifuBrew();

    // Temporary location dumpster when images are loaded on ImageIcon[]
    File fileGrab;

    private JLabel lable;
    private ImageIcon [] loadAll;
    private JScrollPane jsp;

    public GUI(Waifu waifu) {
        // super("Waifuarium by TailSoft");

        waifuPanel = new JPanel();
        waifuPanel.setBackground(Color.WHITE);
        add(waifuPanel, BorderLayout.CENTER);

        waifuLabel = new JLabel("Start");
        add(waifuLabel, BorderLayout.SOUTH);

        Handlerclass handler = new Handlerclass();
        waifuPanel.addMouseListener(handler);
        waifuPanel.addMouseMotionListener(handler);

        loadAll = new ImageIcon[Mood.values().length]; // Needs nested loop for more characters later
        for(int i = 0; i<Mood.values().length; i++) {
            System.out.println("For loop here: " + i + " value is " + Mood.values()[i].toString());
            fileGrab = new File("resources/" + newWaifu.getWaifu().name.toLowerCase() + "-" + Mood.values()[i].toString().toLowerCase() + ".jpg");
            loadAll[i] = new ImageIcon(fileGrab.getAbsolutePath());
        }
        lable = new JLabel();
        jsp = new JScrollPane();
    }




    private class Handlerclass implements MouseListener, MouseMotionListener {
        public void mouseClicked(MouseEvent event) {
            waifuLabel.setText(String.format("Clicked at %d, %d", event.getX(), event.getY()));
        }

        public void mousePressed(MouseEvent event) {
            waifuLabel.setText("you pressed down the mouse");
            printMouseIn();
        }

        public void mouseReleased(MouseEvent event) {
            waifuLabel.setText("you released the button");
            printMouseOut();
        }

        public void mouseEntered(MouseEvent event) {
            waifuLabel.setText("you entered the area");
            waifuPanel.setBackground(Color.RED);
        }

        public void mouseExited(MouseEvent event) {
            waifuLabel.setText("the mouse has left the window");
            waifuPanel.setBackground(Color.WHITE);
        }

        // these are mouse motion event
        public void mouseDragged(MouseEvent event) {
            waifuLabel.setText("you are dragging the mouse");
        }

        public void mouseMoved(MouseEvent event) {
            waifuLabel.setText("you moved the mouse");
        }

        public void printMouseIn() {
            waifuLabel.setIcon(loadAll[0]);

            waifuPanel.add(jsp);
        }

        public void printMouseOut() {

            waifuLabel.setIcon(loadAll[1]);

            waifuPanel.add(jsp);
        }
    }
}