package start;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSlicer {

    private BufferedImage[] sprites;

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

        // Calculates size of the array.
        sprites = new BufferedImage[(imageSheet.getWidth()/widthCut) * (imageSheet.getHeight()/heightCut)];

        // Cuts and puts it in the array.
        for(int verticalCalc = 0; verticalCalc < imageSheet.getHeight()/heightCut; verticalCalc++) {
            for(int horizontalCalc = 0; horizontalCalc < imageSheet.getWidth()/widthCut; horizontalCalc++) {
                sprites[(imageSheet.getWidth()/widthCut) * verticalCalc + horizontalCalc] = imageSheet.getSubimage(widthCut*horizontalCalc, heightCut*verticalCalc, widthCut, heightCut);
            }
        }
    }
    public BufferedImage[] getSprites() {
        return sprites;
    }
}
