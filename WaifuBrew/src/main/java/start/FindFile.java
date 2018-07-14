package start;

import java.io.File;
import java.io.FilenameFilter;

public class FindFile {

    public void listFile(String folder, String extension) {

        GenericExtFilter filter = new GenericExtFilter(extension);

        File dir = new File(folder);

        if(dir.isDirectory()==false){
            System.out.println("Directory does not exists : " + folder);
            return;
        }

        // list out all the file name and filter by the extension
        String[] list = dir.list(filter);

        if (list.length == 0) {
            System.out.println("Files not found with extension : " + extension);
            return;
        }

        for (String file : list) {
            String temp = new StringBuffer(folder).append(File.separator).append(file).toString();
            System.out.println("Found file : " + temp);
        }
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