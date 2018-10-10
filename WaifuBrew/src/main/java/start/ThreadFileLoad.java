package start;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ThreadFileLoad implements Runnable {

    private volatile ArrayList<ArrayList<ImageDesc>> fileList;
    private final String RESOURCE_PATH = "src/main/java/resources/";
    private BufferedImage[] systemImages = new BufferedImage[10];

    public ThreadFileLoad() {

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Load Thread running");

        fileList = new ImageLoader(RESOURCE_PATH).imgCompiler(new FindFile().listFile(RESOURCE_PATH, ".png"));
        for (ImageDesc buttons : fileList.get(0)) {
            systemImages[Integer.parseInt(buttons.getImageDescription())] = buttons.getImageItself();
        }
    }

    public ArrayList<ArrayList<ImageDesc>> getFileList() {
        return fileList;
    }

    public BufferedImage[] getSystemImages() {
        return systemImages;
    }
}
