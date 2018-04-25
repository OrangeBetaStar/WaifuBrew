import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements ActionListener {
    private BufferedImage backgroundPicture;
    private BufferedImage[] intersectionImage;
    private final String RESOURCE_PATH = "src/main/java/resources/";


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
            System.out.println("File failure in ImagePanel class");
            e.printStackTrace(); // Wall of error
            System.exit(-1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(getPreferredSize().width < getSize().width) {
            if((getSize().width/(double)getPreferredSize().width) * getPreferredSize().height < getSize().height) {
                // TODO: Change x0, y0 if picture is changed.
                g.drawImage(backgroundPicture,0,0,(int)Math.round(getSize().height / (double)getPreferredSize().height * getPreferredSize().width), getSize().height, this);
                // System.out.println("Using height priority");
            }
            else {
                g.drawImage(backgroundPicture, 0, 0, getSize().width,(int)Math.round(getSize().width / (double)getPreferredSize().width * getPreferredSize().height), this);
                // System.out.println("Using width priority");
            }
        }
        for(int i = 0; i < intersectionImage.length; i++) {
            g.drawImage(intersectionImage[i], (backgroundPicture.getWidth() / (1 + intersectionImage.length)) * i, 200, this); // Prints all nicos as test (test of lets say... four characters at once?)
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (backgroundPicture != null) {
            int width = backgroundPicture.getWidth();
            int height = backgroundPicture.getHeight();
            return new Dimension(width , height );
        }
        return super.getPreferredSize();
    }

    public void move(Point a) {
        //stub
    }
}