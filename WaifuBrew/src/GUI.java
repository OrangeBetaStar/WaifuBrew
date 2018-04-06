import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {
    private JPanel waifuPanel;
    private JLabel waifuLabel;

    private int buttonY = 600;
    private Handlerclass handler = new Handlerclass();
    private StartScreen startPage;
    private Config configPage;

    // Temporary location dumpster when images are loaded on ImageIcon[]
    private File fileGrab[];

    private ImageIcon [] loadAll;
    private JScrollPane jsp;
    private String sourcePath = "src/resources/";
    private int stage = 0;

    private int testInt = 0;

    public GUI(WaifuBrew program) {
        super("Waifuarium by TailSoft");

        waifuPanel = new JPanel();
        waifuPanel.setBackground(Color.BLACK);
        // add(waifuPanel, BorderLayout.CENTER);

        waifuLabel = new JLabel("Start");

        waifuLabel.addMouseListener(handler);
        waifuLabel.addMouseMotionListener(handler);

        loadAll = new ImageIcon[Mood.values().length]; // Needs nested loop for more characters later
        fileGrab = new File[Mood.values().length];
        for(int i = 0; i<Mood.values().length; i++) {
            fileGrab[i] = new File(sourcePath + program.getWaifu().getName().toLowerCase() + "-" + Mood.values()[i].toString().toLowerCase() + ".jpg");
            loadAll[i] = new ImageIcon(fileGrab[i].getAbsolutePath());
        }

        revalidateGraphics();

        jsp = new JScrollPane();

        waifuPanel.setBounds(program.getRes()[1].x, program.getRes()[1].y, program.getRes()[2].x, program.getRes()[2].y);
    }

    private void revalidateGraphics() {
        if (stage == 0) {
            startPage = new StartScreen();
            startPage.addMouseListener(handler);
            startPage.addMouseMotionListener(handler);
            add(startPage);
            revalidate();
            setLocationByPlatform(true);
        }

        // Stage 1 is the play field.
        // Perhaps a custom test panel instead of JLabel. (Later work)
        else if(stage == 1) {
            if(startPage.getParent() != null) {
                // This will clear the panel of main screen.
                remove(startPage);
            }
            ImagePanel imageSquare = new ImagePanel(new File(sourcePath + "bg.png"), fileGrab, null, null); // As a tester, using the old image
            // ^ null for locations since I am just testing
            // if null, it will use default spot. (For now there has to be either no declaration or full declaration for character location.)
            imageSquare.addMouseListener(handler);
            imageSquare.addMouseMotionListener(handler);
            add(imageSquare);
            add(waifuLabel, BorderLayout.SOUTH);
            revalidate();
        }

        else if(stage == 2) {
            if(startPage.getParent() != null) {
                remove(startPage);
            }

            configPage = new Config();
            configPage.addMouseListener(handler);
            configPage.addMouseMotionListener(handler);
            add(configPage);
            revalidate();

            // TODO: Implement this.
        }
    }

    private class Handlerclass implements MouseListener, MouseMotionListener{

        public void mouseClicked(MouseEvent event) {
            if(stage == 0) {
                if (event.getX() > 0 && event.getX() < getSize().width / 3) {
                    stage = 1;
                    System.out.println("Successfully verified start location!");
                    revalidateGraphics();
                } else if (event.getX() > getSize().width / 3 && event.getX() < (getSize().width / 3) * 2) {
                    System.out.println("Successfully verified config location!");
                    stage = 2;
                    revalidateGraphics();
                } else {
                    System.out.println("Successfully verified exit location!");
                    System.exit(0);
                }
                waifuLabel.setText(String.format("Clicked at %d, %d", event.getX(), event.getY()));
            }
            else if(stage == 2) {
                // TODO: Stub for configs stage
                if((getSize().width / 3) * 2 > event.getX() && getSize().width < event.getX() ) {
                    stage = 0;
                    revalidateGraphics();
                    System.out.println("Stage 2 back button");
                }
            }
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