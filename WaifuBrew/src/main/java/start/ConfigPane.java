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
    private javaxt.io.Image sliderTrack;
    private javaxt.io.Image dialogueTransparencySlider;
    private javaxt.io.Image dialogueTransparencyKnob;
    private javaxt.io.Image dialogueSpeedSlider;
    private javaxt.io.Image dialogueSpeedKnob;

    // PLACEMENT
    private int dialogueX = 100;
    private int dialogueTransparencyY = 220;
    private int dialogueSpeedY = dialogueTransparencyY + 80;
    private int dialogueTransparencyKnobX;
    private int dialogueTransparencyKnobY;
    private int dialogueSpeedKnobX;
    private int dialogueSpeedKnobY;
    private int backButtonX = 1100;
    private int backButtonY = 600;
    private boolean dialogueTransparencyMove = false;
    private boolean dialogueSpeedMove = false;

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private Handlerclass handler = new Handlerclass();
    private boolean initStage = true;

    private boolean backButtonUI = false;

    public int dialogueTransparency = 70;
    public int dialogueSpeed = 50;

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public ConfigPane() {
        try {
            // Load needed images
            backgroundPicture = ImageIO.read(new File(RESOURCE_PATH + "options-background.png"));

            // Convert images
            back_button = new javaxt.io.Image(RESOURCE_PATH + "config_back_button.png");
            sliderTrack = new javaxt.io.Image(RESOURCE_PATH + "white.png");
            dialogueTransparencyKnob = new javaxt.io.Image(RESOURCE_PATH + "white.png");
            dialogueTransparencySlider = new javaxt.io.Image(RESOURCE_PATH + "white.png");
            dialogueSpeedKnob = new javaxt.io.Image(RESOURCE_PATH + "white.png");
            dialogueSpeedSlider = new javaxt.io.Image(RESOURCE_PATH + "white.png");

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
            if(!dialogueTransparencyMove) {
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

        if(initStage) {
            // TODO: FIX THIS. SHIT IMPLEMENTATION AS TEMP (CHANGING EACH PIXEL WTF)
            for(int a=0; a <= dialogueTransparencyKnob.getWidth(); a++) {
                for(int b=0; b <= dialogueTransparencySlider.getHeight(); b++) {
                    dialogueTransparencySlider.setColor(a, b, Color.CYAN);
                    dialogueTransparencyKnob.setColor(a, b, Color.ORANGE);
                    dialogueSpeedSlider.setColor(a, b, Color.CYAN);
                    dialogueSpeedKnob.setColor(a, b, Color.ORANGE);
                }
            }

            // SLIDER TRACK
            sliderTrack.resize(200, 20, false);

            // dialogueTransparencySlider.setBackgroundColor(90, 90, 90);
            dialogueTransparencyKnob.resize(sliderTrack.getHeight(), sliderTrack.getHeight(), false);
            dialogueTransparencyKnobX = dialogueX + (int)(sliderTrack.getWidth() * (dialogueTransparency/100.0));
            dialogueTransparencyKnobY = dialogueTransparencyY;

            dialogueSpeedKnob.resize(sliderTrack.getHeight(), sliderTrack.getHeight(), false);
            dialogueSpeedKnobX = dialogueX + (int)(sliderTrack.getWidth() * (dialogueSpeed/100.0));
            dialogueSpeedKnobY = dialogueTransparencyY + 80;

            initStage = false;
        }

        // DISABLES CONFIGPANE BG WHEN USING SLIDER
        if(!dialogueTransparencyMove) {
            // BACK BUTTON
            javaxt.io.Image tempBackButton = back_button.copy();
            if (backButtonUI) {
                tempBackButton.setOpacity(100);
            } else {
                tempBackButton.setOpacity(20);
            }
            g.drawImage(tempBackButton.getBufferedImage(), (backButtonX - (getPreferredSize(tempBackButton.getBufferedImage()).width / 2)), (backButtonY - (getPreferredSize(tempBackButton.getBufferedImage()).height / 2)), getPreferredSize(tempBackButton.getBufferedImage()).width, getPreferredSize(tempBackButton.getBufferedImage()).height, this);
        }
        else {
            // DialogueBox
            javaxt.io.Image dialogueBox = new javaxt.io.Image(RESOURCE_PATH+"dialogbar.png");
            dialogueBox.resize((int)(dialogueBox.getWidth() * 0.9), (int)(dialogueBox.getHeight() * 0.9));
            dialogueBox.setOpacity(dialogueTransparency);
            g.drawImage(dialogueBox.getBufferedImage(),WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2, WaifuBrew.getInstance().getRes()[1].y - dialogueBox.getHeight() - (WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2),this);
        }


        // DRAW ELEMENTS
        // TODO: Calculate the following (x, y) to relative to resolution (current defaults to mid way of knob)
        // +20 on width so knob fits
        g.drawImage(sliderTrack.getBufferedImage(), dialogueX, dialogueTransparencyY, sliderTrack.getWidth() + 20, sliderTrack.getHeight(), this);
        g.drawImage(dialogueTransparencySlider.getBufferedImage(), dialogueX, dialogueTransparencyY, (int)(sliderTrack.getWidth() * (dialogueTransparency/100.0)) + (int)(dialogueTransparencyKnob.getWidth() * 0.5), sliderTrack.getHeight(), this);
        g.drawImage(dialogueTransparencyKnob.getBufferedImage(), dialogueTransparencyKnobX, dialogueTransparencyKnobY, this);

        g.drawImage(sliderTrack.getBufferedImage(), dialogueX, dialogueSpeedY, sliderTrack.getWidth() + 20, sliderTrack.getHeight(), this);
        g.drawImage(dialogueSpeedSlider.getBufferedImage(), dialogueX, dialogueSpeedY, (int)(sliderTrack.getWidth() * (dialogueSpeed/100.0)) + (int)(dialogueSpeedKnob.getWidth() * 0.5), sliderTrack.getHeight(), this);
        g.drawImage(dialogueSpeedKnob.getBufferedImage(), dialogueSpeedKnobX, dialogueSpeedKnobY, this);

        g.drawString("Diologue Bar Transparency", dialogueX, dialogueTransparencyY - 20);
        g.drawString("Diologue Text Speed", dialogueX, dialogueSpeedY - 20);

        repaint();
    }

    private class Handlerclass implements MouseListener, MouseMotionListener {

        // TODO: FINE TUNE THE KNOBS SO THAT IT KEEPS THE ORIGINAL POSITION OF CLICK POINT OF SQUARE (CURRENT IS MIDDLE)
        public void mousePressed (MouseEvent event) {
            if(event.getX() >= dialogueTransparencyKnobX && event.getX() <= dialogueTransparencyKnobX + dialogueTransparencyKnob.getWidth() && event.getY() >= dialogueTransparencyKnobY && event.getY() <= dialogueTransparencyKnobY + dialogueTransparencyKnob.getHeight()) {
                dialogueTransparencyMove = true;
            }
            if(event.getX() >= dialogueSpeedKnobX && event.getX() <= dialogueSpeedKnobX + dialogueSpeedKnob.getWidth() && event.getY() >= dialogueSpeedKnobY && event.getY() <= dialogueSpeedKnobY + dialogueSpeedKnob.getHeight()) {
                dialogueSpeedMove = true;
            }
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
            if(dialogueTransparencyMove) {
                if(event.getX() >= dialogueX + (int)(dialogueTransparencyKnob.getWidth() * 0.5) && event.getX() <= dialogueX + dialogueTransparencySlider.getWidth() + (int)(dialogueTransparencyKnob.getWidth() * 0.5)) {
                    dialogueTransparencyKnobX = (int) (event.getX() - (dialogueTransparencyKnob.getWidth() * 0.5));
                    dialogueTransparency = (int)(((dialogueTransparencyKnobX - dialogueX) / (double)sliderTrack.getWidth()) * 100);
                }
            }

            if(dialogueSpeedMove) {
                if(event.getX() >= dialogueX + (int)(dialogueSpeedKnob.getWidth() * 0.5) && event.getX() <= dialogueX + dialogueSpeedSlider.getWidth() + (int)(dialogueSpeedKnob.getWidth() * 0.5)) {
                    dialogueSpeedKnobX = (int) (event.getX() - (dialogueSpeedKnob.getWidth() * 0.5));
                    dialogueSpeed = (int)(((dialogueSpeedKnobX - dialogueX) / (double)sliderTrack.getWidth()) * 100);
                }
            }
        }
        public void mouseClicked (MouseEvent event) {

            if(event.getX() >= (backButtonX - (getPreferredSize(back_button.getBufferedImage()).width / 2)) && event.getY() >= (backButtonY - (getPreferredSize(back_button.getBufferedImage()).height / 2)) && event.getX() <= ((backButtonX - (getPreferredSize(back_button.getBufferedImage()).width / 2)) + back_button.getWidth()) && event.getY() <= ((backButtonY - (getPreferredSize(back_button.getBufferedImage()).height / 2)) + back_button.getHeight())) {
                initStage = true;
                WaifuBrew.getInstance().getGUIInstance().setStage(0);
                WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
            }

        }
        public void mouseEntered (MouseEvent event) {

        }
        public void mouseReleased (MouseEvent event) {
            if(dialogueTransparencyMove) {
                dialogueTransparencyMove = false;
                WaifuBrew.getInstance().setDialogueTransparency(dialogueTransparency);
                // System.out.println("SET: "+dialogueTransparency);
            }

            if(dialogueSpeedMove) {
                dialogueSpeedMove = false;
                // WaifuBrew.getInstance().setdialogueSpeed(dialogueSpeed);
                // System.out.println("SET: "+dialogueSpeed);
            }
        }
        public void mouseExited (MouseEvent event) {
            // Fooling around
            // setFocusableWindowState(false);
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
