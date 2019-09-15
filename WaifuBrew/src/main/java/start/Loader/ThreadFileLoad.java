package start.Loader;

import org.json.JSONException;
import start.Calculation.MathClass;
import start.Containers.ImageDesc;
import start.Parser.DefaultLoader;
import start.Parser.DialogueParser;
import start.Parser.ParserException.DialogueDataMissingException;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ThreadFileLoad implements Runnable {

    private volatile ArrayList<ArrayList<ImageDesc>> imageLoad;
    private volatile HashMap<String, Font> sysFont = new HashMap<>();
    private final String RESOURCE_PATH = "src/main/java/resources/";
    private DialogueParser dp; // dialogue parse
    private DefaultLoader sd; // save data
    private DefaultLoader us; // user setting
    private java.util.List<java.util.List<Waifu>> e;
    private MathClass mathClass = new MathClass();

    public ThreadFileLoad() {

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Load Thread running");

        imageLoad = new ImageLoader(RESOURCE_PATH).imgCompiler(new FindFile().listFile(RESOURCE_PATH, ".png"));
        LinkedList<String> fontLoad = new FindFile().listFile(RESOURCE_PATH, ".ttf");

        BufferedInputStream myStream;
        for(String loadedFontNames : fontLoad) {
            try {
                myStream = new BufferedInputStream(new FileInputStream(RESOURCE_PATH + loadedFontNames));
                sysFont.put(loadedFontNames, Font.createFont(Font.TRUETYPE_FONT, myStream));
                System.out.println("Loaded font: " + "\"" + loadedFontNames + "\"");
            } catch (FontFormatException ex) {
                ex.printStackTrace();
                sysFont.put("serif", new Font("serif", Font.PLAIN, 12));
                System.err.println("\"" + loadedFontNames + "\" has failed. Loading serif font.");
            } catch (FileNotFoundException ex) {
                System.out.println("FileNotFoundException in ThreadFileLoad");
                sysFont.put("serif", new Font("serif", Font.PLAIN, 12));
                System.err.println("\"" + loadedFontNames + "\" has failed. Loading serif font.");
            } catch (IOException ex) {
                System.out.println("IOException in in ThreadFileLoad");
                sysFont.put("serif", new Font("serif", Font.PLAIN, 12));
                System.err.println("\"" + loadedFontNames + "\" has failed. Loading serif font.");
            }
        }


        // Have the ImageLoader take in Dimension from UserSetting to calculate proper dimension for buttons

        try {
            // Load user setting
            us = new DefaultLoader(RESOURCE_PATH + "user.json", "user");
            us.parse();

            sd = new DefaultLoader(RESOURCE_PATH + "saves.json", "saves");
            sd.parse();

            /*
            // HashMap Printer
            if(loadedSettings != null) {
                for (String name: (String[])loadedSettings.keySet().toArray(new String[0])){
                    String key =name.toString();
                    String value = loadedSettings.get(name).toString();
                    System.out.println(key + " " + value);
                }
            }
            */

            // Load dialogues
            dp = new DialogueParser(RESOURCE_PATH + "test.json");
            dp.parse();
            e = dp.getPackagedDialogue();

        } catch (IOException ex) {
            System.out.println("Simple IOException");
            ex.printStackTrace();
        } catch (DialogueDataMissingException ex) {
            System.out.println("Missing Dialog!"); //Wait what, it is okay to have missing dialog. i.e.: just character change
            ex.printStackTrace();
        } catch (JSONException ex) {
            System.out.println("There is an error with JSON");
            ex.printStackTrace();
        }

        // Calculate easing array beforehand.
        // movement = mathClass.easeOut(0.0, 0.5, 0, 1280 / 4);
    }

    public ArrayList<double[]> getMovement() {
        return mathClass.getArrayList();
    }

    public java.util.List<java.util.List<Waifu>> getDialoguePackage() {
        return e;
    }

    public ArrayList<ArrayList<ImageDesc>> getFileList() {
        return imageLoad;
    }

    public HashMap getLoadedSettings() {
        return us.getLastObject();
    }

    public ArrayList getSaves() {
        return sd.getMultipleObject();
    }

    public HashMap getFonts() {
        return sysFont;
    }
}
