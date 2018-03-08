/*
 * Project by Sidetail & Gaia

*/

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class WaifuBrew {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    GUI sample;
    private Point center = new Point(screenSize.width / 2, screenSize.height / 2);
    private Point2D.Double size = new Point2D.Double(1280, 720);

    //sample waifu
    Waifu currWaifu;

    WaifuBrew() {
        createWaifu();
    }

    public GUI getGUIStat (){
        return sample;
    }

    public static void main(String[] args) {

        WaifuBrew programStart = new WaifuBrew();
        programStart.visual(programStart);
        programStart.createWaifu();

    }

    public Waifu getWaifu() {
        return currWaifu;
    }


    public void visual(WaifuBrew a) {
        sample = new GUI(a);
        sample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sample.setSize((int)size.x, (int)size.y);
        sample.setLocation(center);
        sample.setLayout(new FlowLayout());
        sample.setVisible(true);

    }

    public Waifu createWaifu(){

        // Just default trash
        Waifu nicoNii = new Waifu("Nico",43, 153, new int[] {74, 57, 79}, Mood.HAPPY);
        currWaifu = nicoNii;
        return nicoNii;
    }


}
