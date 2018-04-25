import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class StartScreen extends JPanel implements ActionListener {

    private BufferedImage backgroundPicture;
    private BufferedImage start_button;
    private BufferedImage config_button;
    private BufferedImage exit_button;


    private int buttonY = 600;

    private final String RESOURCE_PATH = "src/main/java/resources/";

    public void actionPerformed(ActionEvent e) {
        repaint();
        System.out.println("Hi");
    }

    public StartScreen() {
        try {
            backgroundPicture = ImageIO.read(new File(RESOURCE_PATH + "start.png"));
            start_button = ImageIO.read(new File(RESOURCE_PATH + "startscreen_start_button.png"));
            config_button = ImageIO.read(new File(RESOURCE_PATH + "startscreen_config_button.png"));
            exit_button = ImageIO.read(new File(RESOURCE_PATH + "startscreen_exit_button.png"));
        } catch (IOException e) {
            System.out.println("File failure in StartScreen class");
            e.printStackTrace(); // Wall of error
            System.exit(-1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundPicture != null) {
            // I want to centre the image that is 960:640 to widescreen format, but do not want to stretch. I will zoom in!

            // Not centered tho... lo

            // int b =(int)Math.round(a);
            if(getPreferredSize(backgroundPicture).width < getSize().width) {
                /*
                System.out.println("BG width: "+getPreferredSize(backgroundPicture).width);
                System.out.println("Window width: "+getSize().width);
                System.out.println("BG height: "+getPreferredSize(backgroundPicture).height);
                System.out.println("Window height: "+getSize().height);
                System.out.println("Calculated height: "+(getSize().width/getPreferredSize(backgroundPicture).width) * getPreferredSize(backgroundPicture).height);
                System.out.println("Ratio: "+(getSize().width/(double)getPreferredSize(backgroundPicture).width));
                */
                if((getSize().width/(double)getPreferredSize(backgroundPicture).width) * getPreferredSize(backgroundPicture).height < getSize().height) {
                    // TODO: Change x0, y0 if picture is changed.
                    g.drawImage(backgroundPicture,0,0,(int)Math.round(getSize().height / (double)getPreferredSize(backgroundPicture).height * getPreferredSize(backgroundPicture).width), getSize().height, this);
                    // System.out.println("Using height priority");
                }
                else {
                    g.drawImage(backgroundPicture, 0, 0, getSize().width,(int)Math.round(getSize().width / (double)getPreferredSize(backgroundPicture).width * getPreferredSize(backgroundPicture).height), this);
                    // System.out.println("Using width priority");
                }
            }

            // Add rest of the menu elements

            g.drawImage(start_button, (getSize().width / 4) - (getPreferredSize(start_button).width / 2), buttonY - (getPreferredSize(start_button).height / 2), getPreferredSize(start_button).width, getPreferredSize(start_button).height,this);
            g.drawImage(config_button,(getSize().width / 4) * 2 - (getPreferredSize(config_button).width / 2), buttonY - (getPreferredSize(config_button).height / 2) , getPreferredSize(config_button).width, getPreferredSize(config_button).height,  this);
            g.drawImage(exit_button,(getSize().width / 4) * 3 - (getPreferredSize(exit_button).width / 2), buttonY - (getPreferredSize(exit_button).height / 2), getPreferredSize(exit_button).width, getPreferredSize(exit_button).height,  this);

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