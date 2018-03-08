/*
 * Project by Sidetail & Gaia

*/

import javax.swing.*;

public class WaifuBrew {

    GUI sample;
    private static int WIDTH = 1280;
    private static int HEIGHT = 720;

    //sample waifu
    Waifu currWaifu;

    WaifuBrew() {
        createWaifu();
    }


    public static void main(String[] args) {

        WaifuBrew programStart = new WaifuBrew();

        programStart.visual(programStart.createWaifu());
    }

    public Waifu getWaifu() {
        return currWaifu;
    }


    public void visual(Waifu waifu) {
        sample = new GUI(waifu);
        sample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sample.setSize(WIDTH, HEIGHT);
        sample.setVisible(true);

    }

    public Waifu createWaifu(){

        Waifu nicoNii = new Waifu("Nico",43, 153, new int[] {74, 57, 79}, Mood.HAPPY);
        currWaifu = nicoNii;
        return nicoNii;
    }


}
