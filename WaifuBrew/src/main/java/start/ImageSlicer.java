package start;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSlicer {

    BufferedImage[] sprites;

    public ImageSlicer(int widthCut, int heightCut, String fileName, boolean containsPath) {
        try {
            BufferedImage imageSheet;
            if(!containsPath) {
                imageSheet = ImageIO.read(new File(WaifuBrew.getInstance().getResoucePath() + fileName));
            }
            else {
                imageSheet = ImageIO.read(new File(fileName));
            }
            imageSlice(widthCut, heightCut, imageSheet);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void imageSlice(int widthCut, int heightCut, BufferedImage imageSheet) {

        // AS YOU SEE I HAD TROUBLE CALCULATING THIS THING FOR SOME REASON??????????

        sprites = new BufferedImage[(imageSheet.getWidth()/widthCut) * (imageSheet.getHeight()/heightCut)];
        // System.out.println("Willing to cut this " + imageSheet.getWidth()/widthCut + " times horizontally");
        // System.out.println("and "+ imageSheet.getHeight()/heightCut + " times vertically");
        for(int verticalCalc = 0; verticalCalc < imageSheet.getHeight()/heightCut; verticalCalc++) {
            for(int horizontalCalc = 0; horizontalCalc < imageSheet.getWidth()/widthCut; horizontalCalc++) {
                // System.out.println("Cutting at ("+(widthCut*horizontalCalc)+", "+(heightCut*verticalCalc)+") with size of x: "+widthCut+", y: "+heightCut);
                // System.out.println("and putting it in... " + ((imageSheet.getWidth()/widthCut) * horizontalCalc) + verticalCalc);
                // System.out.println(verticalCalc + " " + horizontalCalc);
                // System.out.println((imageSheet.getWidth()/widthCut) * verticalCalc + horizontalCalc);
                sprites[(imageSheet.getWidth()/widthCut) * verticalCalc + horizontalCalc] = imageSheet.getSubimage(widthCut*horizontalCalc, heightCut*verticalCalc, widthCut, heightCut);
            }
        }
    }
    public BufferedImage[] getSprites() {
        return sprites;
    }
}
