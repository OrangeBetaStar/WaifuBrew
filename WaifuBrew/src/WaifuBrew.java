/*
 * Project by Sidetail & Gaia

*/

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class WaifuBrew {

    // Get resolution
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GUI sample;
    // [0] is Computer monitor resolution
    // [1] is resolution of program window
    // [2] is where window should go (top-left) to be centered in the monitor
    private Point[] defaultSize = {
            new Point(screenSize.width, screenSize.height),
            new Point(1280, 720),
            new Point((screenSize.width / 2) - (1280 / 2) ,(screenSize.height / 2) - (720 / 2))};

    //sample waifu
    Waifu currWaifu;

    WaifuBrew() {
        createWaifu();
    }

    public static void main(String[] args) {

        WaifuBrew programStart = new WaifuBrew();
        programStart.visual(programStart);
        programStart.createWaifu();
    }

    public Point[] getRes () {
        return defaultSize;
    }

    public Waifu getWaifu() {
        return currWaifu;
    }


    public void visual(WaifuBrew a) {
        sample = new GUI(a);
        sample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // default size
        sample.setSize(defaultSize[1].x, defaultSize[1].y);
        // this calculates the window should start from (centered)
        sample.setLocation(defaultSize[2].x, defaultSize[2].y);
        //sample.setLayout(new FlowLayout());
        sample.setVisible(true);

    }

    public Waifu createWaifu(){

        // Just default best girl
        Waifu nicoNii = new Waifu("Nico",43, 153, new int[] {74, 57, 79}, Mood.HAPPY);
        currWaifu = nicoNii;
        return nicoNii;
    }


}
