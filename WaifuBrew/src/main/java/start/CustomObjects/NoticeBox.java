package start.CustomObjects;

import start.Loader.ImageSelector;
import start.Loader.WaifuBrew;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// This is what will be used to notify users if something may cause undesirable operation. (i.e.: going back to screen when settings are not saved.)
public class NoticeBox extends InteractiveObjects {

    // Size of notice box
    private int length = WaifuBrew.getInstance().getRes()[1].x / 2;
    private int height = WaifuBrew.getInstance().getRes()[1].y / 2;

    // TODO: Perhaps this needs to be updated when the size of the window has been changed.
    private int x = (WaifuBrew.getInstance().getRes()[1].x / 2) - (length / 2);
    private int y = (WaifuBrew.getInstance().getRes()[1].y / 2) - (height / 2);

    // This will be (slightly transparent) backing of the notice box.
    private javaxt.io.Image backgroundPane;

    // Custom Button 1
    private CustomButton[] button;

    // Active Boolean
    private boolean isActive = false;

    // Dialogue text string (warning message)
    private String dialogueBoxText = "";

    private int padding = 10;

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
        button[1] = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 2), (WaifuBrew.getInstance().getRes()[1].y / 2) + (height / 2) + padding, centreButton, Origin.MIDDLE_BOTTOM, 0, centreInvert);
        addMouseListener(button[0].retrieveMouseHandler());
        addMouseMotionListener(button[0].retrieveMouseHandler());

        initFont();
    }

    public NoticeBox(String text, String leftButton, String rightButton, boolean leftInvert, boolean rightInvert) {
        button = new CustomButton[2];
        dialogueBoxText = text;
        backgroundPane = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        button[0] = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 2) - (length / 2) + padding, (WaifuBrew.getInstance().getRes()[1].y / 2) + (height / 2) - padding, leftButton, Origin.LEFT_BOTTOM, 0, leftInvert);
        button[1] = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 2) + (length / 2) - padding, (WaifuBrew.getInstance().getRes()[1].y / 2) + (height / 2) - padding, rightButton, Origin.RIGHT_BOTTOM, 0, rightInvert);
        addMouseListener(button[0].retrieveMouseHandler());
        addMouseListener(button[1].retrieveMouseHandler());

        initFont();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getAbsoluteX() {
        return this.x + this.length / 2;
    }

    @Override
    public int getAbsoluteY() {
        return this.y + this.height / 2;
    }

    @Override
    public int getWidth() {
        return length;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public Handlerclass retrieveMouseHandler() {
        return miniHandler;
    }

    public CustomButton[] getButton() {
        return button;
    }

    @Override
    public void paintComponent(Graphics g) {

        backgroundPane.resize(length, height);
        backgroundPane.setBackgroundColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
        backgroundPane.setOpacity(50);
        g.drawImage(backgroundPane.getBufferedImage(), x, y, that);

        // Calcuate the width of the text with font: int width = g.getFontMetrics().stringWidth(text);
        g.setFont(activeFont);
        g.setColor(new Color(0, 0, 0));
        g.drawString(dialogueBoxText, (WaifuBrew.getInstance().getRes()[1].x / 2) - (g.getFontMetrics().stringWidth(dialogueBoxText) / 2), (WaifuBrew.getInstance().getRes()[1].y / 2));

        for (int paintButton = 0; paintButton < button.length; paintButton++) {
            button[paintButton].paintComponent(g);
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean activate) {
        isActive = activate;
    }

    private class Handlerclass extends MasterHandlerClass {
    }

    private void initFont() {
        try {
            // For real time preview later when font change is implemented
            myStream = new BufferedInputStream(new FileInputStream(WaifuBrew.getInstance().getResoucePath() + WaifuBrew.getInstance().getSystemFont() + ".ttf"));
            fontSize = 24;
            Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            activeFont = ttfBase.deriveFont(Font.PLAIN, fontSize);
        } catch (FontFormatException ex) {
            ex.printStackTrace();
            System.err.println(myStream.toString() + " not loaded.  Using serif font.");
            activeFont = new Font("serif", Font.PLAIN, fontSize);
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException in ConfigPane.initFPS()");
            System.err.println(myStream.toString() + " not loaded.  Using serif font.");
            activeFont = new Font("serif", Font.PLAIN, fontSize);
        } catch (IOException ex) {
            System.out.println("IOException in ConfigPane.initFPS()");
            System.err.println(myStream.toString() + " not loaded.  Using serif font.");
            activeFont = new Font("serif", Font.PLAIN, fontSize);
        }
    }
}
