package start;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

public class ImageLoader {

    private String RESOURCE_PATH;
    private ArrayList<ImageDesc> load_first_images = new ArrayList<ImageDesc>();
    private ArrayList<ImageDesc> vectorImages = new ArrayList<ImageDesc>();
    private ArrayList<ImageDesc> bg_images = new ArrayList<ImageDesc>();

    public ImageLoader(String RESOURCE_PATH) {
        this.RESOURCE_PATH = RESOURCE_PATH;
    }

    public ArrayList<ArrayList<ImageDesc>> imgCompiler (LinkedList<String> fileList) {
        for(String loadImage : fileList) {
            loadImage = loadImage.toLowerCase();
            // This will look inside resource folder and automatically load the images according to file names.
            if(loadImage.contains("load_first")) {
                if(loadImage.contains("startscreen_elementsheet")) {
                    ImageSlicer systemButtons = new ImageSlicer(500, 200, RESOURCE_PATH + loadImage, true);
                    BufferedImage[] systemImages = systemButtons.getSprites();
                    for(int a = 0; a < systemImages.length; a++) {
                        load_first_images.add(new ImageDesc(Integer.toString(a), systemImages[a]));
                    }
                }
                // Any images that are used in effects (used with scaleVec like things)
                else {//if(loadImage.contains("whitebox")) {
                    vectorImages.add(new ImageDesc(loadImage, new javaxt.io.Image(RESOURCE_PATH + loadImage)));
                }
            }
            else if(loadImage.contains("bg")) {
                //System.out.println("What's the loadImage's name? " + loadImage);
                bg_images.add(new ImageDesc(loadImage, new javaxt.io.Image(RESOURCE_PATH + loadImage)));
            }
        }

        ArrayList<ArrayList<ImageDesc>> imagePackage = new ArrayList<ArrayList<ImageDesc>>();
        imagePackage.add(load_first_images);
        imagePackage.add(vectorImages);
        imagePackage.add(bg_images);

        return imagePackage;
    }
}
