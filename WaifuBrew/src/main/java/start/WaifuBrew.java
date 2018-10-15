package start;
/*
 * Project by BetaStar and Gaia
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class WaifuBrew {

    // Get resolution
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GUI sample;
    public final String RESOURCE_PATH = "src/main/java/resources/";
    private ArrayList<ArrayList<ImageDesc>> fileList;
    private static WaifuBrew singleton;
    private static HashMap<String, Integer> configStorage = new HashMap<>();
    private static String[] configUI = new String[10];
    // Have a look below to see what each of the slots are for.


    // [0] is Computer monitor resolution
    // [1] is resolution of program window
    // [2] is where window should go (top-left) to be centered in the monitor
    private Point[] defaultSize = {
            new Point(screenSize.width, screenSize.height),
            new Point(1280, 720),
            new Point((screenSize.width / 2) - (1280 / 2), (screenSize.height / 2) - (720 / 2))};

    WaifuBrew() {

        initConfig();

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

    private void initConfig() {
        configStorage.put("dialogueTransparency", 70);
        configStorage.put("dialogueSpeed", 60);
        configStorage.put("fontSize", 30);
        configStorage.put("frameRate", 60);
        configStorage.put("GUIScaling", 100);
        configStorage.put("autoAdvancer", 0);
        configStorage.put("stage", 0);
        this.setFontName("Halogen");
    }

    public int getDialogueTransparency() {
        return configStorage.get("dialogueTransparency");
    }

    public void setDialogueTransparency(int dialogueTransparency) {
        configStorage.put("dialogueTransparency", dialogueTransparency);
    }

    public int getDialogueSpeed() {
        return configStorage.get("dialogueSpeed");
    }

    public void setDialogueSpeed(int dialogueSpeed) {
        configStorage.put("dialogueSpeed", dialogueSpeed);
    }

    public int getFrameRate() {
        return configStorage.get("frameRate");
    }

    public void setFrameRate(int frameRate) {
        configStorage.put("frameRate", frameRate);
    }

    public int getSystemGUIScale() {
        return configStorage.get("GUIScaling");
    }

    public void setSystemGUIScale(int GUIScale) {
        configStorage.put("GUIScaling", GUIScale);
    }

    public boolean getAutoAdvancer() {
        return (configStorage.get("autoAdvancer") == 1);
    }

    public void setAutoAdvancer(boolean autoAdvancer) {
        configStorage.put("autoAdvancer", autoAdvancer ? 1 : 0);
    }

    public int getFontSize() {
        return configStorage.get("fontSize");
    }

    public void setFontSize(int fontSize) {
        configStorage.put("fontSize", fontSize);
    }

    public int getStage() {
        return configStorage.get("stage");
    }

    public void setStage(int stage) {
        configStorage.put("stage", stage);
    }

    public String getResoucePath() {
        return RESOURCE_PATH;
    }

    public Point[] getRes() {
        return defaultSize;
    }

    public GUI getGUIInstance() {
        return sample;
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
        for (ImageDesc pictures : fileList.get(whichPile.getValue())) {
            if (pictures.getImageDescription().toLowerCase().contains(whichOne.toLowerCase())) {
                return pictures.getImageItself();
            }
        }
        // Failed case
        System.out.println("Couldn't find: " + whichOne);
        javaxt.io.Image createDefaultImage = new javaxt.io.Image(getImageByName(ImageSelector.VECTOR, "blackbox"));
        createDefaultImage.resize(getFontSize() * 2, getFontSize());
        createDefaultImage.addText(whichOne, 0, createDefaultImage.getHeight() / 2, null, getFontSize(), 128, 128, 128);
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
