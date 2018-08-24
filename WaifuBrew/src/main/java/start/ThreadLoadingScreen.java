package start;

import javax.swing.*;
import java.awt.*;

public class ThreadLoadingScreen implements Runnable {

    private final String RESOURCE_PATH = "src/main/java/resources/";
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Loadscreen Thread running");

        ImageIcon loadingImage = new ImageIcon((RESOURCE_PATH+"loading.gif"));
        JWindow window = new JWindow();
            window.getContentPane().add(
                    new JLabel("Loading... :D", loadingImage, JLabel.CENTER));

        window.setBounds((screenSize.width / 2) - (1280 / 2) ,(screenSize.height / 2) - (720 / 2), 1280, 720);
        window.setVisible(true);
        try {
            // What if HDD is so slow that it's over this sleep time?
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
    }
}
