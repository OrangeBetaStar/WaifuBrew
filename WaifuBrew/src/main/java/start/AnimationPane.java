package start;

import org.json.JSONException;
import parser.DialogueParser;
import parser.exception.DialogueDataMissingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AnimationPane extends JPanel {

    private boolean initStage = true;
    private boolean initStory = true;
    private Handlerclass handler = new Handlerclass();
    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private javaxt.io.Image dialogueBox;
    private DialogueParser dp;
    private Point[] res;
    private javaxt.io.Image characterImage[] = new javaxt.io.Image[10]; // Size of pictures per advance? Wait wtf? TODO: NEEDS QUICK FIX
    private double GUIScale = (double)WaifuBrew.getInstance().getSystemGUIScale();
    private boolean clickActivate = true;

    private boolean stop = false;

    private int textSpeedMS = WaifuBrew.getInstance().getDialogueSpeed() * 10;
    private int dialogueTransparency = WaifuBrew.getInstance().getDialogueTransparency();

    private int buttonPlacementY = 640;

    private CustomButton saveButton;
    private CustomButton loadButton;
    private CustomButton configButton;
    private CustomButton startButton;

    // Delete after experiment
    private static InputStream myStream;
    private static Font ttfBase;
    private static Font activeFont;

    // Advancer keeps track of which line it reads
    private int advancer = 0;

    // Retrieve String from JSON
    private String a = "Click anywhere to initiate dialog...!";
    private String tempString = "";
    private java.util.List<java.util.List<Waifu>> e;

    public AnimationPane() {
        init();

        addMouseListener(handler);
        addMouseMotionListener(handler);

        this.res = WaifuBrew.getInstance().getRes();
        try {
            dialogueBox = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "dialogbar"));

            // GARBAGE IMPLEMENTATION
            startButton = new CustomButton(640, buttonPlacementY, "startscreen_start_button.png", false, 60, true);
            addMouseListener(startButton.retrieveMouseHandler());
            addMouseMotionListener(startButton.retrieveMouseHandler());

            loadButton = new CustomButton(760, buttonPlacementY, "startscreen_load_button.png", false, 60, true);
            addMouseListener(loadButton.retrieveMouseHandler());
            addMouseMotionListener(loadButton.retrieveMouseHandler());

            saveButton = new CustomButton(870, buttonPlacementY, "startscreen_save_button.png", false, 60, true);
            addMouseListener(saveButton.retrieveMouseHandler());
            addMouseMotionListener(saveButton.retrieveMouseHandler());

            configButton = new CustomButton(1000, buttonPlacementY, "startscreen_config_button.png", false, 60, true);
            addMouseListener(configButton.retrieveMouseHandler());
            addMouseMotionListener(configButton.retrieveMouseHandler());

            dialogueBox.resize((int)(dialogueBox.getWidth() * 0.9), (int)(dialogueBox.getHeight() * 0.9),true);

            dp = new DialogueParser(RESOURCE_PATH + "test.json");
            dp.parse();
            e = dp.getPackagedDialogue();

            Timer stringTimer = new Timer(textSpeedMS, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(!a.isEmpty()) {
                        if (tempString.length() != a.length()) {
                            tempString = tempString + a.charAt(tempString.length());
                        }
                        else { // TODO: Check if this works
                            System.out.println("The current advancer: " + WaifuBrew.getInstance().getAutoAdvancer());
                            System.out.println("The transparency: " + WaifuBrew.getInstance().getDialogueTransparency());
                            if(WaifuBrew.getInstance().getAutoAdvancer()) {
                                clickActivate = true;
                                // TODO: NEEDS AWAIT
                                // TODO: FIX TRANSPARENCY
                                if(WaifuBrew.getInstance().getStage() == 1) {
                                    triggerNext();
                                }

                            }
                        }
                    }
                }
            });

            // Coalesce is disabled since there is no multiple firing of triggers.
            stringTimer.setRepeats(true);
            stringTimer.setCoalesce(false);
            stringTimer.start();


            try {
                myStream = new BufferedInputStream(new FileInputStream(RESOURCE_PATH + "Halogen.ttf"));
                ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
                activeFont = ttfBase.deriveFont(Font.PLAIN, 24);
            } catch (FontFormatException ex) {
                ex.printStackTrace();
                System.err.println(myStream.toString() + " not loaded.  Using serif font.");
                activeFont = new Font("serif", Font.PLAIN, 24);
            }


        } catch (IOException ex) {
            System.out.println("Simple IOException");
            ex.printStackTrace();
        } catch (DialogueDataMissingException ex) {
            System.out.println("Missing Dialog!"); //Wait what, it is okay to have missing dialog. i.e.: just character change
            ex.printStackTrace();
        } catch (JSONException ex) {
            System.out.println("There is an error with JSON");
            ex.printStackTrace();
        }
    }

    public void triggerNext() {
        if(e.get(advancer).get(0).getDialogue() != null) {
            tempString = "";
            a = e.get(advancer).get(0).getDialogue();
        }
        else {
            tempString = "";
            a = "";
        }
        if(advancer < e.size()-1) {
            advancer++;
        }
    }

    private class Handlerclass implements  MouseListener, MouseMotionListener, ItemListener{

        public void itemStateChanged(ItemEvent event) {

        }

        public void mouseClicked(MouseEvent event) {

        }

        public void mousePressed(MouseEvent event) {

        }

        public void mouseReleased(MouseEvent event) {
            // There may be a dialogue without dialogue and only character movement

            // Have this inside if statement where it is not run on buttons.
            clickActivate = true;
            triggerNext();
        }

        public void mouseEntered(MouseEvent event) {

        }

        public void mouseExited(MouseEvent event) {

        }

        public void mouseMoved(MouseEvent event) {

        }

        public void mouseDragged(MouseEvent event) {

        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(initStage) {
            dialogueTransparency = WaifuBrew.getInstance().getDialogueTransparency();
        }

        /*
        private double GUIScale = (double)WaifuBrew.getInstance().getSystemGUIScale();
        originalButton.resize((int)(originalButton.getWidth() * (GUIScale/originalButton.getHeight())), 75, true);
        */

        // Do not show character on first viewing
        if(advancer != 0) {

            // Character
            if(clickActivate) {
                // This will get all the chars that are needed for each dialog.
                for (int a = 0; a < e.get(advancer - 1).size(); a++) {
                    //backgroundPicture = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.BACKGROUND, "bg_start.png"));
                    // System.out.println(e.get(advancer - 1).get(a).getName().toString().toLowerCase() + "-" + e.get(advancer - 1).get(a).getMood().toString().toLowerCase());
                    characterImage[a] = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.CHARACTERS, e.get(advancer - 1).get(a).getName().toString().toLowerCase() + "-" + e.get(advancer - 1).get(a).getMood().toString().toLowerCase()));
                    characterImage[a].resize((int) (200 * (GUIScale / 250)), 250, true);
                    //characterImage[a] = new javaxt.io.Image(RESOURCE_PATH + e.get(advancer - 1).get(a).getName().toString().toLowerCase() + "-" + e.get(advancer - 1).get(a).getMood().toString().toLowerCase() + ".png");
                }
                clickActivate = false;
            }
            for(int b = 1; b <= e.get(advancer-1).size(); b++) {
                g.drawImage(characterImage[b - 1].getBufferedImage(), ((res[1].x / (e.get(advancer-1).size() + 1)) * b) - (characterImage[b - 1].getWidth() / 2), (res[1].y / 4) + (characterImage[b - 1].getHeight() / 2), this);
            }

            // DialogueBox

            g.drawImage(dialogueBox.getBufferedImage(),res[1].x / 2 - dialogueBox.getWidth() / 2, res[1].y - dialogueBox.getHeight() - (res[1].x / 2 - dialogueBox.getWidth() / 2),this);
            g.drawString(e.get(advancer-1).get(0).getName().toString(), 100, 430);

            // Run once. Different from initStage. initStory runs after very first dialogue while initStage runs right after stage has been entered.
            if(initStory) {

                initStory = false;
            }
            // g.setFont(new Font("Comic Sans MS", Font.BOLD, (int)(3000/GUIScale)));
            g.setFont(activeFont);
            g.setColor(new Color(0,0,0));
        }
        else {
            if(initStage) {
                dialogueBox.setOpacity(dialogueTransparency);
            }
        }

        if(tempString != "") {
            g.drawString(tempString, 150, 550);
        }

        startButton.paintComponent(g);
        loadButton.paintComponent(g);
        saveButton.paintComponent(g);
        configButton.paintComponent(g);

        // Use the bottom link for implementing string wrap around by distance used by font.
        // https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html

        initStage = false;
    }

    public void init(){
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
    }

}
