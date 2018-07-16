package start;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ImageLoader {

    private String RESOURCE_PATH;
    private ArrayList<ImageDesc> load_first_images = new ArrayList<ImageDesc>();
    private ArrayList<ImageDesc> vectorImages = new ArrayList<ImageDesc>();
    private ArrayList<ImageDesc> bg_images = new ArrayList<ImageDesc>();
    private ImageDesc[] character_imagesArr= new ImageDesc[Characters.length * Mood.length];
    // private ArrayList<ImageDesc> arrayList = new ArrayList<ImageDesc>(Arrays.asList(array));
    private int charCounter = 0;
    // private int charLoop = 0;
    // private int moodLoop = 0;


    public ImageLoader(String RESOURCE_PATH) {
        this.RESOURCE_PATH = RESOURCE_PATH;
    }

    public ArrayList<ArrayList<ImageDesc>> imgCompiler (LinkedList<String> fileList) {
        for(String loadImage : fileList) {
            loadImage = loadImage.toLowerCase();
            // This will look inside resource folder and automatically load the images according to file names.
            if (loadImage.contains("load_first")) {
                if (loadImage.contains("startscreen_elementsheet")) { // this tag in filename will trigger auto split image and load
                    ImageSlicer systemButtons = new ImageSlicer(500, 200, RESOURCE_PATH + loadImage, true);
                    BufferedImage[] systemImages = systemButtons.getSprites();
                    for (int a = 0; a < systemImages.length; a++) {
                        load_first_images.add(new ImageDesc(Integer.toString(a), systemImages[a]));
                    }
                }
                // Any images that are used in effects (used with scaleVec like things)
                else {//if(loadImage.contains("whitebox")) {
                    vectorImages.add(new ImageDesc(loadImage, new javaxt.io.Image(RESOURCE_PATH + loadImage)));
                }
            } else if (loadImage.contains("bg")) {
                //System.out.println("What's the loadImage's name? " + loadImage);
                bg_images.add(new ImageDesc(loadImage, new javaxt.io.Image(RESOURCE_PATH + loadImage)));
            } else if (loadImage.contains("char")) {
                ImageSlicer charSlices = new ImageSlicer(400, 500, RESOURCE_PATH + loadImage, true);
                BufferedImage[] charImageArray = charSlices.getSprites();
                // System.out.println("How long is the sliced Nico images: " + charImageArray.length);
                ImageDesc[] tempImageDesc = new ImageDesc[charImageArray.length];
                // Convert BufferedImage[] to ImageDesc[]
                for(int inde = 0; inde < charImageArray.length; inde++) {
                    // System.out.println("loadImage: " + loadImage.substring(5, loadImage.indexOf(".png"))+"-"+getMood(inde).toString().toLowerCase());
                    character_imagesArr[Mood.length * charCounter + inde] = new ImageDesc(loadImage.substring(5, loadImage.indexOf(".png"))+"-"+getMood(inde).toString().toLowerCase(), charImageArray[inde]);
                }

                /*
                System.out.println(loadImage.substring(loadImage.indexOf("_") + 1, (loadImage.indexOf("-"))));

                for (charLoop = 0; charLoop < Characters.length; charLoop++) {
                    if(loadImage.substring(loadImage.indexOf("_") + 1, (loadImage.indexOf("-"))).toLowerCase().compareTo(getChar(charLoop).toString().toLowerCase()) == 0) {
                        System.out.println("The char was: "+getChar(charLoop));
                    }
                }
                for (moodLoop = 0; moodLoop < Mood.length; moodLoop++) {
                    // getMood(moodLoop).toString().toLowerCase()
                    if(loadImage.substring(loadImage.indexOf("-") + 1, (loadImage.indexOf(".png"))).compareTo(getMood(moodLoop).toString().toLowerCase()) == 0) {
                        System.out.println("The mood was: "+getMood(moodLoop));
                    }
                }
                // character_images.add(new ImageDesc(loadImage.substring(loadImage.indexOf("_") + 1, loadImage.indexOf(".png")), new javaxt.io.Image(RESOURCE_PATH + loadImage)));
                */
                charCounter++;
            }
        }

        for(ImageDesc asdf : character_imagesArr) {
            System.out.println("Image Processed: "+asdf.getImageDescription());
        }

        ArrayList<ArrayList<ImageDesc>> imagePackage = new ArrayList<ArrayList<ImageDesc>>();
        imagePackage.add(load_first_images);
        imagePackage.add(vectorImages);
        imagePackage.add(bg_images);
        imagePackage.add(new ArrayList<ImageDesc>(Arrays.asList(character_imagesArr)));

        return imagePackage;
    }

    private ImageDesc[] concatenater(ImageDesc[] first, ImageDesc[] second) {
        ImageDesc[] both = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, both, first.length, second.length);
        return both;
    }

    public Characters getChar(int index) {
        for(Characters a : Characters.values()) {
            if(a.getValue() == index) {
                return a;
            }
        }
        return null;
    }
    public Mood getMood(int index) {
        for(Mood a : Mood.values()) {
            if(a.getValue() == index) {
                return a;
            }
        }
        return null;
    }
}
