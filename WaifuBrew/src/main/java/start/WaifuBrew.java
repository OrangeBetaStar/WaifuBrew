package start;
/*
 * Project by BetaStar and Gaia
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WaifuBrew {

    // Get resolution
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GUI sample;
    public final String RESOURCE_PATH = "src/main/java/resources/";
    private BufferedImage[] systemImages;
    private ArrayList<ArrayList<ImageDesc>> fileList;

    private static WaifuBrew singleton;

    // [n] array number / = n value
    // [0] is dialogueTransparency = 70
    // [1] is dialogueSpeed = 50
    private static int[] configStorage = new int[20]; //Increase if needed.
    private static String[] configUI = new String[10];
    // Have a look below to see what each of the slots are for.


    // [0] is Computer monitor resolution
    // [1] is resolution of program window
    // [2] is where window should go (top-left) to be centered in the monitor
    private Point[] defaultSize = {
            new Point(screenSize.width, screenSize.height),
            new Point(1280, 720),
            new Point((screenSize.width / 2) - (1280 / 2),(screenSize.height / 2) - (720 / 2))};

    WaifuBrew() {

        setDialogueTransparency(70);
        setDialogueSpeed(60);
        setFontSize(30);
        setFrameRate(60);
        setStage(0);
        setAutoAdvancer(false);
        setSystemGUIScale(100);
        setFontName("Halogen");

        ThreadFileLoad tfl = new ThreadFileLoad();
        ThreadLoadingScreen tls = new ThreadLoadingScreen();
        Thread ttfl = new Thread(tfl, "ThreadFileLoad");
        Thread ttls = new Thread(tls, "ThreadLoadScreen");

        ttfl.start();
        ttls.start();

        try {
            ttfl.join();
            ttls.join();
        } catch (InterruptedException e) {
            System.out.println("Thread Error");
            e.printStackTrace();
        }

        System.out.println("Threads should be done by now!  ");

        // Getting files ready-ied by thread.
        fileList = tfl.getFileList();
        systemImages = tfl.getSystemImages();
    }

    public static WaifuBrew getInstance() {
        return singleton;
    }

    public static void main(String[] args) throws WaifuException {
        try {
            singleton = new WaifuBrew();
            singleton.start();

        }
        // catches any exception
        catch (Exception e) {
            System.out.println("Catastrophic error!");
            throw new WaifuException(e);
        }
    }

    // ConfigUI:
    // 0 - Font

    public String getFontName() {
        return configUI[0];
    }

    public void setFontName(String fontName) {
        configUI[0] = fontName;
    }

    // ConfigStorage:
    // 0 - Dialog Transparency
    // 1 - Text Speed
    // 2 - Frame Rate
    // 3 - GUI Scaling
    // 4 - Auto Dialogue Advancer
    // 5 - Dialog Font Size
    // 6 -
    // 7 -
    // 8 -
    // 9 - Stage

    public int getDialogueTransparency () {
        return configStorage[0];
    }

    public void setDialogueTransparency (int dialogueTransparency) {
        configStorage[0] = dialogueTransparency;
    }

    public int getDialogueSpeed () {
        return configStorage[1];
    }

    public void setDialogueSpeed (int dialogueSpeed) {
        configStorage[1] = dialogueSpeed;
    }

    public int getFrameRate() {
        return configStorage[2];
    }

    public void setFrameRate(int frameRate) {
        configStorage[2] = frameRate;
    }

    public int getSystemGUIScale() { return configStorage[3]; }

    public void setSystemGUIScale(int GUIScale) { configStorage[3] = GUIScale; }

    public boolean getAutoAdvancer() {
        return (configStorage[4] == 1);
    }

    public void setAutoAdvancer(boolean autoAdvancer) {
        if(autoAdvancer) {
            configStorage[4] = 1;
        }
        else {
            configStorage[4] = 0;
        }
    }

    public int getFontSize() {
        return configStorage[5];
    }

    public void setFontSize(int fontSize) {
        configStorage[5] = fontSize;
    }

    public int getStage() {
        return configStorage[9];
    }

    public void setStage(int stage) {
        configStorage[9] = stage;
    }

    public String getResoucePath () {
        return RESOURCE_PATH;
    }

    public Point[] getRes () {
        return defaultSize;
    }

    public GUI getGUIInstance(){
        return sample;
    }

    public BufferedImage[] getSystemImage() {
        return systemImages;
    }

    public ArrayList<ImageDesc> getImageSet(int index) {
        return fileList.get(index);
    }
    public ArrayList<ImageDesc> getImageSet(ImageSelector imageSelector) {
        return fileList.get(imageSelector.getValue());
    }

    // No image will result in blank image.
    public BufferedImage getImageByName(ImageSelector whichPile, String whichOne) {

        // Getting ImageSelector.VECTOR - blackbox is a fail-safe
        for(ImageDesc pictures : fileList.get(whichPile.getValue())) {
            if(pictures.getImageDescription().contains(whichOne)) {
                return pictures.getImageItself();
            }
        }
        javaxt.io.Image createDefaultImage = new javaxt.io.Image(getImageByName(ImageSelector.VECTOR, "blackbox"));
        createDefaultImage.resize(getFontSize() * 2, getFontSize());
        createDefaultImage.addText(whichOne, 0, createDefaultImage.getHeight()/2, null, getFontSize(), 128, 128, 128);
        return createDefaultImage.getBufferedImage();
    }

    public void start() {
        sample = new GUI();
        sample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // default size
        sample.setSize(defaultSize[1].x, defaultSize[1].y);
        // disable resizing of the window since scaling is retarded.
        sample.setResizable(false);
        // this calculates the window should start from (centered)
        sample.setLocation(defaultSize[2].x, defaultSize[2].y);
        //sample.setLayout(new FlowLayout());
        sample.setVisible(true);
    }
}
