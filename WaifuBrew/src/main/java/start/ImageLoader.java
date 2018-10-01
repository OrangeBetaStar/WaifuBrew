package start;

import com.sun.deploy.config.Config;
import javafx.scene.Parent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

// It's extending JPanel due to usage of font width calculation.
public class ImageLoader extends JPanel {

    private String RESOURCE_PATH;
    private ArrayList<ImageDesc> load_first_images = new ArrayList<ImageDesc>();
    private ArrayList<ImageDesc> vectorImages = new ArrayList<ImageDesc>();
    private ArrayList<ImageDesc> bg_images = new ArrayList<ImageDesc>();
    private ImageDesc[] character_imagesArr= new ImageDesc[Characters.length * Mood.length];
    private int charCounter = 0;

    // Creating a menu button fonts
    // Need a font, string, size, width
    private BufferedInputStream myStream;
    private Font menuFont;
    private String textString;
    private int fontSize = 64; // probably use it for height
    private int width = 0;
    private int padding = 5;
    private int count = 0;



    public ImageLoader(String RESOURCE_PATH) {
        this.RESOURCE_PATH = RESOURCE_PATH;
    }

    public ArrayList<ArrayList<ImageDesc>> imgCompiler (LinkedList<String> fileList) {

        for(String loadImage : fileList) {
            loadImage = loadImage.toLowerCase();
            // This will look inside resource folder and automatically load the images according to file names.
            if (loadImage.contains("load_first")) {
                if (loadImage.contains("startscreen_elementsheet")) { // this tag in filename will trigger auto split image and load
                    try {

                        javaxt.io.Image menuCreationArea = new javaxt.io.Image(RESOURCE_PATH + "load_first_whitebox.png");

                        myStream = new BufferedInputStream(new FileInputStream(RESOURCE_PATH + "FashionF" + ".ttf"));
                        menuFont = Font.createFont(Font.TRUETYPE_FONT, myStream);
                        menuFont = menuFont.deriveFont(Font.PLAIN, fontSize);

                        // menuFont = new Font("serif", Font.PLAIN, fontSize);
                        // new Font("serif", Font.PLAIN, fontSize);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(RESOURCE_PATH + "load_first_buttonText.txt"), "UTF-8"));
                        while((textString = reader.readLine()) != null) {
                            width = getFontMetrics(menuFont).stringWidth(textString);
                            javaxt.io.Image tempCreation = menuCreationArea.copy();
                            tempCreation.resize(width + (padding * 2), fontSize + (padding * 2));
                            // There is a weird symbol just before Start symbol maker.
                            tempCreation.addText(textString.replaceAll("\\s+",""), (tempCreation.getWidth() / 2) - (width / 2) + padding, fontSize + padding, menuFont,255, 255, 255);
                            load_first_images.add(new ImageDesc(Integer.toString(count), tempCreation.getBufferedImage()));
                            count++;
                        }

                    } catch (FileNotFoundException e) {

                        // If textfile could not be found, it will use the pre-made image to slice.
                        ImageSlicer systemButtons = new ImageSlicer(500, 200, RESOURCE_PATH + loadImage, true);
                        BufferedImage[] systemImages = systemButtons.getSprites();
                        for (int a = 0; a < systemImages.length; a++) {
                            load_first_images.add(new ImageDesc(Integer.toString(a), systemImages[a]));
                        }
                    } catch (IOException e) {
                        // If there is error reading with textfile, it will use the pre-made image to slice.
                        ImageSlicer systemButtons = new ImageSlicer(500, 200, RESOURCE_PATH + loadImage, true);
                        BufferedImage[] systemImages = systemButtons.getSprites();
                        for (int a = 0; a < systemImages.length; a++) {
                            load_first_images.add(new ImageDesc(Integer.toString(a), systemImages[a]));
                        }
                    } catch (FontFormatException e) {
                        // If there is error with format of font, it will use the pre-made image to slice.
                        ImageSlicer systemButtons = new ImageSlicer(500, 200, RESOURCE_PATH + loadImage, true);
                        BufferedImage[] systemImages = systemButtons.getSprites();
                        for (int a = 0; a < systemImages.length; a++) {
                            load_first_images.add(new ImageDesc(Integer.toString(a), systemImages[a]));
                        }
                    }
                }
                // Any images that are used in effects (used with scaleVec like things)
                else { //if(loadImage.contains("whitebox")) {
                    if (loadImage.contains("toggle_housing")) {
                        ImageSlicer toggleHousing = new ImageSlicer(200, 400, RESOURCE_PATH + loadImage, true);
                        BufferedImage[] sliderHousing = toggleHousing.getSprites();
                        for (int a = 0; a < sliderHousing.length; a++) {
                            vectorImages.add(new ImageDesc("toggle_housing-" + Integer.toString(a), sliderHousing[a]));
                        }
                    }
                    else {
                        vectorImages.add(new ImageDesc(loadImage, new javaxt.io.Image(RESOURCE_PATH + loadImage)));
                    }
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
