import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mouseClass extends JPanel {

    public mouseClass() {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                System.out.println(me);
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new mouseClass());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}