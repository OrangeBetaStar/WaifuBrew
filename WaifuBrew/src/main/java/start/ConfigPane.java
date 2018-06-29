package start;

import com.sun.java.swing.Painter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("serial")
public class ConfigPane extends JPanel implements ActionListener {

    private BufferedImage backgroundPicture;
    private javaxt.io.Image back_button;

    private JSlider dialogueTransSlider;
    private UIDefaults sliderDefaults;

    private int backButtonX = 1100;
    private int backButtonY = 600;

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private Handlerclass handler = new Handlerclass();

    private boolean backButtonUI = false;

    private int dialogueTransparency = 70;

     public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public ConfigPane() {
        try {
            backgroundPicture = ImageIO.read(new File(RESOURCE_PATH + "options-background.png"));
            // TODO: Add buttons (back, toggles, sound system?... etc)

            back_button = new javaxt.io.Image(RESOURCE_PATH + "config_back_button.png");
            dialogueTransSlider = new JSlider(0, 100, dialogueTransparency);

            addMouseListener(handler);
            addMouseMotionListener(handler);

            sliderDefaults = new UIDefaults();
            sliderDefaults.put("Slider.thumbWidth", 20);
            sliderDefaults.put("Slider.thumbHeight", 20);

            System.out.println("Outside of the things");

            // sliderDefaults.put(“Slider:SliderThumb[Pressed].backgroundPainter”ONLY WORKS WHEN PRESSED

            sliderDefaults.put("Slider:SliderTrack[Enabled].backgroundPainter", new Painter<JSlider>() {

                public void paint(Graphics2D g, JSlider c, int w, int h) {
                    System.out.println("Middle");
                    int arc         = 10;
                    int trackHeight = 8;
                    int trackWidth  = w - 2;
                    int fillTop     = 4;
                    int fillLeft    = 1;

                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.setStroke(new BasicStroke(1.5f));
                    g.setColor(Color.GRAY);
                    g.fillRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);

                    int fillBottom = fillTop + trackHeight;
                    int fillRight  = xPositionForValue(
                            c.getValue(), c,
                            new Rectangle(fillLeft, fillTop, trackWidth, fillBottom - fillTop));

                    g.setColor(Color.ORANGE);
                    g.fillRect(fillLeft + 1, fillTop + 1, fillRight - fillLeft, fillBottom - fillTop);

                    g.setColor(Color.WHITE);
                    g.drawRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);
                }
            });

            for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(laf.getName())){
                    try {
                        UIManager.setLookAndFeel(laf.getClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            for (Map.Entry<Object, Object> entry : UIManager.getLookAndFeelDefaults().entrySet()) {
                if ((entry.getKey()).toString().startsWith("Slider")){
                    System.out.println(entry.getKey() +" = "+ entry.getValue());
                }
            }

            dialogueTransSlider.putClientProperty("Nimbus.Overrides.InheritDefaults",false);
            dialogueTransSlider.putClientProperty("Nimbus.Overrides",sliderDefaults);


            add(dialogueTransSlider);


        } catch (IOException e) {
            System.out.println("File failure in Config class");
            e.printStackTrace(); // Wall of error
            System.exit(-1);
        }
    }

    protected int xPositionForValue(int value, JSlider slider, Rectangle trackRect) {
        int min = slider.getMinimum();
        int max = slider.getMaximum();
        int trackLength = trackRect.width;
        double valueRange = (double) max - (double) min;
        double pixelsPerValue = (double) trackLength / valueRange;
        int trackLeft = trackRect.x;
        int trackRight = trackRect.x + (trackRect.width - 1);
        int xPosition;

        xPosition = trackLeft;
        xPosition += Math.round(pixelsPerValue * ((double) value - min));

        xPosition = Math.max(trackLeft, xPosition);
        xPosition = Math.min(trackRight, xPosition);

        return xPosition;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundPicture != null) {
            // I want to centre the image that is 960:640 to widescreen format, but do not want to stretch. I will zoom in.

            // TBH I don't really need this next if statement ???
            if(getPreferredSize(backgroundPicture).width < WaifuBrew.getInstance().getRes()[1].getX()) {
                if((WaifuBrew.getInstance().getRes()[1].getX()/(double)getPreferredSize(backgroundPicture).width) * getPreferredSize(backgroundPicture).height < WaifuBrew.getInstance().getRes()[1].getY()) {
                    g.drawImage(backgroundPicture,0,0,(int)Math.round(WaifuBrew.getInstance().getRes()[1].getY() / (double)getPreferredSize(backgroundPicture).height * getPreferredSize(backgroundPicture).width), (int)Math.round(WaifuBrew.getInstance().getRes()[1].getY()), this);
                }
                else {
                    g.drawImage(backgroundPicture, 0, 0, (int)Math.round(WaifuBrew.getInstance().getRes()[1].getX()),(int)Math.round(WaifuBrew.getInstance().getRes()[1].getX() / (double)getPreferredSize(backgroundPicture).width * getPreferredSize(backgroundPicture).height), this);
                }
            }
            else {
                // TODO: Just copied from above. Will refine later
                if((WaifuBrew.getInstance().getRes()[1].getX()/(double)getPreferredSize(backgroundPicture).width) * getPreferredSize(backgroundPicture).height < WaifuBrew.getInstance().getRes()[1].getY()) {
                    g.drawImage(backgroundPicture,0,0,(int)Math.round(WaifuBrew.getInstance().getRes()[1].getY() / (double)getPreferredSize(backgroundPicture).height * getPreferredSize(backgroundPicture).width), (int)Math.round(WaifuBrew.getInstance().getRes()[1].getY()), this);
                }
                else {
                    g.drawImage(backgroundPicture, 0, 0, (int)Math.round(WaifuBrew.getInstance().getRes()[1].getX()),(int)Math.round(WaifuBrew.getInstance().getRes()[1].getX() / (double)getPreferredSize(backgroundPicture).width * getPreferredSize(backgroundPicture).height), this);
                }
            }

            // TODO: Add rest of the menu elements

        }
        javaxt.io.Image tempBackButton = back_button.copy();
        if(backButtonUI) {
            tempBackButton.setOpacity(100);
        }
        else {
            tempBackButton.setOpacity(20);
        }
        g.drawImage(tempBackButton.getBufferedImage(), (backButtonX - (getPreferredSize(tempBackButton.getBufferedImage()).width / 2)), (backButtonY - (getPreferredSize(tempBackButton.getBufferedImage()).height / 2)), getPreferredSize(tempBackButton.getBufferedImage()).width, getPreferredSize(tempBackButton.getBufferedImage()).height, this);

        g.drawString("Diologue Bar Transparency", 100, 180);

        // TODO: https://jasperpotts.com/blog/2008/08/skinning-a-slider-with-nimbus/

        // Save resource (in mouseEntered) - Didn't work
        repaint();
    }

    private class Handlerclass implements MouseListener, MouseMotionListener {
        public void mousePressed (MouseEvent event) {

        }
        public void mouseMoved (MouseEvent event) {
            if(event.getX() >= (backButtonX - (getPreferredSize(back_button.getBufferedImage()).width / 2)) && event.getY() >= (backButtonY - (getPreferredSize(back_button.getBufferedImage()).height / 2)) && event.getX() <= ((backButtonX - (getPreferredSize(back_button.getBufferedImage()).width / 2)) + back_button.getWidth()) && event.getY() <= ((backButtonY - (getPreferredSize(back_button.getBufferedImage()).height / 2)) + back_button.getHeight())) {
                backButtonUI = true;
            }
            else {
                backButtonUI = false;
            }
        }
        public void mouseDragged (MouseEvent event) {

        }
        public void mouseClicked (MouseEvent event) {

            if(event.getX() >= (backButtonX - (getPreferredSize(back_button.getBufferedImage()).width / 2)) && event.getY() >= (backButtonY - (getPreferredSize(back_button.getBufferedImage()).height / 2)) && event.getX() <= ((backButtonX - (getPreferredSize(back_button.getBufferedImage()).width / 2)) + back_button.getWidth()) && event.getY() <= ((backButtonY - (getPreferredSize(back_button.getBufferedImage()).height / 2)) + back_button.getHeight())) {
                WaifuBrew.getInstance().getGUIInstance().setStage(0);
                WaifuBrew.getInstance().getGUIInstance().revalidateGraphics();
            }

        }
        public void mouseEntered (MouseEvent event) {

        }
        public void mouseReleased (MouseEvent event) {

        }
        public void mouseExited (MouseEvent event) {

        }
    }

    public Dimension getPreferredSize(BufferedImage a) {
        if (a != null) {
            int width = a.getWidth();
            int height = a.getHeight();
            return new Dimension(width, height);
        }
        return super.getPreferredSize();
    }
}
