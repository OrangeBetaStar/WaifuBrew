package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class StartScreen extends JPanel implements ActionListener {

    private javaxt.io.Image backgroundPicture;

    // LOAD THE BUTTONS
    private CustomButton start_buton;
    private CustomButton config_buton;
    private CustomButton load_buton;
    private CustomButton exit_buton;

    private Handlerclass handler = new Handlerclass();

    private int buttonY = 600;

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private final Point windowSize = WaifuBrew.getInstance().getRes()[1];

    private int spacing = 5;

    private boolean stop = false;

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public StartScreen() {
        init();

        addMouseListener(handler);
        addMouseMotionListener(handler);
        backgroundPicture = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BACKGROUND, "bg_start.png"));

        // Calculates the scaling needed to fit the screen. Any ratio will work.
        if(backgroundPicture.getWidth() < windowSize.x || backgroundPicture.getHeight() < windowSize.y) {
            if(((double)windowSize.x / backgroundPicture.getWidth()) * backgroundPicture.getHeight() < windowSize.y) {
                backgroundPicture.resize(((int)(((double)windowSize.y / backgroundPicture.getHeight()) * backgroundPicture.getWidth())), (int)(((double)windowSize.y / backgroundPicture.getHeight()) * backgroundPicture.getHeight()));
            }
            else {
                backgroundPicture.resize(((int)(((double)windowSize.x / backgroundPicture.getWidth()) * backgroundPicture.getWidth())), (int)(((double)windowSize.x / backgroundPicture.getWidth()) * backgroundPicture.getHeight()));
            }
        }

        // These actually now gets images from sliced up system_image sheet.
        start_buton = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / spacing), buttonY, "startscreen_start_button.png",true);
        load_buton = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / spacing) * 2, buttonY, "startscreen_load_button.png", true);
        config_buton = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / spacing) * 3, buttonY, "startscreen_config_button.png",true);
        exit_buton = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / spacing) * 4, buttonY, "startscreen_exit_button.png",true);

        addMouseListener(start_buton.retrieveMouseHandler());
        addMouseMotionListener(start_buton.retrieveMouseHandler());
        addMouseListener(load_buton.retrieveMouseHandler());
        addMouseMotionListener(load_buton.retrieveMouseHandler());
        addMouseListener(config_buton.retrieveMouseHandler());
        addMouseMotionListener(config_buton.retrieveMouseHandler());
        addMouseListener(exit_buton.retrieveMouseHandler());
        addMouseMotionListener(exit_buton.retrieveMouseHandler());
    }

    public void init(){
        Timer t = new Timer((int)(1000/WaifuBrew.getInstance().getFrameRate()), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!stop) {
                    repaint();
                } else {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        t.setRepeats(true);
        t.setDelay((int)(1000/WaifuBrew.getInstance().getFrameRate()));
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundPicture.getBufferedImage(), (windowSize.x / 2) - (backgroundPicture.getWidth() / 2), (windowSize.y / 2) - (backgroundPicture.getHeight() / 2), this);

        start_buton.paintComponent(g);
        load_buton.paintComponent(g);
        config_buton.paintComponent(g);
        exit_buton.paintComponent(g);

    }

    private class Handlerclass implements MouseListener, MouseMotionListener {

        public void mouseClicked(MouseEvent event) {

            // 1 - AnimationPane
            // 2 - ConfigPane
            // 3 - Load
            // 4 - Exit lol

            if(WaifuBrew.getInstance().getStage() == 0) {
                if((event.getY() < buttonY + ((double)start_buton.getHeight() / 2)) && (event.getY() > buttonY - ((double)start_buton.getHeight() / 2))) {
                    if ((event.getX() < ((double)windowSize.x / spacing) + ((double)start_buton.getWidth() / 2)) && (event.getX() > ((double)windowSize.x / spacing) - ((double)start_buton.getWidth() / 2))) {
                        WaifuBrew.getInstance().setStage(1);
                        System.out.println("Hit button and changed stage!");
                    } else if ((event.getX() < ((double)windowSize.x / spacing) * 2 + ((double)load_buton.getWidth() / 2)) && (event.getX() > ((double)windowSize.x / spacing) * 2 - ((double)load_buton.getWidth() / 2))) {
                        WaifuBrew.getInstance().setStage(3);
                        System.out.println("Hit button and changed stage!");
                    } else if ((event.getX() < ((double)windowSize.x / spacing) * 3 + ((double)config_buton.getWidth() / 2)) && (event.getX() > ((double)windowSize.x / spacing) * 3 - ((double)config_buton.getWidth() / 2))) {
                        WaifuBrew.getInstance().setStage(2);
                        System.out.println("Hit button and changed stage!");
                    } else if ((event.getX() < ((double)windowSize.x / spacing) * 4 + ((double)exit_buton.getWidth() / 2)) && (event.getX() > ((double)windowSize.x / spacing) * 4 - ((double)exit_buton.getWidth() / 2))) {
                        System.exit(0);
                    }
                }
            }
        }

        public void mousePressed(MouseEvent event) {
        }

        public void mouseReleased(MouseEvent event) {
        }

        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mouseDragged(MouseEvent event) {
        }

        public void mouseMoved(MouseEvent event) {

        }
    }

    public Dimension getPreferredSize(BufferedImage a) {
        if (a != null) {
            int width = a.getWidth();
            int height = a.getHeight();
            return new Dimension(width, height);
        }
        return super.getPreferredSize();
    }
}