package start;
import org.json.JSONException;
import parser.DialogueParser;
import parser.exception.DialogueDataMissingException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimationPane extends JPanel {

    private BufferedImage scrollingImage;

    private boolean initStage = true;
    private int xPos = 0;
    private int direction = 1;
    private double rotationDeg = 0;
    private Handlerclass handler = new Handlerclass();
    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private javaxt.io.Image dialogueBox;
    private DialogueParser dp;
    private Point[] res;
    private int dialogueTransparency = 100;

    //Load elements
    private javaxt.io.Image spinningThing;

    private javaxt.io.Image saveButton;
    private javaxt.io.Image loadButton;
    private javaxt.io.Image configButton;


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

            scrollingImage = ImageIO.read(new File(RESOURCE_PATH + "black.png"));
            dialogueBox = new javaxt.io.Image(RESOURCE_PATH+"dialogbar.png");

            // Fix the image names later
            saveButton = new javaxt.io.Image(RESOURCE_PATH + "black.png");
            loadButton = new javaxt.io.Image(RESOURCE_PATH + "black.png");
            configButton = new javaxt.io.Image(RESOURCE_PATH + "black.png");
            spinningThing = new javaxt.io.Image(RESOURCE_PATH + "black.png");


            dialogueBox.resize((int)(dialogueBox.getWidth() * 0.9), (int)(dialogueBox.getHeight() * 0.9),true);

            dp = new DialogueParser(RESOURCE_PATH + "test.json");
            dp.parse();
            e = dp.getPackagedDialogue();

            Timer timer = new Timer(1, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    xPos += direction;
                    rotationDeg += direction;
                    if (xPos + scrollingImage.getWidth() > getWidth()) {
                        xPos = getWidth() - scrollingImage.getWidth();
                        direction *= -1;
                        rotationDeg *= -1;
                        //  System.out.println("REEEEEEEEEEEEEEEEEE");
                    } else if (xPos < 0) {
                        xPos = 0;
                        rotationDeg = 0;
                        direction *= -1;
                        rotationDeg *= -1;
                        //  System.out.println("AHHHHHHHHHHHHHHHHHH");
                    }
                    repaint();
                }
            });

            Timer stringTimer = new Timer(50, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(!a.isEmpty()) {
                        if (tempString.length() != a.length()) {
                            tempString = tempString + a.charAt(tempString.length());
                        }
                    }
                    repaint();
                }
            });

            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.start();

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
    public Dimension getPreferredSize() {
        return scrollingImage == null ? super.getPreferredSize() : new Dimension(scrollingImage.getWidth() * 4, scrollingImage.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(initStage) {
            System.out.println("Setting dialogueTrans to " + WaifuBrew.getInstance().getDialogueTransparency() + "%");
            dialogueTransparency = WaifuBrew.getInstance().getDialogueTransparency();
        }

        // Needs new image every rotation since reusing will make image blurry and hot garbage. (reconversion after reconversion of a same image)
        javaxt.io.Image sampleImage = new javaxt.io.Image(RESOURCE_PATH + "black.png");

        // TODO: USE copy() instead of getting image every time
        sampleImage.rotate(rotationDeg);
        g.drawImage(sampleImage.getBufferedImage(),xPos - (sampleImage.getWidth() / 2),getSize().height / 2 - (scrollingImage.getHeight() / 2 ) - (sampleImage.getHeight() / 2), this);

        g.drawImage(sampleImage.getBufferedImage(),xPos - (sampleImage.getWidth() / 2),getSize().height / 2 - (scrollingImage.getHeight() / 2 ) - (sampleImage.getHeight() / 2) + 300, this);

        // Does this have to be declared every time?
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.setColor(new Color(0,0,0));

        // Do not show character on first viewing
        if(advancer != 0) {

            // Character
            javaxt.io.Image characterImage = new javaxt.io.Image(RESOURCE_PATH + e.get(advancer-1).get(0).getName().toString().toLowerCase() + "-" + e.get(advancer-1).get(0).getMood().toString().toLowerCase() + ".png");
            g.drawImage(characterImage.getBufferedImage(), 400, 200, this);

            // DialogueBox
            g.drawImage(dialogueBox.getBufferedImage(),res[1].x / 2 - dialogueBox.getWidth() / 2, res[1].y - dialogueBox.getHeight() - (res[1].x / 2 - dialogueBox.getWidth() / 2),this);
            g.drawString(e.get(advancer-1).get(0).getName().toString(), 100, 430);

            // Other buttons (save, load, config)
            javaxt.io.Image tempSpinningThing = spinningThing.copy();
            tempSpinningThing.rotate(rotationDeg);
            // System.out.println(rotationDeg);

            // Fix the bottom part
            tempSpinningThing.setOpacity((rotationDeg%360) / 360);
            // Fix the location since square doesn't spin cleanly.
            g.drawImage(tempSpinningThing.getBufferedImage(), 1000 - tempSpinningThing.getWidth() / 2, 600 - tempSpinningThing.getHeight() / 2, this);
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

        // Use the bottom link for implementing string wrap around by distance used by font.
        // https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html

        initStage = false;
    }

}
