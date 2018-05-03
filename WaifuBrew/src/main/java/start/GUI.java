package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private JPanel waifuPanel;
    private JLabel waifuLabel;

    private Handlerclass handler = new Handlerclass();
    private StartScreen startPage;
    private AnimationPane animationPane;
    private ConfigPane configPane;

    //private JScrollPane jsp;
    private final String RESOURCE_PATH = "src/main/java/resources/";
    private int stage = 0;
    private int lastStage = 10;

    public GUI() {
        super("ワイファブルー by Tailsoft");

        addMouseListener(handler);
        addMouseMotionListener(handler);

        waifuPanel = new JPanel();
        waifuPanel.setBackground(Color.BLACK);
        waifuLabel = new JLabel("ワイファブルー");
        startPage = new StartScreen();
        startPage.addMouseListener(handler);
        startPage.addMouseMotionListener(handler);
        animationPane = new AnimationPane();
        configPane = new ConfigPane();

        revalidateGraphics();

        waifuPanel.setBounds(WaifuBrew.getInstance().getRes()[1].x, WaifuBrew.getInstance().getRes()[1].y, WaifuBrew.getInstance().getRes()[2].x, WaifuBrew.getInstance().getRes()[2].y);

    }



    public void revalidateGraphics() {

        System.out.println("Parent "+startPage.getParent());
        // If stage changed
        if(lastStage != stage) {

            // Stage 0 is the main screen
            if (stage == 0) {
                if(animationPane.getParent() != null) {
                    remove(animationPane);
                }
                if(configPane.getParent() != null) {
                    remove(configPane);
                }
                add(startPage);
            }

            // Stage 1 is the play field.
            else if (stage == 1) {
                if(startPage.getParent() != null) {
                    remove(startPage);
                }
                if(configPane.getParent() != null) {
                    remove(configPane);
                }
                add(animationPane);
            }

            // Stage 2 is the config field
            else if (stage == 2) {
                if(animationPane.getParent() != null) {
                    remove(animationPane);
                }
                if(startPage.getParent() != null) {
                    remove(startPage);
                }
                add(configPane);

            }
            revalidate();
        }

        lastStage = stage;
    }

    public int getStage(){
        return this.stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    private class Handlerclass implements MouseListener, MouseMotionListener{

        public void mouseClicked(MouseEvent event) {
            if(stage == 0) {
                if (event.getX() > 0 && event.getX() < getSize().width / 4) {
                    stage = 1;
                    System.out.println("Successfully verified start location!");
                    revalidateGraphics();
                }
                else if (event.getX() > getSize().width / 4 && event.getX() < (getSize().width / 4) * 2) {
                    System.out.println("Successfully verified load location!");
                }
                else if (event.getX() > (getSize().width / 4) * 2 && event.getX() < (getSize().width / 4) * 3) {
                    stage = 2;
                    revalidateGraphics();
                }
                else {
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
        }

        public void mouseReleased(MouseEvent event) {
            waifuLabel.setText("you released the button");
        }

        public void mouseEntered(MouseEvent event) {
            waifuLabel.setText("you entered the area");
        }

        public void mouseExited(MouseEvent event) {
            waifuLabel.setText("the mouse has left the window");
        }

        public void mouseDragged(MouseEvent event) {
            waifuLabel.setText("you are dragging the mouse");
        }

        public void mouseMoved(MouseEvent event) {
            waifuLabel.setText("you moved the mouse");
        }
    }
}