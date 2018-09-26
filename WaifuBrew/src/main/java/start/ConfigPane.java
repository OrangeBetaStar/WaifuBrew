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

    // Apart Y: 80; Start X: 100; Start Y: 220;
    // TODO: THIS WILL BE CHANGED TOO
    private int backButtonX = 1100;
    private int backButtonY = 600;
    private Timer stringTimer;
    private boolean stop = false;
    private String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();

    // [0] - slider_transparency
    // [1] - slider_speed
    // [2] - slider-fontSize
    private CustomSlider[] settingSliders = new CustomSlider[3];
    private CustomButton backButton;
    private CustomButton saveButton;
    private CustomSwitch autoDialog;
    private NoticeBox saveDialogue;

    private Handlerclass handler = new Handlerclass();

    private String a = "The dialogue would look like this!";
    private String tempString = "";
    private Font activeFont;
    private Font configPaneFont;
    private BufferedInputStream myStream;
    private int fontSize = 24;

    // [1] is resolution of program window
    private Point windowSize = WaifuBrew.getInstance().getRes()[1];

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public ConfigPane() {
        try {
            init();

            backgroundPicture = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BACKGROUND, "config"));
            dialogueBox = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "dialogbar"));
            backButton = new CustomButton(backButtonX, backButtonY, "config_back_button", Origin.MIDDLE_CENTRE, 0, true);
            saveButton = new CustomButton(backButtonX, backButtonY - 100, "config_save_button", Origin.MIDDLE_CENTRE, 0, false);

            saveDialogue = new NoticeBox("Would you like to save the current settings?", "config_save_button", "config_back_button", false, false);

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

            settingSliders[0] = new CustomSlider((windowSize.x / 10), (windowSize.y / 6) * 2, WaifuBrew.getInstance().getDialogueTransparency());
            settingSliders[1] = new CustomSlider((windowSize.x / 10), (windowSize.y / 6) * 3, WaifuBrew.getInstance().getDialogueSpeed());
            // ((settingSliders[2].getLevel() / 2) + 10)
            settingSliders[2] = new CustomSlider((windowSize.x / 10), (windowSize.y / 6) * 4, (WaifuBrew.getInstance().getFontSize() - 10) * 2);
            autoDialog = new CustomSwitch((windowSize.x / 10) * 3, (windowSize.y / 6) * 5, false, true);

            // Handlers listening to mouse like DOGS
            addMouseListener(handler);
            addMouseMotionListener(handler);
            addMouseListener(saveButton.retrieveMouseHandler());
            addMouseMotionListener(saveButton.retrieveMouseHandler());
            addMouseListener(backButton.retrieveMouseHandler());
            addMouseMotionListener(backButton.retrieveMouseHandler());
            addMouseListener(autoDialog.retrieveMouseHandler());
            addMouseMotionListener(autoDialog.retrieveMouseHandler());
            addMouseListener(saveDialogue.retrieveMouseHandler());
            addMouseMotionListener(saveDialogue.retrieveMouseHandler());

            // MouseListners for NoticeBox in ConfigPage
            for(int noticBoxButtonIndix = 0; noticBoxButtonIndix < saveDialogue.getButton().length; noticBoxButtonIndix++) {
                addMouseListener(saveDialogue.getButton()[noticBoxButtonIndix].retrieveMouseHandler());
                addMouseMotionListener(saveDialogue.getButton()[noticBoxButtonIndix].retrieveMouseHandler());
            }

            // Each of the slider's mouselisteners
            for(int applier = 0; applier < settingSliders.length; applier++) {
                addMouseListener(settingSliders[applier].retrieveMouseHandler());
                addMouseMotionListener(settingSliders[applier].retrieveMouseHandler());
            }



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
                            stringTimer.setDelay(100 - settingSliders[1].getLevel()); // Text speed is inverted.
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
        if(settingSliders[2].isSliderActive()) {
            // Configure font preview
            try {
                // For real time preview later when font change is implemented
                myStream = new BufferedInputStream(new FileInputStream(RESOURCE_PATH + WaifuBrew.getInstance().getFontName() + ".ttf"));
                fontSize = (settingSliders[2].getLevel() / 2) + 10; // This equation seems most appropriate
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
            if(!settingSliders[0].isSliderActive() && !settingSliders[1].isSliderActive() && !settingSliders[2].isSliderActive()) {
                g.drawImage(backgroundPicture.getBufferedImage(), (windowSize.x / 2) - (backgroundPicture.getWidth() / 2), (windowSize.y / 2) - (backgroundPicture.getHeight() / 2), this);
            }
        }

        // DISABLES CONFIGPANE BG WHEN USING SLIDER & PREVIEW OF TRANSPARENCY
        if(!settingSliders[0].isSliderActive() && !settingSliders[1].isSliderActive() && !settingSliders[2].isSliderActive()) {

            if (stringTimer.isRunning()) {
                stringTimer.stop();
            }

            g.setFont(configPaneFont);
            g.setColor(new Color(0, 0, 0));
            g.drawString("Diologue Bar Transparency", settingSliders[0].getX(), settingSliders[0].getY() - ((windowSize.x / 10) / 3));
            g.drawString("Diologue Text Speed", settingSliders[1].getX(), settingSliders[1].getY() - ((windowSize.x / 10) / 3));
            g.drawString("Dialog Text Size", settingSliders[2].getX(), settingSliders[2].getY() - ((windowSize.x / 10) / 3));
            g.drawString("Auto dialog advance", (windowSize.x / 10), (windowSize.y / 6) * 5 - ((windowSize.x / 10) / 3));

            if(!saveDialogue.isActive()) {
                backButton.paintComponent(g);
                saveButton.paintComponent(g);
            }
            autoDialog.paintComponent(g);

            for (int applier = 0; applier < settingSliders.length; applier++) {
                settingSliders[applier].paintComponent(g);
            }
        }
        else {
            // DialogueBox
            tempDialogueBox = dialogueBox.copy();
            tempDialogueBox.setOpacity(settingSliders[0].getLevel());
            g.drawImage(tempDialogueBox.getBufferedImage(),WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2, WaifuBrew.getInstance().getRes()[1].y - dialogueBox.getHeight() - (WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2),this);
            stringTimer.start();
            if(!tempString.equals("")) {
                g.setFont(activeFont);
                g.setColor(new Color(0,0,0));
                g.drawString(tempString, 150, 550);
            }
            for (int applier = 0; applier < settingSliders.length; applier++) {
                if(settingSliders[applier].isSliderActive()) {
                    settingSliders[applier].paintComponent(g);
                }
            }
        }
        if(saveDialogue.isActive())
        saveDialogue.paintComponent(g);
    }

    private boolean checkLockInSetting() {
        if(settingSliders[0].getLevel() != WaifuBrew.getInstance().getDialogueTransparency()) {
            /*
            System.out.println("Transparency Level " + settingSliders[0].getLevel());
            System.out.println("Transparency Settings " + WaifuBrew.getInstance().getDialogueTransparency());
            */
            return false;
        }
        if(settingSliders[1].getLevel() != WaifuBrew.getInstance().getDialogueSpeed()) {
            /*
            System.out.println("Speed Level " + settingSliders[1].getLevel());
            System.out.println("Speed Settings " + WaifuBrew.getInstance().getDialogueSpeed());
            */
            return false;
        }
        if(((settingSliders[2].getLevel() / 2) + 10) != WaifuBrew.getInstance().getFontSize()) {
            /*
            System.out.println("FontSize Level " + (settingSliders[2].getLevel() / 2) + 10);
            System.out.println("FontSize Settings " + WaifuBrew.getInstance().getFontSize());
            */
            return false;
        }
        return true;
    }


    private class Handlerclass extends MasterHandlerClass {

        public void mouseClicked(MouseEvent event) {
            // Disable original back and save button for noticeBox buttons.
            if(!saveDialogue.isActive()) {
                if (event.getX() >= backButton.getX() - backButton.getWidth() / 2 && event.getY() >= backButton.getY() - backButton.getHeight() / 2 && event.getX() <= backButton.getX() + backButton.getWidth() / 2 && event.getY() <= backButton.getY() + backButton.getHeight() / 2) {
                    if (checkLockInSetting()) {
                        WaifuBrew.getInstance().setStage(0);
                        WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                    } else {
                        // Save setting first!
                        saveDialogue.setActive(true);
                    }
                }
                if (event.getX() >= saveButton.getX() - saveButton.getWidth() / 2 && event.getY() >= saveButton.getY() - saveButton.getHeight() / 2 && event.getX() <= saveButton.getX() + saveButton.getWidth() / 2 && event.getY() <= saveButton.getY() + saveButton.getHeight() / 2) {
                    WaifuBrew.getInstance().setDialogueTransparency(settingSliders[0].getLevel());
                    WaifuBrew.getInstance().setDialogueSpeed(settingSliders[1].getLevel());
                    WaifuBrew.getInstance().setFontSize((settingSliders[2].getLevel() / 2) + 10);

                    // System.out.println("ConfigPane.Handler: Set auto dia to: " + autoDialog.getValue());
                    WaifuBrew.getInstance().setAutoAdvancer(autoDialog.getValue());
                    WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                }
            }
            else {
                for(int noticBoxButtonIndix = 0; noticBoxButtonIndix < saveDialogue.getButton().length; noticBoxButtonIndix++) {
                    if (event.getX() > saveDialogue.getButton()[noticBoxButtonIndix].getAbsoluteX() &&
                        event.getX() < saveDialogue.getButton()[noticBoxButtonIndix].getAbsoluteX() + saveDialogue.getButton()[noticBoxButtonIndix].getWidth() &&
                        event.getY() > saveDialogue.getButton()[noticBoxButtonIndix].getAbsoluteY() &&
                        event.getY() < saveDialogue.getButton()[noticBoxButtonIndix].getAbsoluteY() + saveDialogue.getButton()[noticBoxButtonIndix].getHeight()) {

                        if(noticBoxButtonIndix == 0) {
                            // Save is clicked

                            // Save all the settings.
                            WaifuBrew.getInstance().setDialogueTransparency(settingSliders[0].getLevel());
                            WaifuBrew.getInstance().setDialogueSpeed(settingSliders[1].getLevel());
                            WaifuBrew.getInstance().setFontSize((settingSliders[2].getLevel() / 2) + 10);
                            WaifuBrew.getInstance().setAutoAdvancer(autoDialog.getValue());

                            // Disable NoticeBox
                            saveDialogue.setActive(false);

                            // Go back to Main screen.
                            WaifuBrew.getInstance().setStage(0);
                            WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                        }
                        else if(noticBoxButtonIndix == 1) {
                            // Back is clicked

                            // Disable NoticeBox
                            saveDialogue.setActive(false);
                        }
                    }
                }
            }
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
            myStream = new BufferedInputStream(new FileInputStream(RESOURCE_PATH + WaifuBrew.getInstance().getFontName() + ".ttf"));
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
