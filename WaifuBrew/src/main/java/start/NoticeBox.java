package start;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// This is what will be used to notify users if something may cause undesirable operation. (i.e.: going back to screen when settings are not saved.)
public class NoticeBox extends JPanel {

    // Size of notice box
    private int length = WaifuBrew.getInstance().getRes()[1].x / 2;
    private int height = WaifuBrew.getInstance().getRes()[1].y / 2;

    // This will be (slightly transparent) backing of the notice box.
    private javaxt.io.Image backgroundPane;

    // Custom Button 1
    private CustomButton[] button;

    // Active Boolean
    private boolean isActive = false;

    // Boolean to keep track if it has two buttons
    private boolean dualButton = false;

    // Dialogue text string (warning message)
    private String dialogueBoxText = "";

    // Font things
    private Font activeFont;
    private BufferedInputStream myStream;
    private int fontSize = 24;

    private Handlerclass miniHandler = new Handlerclass();
    private java.awt.image.ImageObserver that = WaifuBrew.getInstance().getGUIInstance();

    public NoticeBox(String text, String centreButton, boolean centreInvert) {
        button = new CustomButton[1];
        dialogueBoxText = text;
        backgroundPane = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        button[1] = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 2), (WaifuBrew.getInstance().getRes()[1].y / 2) + (height / 2), centreButton, Origin.MIDDLE_BOTTOM, 0, centreInvert);
        addMouseListener(button[0].retrieveMouseHandler());
        addMouseMotionListener(button[0].retrieveMouseHandler());

        initFont();
    }

    public NoticeBox(String text, String leftButton, String rightButton, boolean leftInvert, boolean rightInvert) {
        button = new CustomButton[2];
        dialogueBoxText = text;
        dualButton = true;
        backgroundPane = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        button[0] = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 2) - (length / 2), (WaifuBrew.getInstance().getRes()[1].y / 2) + (height / 2), leftButton, Origin.LEFT_BOTTOM, 0, leftInvert);
        button[1] = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 2) + (length / 2), (WaifuBrew.getInstance().getRes()[1].y / 2) + (height / 2), rightButton, Origin.RIGHT_BOTTOM, 0, rightInvert);
        addMouseListener(button[0].retrieveMouseHandler());
        addMouseMotionListener(button[0].retrieveMouseHandler());
        addMouseListener(button[1].retrieveMouseHandler());
        addMouseMotionListener(button[1].retrieveMouseHandler());

        initFont();
    }

    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    public CustomButton[] getButton() {
        return button;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        backgroundPane.resize(length, height);
        backgroundPane.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        backgroundPane.setOpacity(50);
        g.drawImage(backgroundPane.getBufferedImage(), (WaifuBrew.getInstance().getRes()[1].x / 2) - (length / 2), (WaifuBrew.getInstance().getRes()[1].y / 2) - (height / 2), that);
        // Calcuate the width of the text with font: int width = g.getFontMetrics().stringWidth(text);
        g.setFont(activeFont);
        g.setColor(new Color(0,0,0));
        g.drawString(dialogueBoxText, (WaifuBrew.getInstance().getRes()[1].x / 2) - (g.getFontMetrics().stringWidth(dialogueBoxText) / 2), (WaifuBrew.getInstance().getRes()[1].y / 2));

        for(int paintButton = 0; paintButton < button.length; paintButton++) {
            button[paintButton].paintComponent(g);
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean activate) {
        isActive = activate;
        System.out.println("Dialogue set to active");
    }

    private class Handlerclass extends MasterHandlerClass {
        public void mouseClicked(MouseEvent e) {

        }
        public void mousePressed(MouseEvent e) {

        }
        public void mouseReleased(MouseEvent e) {

        }
        public void mouseMoved(MouseEvent e) {

        }
        public void mouseEntered(MouseEvent e) {

        }
        public void mouseDragged(MouseEvent e) {

        }
        public void mouseExited(MouseEvent e) {

        }
    }
    private void initFont() {
        try {
            // For real time preview later when font change is implemented
            myStream = new BufferedInputStream(new FileInputStream(WaifuBrew.getInstance().getResoucePath() + WaifuBrew.getInstance().getFontName() + ".ttf"));
            fontSize = 24;
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
}
