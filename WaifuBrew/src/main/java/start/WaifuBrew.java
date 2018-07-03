package start;
/*
 * Project by Sidetail & Gaia
 *
 */

import javax.swing.*;
import java.awt.*;

public class WaifuBrew {

    // Get resolution
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GUI sample;
    private static WaifuBrew instance = null;
    private final String RESOURCE_PATH = "src/main/java/resources/";

    // TODO: CREATE ARRAYS OF SETTINGS
    private int dialogueTransparency = 100; //Temporary solution

    // [0] is Computer monitor resolution
    // [1] is resolution of program window
    // [2] is where window should go (top-left) to be centered in the monitor
    private Point[] defaultSize = {
            new Point(screenSize.width, screenSize.height),
            new Point(1280, 720),
            new Point((screenSize.width / 2) - (1280 / 2) ,(screenSize.height / 2) - (720 / 2))};

    WaifuBrew() {

    }

    private static WaifuBrew singleton  = new WaifuBrew();

    public static WaifuBrew getInstance() {
        return singleton;
    }

    public static void main(String[] args) {
        try {
            WaifuBrew programStart = new WaifuBrew();
            programStart.start();
            singleton = programStart;
        }
        // catches any exception
        catch (Exception e) {
            System.out.println("Catastrophic error!");
            e.printStackTrace();
        }
    }

    public int getDialogueTransparency () {
        return dialogueTransparency;
    }

    public void setDialogueTransparency (int dialogueTransparency) {
        this.dialogueTransparency = dialogueTransparency;
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
