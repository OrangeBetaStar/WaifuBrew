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

    // Temporary location dumpster when images are loaded on ImageIcon[]
    File fileGrab;

    private ImageIcon [] loadAll;
    private JScrollPane jsp;

    private int testInt = 0;

    public GUI(WaifuBrew program) {
        super("Waifuarium by TailSoft");

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
            // System.out.println("For loop here: " + i + " value is " + Mood.values()[i].toString());
            fileGrab = new File("resources/" + program.getWaifu().name.toLowerCase() + "-" + Mood.values()[i].toString().toLowerCase() + ".jpg");
            loadAll[i] = new ImageIcon(fileGrab.getAbsolutePath());
        }
        jsp = new JScrollPane();

        waifuPanel.setBounds(program.getRes()[1].x, program.getRes()[1].y, program.getRes()[2].x, program.getRes()[2].y);

    }




    private class Handlerclass implements MouseListener, MouseMotionListener {
        public void mouseClicked(MouseEvent event) {
            waifuLabel.setText(String.format("Clicked at %d, %d", event.getX(), event.getY()));
        }

        public void mousePressed(MouseEvent event) {
            waifuLabel.setText("you pressed down the mouse");
            print();
        }

        public void mouseReleased(MouseEvent event) {
            waifuLabel.setText("you released the button");
            print();
        }

        public void mouseEntered(MouseEvent event) {
            waifuLabel.setText("you entered the area");
        }

        public void mouseExited(MouseEvent event) {
            waifuLabel.setText("the mouse has left the window");
        }

        // these are mouse motion event
        public void mouseDragged(MouseEvent event) {
            waifuLabel.setText("you are dragging the mouse");
        }

        public void mouseMoved(MouseEvent event) {
            waifuLabel.setText("you moved the mouse");
        }

        public void print() {
            // Prints
            waifuLabel.setIcon(loadAll[testInt]);
            if(testInt==(Mood.values().length-1)) {
                testInt = 0;
            }
            else {
                testInt++;
            }
            waifuPanel.add(jsp);
        }
    }
}