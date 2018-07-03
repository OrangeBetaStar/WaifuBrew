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

    // PLACEMENT
    private int dialogueTransparencyX = 100;
    private int dialogueTransparencyY = 220;
    private int dialogueTransparencyKnobX;
    private int dialogueTransparencyKnobY;
    private int backButtonX = 1100;
    private int backButtonY = 600;
    private boolean dialogueTransparencyMove = false;

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private Handlerclass handler = new Handlerclass();
    private boolean initStage = true;

    private boolean backButtonUI = false;

    private int dialogueTransparency = 70;

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
            // Will run only once to save resource

            // SHIT IMPLEMENTATION AS TEMP
            for(int a=0; a <= dialogueTransparencyKnob.getWidth(); a++) {
                for(int b=0; b <= dialogueTransparencySlider.getHeight(); b++) {
                    dialogueTransparencySlider.setColor(a, b, Color.CYAN);
                    dialogueTransparencyKnob.setColor(a, b, Color.ORANGE);
                }
            }

            // SLIDER TRACK
            sliderTrack.resize(200, 20, false);

            // dialogueTransparencySlider.setBackgroundColor(90, 90, 90);
            dialogueTransparencyKnob.resize(sliderTrack.getHeight(), sliderTrack.getHeight(), false);
            dialogueTransparencyKnobX = dialogueTransparencyX + (int)(sliderTrack.getWidth() * (dialogueTransparency/100.0));
            dialogueTransparencyKnobY = dialogueTransparencyY;

            initStage = false;
        }


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
            dialogueBox.setOpacity(dialogueTransparency);
            g.drawImage(dialogueBox.getBufferedImage(),WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2, WaifuBrew.getInstance().getRes()[1].y - dialogueBox.getHeight() - (WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2),this);

        }


        // DRAW ELEMENTS
        // TODO: Calcuate the following (x, y) to relative to resolution
        g.drawString("Diologue Bar Transparency", 100, 180);
        // +20 on width so knob fits
        g.drawImage(sliderTrack.getBufferedImage(), dialogueTransparencyX, dialogueTransparencyY, sliderTrack.getWidth() + 20, sliderTrack.getHeight(), this);
        g.drawImage(dialogueTransparencySlider.getBufferedImage(), dialogueTransparencyX, dialogueTransparencyY, (int)(sliderTrack.getWidth() * (dialogueTransparency/100.0)) + (int)(dialogueTransparencyKnob.getWidth() * 0.5), sliderTrack.getHeight(), this);
        g.drawImage(dialogueTransparencyKnob.getBufferedImage(), dialogueTransparencyKnobX, dialogueTransparencyKnobY, this);



        repaint();
    }

    private class Handlerclass implements MouseListener, MouseMotionListener {

         // TODO: FINE TUNE THE KNOBS SO THAT IT KEEPS THE ORIGINAL POSITION OF CLICK POINT OF SQUARE (CURRENT IS MIDDLE)
        public void mousePressed (MouseEvent event) {
            if(event.getX() >= dialogueTransparencyKnobX && event.getX() <= dialogueTransparencyKnobX + dialogueTransparencyKnob.getWidth() && event.getY() >= dialogueTransparencyKnobY && event.getY() <= dialogueTransparencyKnobY + dialogueTransparencyKnob.getHeight()) {
                dialogueTransparencyMove = true;
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
                if(event.getX() >= dialogueTransparencyX + (int)(dialogueTransparencyKnob.getWidth() * 0.5) && event.getX() <= dialogueTransparencyX + dialogueTransparencySlider.getWidth() + (int)(dialogueTransparencyKnob.getWidth() * 0.5)) {
                    dialogueTransparencyKnobX = (int) (event.getX() - (dialogueTransparencyKnob.getWidth() * 0.5));
                    dialogueTransparency = (int)(((dialogueTransparencyKnobX - dialogueTransparencyX) / (double)sliderTrack.getWidth()) * 100);
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
            dialogueTransparencyMove = false;
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
