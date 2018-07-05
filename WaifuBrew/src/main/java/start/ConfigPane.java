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

    // LOAD IMAGE
    private BufferedImage backgroundPicture;
    private javaxt.io.Image back_button;
    private javaxt.io.Image dialogueBox; // So it doesn't use HDD every time and kill performance
    private javaxt.io.Image tempDialogueBox; // Preview

    // PLACEMENT TODO: (Probably change to relative later)
    private int dialogueX = 100;
    private int dialogueTransparencyY = 220;
    private int dialogueSpeedY = dialogueTransparencyY + 80;
    private int backButtonX = 1100;
    private int backButtonY = 600;
    private boolean backButtonUI = false;
    private Timer stringTimer;

    private CustomSlider slider_transparency;
    private CustomSlider slider_speed;

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private Handlerclass handler = new Handlerclass();

    public int dialogueTransparency = WaifuBrew.getInstance().getDialogueTransparency();
    public int dialogueSpeed = WaifuBrew.getInstance().getDialogueSpeed() * 10;
    private String a = "Your waifu isn't real."; // Test String.
    private String tempString = "";

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public ConfigPane() {
        try {
            // Load needed images
            backgroundPicture = ImageIO.read(new File(RESOURCE_PATH + "options-background.png"));
            back_button = new javaxt.io.Image(RESOURCE_PATH + "config_back_button.png");
            dialogueBox = new javaxt.io.Image(RESOURCE_PATH+"dialogbar.png");
            dialogueBox.resize((int)(dialogueBox.getWidth() * 0.9), (int)(dialogueBox.getHeight() * 0.9));

            // Testing CUSTOM SLIDER
            slider_transparency = new CustomSlider(dialogueX, dialogueTransparencyY, dialogueTransparency);
            slider_speed = new CustomSlider(dialogueX,dialogueSpeedY,dialogueSpeed);

            // Handlers listening to mouse like DOGS
            addMouseListener(handler);
            addMouseMotionListener(handler);
            addMouseListener(slider_transparency.retrieveMouseHandler());
            addMouseMotionListener(slider_transparency.retrieveMouseHandler());
            addMouseListener(slider_speed.retrieveMouseHandler());
            addMouseMotionListener(slider_speed.retrieveMouseHandler());

            stringTimer = new Timer((WaifuBrew.getInstance().getDialogueSpeed() * 10), new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!a.isEmpty()) {
                        if (tempString.length() != a.length()) {
                            tempString = tempString + a.charAt(tempString.length());
                        }
                        else {
                            tempString = a.substring(0,1);
                            stringTimer.stop();
                            stringTimer.setDelay(slider_speed.getLevel());
                            stringTimer.start();
                        }
                    }
                }
            });

            // Coalesce is disabled since there is no multiple firing of triggers.
            stringTimer.setRepeats(true);
            stringTimer.setCoalesce(false);


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
            if(!slider_transparency.isSliderActive() && !slider_speed.isSliderActive()) {
                // TBH I don't really need this next if statement ???
                if (getPreferredSize(backgroundPicture).width < WaifuBrew.getInstance().getRes()[1].getX()) {
                    if ((WaifuBrew.getInstance().getRes()[1].getX() / (double) getPreferredSize(backgroundPicture).width) * getPreferredSize(backgroundPicture).height < WaifuBrew.getInstance().getRes()[1].getY()) {
                        g.drawImage(backgroundPicture, 0, 0, (int) Math.round(WaifuBrew.getInstance().getRes()[1].getY() / (double) getPreferredSize(backgroundPicture).height * getPreferredSize(backgroundPicture).width), (int) Math.round(WaifuBrew.getInstance().getRes()[1].getY()), this);
                    } else {
                        g.drawImage(backgroundPicture, 0, 0, (int) Math.round(WaifuBrew.getInstance().getRes()[1].getX()), (int) Math.round(WaifuBrew.getInstance().getRes()[1].getX() / (double) getPreferredSize(backgroundPicture).width * getPreferredSize(backgroundPicture).height), this);
                    }
                } else {
                    // TODO: Just copied from above. Will refine later
                    if ((WaifuBrew.getInstance().getRes()[1].getX() / (double) getPreferredSize(backgroundPicture).width) * getPreferredSize(backgroundPicture).height < WaifuBrew.getInstance().getRes()[1].getY()) {
                        g.drawImage(backgroundPicture, 0, 0, (int) Math.round(WaifuBrew.getInstance().getRes()[1].getY() / (double) getPreferredSize(backgroundPicture).height * getPreferredSize(backgroundPicture).width), (int) Math.round(WaifuBrew.getInstance().getRes()[1].getY()), this);
                    } else {
                        g.drawImage(backgroundPicture, 0, 0, (int) Math.round(WaifuBrew.getInstance().getRes()[1].getX()), (int) Math.round(WaifuBrew.getInstance().getRes()[1].getX() / (double) getPreferredSize(backgroundPicture).width * getPreferredSize(backgroundPicture).height), this);
                    }
                }
            }
        }

        // DISABLES CONFIGPANE BG WHEN USING SLIDER & PREVIEW OF TRANSPARENCY
        if(!slider_transparency.isSliderActive() && !slider_speed.isSliderActive()) {
            // BACK BUTTON
            javaxt.io.Image tempBackButton = back_button.copy();
            if (backButtonUI) {
                tempBackButton.setOpacity(100);
            } else {
                tempBackButton.setOpacity(20);
            }

            g.drawImage(tempBackButton.getBufferedImage(), (backButtonX - (getPreferredSize(tempBackButton.getBufferedImage()).width / 2)), (backButtonY - (getPreferredSize(tempBackButton.getBufferedImage()).height / 2)), getPreferredSize(tempBackButton.getBufferedImage()).width, getPreferredSize(tempBackButton.getBufferedImage()).height, this);
            if(stringTimer.isRunning()) {
                stringTimer.stop();
            }
        }
        else {
            // DialogueBox
            tempDialogueBox = dialogueBox.copy();
            tempDialogueBox.setOpacity(slider_transparency.getLevel());
            g.drawImage(tempDialogueBox.getBufferedImage(),WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2, WaifuBrew.getInstance().getRes()[1].y - dialogueBox.getHeight() - (WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2),this);
            stringTimer.start();
            if(tempString != "") {
                g.drawString(tempString, 150, 550);
            }
        }

        // TODO: Perhaps implement title for CustomSlider
        g.drawString("Diologue Bar Transparency", dialogueX, dialogueTransparencyY - 20);
        g.drawString("Diologue Text Speed", dialogueX, dialogueSpeedY - 20);

        slider_transparency.paintComponent(g);
        slider_speed.paintComponent(g);

        repaint();
    }

    private class Handlerclass implements MouseListener, MouseMotionListener {

        // TODO: FINE TUNE THE KNOBS SO THAT IT KEEPS THE ORIGINAL POSITION OF CLICK POINT OF SQUARE (CURRENT IS MIDDLE)
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
        public void mouseReleased (MouseEvent event) {}
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
