package start.Loader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.LinkedList;

public class FindFile {

    LinkedList<String> fileList = new LinkedList<String>();

    public LinkedList<String> listFile(String folder, String extension) {

        GenericExtFilter filter = new GenericExtFilter(extension);

        File dir = new File(folder);

        if (dir.isDirectory() == false) {
            System.out.println("Directory does not exists : " + folder);
            return null;
        }

        String[] list = dir.list(filter);

        if (list.length == 0) {
            System.out.println("Files not found with extension : " + extension);
            return null;
        }

        for (String file : list) {
            fileList.add(file);
            // String temp = new StringBuffer(folder).append(file).toString();
            System.out.println("Found file : " + (new StringBuffer(folder).append(file).toString()));
        }

        Collections.sort(fileList);
        return fileList;
    }

    public class GenericExtFilter implements FilenameFilter {

        private String ext;

        public GenericExtFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(ext));
        }
    }
}