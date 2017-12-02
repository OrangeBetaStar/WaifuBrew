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
        Waifu asdf = new Waifu(45, 140);
        System.out.println("Height is " + asdf.getHeight() + "cm.");
    }
}
