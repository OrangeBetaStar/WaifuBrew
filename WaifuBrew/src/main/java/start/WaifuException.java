package start;

public class WaifuException extends Exception {

    public WaifuException() {
        System.out.println("Exception: rip waifu");
        super.printStackTrace();
    }

    public WaifuException(Exception e) {
        e.printStackTrace();
    }

    public WaifuException(String message) {
        super(message);
    }

    public WaifuException(Throwable cause) {
        super(cause);
    }

    public WaifuException(String message, Throwable cause) {
        super(message, cause);
    }

}