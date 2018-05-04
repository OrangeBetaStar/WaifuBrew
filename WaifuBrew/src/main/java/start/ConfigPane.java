package start;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class ConfigPane extends JPanel implements ActionListener {

    private BufferedImage backgroundPicture;
    private javaxt.io.Image back_button;

    // TODO:
    private javaxt.io.Image dialogueTransBack;
    private javaxt.io.Image dialogueTransKnob;
    private JSlider dialogueTransSlider;

    private int backButtonX = 1100;
    private int backButtonY = 600;

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private Handlerclass handler = new Handlerclass();

    private boolean backButtonUI = false;

    private int dialogueTransparency = 70;

     public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public ConfigPane() {
        try {
            backgroundPicture = ImageIO.read(new File(RESOURCE_PATH + "options-background.png"));
            // TODO: Add buttons (back, toggles, sound system?... etc)

            back_button = new javaxt.io.Image(RESOURCE_PATH + "config_back_button.png");

            // TODO:
            dialogueTransBack = new javaxt.io.Image(RESOURCE_PATH + "black.png");
            dialogueTransKnob = new javaxt.io.Image(RESOURCE_PATH + "black.png");
            dialogueTransSlider = new JSlider(0, 100, dialogueTransparency);

            addMouseListener(handler);
            addMouseMotionListener(handler);

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
            if(getPreferredSize(backgroundPicture).width < WaifuBrew.getInstance().getRes()[1].getX()) {
                if((WaifuBrew.getInstance().getRes()[1].getX()/(double)getPreferredSize(backgroundPicture).width) * getPreferredSize(backgroundPicture).height < WaifuBrew.getInstance().getRes()[1].getY()) {
                    g.drawImage(backgroundPicture,0,0,(int)Math.round(WaifuBrew.getInstance().getRes()[1].getY() / (double)getPreferredSize(backgroundPicture).height * getPreferredSize(backgroundPicture).width), (int)Math.round(WaifuBrew.getInstance().getRes()[1].getY()), this);
                }
                else {
                    g.drawImage(backgroundPicture, 0, 0, (int)Math.round(WaifuBrew.getInstance().getRes()[1].getX()),(int)Math.round(WaifuBrew.getInstance().getRes()[1].getX() / (double)getPreferredSize(backgroundPicture).width * getPreferredSize(backgroundPicture).height), this);
                }
            }
            else {
                // TODO: Just copied from above. Will refine later
                if((WaifuBrew.getInstance().getRes()[1].getX()/(double)getPreferredSize(backgroundPicture).width) * getPreferredSize(backgroundPicture).height < WaifuBrew.getInstance().getRes()[1].getY()) {
                    g.drawImage(backgroundPicture,0,0,(int)Math.round(WaifuBrew.getInstance().getRes()[1].getY() / (double)getPreferredSize(backgroundPicture).height * getPreferredSize(backgroundPicture).width), (int)Math.round(WaifuBrew.getInstance().getRes()[1].getY()), this);
                }
                else {
                    g.drawImage(backgroundPicture, 0, 0, (int)Math.round(WaifuBrew.getInstance().getRes()[1].getX()),(int)Math.round(WaifuBrew.getInstance().getRes()[1].getX() / (double)getPreferredSize(backgroundPicture).width * getPreferredSize(backgroundPicture).height), this);
                }
            }

            // TODO: Add rest of the menu elements

        }
        javaxt.io.Image tempBackButton = back_button.copy();
        if(backButtonUI) {
            tempBackButton.setOpacity(100);
        }
        else {
            tempBackButton.setOpacity(20);
        }
        g.drawImage(tempBackButton.getBufferedImage(), (backButtonX - (getPreferredSize(tempBackButton.getBufferedImage()).width / 2)), (backButtonY - (getPreferredSize(tempBackButton.getBufferedImage()).height / 2)), getPreferredSize(tempBackButton.getBufferedImage()).width, getPreferredSize(tempBackButton.getBufferedImage()).height, this);
        dialogueTransBack.resize(200,10);
        g.drawString("Diologue Bar Transparency", 100, 180);
        g.drawImage(dialogueTransBack.getBufferedImage(), 150, 200, this);
        // TODO: https://jasperpotts.com/blog/2008/08/skinning-a-slider-with-nimbus/

        // Save resource (in mouseEntered) - Didn't work
        repaint();
    }

    private class Handlerclass implements MouseListener, MouseMotionListener {
        public void mousePressed (MouseEvent event) {

        }
        public void mouseMoved (MouseEvent event) {
            if(event.getX() >= (backButtonX - (getPreferredSize(back_button.getBufferedImage()).width / 2)) && event.getY() >= (backButtonY - (getPreferredSize(back_button.getBufferedImage()).height / 2)) && event.getX() <= ((backButtonX - (getPreferredSize(back_button.getBufferedImage()).width / 2)) + back_button.getWidth()) && event.getY() <= ((backButtonY - (getPreferredSize(back_button.getBufferedImage()).height / 2)) + back_button.getHeight())) {
                backButtonUI = true;
            }
            else {
                backButtonUI = false;
            }
        }
        public void mouseDragged (MouseEvent event) {

        }
        public void mouseClicked (MouseEvent event) {

            if(event.getX() >= (backButtonX - (getPreferredSize(back_button.getBufferedImage()).width / 2)) && event.getY() >= (backButtonY - (getPreferredSize(back_button.getBufferedImage()).height / 2)) && event.getX() <= ((backButtonX - (getPreferredSize(back_button.getBufferedImage()).width / 2)) + back_button.getWidth()) && event.getY() <= ((backButtonY - (getPreferredSize(back_button.getBufferedImage()).height / 2)) + back_button.getHeight())) {
                WaifuBrew.getInstance().getGUIInstance().setStage(0);
                WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
            }

        }
        public void mouseEntered (MouseEvent event) {

        }
        public void mouseReleased (MouseEvent event) {

        }
        public void mouseExited (MouseEvent event) {

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
