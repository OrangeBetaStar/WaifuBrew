import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimationPane extends JPanel {

    private BufferedImage scrollingImage;
    private int xPos = 0;
    private int direction = 1;

    public AnimationPane() {
        try {
            scrollingImage = ImageIO.read(new File("src/resources/icon.jpg"));
            Timer timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    xPos += direction;
                    if (xPos + scrollingImage.getWidth() > getWidth()) {
                        xPos = getWidth() - scrollingImage.getWidth();
                        direction *= -1;
                    } else if (xPos < 0) {
                        xPos = 0;
                        direction *= -1;
                    }
                    repaint();
                }

            });
            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return scrollingImage == null ? super.getPreferredSize() : new Dimension(scrollingImage.getWidth() * 4, scrollingImage.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int y = getHeight() - scrollingImage.getHeight();
        g.drawImage(scrollingImage, xPos, y, this);

    }

}