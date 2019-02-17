package start.PaneFrame;

import start.CustomObjects.*;
import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;

public class ConfigPane extends JPanel implements ActionListener {

    // LOAD IMAGE
    private javaxt.io.Image backgroundPicture;
    private javaxt.io.Image dialogueBox; // So it doesn't use HDD every time and kill performance
    private javaxt.io.Image tempDialogueBox; // Preview
    private Timer stringTimer;
    private boolean fpsLimitStop = false;

    private HashMap<String, CustomSlider> settingSlidersMap = new HashMap<>(3);
    private HashMap<String, CustomButton> settingButtonsMap = new HashMap<>(3);
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
        initFPS();
        initFont();
        initImage();
        initStringTimer();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.settingSlidersMap.get("textSize").isSliderActive()) {

            Font ttfBase = WaifuBrew.getInstance().getSystemFont(WaifuBrew.getInstance().getSystemFontName());
            fontSize = (this.settingSlidersMap.get("textSize").getLevel() / 2) + 10;
            activeFont = ttfBase.deriveFont(Font.PLAIN, fontSize);
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

            // Draw sliders
            for (Map.Entry sliderObject : settingSlidersMap.entrySet()) {
                CustomSlider sliders = (CustomSlider) sliderObject.getValue();
                g.drawString(sliders.getSliderDesc(), sliders.getX(), sliders.getY() - ((windowSize.x / 10) / 3));
            }
            // Draw switch
            g.drawString(autoDialog.getSwitchDesc(), autoDialog.getX(), autoDialog.getY() - ((windowSize.x / 10) / 3));

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
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                FontRenderContext frc = g2d.getFontRenderContext();
                TextLayout textTl = new TextLayout(tempString, activeFont, frc);
                Shape outline = textTl.getOutline(null);

                FontMetrics fm = g2d.getFontMetrics(activeFont);
                g2d.translate((windowSize.x / 9.0), ((windowSize.y / 13.0) * 9) + fm.getAscent());
                g2d.setColor(Color.WHITE);
                g2d.fill(outline);
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.BLACK);
                g2d.draw(outline);
                g2d.dispose();
            }
            for (Map.Entry<String, CustomSlider> entry : this.settingSlidersMap.entrySet()) {
                CustomSlider slider = entry.getValue();
                if (slider.isSliderActive()) {
                    slider.paintComponent(g);
                }
            }
        }
        if (saveDialogue.isActive()) {
            saveDialogue.paintComponent(g);
        }
    }

    public void stageChange(int lastStage) {
        // Reload anything that can have settings changed.
        activeFont = new Font(WaifuBrew.getInstance().getDialogueFont(), Font.BOLD, WaifuBrew.getInstance().getPlayFontSize());
        initStringTimer();
    }

    private boolean checkLockInSetting() {
        return (
                !(this.settingSlidersMap.get("barTransparency").getLevel() != WaifuBrew.getInstance().getDialogueTransparency() ||
                        this.settingSlidersMap.get("textSpeed").getLevel() != WaifuBrew.getInstance().getDialogueSpeed() ||
                        (((this.settingSlidersMap.get("textSize").getLevel() / 2) + 10)) != WaifuBrew.getInstance().getPlayFontSize() ||
                        this.autoDialog.getValue() != WaifuBrew.getInstance().getAutoAdvancer())
        );
    }


    private class Handlerclass extends MasterHandlerClass {

        public void mouseClicked(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON1) {

                // Disable original back and save button for noticeBox buttons.
                if (!saveDialogue.isActive()) {
                    CustomButton button = settingButtonsMap.get("back");
                    if (inBound(event, button, false)) {
                        if (checkLockInSetting()) {
                            WaifuBrew.getInstance().setStage(0);
                            WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                        } else {
                            // Save setting first!
                            saveDialogue.setActive(true);
                        }
                    }
                    button = settingButtonsMap.get("save");
                    if (inBound(event, button, false)) {
                        WaifuBrew.getInstance().setDialogueTransparency(settingSlidersMap.get("barTransparency").getLevel());
                        WaifuBrew.getInstance().setDialogueSpeed(settingSlidersMap.get("textSpeed").getLevel());
                        WaifuBrew.getInstance().setPlayFontSize((settingSlidersMap.get("textSize").getLevel() / 2) + 10);
                        WaifuBrew.getInstance().setAutoAdvancer(autoDialog.getValue());

                        // Export the newly saved file.
                        WaifuBrew.getInstance().exportSettings();
                        WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                    }
                    button = settingButtonsMap.get("reset");
                    if (inBound(event, button, false)) {
                        settingSlidersMap.get("barTransparency").setLevel(WaifuBrew.getInstance().getDialogueTransparency());
                        settingSlidersMap.get("textSpeed").setLevel(WaifuBrew.getInstance().getDialogueSpeed());
                        settingSlidersMap.get("textSize").setLevel(((WaifuBrew.getInstance().getPlayFontSize() - 10) * 2));
                        autoDialog.setValue(WaifuBrew.getInstance().getAutoAdvancer());
                    }
                } else {
                    for (int noticeBoxButtonIndex = 0; noticeBoxButtonIndex < saveDialogue.getButton().length; noticeBoxButtonIndex++) {
                        if (inBound(event, saveDialogue.getButton()[noticeBoxButtonIndex], true)) {
                            if (noticeBoxButtonIndex == 0) {
                                // Save is clicked

                                // Save all the settings.
                                WaifuBrew.getInstance().setDialogueTransparency(settingSlidersMap.get("barTransparency").getLevel());
                                WaifuBrew.getInstance().setDialogueSpeed(settingSlidersMap.get("textSpeed").getLevel());
                                WaifuBrew.getInstance().setPlayFontSize(((settingSlidersMap.get("textSize").getLevel() / 2) + 10));
                                WaifuBrew.getInstance().setAutoAdvancer(autoDialog.getValue());

                                // Disable NoticeBox
                                saveDialogue.setActive(false);

                                // Export the newly saved file.
                                WaifuBrew.getInstance().exportSettings();

                                // Go back to Main screen.
                                WaifuBrew.getInstance().setStage(0);
                                WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                            } else if (noticeBoxButtonIndex == 1) {
                                // Back is clicked

                                // Disable NoticeBox
                                saveDialogue.setActive(false);
                                settingSlidersMap.get("barTransparency").setLevel(WaifuBrew.getInstance().getDialogueTransparency());
                                settingSlidersMap.get("textSpeed").setLevel(WaifuBrew.getInstance().getDialogueSpeed());
                                settingSlidersMap.get("textSize").setLevel((WaifuBrew.getInstance().getPlayFontSize() - 10) * 2);
                                autoDialog.setValue(WaifuBrew.getInstance().getAutoAdvancer());
                                WaifuBrew.getInstance().setStage(0);
                                WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                            }
                        }
                    }
                }
            }
        }
    }

    private void initFPS() {
        // TODO: I wonder if I use this lambda
        Timer t = new Timer((int) (1000.0 / WaifuBrew.getInstance().getFrameRate()), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!fpsLimitStop) {
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        t.setRepeats(true);
        t.setDelay((int) (1000 / WaifuBrew.getInstance().getFrameRate()));
        t.start();
    }

    private void initFont() {
        Font ttfBase = WaifuBrew.getInstance().getSystemFont(WaifuBrew.getInstance().getSystemFontName());
        activeFont = new Font(WaifuBrew.getInstance().getDialogueFont(), Font.BOLD, WaifuBrew.getInstance().getPlayFontSize());
        configPaneFont = ttfBase.deriveFont(Font.PLAIN, 24);
    }

    private void initImage() {

        try {
            backgroundPicture = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BACKGROUND, "config"));
            dialogueBox = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "dialogbar"));
            this.settingButtonsMap.put("back", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "back_button", Origin.MIDDLE_CENTRE, 0, true));
            this.settingButtonsMap.put("save", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 4, "save_button", Origin.MIDDLE_CENTRE, 0, false));
            this.settingButtonsMap.put("reset", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 3, "reset_button", Origin.MIDDLE_CENTRE, 0, true));

            saveDialogue = new NoticeBox("Would you like to save the current settings?", "save_button", "don't_save_button", false, true);

            // Pre-scale
            double scale = Math.max(((double) windowSize.x / backgroundPicture.getWidth()), ((double) windowSize.y / backgroundPicture.getHeight()));
            backgroundPicture.resize((int) ((scale) * backgroundPicture.getWidth()), (int) ((scale) * backgroundPicture.getHeight()));
            dialogueBox.resize((int) (((dialogueBox.getWidth() * 0.85) / 1280) * WaifuBrew.getInstance().getRes()[1].x), (int) (((dialogueBox.getHeight() * 0.85) / 720) * WaifuBrew.getInstance().getRes()[1].y), false);

            this.settingSlidersMap.put("barTransparency", new CustomSlider((windowSize.x / 10), (windowSize.y / 6) * 2, WaifuBrew.getInstance().getDialogueTransparency(), "Diologue Bar Transparency"));
            this.settingSlidersMap.put("textSpeed", new CustomSlider((windowSize.x / 10), (windowSize.y / 6) * 3, WaifuBrew.getInstance().getDialogueSpeed(), "Dialog Text Speed"));
            this.settingSlidersMap.put("textSize", new CustomSlider((windowSize.x / 10), (windowSize.y / 6) * 4, (WaifuBrew.getInstance().getPlayFontSize() - 10) * 2, "Dialog Text Size"));
            autoDialog = new CustomSwitch((windowSize.x / 10), (windowSize.y / 6) * 5, WaifuBrew.getInstance().getAutoAdvancer(), false, "Auto dialog advance");

            // Handlers listening to mouse like DOGS
            addMouseListener(handler);
            addMouseMotionListener(handler);
            addMouseListener(autoDialog.retrieveMouseHandler());
            addMouseMotionListener(autoDialog.retrieveMouseHandler());

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
        } catch (Exception e) {
            System.out.println("File failure in Config class");
            e.printStackTrace(); // Wall of error
            System.exit(-1);
        }
    }

    private void initStringTimer() {

        // Builds character into sentence one by one. Using timers are bit meh since it needs to finish to change duration.
        stringTimer = new Timer((WaifuBrew.getInstance().getDialogueSpeed()), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!a.isEmpty()) {
                    if (tempString.length() != a.length()) {
                        tempString = tempString + a.charAt(tempString.length());
                        stringTimer.setDelay(100 - settingSlidersMap.get("textSpeed").getLevel()); // Text speed is inverted.
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
        stringTimer.setCoalesce(true);

    }
}
