package start;
/*
 * Project by Sidetail & Gaia
 *
 */

import javax.swing.*;
import java.awt.*;

public class WaifuBrew extends WaifuException{

    // Get resolution
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GUI sample;
    private final String RESOURCE_PATH = "src/main/java/resources/";

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
        setFrameRate(2);
        setStage(0);
    }

    private static WaifuBrew singleton  = new WaifuBrew();

    public static WaifuBrew getInstance() {
        return singleton;
    }

    public static void main(String[] args) throws WaifuException {
        try {
            WaifuBrew programStart = new WaifuBrew();
            programStart.start();
            singleton = programStart;

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
    // 3 -
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
