package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    private HashMap<String, CustomSlider> settingSlidersMap = new HashMap<>(3);
    private HashMap<String, CustomButton> settingButtonsMap = new HashMap<>(3);
//    private CustomButton[] settingButton = new CustomButton[3];
    private CustomSwitch autoDialog;
    private NoticeBox saveDialogue;

    private Handlerclass handler = new Handlerclass();

    private String a = "The dialogue would look like this!";
    private String tempString = "";
    private Font activeFont;
    private Font configPaneFont;
    private BufferedInputStream myStream;
    private int fontSize = 24;

    int upperBound = 90;

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
            this.settingButtonsMap.put("back", new CustomButton(backButtonX, (windowSize.y / 6) * 5, "config_back_button", Origin.MIDDLE_CENTRE, 0, true));
            this.settingButtonsMap.put("save", new CustomButton(backButtonX, (windowSize.y / 6) * 4, "config_save_button", Origin.MIDDLE_CENTRE, 0, false));
            this.settingButtonsMap.put("reset", new CustomButton(backButtonX, (windowSize.y / 6) * 3, "config_reset_button", Origin.MIDDLE_CENTRE, 0, true));

            saveDialogue = new NoticeBox("Would you like to save the current settings?", "config_save_button", "config_savenot_button", false, true);

            // Pre-scale
            if (backgroundPicture.getWidth() < windowSize.x || backgroundPicture.getHeight() < windowSize.y) {
                if (((double) windowSize.x / backgroundPicture.getWidth()) * backgroundPicture.getHeight() < windowSize.y) {
                    backgroundPicture.resize(((int) (((double) windowSize.y / backgroundPicture.getHeight()) * backgroundPicture.getWidth())), (int) (((double) windowSize.y / backgroundPicture.getHeight()) * backgroundPicture.getHeight()));
                } else {
                    backgroundPicture.resize(((int) (((double) windowSize.x / backgroundPicture.getWidth()) * backgroundPicture.getWidth())), (int) (((double) windowSize.x / backgroundPicture.getWidth()) * backgroundPicture.getHeight()));
                }
            }
            dialogueBox.resize((int) (dialogueBox.getWidth() * 0.9), (int) (dialogueBox.getHeight() * 0.9));

            this.settingSlidersMap.put("barTransparency", new CustomSlider((windowSize.x / 10), (windowSize.y / 6) * 2, WaifuBrew.getInstance().getDialogueTransparency()));
            this.settingSlidersMap.put("textSpeed", new CustomSlider((windowSize.x / 10), (windowSize.y / 6) * 3, WaifuBrew.getInstance().getDialogueSpeed()));
            this.settingSlidersMap.put("textSize", new CustomSlider((windowSize.x / 10), (windowSize.y / 6) * 4, (WaifuBrew.getInstance().getFontSize() - 10) * 2));
            autoDialog = new CustomSwitch((windowSize.x / 10) * 3, (windowSize.y / 6) * 5, false, true);

            // Handlers listening to mouse like DOGS
            addMouseListener(handler);
            addMouseMotionListener(handler);
            addMouseListener(autoDialog.retrieveMouseHandler());
            addMouseMotionListener(autoDialog.retrieveMouseHandler());
            addMouseListener(saveDialogue.retrieveMouseHandler());
            addMouseMotionListener(saveDialogue.retrieveMouseHandler());

            // MouseListners for NoticeBox in ConfigPage
            for (int noticBoxButtonIndix = 0; noticBoxButtonIndix < saveDialogue.getButton().length; noticBoxButtonIndix++) {
                addMouseListener(saveDialogue.getButton()[noticBoxButtonIndix].retrieveMouseHandler());
                addMouseMotionListener(saveDialogue.getButton()[noticBoxButtonIndix].retrieveMouseHandler());
            }

            // Each of the slider's mouselisteners
            for (Map.Entry<String, CustomSlider> entry : this.settingSlidersMap.entrySet()) {
                CustomSlider slider = entry.getValue();
                addMouseListener(slider.retrieveMouseHandler());
                addMouseMotionListener(slider.retrieveMouseHandler());
            }

            // Each of the button's mouselisteners
            for (Map.Entry<String, CustomButton> entry : this.settingButtonsMap.entrySet()) {
                CustomButton button = entry.getValue();
                addMouseListener(button.retrieveMouseHandler());
                addMouseMotionListener(button.retrieveMouseHandler());
            }

            // Builds character into sentence one by one. Using timers are bit meh since it needs to finish to change duration.
            stringTimer = new Timer((WaifuBrew.getInstance().getDialogueSpeed()), new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!a.isEmpty()) {
                        if (tempString.length() != a.length()) {
                            tempString = tempString + a.charAt(tempString.length());
                        } else {
                            tempString = a.substring(0, 1);
                            stringTimer.stop();
                            stringTimer.setDelay(100 - settingSlidersMap.get("textSpeed").getLevel()); // Text speed is inverted.
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
        if (this.settingSlidersMap.get("textSize").isSliderActive()) {
            // Configure font preview
            try {
                // For real time preview later when font change is implemented
                myStream = new BufferedInputStream(new FileInputStream(RESOURCE_PATH + WaifuBrew.getInstance().getFontName() + ".ttf"));
                fontSize = (this.settingSlidersMap.get("textSize").getLevel() / 2) + 10; // This equation seems most appropriate
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
            if (
                    !this.settingSlidersMap.get("barTransparency").isSliderActive() &&
                    !this.settingSlidersMap.get("textSpeed").isSliderActive() &&
                    !this.settingSlidersMap.get("textSize").isSliderActive()
            ) {
                g.drawImage(backgroundPicture.getBufferedImage(), (windowSize.x / 2) - (backgroundPicture.getWidth() / 2), (windowSize.y / 2) - (backgroundPicture.getHeight() / 2), this);
            }
        }

        // DISABLES CONFIGPANE BG WHEN USING SLIDER & PREVIEW OF TRANSPARENCY
        if (
                !this.settingSlidersMap.get("barTransparency").isSliderActive() &&
                !this.settingSlidersMap.get("textSpeed").isSliderActive() &&
                !this.settingSlidersMap.get("textSize").isSliderActive()
        ) {

            if (stringTimer.isRunning()) {
                stringTimer.stop();
            }

            g.setFont(configPaneFont);
            g.setColor(new Color(0, 0, 0));
            g.drawString("Diologue Bar Transparency", this.settingSlidersMap.get("barTransparency").getX(), this.settingSlidersMap.get("barTransparency").getY() - ((windowSize.x / 10) / 3));
            g.drawString("Diologue Text Speed", this.settingSlidersMap.get("textSpeed").getX(), this.settingSlidersMap.get("textSpeed").getY() - ((windowSize.x / 10) / 3));
            g.drawString("Dialog Text Size", this.settingSlidersMap.get("textSize").getX(), this.settingSlidersMap.get("textSize").getY() - ((windowSize.x / 10) / 3));
            g.drawString("Auto dialog advance", (windowSize.x / 10), (windowSize.y / 6) * 5 - ((windowSize.x / 10) / 3));

            if (!saveDialogue.isActive()) {
                for (Map.Entry<String, CustomButton> entry : this.settingButtonsMap.entrySet()) {
                    entry.getValue().paintComponent(g);
                }
            }
            autoDialog.paintComponent(g);

            for (Map.Entry<String, CustomSlider> entry : this.settingSlidersMap.entrySet()) {
                entry.getValue().paintComponent(g);
            }
        } else {
            // DialogueBox
            tempDialogueBox = dialogueBox.copy();
            tempDialogueBox.setOpacity(this.settingSlidersMap.get("barTransparency").getLevel());
            g.drawImage(tempDialogueBox.getBufferedImage(), WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2, WaifuBrew.getInstance().getRes()[1].y - dialogueBox.getHeight() - (WaifuBrew.getInstance().getRes()[1].x / 2 - dialogueBox.getWidth() / 2), this);
            stringTimer.start();
            if (!tempString.equals("")) {
                g.setFont(activeFont);
                g.setColor(new Color(0, 0, 0));
                g.drawString(tempString, 150, 550);
            }
            for (Map.Entry<String, CustomSlider> entry : this.settingSlidersMap.entrySet()) {
                CustomSlider slider = entry.getValue();
                if (slider.isSliderActive()) {
                    slider.paintComponent(g);
                }
            }
        }
        if (saveDialogue.isActive())
            saveDialogue.paintComponent(g);

    }

    private boolean checkLockInSetting() {
        return (
                this.settingSlidersMap.get("barTransparency").getLevel() != WaifuBrew.getInstance().getDialogueTransparency() ||
                this.settingSlidersMap.get("textSpeed").getLevel() != WaifuBrew.getInstance().getDialogueSpeed() ||
                ((this.settingSlidersMap.get("textSize").getLevel() / 2) + 10) != WaifuBrew.getInstance().getFontSize()
        );
    }


    private class Handlerclass extends MasterHandlerClass {

        public void mouseClicked(MouseEvent event) {
            // Disable original back and save button for noticeBox buttons.
            if (!saveDialogue.isActive()) {
                CustomButton button = settingButtonsMap.get("back");
                if (
                        event.getX() >= button.getX() - settingButtonsMap.get("back").getWidth() / 2 &&
                        event.getY() >= button.getY() - button.getHeight() / 2 &&
                        event.getX() <= button.getX() + button.getWidth() / 2 &&
                        event.getY() <= button.getY() + button.getHeight() / 2
                ) {
                    if (checkLockInSetting()) {
                        WaifuBrew.getInstance().setStage(0);
                        WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                    } else {
                        // Save setting first!
                        saveDialogue.setActive(true);
                    }
                }
                button = settingButtonsMap.get("save");
                if (
                        event.getX() >= button.getX() - button.getWidth() / 2 &&
                        event.getY() >= button.getY() - button.getHeight() / 2 &&
                        event.getX() <= button.getX() + button.getWidth() / 2 &&
                        event.getY() <= button.getY() + button.getHeight() / 2
                ) {
                    WaifuBrew.getInstance().setDialogueTransparency(settingSlidersMap.get("barTransparency").getLevel());
                    WaifuBrew.getInstance().setDialogueSpeed(settingSlidersMap.get("textSpeed").getLevel());
                    WaifuBrew.getInstance().setFontSize((settingSlidersMap.get("textSpeed").getLevel() / 2) + 10);

                    // System.out.println("ConfigPane.Handler: Set auto dia to: " + autoDialog.getValue());
                    WaifuBrew.getInstance().setAutoAdvancer(autoDialog.getValue());
                    WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                }
                button = settingButtonsMap.get("reset");
                if (
                        event.getX() >= button.getX() - button.getWidth() / 2 &&
                        event.getY() >= button.getY() - button.getHeight() / 2 &&
                        event.getX() <= button.getX() + button.getWidth() / 2 &&
                        event.getY() <= button.getY() + button.getHeight() / 2
                ) {
                    settingSlidersMap.get("barTransparency").setLevel(WaifuBrew.getInstance().getDialogueTransparency());
                    settingSlidersMap.get("textSpeed").setLevel(WaifuBrew.getInstance().getDialogueSpeed());
                    settingSlidersMap.get("textSpeed").setLevel((WaifuBrew.getInstance().getFontSize() - 10) * 2);
                }
            } else {
                for (int noticeBoxButtonIndix = 0; noticeBoxButtonIndix < saveDialogue.getButton().length; noticeBoxButtonIndix++) {
                    if (event.getX() > saveDialogue.getButton()[noticeBoxButtonIndix].getAbsoluteX() &&
                            event.getX() < saveDialogue.getButton()[noticeBoxButtonIndix].getAbsoluteX() + saveDialogue.getButton()[noticeBoxButtonIndix].getWidth() &&
                            event.getY() > saveDialogue.getButton()[noticeBoxButtonIndix].getAbsoluteY() &&
                            event.getY() < saveDialogue.getButton()[noticeBoxButtonIndix].getAbsoluteY() + saveDialogue.getButton()[noticeBoxButtonIndix].getHeight()) {

                        if (noticeBoxButtonIndix == 0) {
                            // Save is clicked

                            // Save all the settings.
                            WaifuBrew.getInstance().setDialogueTransparency(settingSlidersMap.get("barTransparency").getLevel());
                            WaifuBrew.getInstance().setDialogueSpeed(settingSlidersMap.get("textSpeed").getLevel());
                            WaifuBrew.getInstance().setFontSize((settingSlidersMap.get("textSpeed").getLevel() / 2) + 10);
                            WaifuBrew.getInstance().setAutoAdvancer(autoDialog.getValue());

                            // Disable NoticeBox
                            saveDialogue.setActive(false);

                            // Go back to Main screen.
                            WaifuBrew.getInstance().setStage(0);
                            WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                        } else if (noticeBoxButtonIndix == 1) {
                            // Back is clicked

                            // Disable NoticeBox
                            saveDialogue.setActive(false);
                            settingSlidersMap.get("barTransparency").setLevel(WaifuBrew.getInstance().getDialogueTransparency());
                            settingSlidersMap.get("textSpeed").setLevel(WaifuBrew.getInstance().getDialogueSpeed());
                            settingSlidersMap.get("textSpeed").setLevel((WaifuBrew.getInstance().getFontSize() - 10) * 2);
                            repaint();
                            WaifuBrew.getInstance().setStage(0);
                            WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                        }
                    }
                }
            }
        }
    }


    public void init() {
        // TODO: I wonder if I use this lambda
        Timer t = new Timer((int) (1000 / WaifuBrew.getInstance().getFrameRate()), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!stop) {
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        t.setRepeats(true);
        t.setDelay((int) (1000 / WaifuBrew.getInstance().getFrameRate()));
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
