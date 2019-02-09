package start.Loader;

import start.Containers.ImageDesc;
import start.Containers.LoadSaveWrapper;
import start.PaneFrame.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class WaifuBrew {

    // Get resolution
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GUI Frame;
    public final String RESOURCE_PATH = "src/main/java/resources/";
    private ArrayList<ArrayList<ImageDesc>> fileList;
    private java.util.List<java.util.List<Waifu>> dialoguePackage;
    private HashMap loadedSettings;
    private ArrayList<HashMap> loadedSaves;

    private static WaifuBrew singleton;

    // Data storage
    private static HashMap<String, Integer> configStorage = new HashMap<>();
    private static HashMap<String, String> configUI = new HashMap<>();
    private static HashMap<String, Font> systemFont = new HashMap<>();
    private static ArrayList<LoadSaveWrapper> loadSaveContainers = new ArrayList<>();

    // Keeps the calculated easing from pre-run thread.
    private ArrayList<double[]> easingArray;

    private Dimension programDimension = new Dimension(1280, 720); // The default

    // [0] is Computer monitor resolution
    // [1] is resolution of program window
    // [2] is where window should go (top-left) to be centered in the monitor
    private Point[] defaultSize = {
            new Point(screenSize.width, screenSize.height),
            new Point(1280, 720),
            new Point((screenSize.width / 2) - (programDimension.width / 2), (screenSize.height / 2) - (programDimension.height / 2))};

    WaifuBrew() {

        // Must run first. Loads default settings (before overriding with available loaded user settings.
        initConfig();
        initLoadSave();

        // Threaded loading things.
        ThreadFileLoad tfl = new ThreadFileLoad();
        ThreadLoadingScreen tls = new ThreadLoadingScreen();
        Thread ttfl = new Thread(tfl, "ThreadFileLoad");
        Thread ttls = new Thread(tls, "ThreadLoadScreen");

        ttfl.start();
        ttls.start();

        try {
            ttfl.join();
            ttls.join();
        } catch (InterruptedException e) {
            System.out.println("Thread Error");
            e.printStackTrace();
        }

        System.out.println("Threads successfully merged!");

        // Getting files ready-ied by thread.
        fileList = tfl.getFileList();
        dialoguePackage = tfl.getDialoguePackage();
        easingArray = tfl.getMovement();

        loadedSettings = tfl.getLoadedSettings();
        if (loadedSettings != null) {
            applyLoadedSettings();
        }

        loadedSaves = tfl.getSaves();
        if (loadedSaves != null) {
            applyLoadedSaves();
        }

        // Load fonts (this is checked way to load hashmap)
        if(!tfl.getFonts().isEmpty()) {
            for (String name: (String[])tfl.getFonts().keySet().toArray(new String[0])){
                systemFont.put(name, (Font) tfl.getFonts().get(name));
            }
        }

        // HashMap Printer
        /*if(loadedSaves != null) {
            for (String name: (String[])loadedSaves.keySet().toArray(new String[0])){
                String key =name.toString();
                String value = loadedSaves.get(name).toString();
                System.out.println(key + " " + value);
            }
        }*/
    }

    private void applyLoadedSaves() {
        if(!loadedSaves.isEmpty()) {
            for(HashMap eachPanel : loadedSaves) {
                for(int deleteFinder = 0; deleteFinder < loadSaveContainers.size(); deleteFinder++) {
                    if(loadSaveContainers.get(deleteFinder).getPanelLocation() == Integer.parseInt((String)eachPanel.get("save_panel"))) {

                        // Perhaps I can automate this with Enum later on.
                        loadSaveContainers.get(deleteFinder).setSaveDate((String)eachPanel.get("save_date"));
                        loadSaveContainers.get(deleteFinder).setRouteStory((String)eachPanel.get("route"));
                        loadSaveContainers.get(deleteFinder).setThumbnailFile((String)eachPanel.get("thumbnail"));
                        loadSaveContainers.get(deleteFinder).setAdvancerDialogue(Integer.parseInt((String)eachPanel.get("advancer")));
                    }
                }
            }
        }
    }

    private void applyLoadedSettings() {
        for (String name : (String[]) loadedSettings.keySet().toArray(new String[0])) {
            if (name.equals("stage")) {
                // Perhaps in debug mode, I can start taking advantage of this. (Go straight into specified stage, etc)
                continue;
            }
            try {
                if (name.contains("dimension")) {
                    if (name.toLowerCase().contains("x")) {
                        configStorage.replace("dimensionX", Integer.parseInt(loadedSettings.get(name).toString()));
                    } else if (name.toLowerCase().contains("y")) {
                        configStorage.replace("dimensionY", Integer.parseInt(loadedSettings.get(name).toString()));
                    }
                } else {
                    configStorage.replace(name, Integer.parseInt(loadedSettings.get(name).toString()));
                }
            } catch (NumberFormatException e) {
                if (name.equals("systemFont")) {
                    configUI.replace(name, loadedSettings.get(name).toString());
                }
            }
        }

        // if loaded dimension is different from default, recalculate.
        defaultSize[1] = new Point(configStorage.get("dimensionX"), configStorage.get("dimensionY"));
        defaultSize[2] = new Point((screenSize.width / 2) - (configStorage.get("dimensionX") / 2), (screenSize.height / 2) - (configStorage.get("dimensionY") / 2));
    }

    public void exportSettings() {
        FileExporter fw = new FileExporter();
        fw.exportSettings(configStorage, configUI);
    }

    public java.util.List<java.util.List<Waifu>> getDialoguePackage() {
        return dialoguePackage;
    }

    public static WaifuBrew getInstance() {
        return singleton;
    }

    public static void main(String[] args) throws WaifuException {
        try {
            singleton = new WaifuBrew();
            singleton.start();
        }
        // catches any ParserException
        catch (Exception e) {
            System.out.println("Catastrophic error!");
            throw new WaifuException(e);
        }
    }

    public String getSystemFontName() {
        return configUI.get("systemFont");
    }

    public void setSystemFontName(String systemFont) {
        configUI.replace("systemFont", systemFont);
    }

    public Font getSystemFont(String fontName) {

        /*if(loadedSaves != null) {
            for (String name: (String[])loadedSaves.keySet().toArray(new String[0])){
                String key =name.toString();
                String value = loadedSaves.get(name).toString();
                System.out.println(key + " " + value);
            }
        }*/

        if(!systemFont.isEmpty()) {
            for (String name: systemFont.keySet().toArray(new String[0])){
                if(name.contains(fontName)) {
                    return systemFont.get(name);
                }
            }
        }
        return new Font("serif", Font.PLAIN, 12);
        // return systemFont.get(fontName) != null ? systemFont.get(fontName) : new Font("serif", Font.PLAIN, 12);
    }

    public String getDialogueFont() {
        return configUI.get("dialogueFont");
    }

    public void setDialogueFont(String dialogueFont) {
        configUI.replace("dialogueFont", dialogueFont);
    }

    private void initConfig() {
        // Pre-configure default conditions
        configStorage.put("dialogueTransparency", 70);
        configStorage.put("dialogueSpeed", 60);
        configStorage.put("fontSize", 30);
        configStorage.put("frameRate", 60);
        configStorage.put("GUIScaling", 100);
        configStorage.put("autoAdvancer", 0);
        configStorage.put("stage", 0);
        configStorage.put("dimensionX", 1280);
        configStorage.put("dimensionY", 720);
        configUI.put("systemFont", "Halogen");
        configUI.put("dialogueFont", "MS Mincho");
    }

    private void initLoadSave() {
        // Pre-configure blank states of load / save panels
        for(int panelProduction = 1; panelProduction <= 8; panelProduction++) {
            // loadSaveContainers.add(new LoadSaveWrapper(Calendar.getInstance().getTime().toString(), panelProduction, panelProduction, "uwu", Integer.toString(panelProduction)));
            loadSaveContainers.add(new LoadSaveWrapper("Empty", panelProduction, 0, "Empty", "Slot: " + panelProduction));
        }
    }

    public ArrayList<LoadSaveWrapper> getLoadSaveContainers() {
        return loadSaveContainers;
    }

    public int getDialogueTransparency() {
        return configStorage.get("dialogueTransparency");
    }

    public void setDialogueTransparency(int dialogueTransparency) {
        configStorage.replace("dialogueTransparency", dialogueTransparency);
    }

    public int getDialogueSpeed() {
        return configStorage.get("dialogueSpeed");
    }

    public void setDialogueSpeed(int dialogueSpeed) {
        configStorage.replace("dialogueSpeed", dialogueSpeed);
    }

    public int getFrameRate() {
        return configStorage.get("frameRate");
    }

    public void setFrameRate(int frameRate) {
        configStorage.replace("frameRate", frameRate);
    }

    public int getSystemGUIScale() {
        return configStorage.get("GUIScaling");
    }

    public void setSystemGUIScale(int GUIScale) {
        configStorage.replace("GUIScaling", GUIScale);
    }

    public boolean getAutoAdvancer() {
        return (configStorage.get("autoAdvancer") == 1);
    }

    public void setAutoAdvancer(boolean autoAdvancer) {
        configStorage.replace("autoAdvancer", autoAdvancer ? 1 : 0);
    }

    public int getPlayFontSize() {
        return configStorage.get("fontSize");
    }

    public void setPlayFontSize(int fontSize) {
        configStorage.replace("fontSize", fontSize);
    }

    public int getStage() {
        return configStorage.get("stage");
    }

    public void setStage(int stage) {
        configStorage.replace("stage", stage);
    }

    public String getResoucePath() {
        return RESOURCE_PATH;
    }

    public Point[] getRes() {
        return defaultSize;
    }

    public GUI getGUIInstance() {
        return Frame;
    }

    public ArrayList<ImageDesc> getImageSetByIndex(int index) {
        return fileList.get(index);
    }

    public ArrayList<ImageDesc> getImageSetBySelector(ImageSelector imageSelector) {
        return fileList.get(imageSelector.getValue());
    }

    public double[] getMovement(EasingSelector selector) {
        double[] returnEasingArray;
        switch (selector.getValue()) {
            // Linear
            case 1:
                returnEasingArray = easingArray.get(0);
                break;
            // Easing In
            case 2:
                returnEasingArray = easingArray.get(1);
                break;
            // Easing Out
            case 3:
                returnEasingArray = easingArray.get(2);
                break;
            // Out_In Expo
            case 4:
                returnEasingArray = easingArray.get(3);
                break;
            default:
                // Defaults to linear
                returnEasingArray = easingArray.get(0);
                break;
        }
        return returnEasingArray;
    }

    // No image will result in blank image.
    public BufferedImage getImageByName(ImageSelector whichPile, String whichOne) {

        // Getting ImageSelector.VECTOR - blackbox is a fail-safe
        for (ImageDesc pictures : fileList.get(whichPile.getValue())) {
            // System.out.println("The pick: "+whichPile + " " + whichOne);
            if (pictures.getImageDescription().toLowerCase().contains(whichOne.toLowerCase())) {
                return pictures.getImageItself();
            }
        }
        // Failed case
        System.out.println("Could not find: \"" + whichOne + "\" image from resource folder.");
        javaxt.io.Image createDefaultImage = new javaxt.io.Image(getImageByName(ImageSelector.VECTOR, "blackbox"));

        // TODO: Make the fonts not rely on play text size (placement / scales fine though)
        createDefaultImage.resize(getPlayFontSize() * 4, getPlayFontSize() * 2);
        createDefaultImage.addText(whichOne, createDefaultImage.getWidth() / 12, createDefaultImage.getHeight() / 2, getSystemFontName(), getPlayFontSize(), 128, 128, 128);
        return createDefaultImage.getBufferedImage();
    }

    public void start() {
        Frame = new GUI();
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // default size
        Frame.setSize(defaultSize[1].x, defaultSize[1].y);
        // disable resizing of the window since scaling is retarded.
        Frame.setResizable(false);
        // this calculates the window should start from (centered)
        Frame.setLocation(defaultSize[2].x, defaultSize[2].y);
        //Frame.setLayout(new FlowLayout());
        Frame.setVisible(true);
        Frame.setAlwaysOnTop(true);
    }
}
