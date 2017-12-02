/*
 * Project by Sidetail & Gaia

*/

import javax.swing.JFrame;
import java.awt.*;

public class WaifuBrew {
    BorderLayout asdf;
    public static void main(String[] args) {
        createWaifu();
        visual();
    }

    public static void visual() {
        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void createWaifu(){
        Waifu asdf = new Waifu("asdf",45, 140);
        System.out.println("Height is " + asdf.getHeight() + "cm.");

        Tsundere shana = new Tsundere("perry", 60, 170);
        System.out.println("Wieght is " + shana.getWeight());

    }
}
