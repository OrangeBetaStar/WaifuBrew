/*
 * Project by Sidetail & Gaia

*/

import javax.swing.*;
import java.io.*;
import java.awt.*;

public class WaifuBrew {

    private static int WIDTH = 1280;
    private static int HEIGHT = 720;

    GUI sample;

    //sample waifu
    Waifu currWaifu;

    public static void main(String[] args) {
        WaifuBrew sampleWaifu = new WaifuBrew();

        sampleWaifu.visual(sampleWaifu.createWaifu());

        File f = new File("resources/" + sampleWaifu.getWaifu().name.toLowerCase() + "-" + sampleWaifu.createWaifu().mood.toString().toLowerCase() + ".jpg");
        System.out.println(f.getAbsoluteFile());
        ImageIcon image = new ImageIcon(f.getAbsolutePath());
        JLabel imageLabel = new JLabel(image);
    }

    public Waifu getWaifu() {
        return currWaifu;
    }

    public void visual(Waifu waifu) {
        sample = new GUI();
        sample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sample.setSize(WIDTH, HEIGHT);
        sample.setVisible(true);


        File f = new File("WaifuBrew/res/resources/icon.jpg");
        sample.setIconImage(new ImageIcon(f.getAbsolutePath()).getImage());

        File waifuFile = new File("resources/" + waifu.name.toLowerCase() + "-" + waifu.mood.toString().toLowerCase() + ".jpg");
        System.out.println(waifuFile.getAbsoluteFile());
    }

    public Waifu createWaifu(){

        Waifu nicoNii = new Waifu("Nico",43, 153, new int[] {74, 57, 79}, Mood.HAPPY);
        currWaifu = nicoNii;
        return nicoNii;
    }
}
