package start;
import org.json.JSONException;
import parser.DialogueParser;
import parser.exception.DialogueDataMissingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import static com.sun.java.accessibility.util.SwingEventMonitor.addChangeListener;

public class AnimationPane extends JPanel {

    private boolean initStage = true;
    private boolean initStory = true;
    private Handlerclass handler = new Handlerclass();
    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private javaxt.io.Image dialogueBox;
    private DialogueParser dp;
    private Point[] res;
    private javaxt.io.Image characterImage[] = new javaxt.io.Image[5];

    private int textSpeedMS = WaifuBrew.getInstance().getDialogueSpeed() * 10;
    private int dialogueTransparency = WaifuBrew.getInstance().getDialogueTransparency();

    CustomButton saveButton;
    CustomButton configButton;

    // Advancer keeps track of which line it reads
    private int advancer = 0;

    // Retrieve String from JSON
    private String a = "...";
    private String tempString = "";
    private java.util.List<java.util.List<Waifu>> e;


    public AnimationPane() {
        addMouseListener(handler);
        addMouseMotionListener(handler);


        this.res = WaifuBrew.getInstance().getRes();
        try {
            dialogueBox = new javaxt.io.Image(RESOURCE_PATH + "dialogbar.png");

            // GARBAGE IMPLEMENTATION
            saveButton = new CustomButton(500, 500, "startscreen_save_button.png");
            addMouseListener(saveButton.retrieveMouseHandler());
            addMouseMotionListener(saveButton.retrieveMouseHandler());

            configButton = new CustomButton(700, 500, "startscreen_config_button.png");
            addMouseListener(configButton.retrieveMouseHandler());
            addMouseMotionListener(configButton.retrieveMouseHandler());

            dialogueBox.resize((int)(dialogueBox.getWidth() * 0.9), (int)(dialogueBox.getHeight() * 0.9),true);

            dp = new DialogueParser(RESOURCE_PATH + "test.json");
            dp.parse();
            e = dp.getPackagedDialogue();

            // System.out.println("Currently speed is: "+textSpeedMS);
            Timer stringTimer = new Timer(textSpeedMS, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(!a.isEmpty()) {
                        if (tempString.length() != a.length()) {
                            tempString = tempString + a.charAt(tempString.length());
                        }
                    }
                    repaint();
                }
            });

            // Coalesce is disabled since there is no multiple firing of triggers.
            stringTimer.setRepeats(true);
            stringTimer.setCoalesce(false);
            stringTimer.start();

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

    private class Handlerclass implements  MouseListener, MouseMotionListener{

        public void mouseClicked(MouseEvent event) {

        }

        public void mousePressed(MouseEvent event) {

        }

        public void mouseReleased(MouseEvent event) {
            // There may be a dialogue without dialogue and only character movement
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
            // System.out.println("Setting dialogueTrans to " + WaifuBrew.getInstance().getDialogueTransparency() + "%");
            dialogueTransparency = WaifuBrew.getInstance().getDialogueTransparency();
        }

        // Do not show character on first viewing
        if(advancer != 0) {

            // Character
            for(int a = 0; a < e.get(advancer-1).size(); a++) {
                characterImage[a] = new javaxt.io.Image(RESOURCE_PATH + e.get(advancer - 1).get(a).getName().toString().toLowerCase() + "-" + e.get(advancer - 1).get(a).getMood().toString().toLowerCase() + ".png");
            }
            for(int b = 1; b <= e.get(advancer-1).size(); b++) {
                System.out.println((res[1].x / e.get(advancer-1).size() + 1) * b);
                g.drawImage(characterImage[b - 1].getBufferedImage(), ((res[1].x / (e.get(advancer-1).size() + 1)) * b) - (characterImage[b - 1].getWidth() / 2), (res[1].y / 4) + (characterImage[b - 1].getHeight() / 2), this);
            }

            // DialogueBox
            g.drawImage(dialogueBox.getBufferedImage(),res[1].x / 2 - dialogueBox.getWidth() / 2, res[1].y - dialogueBox.getHeight() - (res[1].x / 2 - dialogueBox.getWidth() / 2),this);
            g.drawString(e.get(advancer-1).get(0).getName().toString(), 100, 430);

            // Run once. Different from initStage. initStory runs after very first dialogue while initStage runs right after stage has been entered.
            if(initStory) {

                initStory = false;
            }
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            g.setColor(new Color(0,0,0));
        }
        else {
            if(initStage) {
                dialogueBox.setOpacity(dialogueTransparency);
                // System.out.println("dialoguebox transparency set to: " + dialogueTransparency);

            }
        }

        if(tempString != "") {
            g.drawString(tempString, 150, 550);
        }

        saveButton.paintComponent(g);
        configButton.paintComponent(g);

        // Use the bottom link for implementing string wrap around by distance used by font.
        // https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html

        initStage = false;
    }

}
