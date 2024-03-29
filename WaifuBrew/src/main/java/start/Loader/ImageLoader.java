package start.Loader;

import start.Containers.Characters;
import start.Containers.ImageDesc;
import start.Containers.Mood;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

// It's extending JPanel due to usage of font width calculation.
public class ImageLoader extends JPanel {

    private String RESOURCE_PATH;
    private ArrayList<ImageDesc> load_first_images = new ArrayList<ImageDesc>();
    private ArrayList<ImageDesc> vector_images = new ArrayList<ImageDesc>();
    private ArrayList<ImageDesc> bg_images = new ArrayList<ImageDesc>();
    private ArrayList<ImageDesc> thumbnail_images = new ArrayList<ImageDesc>();
    private ImageDesc[] character_imagesArr = new ImageDesc[Characters.length * Mood.length];
    private int charCounter = 0;

    // Creating a menu button fonts
    // Need a font, string, size, width
    private BufferedInputStream myStream;
    private Font menuFont;
    private int fontSize = 64; // probably use it for height
    private int width = 0;
    private int padding = 5;

    private String[] systemButtonStrings = {
            "Start",
            "Save",
            "Load",
            "Back",
            "Config",
            "Exit",
            "Reset",
            "Don't Save",
            "Options",
            "Quit"};

    public ImageLoader(String RESOURCE_PATH) {
        this.RESOURCE_PATH = RESOURCE_PATH;
    }

    public ArrayList<ArrayList<ImageDesc>> imgCompiler(LinkedList<String> fileList) {

        javaxt.io.Image menuCreationArea = new javaxt.io.Image(RESOURCE_PATH + "load_first_whitebox.png");
        try {
            myStream = new BufferedInputStream(new FileInputStream(RESOURCE_PATH + "FashionF" + ".ttf"));
            menuFont = Font.createFont(Font.TRUETYPE_FONT, myStream);
            menuFont = menuFont.deriveFont(Font.PLAIN, fontSize);
        } catch (FontFormatException e) {
            System.err.println("Custom font failed to load due to font format.  Using serif font.");
            menuFont = new Font("serif", Font.PLAIN, fontSize);
        } catch (IOException e) {
            System.err.println("Custom font failed to load due file error.  Using serif font.");
            menuFont = new Font("serif", Font.PLAIN, fontSize);
        }
        for (String textString : systemButtonStrings) {
            width = getFontMetrics(menuFont).stringWidth(textString);
            javaxt.io.Image tempCreation = menuCreationArea.copy();
            tempCreation.resize(width + (padding * 2), fontSize + (padding * 2));
            tempCreation.addText(textString, (tempCreation.getWidth() / 2) - (width / 2) + padding, fontSize + padding, menuFont, 255, 255, 255);
            load_first_images.add(new ImageDesc(textString, tempCreation.getBufferedImage()));
        }

        try {
            for (String loadImage : fileList) {
                loadImage = loadImage.toLowerCase();
                // This will look inside resource folder and automatically load the images according to file names.
                if (loadImage.contains("load_first")) {
                    if (loadImage.contains("toggle_housing")) {
                        ImageSlicer toggleHousing = new ImageSlicer(200, 400, RESOURCE_PATH + loadImage, true);
                        BufferedImage[] sliderHousing = toggleHousing.getSprites();
                        for (int a = 0; a < sliderHousing.length; a++) {
                            vector_images.add(new ImageDesc("toggle_housing-" + Integer.toString(a), sliderHousing[a]));
                        }
                    } else {
                        vector_images.add(new ImageDesc(loadImage, new javaxt.io.Image(RESOURCE_PATH + loadImage)));
                    }
                } else if (loadImage.contains("bg")) {
                    bg_images.add(new ImageDesc(loadImage, new javaxt.io.Image(RESOURCE_PATH + loadImage)));
                } else if (loadImage.contains("thumbnail")) {
                    thumbnail_images.add(new ImageDesc(loadImage, new javaxt.io.Image(RESOURCE_PATH + loadImage)));
                } else if (loadImage.contains("char")) {
                    ImageSlicer charSlices = new ImageSlicer(400, 500, RESOURCE_PATH + loadImage, true);
                    BufferedImage[] charImageArray = charSlices.getSprites();
                    for (int inde = 0; inde < charImageArray.length; inde++) {
                        character_imagesArr[Mood.length * charCounter + inde] = new ImageDesc(loadImage.substring(5, loadImage.indexOf(".png")) + "-" + getMood(inde).toString().toLowerCase(), charImageArray[inde]);
                    }
                    charCounter++;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("No required resource file found. Check directory.");
            System.exit(-1);
        }

        for (ImageDesc asdf : character_imagesArr) {
            System.out.println("Image Processed and converted: " + asdf.getImageDescription());
        }

        for (ImageDesc asdf : load_first_images) {
            System.out.println("Successfully loaded: " + asdf.getImageDescription());
        }


        ArrayList<ArrayList<ImageDesc>> imagePackage = new ArrayList<>();
        imagePackage.add(load_first_images);
        imagePackage.add(vector_images);
        imagePackage.add(bg_images);
        imagePackage.add(new ArrayList<>(Arrays.asList(character_imagesArr)));
        imagePackage.add(new ArrayList<ImageDesc>()); // TODO: Placeholder for effects
        imagePackage.add(thumbnail_images);

        return imagePackage;
    }


    public Mood getMood(int index) {
        for (Mood a : Mood.values()) {
            if (a.getValue() == index) {
                return a;
            }
        }
        return null;
    }
}
