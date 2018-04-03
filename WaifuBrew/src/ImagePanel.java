import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
    private BufferedImage intersectionImage;

    public ImagePanel(File w) {
        try {
            intersectionImage = ImageIO.read(w);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (intersectionImage != null) {
            g.drawImage(intersectionImage, 30, 200, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (intersectionImage != null) {
            int width = intersectionImage.getWidth();
            int height = intersectionImage.getHeight();
            return new Dimension(width , height );
        }
        return super.getPreferredSize();
    }

    /*
    private static void createAndShowGui() {
        ImagePanel mainPanel = new ImagePanel();

        JFrame frame = new JFrame("IntersectionImage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
    */
}