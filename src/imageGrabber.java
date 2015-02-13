import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Brian on 2/12/2015.
 *
 * Creates a 2D array of integer values from an image given a URL.
 */
public class imageGrabber {

    private int[][] myConvertedImageArray;
    private int myImageWidth,myImageHeight;

    private aRGBConverter myConverter;

    public imageGrabber(){
        myConvertedImageArray = new int[0][0];
        myConverter = new aRGBConverter();
    }

    public void getImageFromURL(String theUrl) throws IOException {

        URL imageURL = new URL(theUrl);
        BufferedImage originalImage= ImageIO.read(imageURL);


        if(originalImage==null){
            System.out.println("no image returned");

        } else {
            myConvertedImageArray = myConverter.get2DArray(originalImage);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos );

        //Persist - in this case to a file
        FileOutputStream fos = new FileOutputStream(new File("test.png"));
        baos.writeTo(fos);
        fos.close();

    }

    private void getImageSize(BufferedImage image){
        myImageWidth = image.getWidth();
        myImageHeight = image.getHeight();
    }

    public int[][] getImageArray(){
        return myConvertedImageArray;
    }

    public int getMyImageWidth() {
        return myImageWidth;
    }

    public int getMyImageHeight() {
        return myImageHeight;
    }
}
