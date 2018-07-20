package start;
/*
 * Project by Sidetail & Gaia
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WaifuBrew{

    // Get resolution
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GUI sample;
    private final String RESOURCE_PATH = "src/main/java/resources/";
    private BufferedImage[] systemImages = new BufferedImage[10];
    ArrayList<ArrayList<ImageDesc>> fileList;

    private static WaifuBrew singleton;

    // [n] array number / = n value
    // [0] is dialogueTransparency = 70
    // [1] is dialogueSpeed = 50
    private static int[] configStorage = new int[10]; //Increase if needed.


    // [0] is Computer monitor resolution
    // [1] is resolution of program window
    // [2] is where window should go (top-left) to be centered in the monitor
    private Point[] defaultSize = {
            new Point(screenSize.width, screenSize.height),
            new Point(1280, 720),
            new Point((screenSize.width / 2) - (1280 / 2) ,(screenSize.height / 2) - (720 / 2))};

    WaifuBrew() {

        setDialogueTransparency(70);
        setDialogueSpeed(5);
        setFrameRate(60);
        setStage(0);
        setSystemGUIScale(100);
        fileList = new ImageLoader(RESOURCE_PATH).imgCompiler(new FindFile().listFile(RESOURCE_PATH, ".png"));

        for(ImageDesc buttons : fileList.get(0)) {
            systemImages[Integer.parseInt(buttons.getImageDescription())] = buttons.getImageItself();
        }


        // TODO: LOAD SYSTEM BG IMAGES
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

    // 0 - Dialog Transparency
    // 1 - Text Speed
    // 2 - Frame Rate
    // 3 - GUI Scaling
    // 4 -
    // 5 -
    // 6 -
    // 7 -
    // 8 -
    // 9 - Stage

    public int getDialogueTransparency () {
        return configStorage[0];
    }

    public void setDialogueTransparency (int dialogueTransparency) {
        this.configStorage[0] = dialogueTransparency;
    }

    public int getDialogueSpeed () {
        return configStorage[1];
    }

    public void setDialogueSpeed (int dialogueSpeed) {
        this.configStorage[1] = dialogueSpeed;
    }

    public int getFrameRate() {
        return configStorage[2];
    }

    public void setFrameRate(int frameRate) {
        this.configStorage[2] = frameRate;
    }

    public int getSystemGUIScale() { return configStorage[3]; }

    public void setSystemGUIScale(int GUIScale) { this.configStorage[3] = GUIScale; }

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

    /*
    public void setSystemImage(BufferedImage[] systemImage) {

        this.systemImages = systemImage;
    }
    */

    public BufferedImage getImageByName(ImageSelector whichPile, String whichOne) {

        for(ImageDesc pictures : fileList.get(whichPile.getValue())) {
            if(pictures.getImageDescription().contains(whichOne)) {
                return pictures.getImageItself();
            }
        }
        return null;
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
