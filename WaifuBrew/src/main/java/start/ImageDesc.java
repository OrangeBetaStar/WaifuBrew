package start;

import java.awt.image.BufferedImage;

// TODO: Create exception for no description or image

// This class will be a wrapper class of String (for description) and BufferedImage (image) as having list of BufferedImage would be too messy and inefficient.

public class ImageDesc{

    private String imageDescription;
    private BufferedImage imageItself;

    public ImageDesc(String imageDescription, javaxt.io.Image imageItself) throws ImproperFileLoad  {
        if(imageDescription.length() == 0) {
            throw new ImproperFileLoad("ImageDesc's Name can NOT be empty!");
        }
        else {
            this.imageDescription = imageDescription;
            this.imageItself = imageItself.getBufferedImage();
        }
    }

    public ImageDesc(String imageDescription, BufferedImage imageItself) throws ImproperFileLoad  {
        if(imageDescription.length() == 0) {
            throw new ImproperFileLoad("ImageDesc's Name can NOT be empty!");
        }
        else {
            this.imageDescription = imageDescription;
            this.imageItself = imageItself;
        }
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public BufferedImage getImageItself() {
        return imageItself;
    }
}
