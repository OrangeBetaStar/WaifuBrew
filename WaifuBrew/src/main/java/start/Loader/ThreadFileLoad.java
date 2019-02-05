package start.Loader;

import org.json.JSONException;
import start.Calculation.MathClass;
import start.Containers.ImageDesc;
import start.Parser.DefaultLoader;
import start.Parser.DialogueParser;
import start.Parser.ParserException.DialogueDataMissingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ThreadFileLoad implements Runnable {

    private volatile ArrayList<ArrayList<ImageDesc>> fileList;
    private volatile ArrayList<ArrayList<ImageDesc>> loadList;
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

        fileList = new ImageLoader(RESOURCE_PATH).imgCompiler(new FindFile().listFile(RESOURCE_PATH, ".png"));
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
        return fileList;
    }

    public HashMap getLoadedSettings() {
        return us.getLoadedSettings();
    }

    public HashMap getSaves() {
        return sd.getLoadedSettings();
    }
}
