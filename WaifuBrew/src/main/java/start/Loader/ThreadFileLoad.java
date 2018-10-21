package start.Loader;

import start.Containers.ImageDesc;

import java.util.ArrayList;

public class ThreadFileLoad implements Runnable {

    private volatile ArrayList<ArrayList<ImageDesc>> fileList;
    private final String RESOURCE_PATH = "src/main/java/resources/";

    public ThreadFileLoad() {

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Load Thread running");

        fileList = new ImageLoader(RESOURCE_PATH).imgCompiler(new FindFile().listFile(RESOURCE_PATH, ".png"));

    }

    public ArrayList<ArrayList<ImageDesc>> getFileList() {
        return fileList;
    }
}
