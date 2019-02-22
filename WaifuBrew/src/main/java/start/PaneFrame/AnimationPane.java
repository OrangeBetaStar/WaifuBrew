package start.PaneFrame;

import start.Containers.ImageDesc;
import start.CustomObjects.CustomButton;
import start.CustomObjects.MasterHandlerClass;
import start.CustomObjects.Origin;
import start.CustomObjects.SideBar;
import start.Loader.ImageSelector;
import start.Loader.Waifu;
import start.Loader.WaifuBrew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.HashMap;
import java.util.Map;

public class AnimationPane extends JPanel {

    private javaxt.io.Image dialogueBox;
    private javaxt.io.Image characterImage[] = new javaxt.io.Image[10]; // Maximum 10 characters at once.
    private double GUIScale = (double) WaifuBrew.getInstance().getSystemGUIScale();
    private boolean clickActivate = true;

    // Disable text and text background to see only scenery
    private boolean rightClickTempDisableBox = false;

    // Interactive objects.
    private HashMap<String, CustomButton> aniPaneButton = new HashMap<>(4); // Save / Load / Config / Exit
    private SideBar configBar = new SideBar();
    private static Font activeFont;

    // String timer for each character
    private Timer stringTimer;

    // Advancer keeps track of which line it reads
    private int advancer = 0; //line#

    // [1] is resolution of program window
    private Point windowSize = WaifuBrew.getInstance().getRes()[1];

    // internal mouselistener
    private Handlerclass handler = new Handlerclass();

    // Retrieve String from JSON
    private String a = "Click anywhere to initiate dialog...!";
    private String tempString = "";
    private java.util.List<java.util.List<Waifu>> e;
    private ImageDesc background;

    private boolean newStart = true;

    public AnimationPane() {
        // parsing dialogue has to be done before this payload.
        initParseDialogue();
        initFPS();
        initFont();
        initImage();
        initStringTimer();
    }

    public void triggerNext() {
        if (advancer < e.size()) {
            if (e.get(advancer).get(0).getDialogue() != null) {
                tempString = "";
                a = e.get(advancer).get(0).getDialogue();
            } else {
                tempString = "";
                a = "";
            }
            advancer++;
        }
    }

    private class Handlerclass extends MasterHandlerClass {

        public void mouseReleased(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON1) {
                if (rightClickTempDisableBox) {
                    rightClickTempDisableBox = false;
                } else { // When dialogue bar hasn't been disabled for one click.
                    if (inBound(event, aniPaneButton.get("config")) && !configBar.isActive()) {
                        configBar.setActive(true);
                        aniPaneButton.get("config").setActiveButtonState(false);
                    } else if (inBound(event, aniPaneButton.get("load")) && configBar.isActive()) {
                        configBar.setActive(false);
                        WaifuBrew.getInstance().setStage(3);
                        aniPaneButton.get("config").setActiveButtonState(true);
                        WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                        // Ask user if save progress?
                    } else if (inBound(event, aniPaneButton.get("save")) && configBar.isActive()) {
                        configBar.setActive(false);
                        WaifuBrew.getInstance().setStage(4);
                        aniPaneButton.get("config").setActiveButtonState(true);
                        WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                    } else if (inBound(event, aniPaneButton.get("exit")) && configBar.isActive()) {
                        // Ask user if save progress?
                        // This button will go back to startscreen
                        WaifuBrew.getInstance().setStage(0);
                        configBar.setActive(false);
                        aniPaneButton.get("config").setActiveButtonState(true);
                        WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
                    } else {
                        if (configBar.isActive()) {
                            configBar.setActive(false);
                            aniPaneButton.get("config").setActiveButtonState(true);
                        } else {
                            // To avoid advancing of the dialogue when pressed other place to disable configbar.
                            clickActivate = true;
                            triggerNext();
                        }
                    }
                }
            } else if (event.getButton() == MouseEvent.BUTTON3) {
                rightClickTempDisableBox = true;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Do not show character on first viewing
        if (advancer != 0) {

            // Character
            if (clickActivate) {
                // This will get all the chars that are needed for each dialog.
                for (int a = 0; a < e.get(advancer - 1).size(); a++) {
                    characterImage[a] = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.CHARACTERS, e.get(advancer - 1).get(a).getName().toString().toLowerCase() + "-" + e.get(advancer - 1).get(a).getMood().toString().toLowerCase()));
                    characterImage[a].resize((int) (200 * (GUIScale / 250)), 250, true);

                    if (background == null) {
                        for (int finder = 0; finder < e.get(advancer - 1).size(); finder++) {
                            if (e.get(advancer - 1).get(a).getBackground() != null) {
                                if (newStart) { // new / old. start / load
                                    // New start needs to go from top to bottom
                                    background = new ImageDesc(e.get((advancer - 1) + finder).get(a).getBackground(), WaifuBrew.getInstance().getImageByName(ImageSelector.BACKGROUND, e.get((advancer - 1) + finder).get(a).getBackground()));
                                    javaxt.io.Image tempBackground = new javaxt.io.Image(background.getImageItself());
                                    double scale = Math.max(((double) windowSize.x / tempBackground.getWidth()), ((double) windowSize.y / tempBackground.getHeight()));
                                    tempBackground.resize((int) ((scale) * tempBackground.getWidth()), (int) ((scale) * tempBackground.getHeight()));
                                    background.setImageItself(tempBackground.getBufferedImage());
                                } else {
                                    // TODO: Load needs to go from bottom to top (current just mirror of above with -finder ) ["after the load scenario"]
                                    background = new ImageDesc(e.get((advancer - 1) - finder).get(a).getBackground(), WaifuBrew.getInstance().getImageByName(ImageSelector.BACKGROUND, e.get((advancer - 1) - finder).get(a).getBackground()));
                                    javaxt.io.Image tempBackground = new javaxt.io.Image(background.getImageItself());
                                    double scale = Math.max(((double) windowSize.x / tempBackground.getWidth()), ((double) windowSize.y / tempBackground.getHeight()));
                                    tempBackground.resize((int) ((scale) * tempBackground.getWidth()), (int) ((scale) * tempBackground.getHeight()));
                                    background.setImageItself(tempBackground.getBufferedImage());
                                }
                            }
                            if (background != null) {
                                break;
                            }
                        }
                    }
                    // Searches for next background image and resizes it
                    if (e.get(advancer - 1).get(a).getBackground() != null && !(background.getImageDescription().equals(e.get(advancer - 1).get(a).getBackground()))) {
                        background = new ImageDesc(e.get(advancer - 1).get(a).getBackground(), new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BACKGROUND, e.get(advancer - 1).get(a).getBackground())));
                        javaxt.io.Image tempBackground = new javaxt.io.Image(background.getImageItself());
                        double scale = Math.max(((double) windowSize.x / tempBackground.getWidth()), ((double) windowSize.y / tempBackground.getHeight()));
                        tempBackground.resize((int) ((scale) * tempBackground.getWidth()), (int) ((scale) * tempBackground.getHeight()));
                        background.setImageItself(tempBackground.getBufferedImage());
                    }
                }
                clickActivate = false;
            }

            // Draw background here
            g.drawImage(background.getImageItself(), (windowSize.x / 2) - (background.getImageItself().getWidth() / 2), (windowSize.y / 2) - (background.getImageItself().getHeight() / 2), this);

            for (int b = 1; b <= e.get(advancer - 1).size(); b++) {
                // Draw character(s) here
                g.drawImage(characterImage[b - 1].getBufferedImage(), ((windowSize.x / (e.get(advancer - 1).size() + 1)) * b) - (characterImage[b - 1].getWidth() / 2), (windowSize.y / 10) + (characterImage[b - 1].getHeight() / 2), this);
            }

            // DialogueBox
            if (!rightClickTempDisableBox) {
                g.drawImage(dialogueBox.getBufferedImage(), windowSize.x / 2 - dialogueBox.getWidth() / 2, (int) ((windowSize.y / 12.0) * 8) - (dialogueBox.getHeight() / 3), this);
                g.drawString(e.get(advancer - 1).get(0).getName().toString(), (int) (windowSize.x / 9.0), (int) ((windowSize.y / 10.0) * 6));
            }
            // Run once. Different from initStage. initStory runs after very first dialogue while initStage runs right after stage has been entered.
            g.setFont(activeFont);
            g.setColor(new Color(0, 0, 0));
        } else {
            // What
        }
        if (!rightClickTempDisableBox) {
            if (tempString != "") {
                // Dialogue text rendering with boundary shading
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                FontRenderContext frc = g2d.getFontRenderContext();
                TextLayout textTl = new TextLayout(tempString, activeFont, frc);
                Shape outline = textTl.getOutline(null);

                // FontMetrics fm = g2d.getFontMetrics(activeFont);
                g2d.translate((windowSize.x / 9.0), ((windowSize.y / 12.0) * 8));// + fm.getAscent());
                g2d.setColor(Color.WHITE);
                g2d.fill(outline);
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.BLACK);
                g2d.draw(outline);
                g2d.dispose();
            }
        }

        // Buttons will paint over this Panel
        configBar.paintComponent(g);

        // Draws the buttons
        for (Map.Entry<String, CustomButton> entry : this.aniPaneButton.entrySet()) {
            if (configBar.isActive() && !configBar.isMoving()) {
                // Paint every other buttons except config button when configBar is active.
                if (!entry.getKey().equals("config")) {
                    entry.getValue().paintComponent(g);
                }
            } else {
                // Only paint config button when configBar is inactive
                aniPaneButton.get("config").paintComponent(g);
                break;
            }
        }
    }

    public void stageChange(int lastStage) {
        // Reload anything that can have settings changed.

        // Renew Font (Font and Size)
        activeFont = new Font(WaifuBrew.getInstance().getDialogueFont(), Font.BOLD, WaifuBrew.getInstance().getPlayFontSize());

        // Renew FPS
        stringTimer = null;
        initStringTimer();

        // Renew dialogueBox
        dialogueBox = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "dialogbar"));
        dialogueBox.resize((int) (((dialogueBox.getWidth() * 0.85) / 1280) * WaifuBrew.getInstance().getRes()[1].x), (int) (((dialogueBox.getHeight() * 0.85) / 720) * WaifuBrew.getInstance().getRes()[1].y), false);
        dialogueBox.setOpacity(WaifuBrew.getInstance().getDialogueTransparency());

        if(!(lastStage == 0 || lastStage == 10)) {
            // Have to select correct route as well.
            advancer = WaifuBrew.getInstance().getCurrentSave().getAdvancerDialogue();
        }
    }

    private void initFPS() {
        Timer fpsTimer = new Timer((int) (1000 / WaifuBrew.getInstance().getFrameRate()), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        fpsTimer.setRepeats(true);
        fpsTimer.setDelay((int) (1000 / WaifuBrew.getInstance().getFrameRate()));
        fpsTimer.start();

    }

    private void initImage() {
        addMouseListener(handler);
        addMouseMotionListener(handler);

        dialogueBox = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "dialogbar"));

        this.aniPaneButton.put("save", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 3, "save_button", Origin.MIDDLE_CENTRE, 0, true));
        this.aniPaneButton.put("load", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 4, "load_button", Origin.MIDDLE_CENTRE, 0, true));
        this.aniPaneButton.put("exit", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "exit_button", Origin.MIDDLE_CENTRE, 0, true));
        // Remake this as options, rather than config
        this.aniPaneButton.put("config", new CustomButton((windowSize.x / 8) * 7, (windowSize.y / 6) * 5, "options_button", Origin.MIDDLE_TOP, 0, true));

        for (Map.Entry<String, CustomButton> entry : this.aniPaneButton.entrySet()) {
            CustomButton button = entry.getValue();
            addMouseListener(button.retrieveMouseHandler());
            addMouseMotionListener(button.retrieveMouseHandler());
        }

        dialogueBox.resize((int) (dialogueBox.getWidth() * 0.85), (int) (dialogueBox.getHeight() * 0.85), true);
        dialogueBox.setOpacity(WaifuBrew.getInstance().getDialogueTransparency());
    }

    private void initFont() {
        activeFont = new Font(WaifuBrew.getInstance().getDialogueFont(), Font.BOLD, WaifuBrew.getInstance().getPlayFontSize());
    }

    private void initParseDialogue() {
        e = WaifuBrew.getInstance().getDialoguePackage();
    }

    private void initStringTimer() {

        // Add stage check to disable auto dialogue to start without being in the correct stage
        stringTimer = new Timer(WaifuBrew.getInstance().getDialogueSpeed(), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!a.isEmpty()) {
                    if (tempString.length() != a.length()) {
                        tempString = tempString + a.charAt(tempString.length());
                    } else {
                        if (WaifuBrew.getInstance().getAutoAdvancer()) {
                            clickActivate = true;
                            // TODO: NEEDS AWAIT
                            // TODO: FIX TRANSPARENCY
                            if (WaifuBrew.getInstance().getStage() == 1) {
                                triggerNext();
                            }
                        }
                    }
                }
            }
        });

        // Coalesce is disabled since there is no multiple firing of triggers.
        stringTimer.setRepeats(true);
        stringTimer.setCoalesce(true);
        stringTimer.start();
    }
}
