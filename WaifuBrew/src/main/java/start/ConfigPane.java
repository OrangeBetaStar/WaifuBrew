package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SuppressWarnings("serial")
public class ConfigPane extends JPanel implements ActionListener {

    // LOAD IMAGE
    private javaxt.io.Image backgroundPicture;
    private javaxt.io.Image dialogueBox; // So it doesn't use HDD every time and kill performance
    private javaxt.io.Image tempDialogueBox; // Preview

    // PLACEMENT TODO: (Probably change to relative later)
    private int dialogueX = 100;
    private int dialogueTransparencyY = 220;
    private int dialogueSpeedY = dialogueTransparencyY + 80;
    private int dialogueFontSizeY = dialogueTransparencyY + 160 + 40;
    private int backButtonX = 1100;
    private int backButtonY = 600;
    private Timer stringTimer;
    private boolean stop = false;
    private String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();

    private CustomButton backButon;
    private CustomSlider slider_transparency;
    private CustomSlider slider_speed;
    private CustomSwitch auto_dialog;
    private CustomSlider slider_fontSize;

    private Handlerclass handler = new Handlerclass();

    public int dialogueTransparency = WaifuBrew.getInstance().getDialogueTransparency();
    public int dialogueSpeed = WaifuBrew.getInstance().getDialogueSpeed();
    private String a = "The dialogue would look like this!"; // "Your waifu isn't real."; // Test String.
    private String tempString = "";
    private Font activeFont;
    private Font configPaneFont;
    private BufferedInputStream myStream;
    private int fontSize = 24;

    private Point windowSize = WaifuBrew.getInstance().getRes()[1];

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public ConfigPane() {
        try {
            init();

            backgroundPicture = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BACKGROUND, "config"));
            backButon = new CustomButton(backButtonX, backButtonY, "config_back_button", true, 0, true);
            dialogueBox = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "dialogbar"));

            // myStream = new BufferedInputStream(new FileInputStream(RESOURCE_PATH + "Halogen.ttf"));

            // Pre-scale
            if(backgroundPicture.getWidth() < windowSize.x || backgroundPicture.getHeight() < windowSize.y) {
                if(((double)windowSize.x / backgroundPicture.getWidth()) * backgroundPicture.getHeight() < windowSize.y) {
                    backgroundPicture.resize(((int)(((double)windowSize.y / backgroundPicture.getHeight()) * backgroundPicture.getWidth())), (int)(((double)windowSize.y / backgroundPicture.getHeight()) * backgroundPicture.getHeight()));
                }
                else {
                    backgroundPicture.resize(((int)(((double)windowSize.x / backgroundPicture.getWidth()) * backgroundPicture.getWidth())), (int)(((double)windowSize.x / backgroundPicture.getWidth()) * backgroundPicture.getHeight()));
                }
            }
            dialogueBox.resize((int)(dialogueBox.getWidth() * 0.9), (int)(dialogueBox.getHeight() * 0.9));
            auto_dialog = new CustomSwitch(dialogueX + 200, dialogueSpeedY + 60 - 10, false, true);

            // Testing CUSTOM SLIDER
            slider_transparency = new CustomSlider(dialogueX, dialogueTransparencyY, dialogueTransparency);
            slider_speed = new CustomSlider(dialogueX,dialogueSpeedY,dialogueSpeed);
            slider_fontSize = new CustomSlider(dialogueX, dialogueFontSizeY, fontSize);


            // Handlers listening to mouse like DOGS
            addMouseListener(handler);
            addMouseMotionListener(handler);
            addMouseListener(slider_transparency.retrieveMouseHandler());
            addMouseMotionListener(slider_transparency.retrieveMouseHandler());
            addMouseListener(slider_speed.retrieveMouseHandler());
            addMouseMotionListener(slider_speed.retrieveMouseHandler());
            addMouseListener(backButon.retrieveMouseHandler());
            addMouseMotionListener(backButon.retrieveMouseHandler());

            addMouseListener(auto_dialog.retrieveMouseHandler());
            addMouseMotionListener(auto_dialog.retrieveMouseHandler());
            addMouseListener(slider_fontSize.retrieveMouseHandler());
            addMouseMotionListener(slider_fontSize.retrieveMouseHandler());


            // Builds character into sentence one by one. Using timers are bit meh since it needs to finish to change duration.
            stringTimer = new Timer((WaifuBrew.getInstance().getDialogueSpeed()), new ActionListener() {
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


        } catch (Exception e) {
            System.out.println("File failure in Config class");
            e.printStackTrace(); // Wall of error
            System.exit(-1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(slider_fontSize.isSliderActive()) {
            // Configure font preview
            try {
                // TODO: This not a good implementation
                myStream = new BufferedInputStream(new FileInputStream(RESOURCE_PATH + "Halogen.ttf"));
                fontSize = (slider_fontSize.getLevel() / 2) + 10; // This equation seems most appropriate
                Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
                activeFont = ttfBase.deriveFont(Font.PLAIN, fontSize);
            } catch (FontFormatException ex) {
                ex.printStackTrace();
                System.err.println(myStream.toString() + " not loaded.  Using serif font.");
                activeFont = new Font("serif", Font.PLAIN, fontSize);
            } catch (FileNotFoundException ex) {
                System.out.println("FileNotFoundException in ConfigPane.init()");
                System.err.println(myStream.toString() + " not loaded.  Using serif font.");
                activeFont = new Font("serif", Font.PLAIN, fontSize);
            } catch (IOException ex) {
                System.out.println("IOException in ConfigPane.init()");
                System.err.println(myStream.toString() + " not loaded.  Using serif font.");
                activeFont = new Font("serif", Font.PLAIN, fontSize);
            }
        }
        if (backgroundPicture != null) {
            // I want to centre the image that is 960:640 to widescreen format, but do not want to stretch. I will zoom in.
            if(!slider_transparency.isSliderActive() && !slider_speed.isSliderActive() && !slider_fontSize.isSliderActive()) {
                g.drawImage(backgroundPicture.getBufferedImage(), (windowSize.x / 2) - (backgroundPicture.getWidth() / 2), (windowSize.y / 2) - (backgroundPicture.getHeight() / 2), this);
            }
        }

        // DISABLES CONFIGPANE BG WHEN USING SLIDER & PREVIEW OF TRANSPARENCY
        if(!slider_transparency.isSliderActive() && !slider_speed.isSliderActive() && !slider_fontSize.isSliderActive()) {

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
            if(!tempString.equals("")) {
                g.setFont(activeFont);
                g.setColor(new Color(0,0,0));
                g.drawString(tempString, 150, 550);
            }
        }

        // TODO: Perhaps implement title for CustomSlider
        // TODO: implement configPaneFont
        g.setFont(configPaneFont);
        g.setColor(new Color(0,0,0));
        g.drawString("Diologue Bar Transparency", dialogueX, dialogueTransparencyY);
        g.drawString("Diologue Text Speed", dialogueX, dialogueSpeedY);
        g.drawString("Auto dialog advance", dialogueX, dialogueSpeedY + 60);
        g.drawString("Dialog Text Size", dialogueX, dialogueFontSizeY - 20);

        backButon.paintComponent(g);
        slider_transparency.paintComponent(g);
        slider_speed.paintComponent(g);
        auto_dialog.paintComponent(g);
        slider_fontSize.paintComponent(g);
    }

    private class Handlerclass implements MouseListener, MouseMotionListener {

        // TODO: FINE TUNE THE KNOBS SO THAT IT KEEPS THE ORIGINAL POSITION OF CLICK POINT OF SQUARE (CURRENT IS MIDDLE)
        public void mousePressed (MouseEvent event) {
        }
        public void mouseMoved (MouseEvent event) {
        }
        public void mouseDragged (MouseEvent event) {
        }
        public void mouseClicked (MouseEvent event) {
            if(event.getX() >= backButtonX - backButon.getWidth()/2 && event.getY() >= backButtonY - backButon.getHeight()/2 && event.getX() <= backButtonX + backButon.getWidth()/2 && event.getY() <= backButtonY + backButon.getHeight()/2) {
                WaifuBrew.getInstance().setStage(0);
                WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
            }
            System.out.println("Mouseclicked from ConfigPane"); // to check if handler is active outside of settings
        }
        public void mouseEntered (MouseEvent event) {

        }
        public void mouseReleased (MouseEvent event) {
            // TODO: Find better way to implement this.

            if(WaifuBrew.getInstance().getStage() == 2) { // If it is still configPane... perhaps save setting when back button?
                WaifuBrew.getInstance().setDialogueTransparency(slider_transparency.getLevel());
                WaifuBrew.getInstance().setDialogueSpeed(slider_speed.getLevel());
                System.out.println("ConfigPane.Handler: Set auto dia to: " + auto_dialog.getValue());
                WaifuBrew.getInstance().setAutoAdvancer(auto_dialog.getValue());
                WaifuBrew.getInstance().setFontSize((slider_fontSize.getLevel() / 2) + 10);
            }
        }
        public void mouseExited (MouseEvent event) {

        }
    }

    public void init(){
        // TODO: I wonder if I use this lambda
        Timer t = new Timer((int)(1000/WaifuBrew.getInstance().getFrameRate()), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!stop) {
                    repaint();
                } else {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        t.setRepeats(true);
        t.setDelay((int)(1000/WaifuBrew.getInstance().getFrameRate()));
        t.start();

        // Configure font preview
        try {
            myStream = new BufferedInputStream(new FileInputStream(RESOURCE_PATH + "Halogen.ttf"));
            Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            activeFont = ttfBase.deriveFont(Font.PLAIN, fontSize);
            configPaneFont = ttfBase.deriveFont(Font.PLAIN, 24);
        } catch (FontFormatException ex) {
            ex.printStackTrace();
            System.err.println(myStream.toString() + " not loaded.  Using serif font.");
            activeFont = new Font("serif", Font.PLAIN, fontSize);
            configPaneFont = new Font("serif", Font.PLAIN, 24);
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException in ConfigPane.init()");
            System.err.println(myStream.toString() + " not loaded.  Using serif font.");
            activeFont = new Font("serif", Font.PLAIN, fontSize);
            configPaneFont = new Font("serif", Font.PLAIN, 24);
        } catch (IOException ex) {
            System.out.println("IOException in ConfigPane.init()");
            System.err.println(myStream.toString() + " not loaded.  Using serif font.");
            activeFont = new Font("serif", Font.PLAIN, fontSize);
            configPaneFont = new Font("serif", Font.PLAIN, 24);
        }
    }
}
