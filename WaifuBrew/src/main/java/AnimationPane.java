import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimationPane extends JPanel {

    private BufferedImage scrollingImage;
    private javaxt.io.Image sampleImage;
    private int xPos = 0;
    private int direction = 1;
    private double rotationDeg = 0;
    private Handlerclass handler = new Handlerclass();

    // Retrieve String from JSON
    private String a = "Hello World Hello World Hello World Hello World Hello World Hello World Hello World ";
    private String tempString = "";

    public AnimationPane() {
        try {
            scrollingImage = ImageIO.read(new File("src/main/java/resources/black.png"));

            Timer timer = new Timer(1, new ActionListener() {
                // @Override
                public void actionPerformed(ActionEvent e) {
                    xPos += direction;
                    rotationDeg += direction;
                    if (xPos + scrollingImage.getWidth() > getWidth()) {
                        xPos = getWidth() - scrollingImage.getWidth();
                        direction *= -1;
                        rotationDeg *= -1;
                      //  System.out.println("REEEEEEEEEEEEEEEEEE");
                    } else if (xPos < 0) {
                        xPos = 0;
                        rotationDeg = 0;
                        direction *= -1;
                        rotationDeg *= -1;
                      //  System.out.println("AHHHHHHHHHHHHHHHHHH");
                    }
                    repaint();
                }
            });

            Timer stringTimer = new Timer(50, new ActionListener() {
                // @Override
                public void actionPerformed(ActionEvent e) {
                    if(tempString.length() != a.length()) {
                        tempString = tempString + a.charAt(tempString.length());
                    }
                    repaint();
                }
            });

            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.start();

            stringTimer.setRepeats(true);
            stringTimer.setCoalesce(true);
            stringTimer.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private class Handlerclass implements MouseListener {

        public void mouseClicked(MouseEvent event) {
            tempString = "";
        }

        public void mousePressed(MouseEvent event) {

        }

        public void mouseReleased(MouseEvent event) {

        }

        public void mouseEntered(MouseEvent event) {

        }

        public void mouseExited(MouseEvent event) {

        }
    }

    @Override
    public Dimension getPreferredSize() {
        return scrollingImage == null ? super.getPreferredSize() : new Dimension(scrollingImage.getWidth() * 4, scrollingImage.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Needs new image every rotation since reusing will make image blurry and hot garbage. (reconversion after reconversion of a same image)
        sampleImage = new javaxt.io.Image("src/main/java/resources/black.png");
        sampleImage.rotate(rotationDeg);
        g.drawImage(sampleImage.getBufferedImage(),xPos - (sampleImage.getWidth() / 2),getSize().height / 2 - (scrollingImage.getHeight() / 2 ) - (sampleImage.getHeight() / 2), this);

        // Does this have to be declared every time?
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.setColor(new Color(0,0,0));

        // Does this have to be added every time?
        addMouseListener(handler);

        g.drawString(tempString, 100, 550);
    }

}
