package start;

import java.awt.image.BufferedImage;

// TODO: Create exception for no description or image

public class ImageDesc {

    private String imageDescription;
    private BufferedImage imageItself;

    public ImageDesc(String imageDescription, javaxt.io.Image imageItself) {
        this.imageDescription = imageDescription;
        this.imageItself = imageItself.getBufferedImage();
    }

    public ImageDesc(String imageDescription, BufferedImage imageItself) {
        this.imageDescription = imageDescription;
        this.imageItself = imageItself;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public BufferedImage getImageItself() {
        return imageItself;
    }
}
