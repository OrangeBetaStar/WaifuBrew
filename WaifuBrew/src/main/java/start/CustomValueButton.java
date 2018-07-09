package start;

import java.awt.*;

public class CustomValueButton extends CustomButton {

    private String[] valueStrings;
    private String showValueg = "";
    private boolean centered = false;

    public CustomValueButton(int x, int y, String fileName, String[] valueStrings) {
        super(x, y, fileName);
        this.valueStrings = valueStrings;
    }

    public CustomValueButton(int x, int y, String fileName, boolean centered, String[] valueStrings) {
        super(x, y, fileName, centered);
        this.valueStrings = valueStrings;
        this.centered = centered;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!centered) {
        }

    }

}
