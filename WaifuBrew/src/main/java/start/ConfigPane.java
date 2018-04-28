package start;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class ConfigPane extends JPanel implements ActionListener {

    private BufferedImage backgroundPicture;
    private BufferedImage back_button;

    private int buttonY = 600;

    private String RESOURCE_PATH = "src/main/java/resources/";

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public ConfigPane(WaifuBrew mainProgram) {
        try {
            backgroundPicture = ImageIO.read(new File(RESOURCE_PATH + "options-background.png"));
            // TODO: Add buttons (back, toggles, sound system?... etc)

            back_button = ImageIO.read(new File(RESOURCE_PATH + "config_back_button.png"));

        } catch (IOException e) {
            System.out.println("File failure in Config class");
            e.printStackTrace(); // Wall of error
            System.exit(-1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundPicture != null) {
            // I want to centre the image that is 960:640 to widescreen format, but do not want to stretch. I will zoom in.

            // TBH I don't really need this next if statement ???
            if(getPreferredSize(backgroundPicture).width < getSize().width) {
                if((getSize().width/(double)getPreferredSize(backgroundPicture).width) * getPreferredSize(backgroundPicture).height < getSize().height) {
                    // TODO: Change x0, y0 if picture is changed.
                    g.drawImage(backgroundPicture,0,0,(int)Math.round(getSize().height / (double)getPreferredSize(backgroundPicture).height * getPreferredSize(backgroundPicture).width), getSize().height, this);
                    //System.out.println("Using height priority (This is Config)");
                }
                else {
                    g.drawImage(backgroundPicture, 0, 0, getSize().width,(int)Math.round(getSize().width / (double)getPreferredSize(backgroundPicture).width * getPreferredSize(backgroundPicture).height), this);
                    //System.out.println("Using width priority (This is Config)");
                }
            }
            else {
                // TODO: Just copied from above. Will refine later
                if((getSize().width/(double)getPreferredSize(backgroundPicture).width) * getPreferredSize(backgroundPicture).height < getSize().height) {
                    // TODO: Change x0, y0 if picture is changed.
                    g.drawImage(backgroundPicture,0,0,(int)Math.round(getSize().height / (double)getPreferredSize(backgroundPicture).height * getPreferredSize(backgroundPicture).width), getSize().height, this);
                }
                else {
                    g.drawImage(backgroundPicture, 0, 0, getSize().width,(int)Math.round(getSize().width / (double)getPreferredSize(backgroundPicture).width * getPreferredSize(backgroundPicture).height), this);
                }
            }

            // TODO: Add rest of the menu elements

            /*
            g.drawImage(start_button, (getSize().width / 4) - (getPreferredSize(start_button).width / 2), buttonY - (getPreferredSize(start_button).height / 2), getPreferredSize(start_button).width, getPreferredSize(start_button).height,this);
            g.drawImage(config_button,(getSize().width / 4) * 2 - (getPreferredSize(config_button).width / 2), buttonY - (getPreferredSize(config_button).height / 2) , getPreferredSize(config_button).width, getPreferredSize(config_button).height,  this);
            g.drawImage(exit_button,(getSize().width / 4) * 3 - (getPreferredSize(exit_button).width / 2), buttonY - (getPreferredSize(exit_button).height / 2), getPreferredSize(exit_button).width, getPreferredSize(exit_button).height,  this);
            */

            g.drawImage(back_button, 1100 - (getPreferredSize(back_button).width / 2), buttonY - (getPreferredSize(back_button).height / 2), getPreferredSize(back_button).width, getPreferredSize(back_button).height, this);
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