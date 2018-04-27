package start;/*
 * Project by Sidetail & Gaia

*/

import javax.swing.*;
import java.awt.*;

public class WaifuBrew {

    // Get resolution
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GUI sample;
    // [0] is Computer monitor resolution
    // [1] is resolution of program window
    // [2] is where window should go (top-left) to be centered in the monitor
    private  Point[] defaultSize = {
            new Point(screenSize.width, screenSize.height),
            new Point(1280, 720),
            new Point((screenSize.width / 2) - (1280 / 2) ,(screenSize.height / 2) - (720 / 2))};

    WaifuBrew() {
        // lol
    }

    public static void main(String[] args) {

        WaifuBrew programStart = new WaifuBrew();
        programStart.visual(programStart);
    }

    public Point[] getRes () {
        return defaultSize;
    }

    public void visual(WaifuBrew a) {
        sample = new GUI(a);
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
