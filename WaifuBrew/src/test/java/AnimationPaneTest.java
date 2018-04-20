import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimationPaneTest extends JPanel {

    private BufferedImage scrollingImage;
    private javaxt.io.Image sampleImage;
    private int xPos = 0;
    private int direction = 1;
    private double rotationDeg = 0;

    public AnimationPaneTest() {
        try {
            scrollingImage = ImageIO.read(new File("src/main/java/resources/icon.jpg"));


            Timer timer = new Timer(1, new ActionListener() {
                // @Override
                public void actionPerformed(ActionEvent e) {
                    xPos += direction;
                    rotationDeg += direction;
                    if (xPos + scrollingImage.getWidth() > getWidth()) {
                        xPos = getWidth() - scrollingImage.getWidth();
                        direction *= -1;
                        rotationDeg *= -1;
                        System.out.println("REEEEEEEEEEEEEEEEEE");
                    } else if (xPos < 0) {
                        xPos = 0;
                        rotationDeg = 0;
                        direction *= -1;
                        rotationDeg *= -1;
                        System.out.println("AHHHHHHHHHHHHHHHHHH");
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
        // System.out.println("scrollingImageWidth: "+scrollingImage.getWidth() + " scrollingImageHeight: " + scrollingImage.getHeight());
        return scrollingImage == null ? super.getPreferredSize() : new Dimension(scrollingImage.getWidth() * 4, scrollingImage.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y = getHeight() - scrollingImage.getHeight();

        g.drawString("java.io:", 100, getSize().height / 2 - (scrollingImage.getHeight() / 2 ) - 50);
        g.drawString("javaxt.io:", 100, getSize().height / 2 - (scrollingImage.getHeight() / 2 ) + 150);

        // g.drawImage(scrollingImage, xPos, y, this);
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(rotationDeg), scrollingImage.getWidth() / 2, scrollingImage.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(rotation, AffineTransformOp.TYPE_BICUBIC);

        // Apparently I can save the image through dst.
        g.drawImage(op.filter(scrollingImage, null), xPos, getSize().height / 2 - (scrollingImage.getHeight() / 2 ), this);


        // http://www.javaxt.com/javaxt-core/io/Image/


        // Needs new image every rotation since reusing will make image blurry and hot garbage. (reconversion after reconversion)
        sampleImage = new javaxt.io.Image("src/main/java/resources/icon.jpg");
        sampleImage.rotate(rotationDeg);
        g.drawImage(sampleImage.getBufferedImage(),xPos,getSize().height / 2 - (scrollingImage.getHeight() / 2 ) + 200, this);

    }

}
