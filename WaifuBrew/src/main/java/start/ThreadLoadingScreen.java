package start;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ThreadLoadingScreen implements Runnable {

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Loadscreen Thread running");

        JWindow window = new JWindow();
        try {
            window.getContentPane().add(
                    new JLabel("", new ImageIcon(new URL("http://docs.oracle.com/javase/tutorial/uiswing/examples/misc/SplashDemoProject/src/misc/images/splash.gif")), SwingConstants.CENTER));
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL Exception");
        }

        window.setBounds((screenSize.width / 2) - (1280 / 2) ,(screenSize.height / 2) - (720 / 2), 1280, 720);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
    }
}
