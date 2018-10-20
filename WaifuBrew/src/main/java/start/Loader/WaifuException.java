package start.Loader;

public class WaifuException extends Exception {

    public WaifuException(Exception e) {
        e.printStackTrace();
    }

    public WaifuException(String message) {
        super(message);
        System.out.println("Waifu Exception Reached - Check WaifuBrew or ConfigPane");
    }

    public WaifuException(Throwable cause) {
        super(cause);
        System.out.println("Waifu Exception Reached - Check WaifuBrew or ConfigPane");
    }

    public WaifuException(String message, Throwable cause) {
        super(message, cause);
        System.out.println("Waifu Exception Reached - Check WaifuBrew or ConfigPane");
    }

}