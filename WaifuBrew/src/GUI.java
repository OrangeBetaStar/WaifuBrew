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

    File waifuHappy;
    File waifuAngry;

    ImageIcon ii;

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

        waifuHappy = new File("resources/" + newWaifu.getWaifu().name.toLowerCase() + "-" + newWaifu.getWaifu().mood.toString().toLowerCase() + ".jpg");
        newWaifu.getWaifu().setMood(Mood.ANGRY);
        waifuAngry = new File("resources/" + newWaifu.getWaifu().name.toLowerCase() + "-" + newWaifu.getWaifu().mood.toString().toLowerCase() + ".jpg");

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
            // path has to change
            ii = new ImageIcon(waifuHappy.getAbsolutePath());

            JLabel lable = new JLabel(ii);
            JScrollPane jsp = new JScrollPane(lable);

            waifuPanel.add(jsp);
        }
        public void printMouseOut() {
            // path has to change
            ii = new ImageIcon(waifuAngry.getAbsolutePath());

            JLabel lable = new JLabel(ii);
            JScrollPane jsp = new JScrollPane(lable);

            waifuPanel.add(jsp);
        }
    }
}