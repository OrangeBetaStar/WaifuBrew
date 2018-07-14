package start;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

public class ImageLoader {

    private final String RESOURCE_PATH = WaifuBrew.getInstance().getResoucePath();
    private LinkedList<ImageDesc> load_first_images = new LinkedList<ImageDesc>();
    private LinkedList<ImageDesc> vectorImages = new LinkedList<ImageDesc>();
    private LinkedList<ImageDesc> bg_images = new LinkedList<ImageDesc>();

    public ArrayList<LinkedList<ImageDesc>> imgCompiler (LinkedList<String> fileList) {
        for(String loadImage : fileList) {
            // This will look inside resource folder and automatically load the images according to file names.
            if(loadImage.contains("load_first")) {
                if(loadImage.contains("startscreen_elementsheet")) {
                    ImageSlicer systemButtons = new ImageSlicer(500, 200, RESOURCE_PATH + loadImage, true);
                    BufferedImage[] systemImages = systemButtons.getSprites();
                    for(int a = 0; a < systemImages.length - 1; a++) {
                        load_first_images.add(new ImageDesc(Integer.toString(a), systemImages[a]));
                    }
                }
                // Any images that are used in effects (used with scaleVec like things)
                else if(loadImage.contains("whitebox")) {
                    vectorImages.add(new ImageDesc(loadImage, new javaxt.io.Image(RESOURCE_PATH + loadImage)));
                }
            }
            else if(loadImage.contains("bg")) {
                // STUB: need to make class that takes (description, image)
                bg_images.add(new ImageDesc(loadImage, new javaxt.io.Image(RESOURCE_PATH + loadImage)));
            }
        }

        ArrayList<LinkedList<ImageDesc>> imagePackage = new ArrayList<LinkedList<ImageDesc>>();
        imagePackage.add(load_first_images);
        imagePackage.add(vectorImages);
        imagePackage.add(bg_images);

        return imagePackage;
    }
}
