package start.Loader;

import javax.swing.*;
import java.awt.*;

public class ThreadLoadingScreen implements Runnable {

    private final String RESOURCE_PATH = "src/main/java/resources/";
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Loadscreen Thread running");

        ImageIcon loadingImage = new ImageIcon((RESOURCE_PATH + "loading.gif"));
        // final javaxt.io.Image backgroundImage = new javaxt.io.Image(RESOURCE_PATH + "bg_bath.png");

        JWindow window = new JWindow();
        //window.setLayout(null);
        window.setBounds((screenSize.width / 2) - (1280 / 2), (screenSize.height / 2) - (720 / 2), 1280, 720);
        window.setVisible(true);
        window.getContentPane().add(
        /*
        new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.drawImage(backgroundImage.getBufferedImage(), 0 , 0, this);
          for(int a = 0; a < 720; a++) {
              g.drawString("helpppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp", 0, a);
              }
          }
        },
*/
                new JLabel("Loading... :D", loadingImage, JLabel.CENTER));

        window.validate();


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
