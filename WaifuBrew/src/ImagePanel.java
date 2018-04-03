import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements ActionListener {
    private BufferedImage backgroundPicture;
    private BufferedImage[] intersectionImage;

    public void actionPerformed(ActionEvent e) {
        repaint();
        System.out.println("Hi");
    }

// curr is the initial position (perhaps predefined spot if not available.
// next is final position where character stays
// point[] will correspond to w[] (character number[])

    public ImagePanel(File bg, File[] w, Point[] curr, Point[] next) {
        try {
            intersectionImage = new BufferedImage[w.length]; // Set to 3 max character for now

            for(int i = 0; i < w.length; i++) {
                intersectionImage[i] = ImageIO.read(w[i]);
            }
                backgroundPicture = ImageIO.read(bg);
        } catch (IOException e) {
            e.printStackTrace(); // Wall of error
            System.exit(-1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (intersectionImage[0] != null) {
            g.drawImage(backgroundPicture,0,0, this);
            for(int i = 0; i < intersectionImage.length; i++) {
                g.drawImage(intersectionImage[i], (backgroundPicture.getWidth() / (1 + intersectionImage.length)) * i, 200, this); // Prints all nicos as test (test of lets say... four characters at once?)
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (intersectionImage != null) {
            int width = intersectionImage[0].getWidth();
            int height = intersectionImage[0].getHeight();
            return new Dimension(width , height );
        }
        return super.getPreferredSize();
    }

    public void move(Point a) {
        //stub
    }
}