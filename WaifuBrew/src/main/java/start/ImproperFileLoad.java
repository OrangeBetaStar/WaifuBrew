package start;

// This class is RuntimeException instead of Exception since player can not fix it.

public class ImproperFileLoad extends RuntimeException {

    public ImproperFileLoad(Exception e) {
        e.printStackTrace();
        System.out.println("ImproperFileLoad Exception Reached - Check file system.");
        System.exit(-1);
    }
    public ImproperFileLoad(String message) {
        System.out.println(message);
        System.out.println("ImproperFileLoad Exception Reached - Check file system.");
        System.exit(-1);
    }
    public ImproperFileLoad(Exception e, String message) {
        e.printStackTrace();
        System.out.println(message);
        System.out.println("ImproperFileLoad Exception Reached - Check file system.");
        System.exit(-1);
    }
    public ImproperFileLoad(Throwable cause) {
        super(cause);
        System.out.println("ImproperFileLoad Exception Reached - Check file system.");
    }
}