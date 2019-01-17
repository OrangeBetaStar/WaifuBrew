package start.Loader;

import org.json.JSONException;
import start.Parser.DialogueParser;
import start.Parser.ParserException.DialogueDataMissingException;
import start.Calculation.MathClass;
import start.Containers.ImageDesc;

import java.io.IOException;
import java.util.ArrayList;

public class ThreadFileLoad implements Runnable {

    private volatile ArrayList<ArrayList<ImageDesc>> fileList;
    private final String RESOURCE_PATH = "src/main/java/resources/";
    private DialogueParser dp;
    private java.util.List<java.util.List<Waifu>> e;
    private MathClass mathClass = new MathClass();

    public ThreadFileLoad() {

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Load Thread running");

        fileList = new ImageLoader(RESOURCE_PATH).imgCompiler(new FindFile().listFile(RESOURCE_PATH, ".png"));

        try {
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
}
