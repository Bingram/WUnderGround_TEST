import DataStructures.CircleBoundary;
import DataStructures.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by Brian on 2/12/2015.
 *
 * Creates a 2D array of integer values from an image given a URL.
 */
public class imageGrabber {

    private int[][] myConvertedImageArray;
    private int myImageWidth,myImageHeight;
    private BufferedImage myOriginal;

    private aRGBConverter myConverter;

    public imageGrabber(){
        myConvertedImageArray = new int[0][0];
        myConverter = new aRGBConverter();
        myOriginal = null;
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

    public void addBoundary(CircleBoundary circle){
        BufferedImage temp = new BufferedImage(639,479, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < myImageHeight; x++) {
            for (int y = 0; y < myImageWidth; y++) {

                temp.setRGB(x, y, myConvertedImageArray[x][y]);
            }
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(temp, "png", baos );

        //Persist - in this case to a file
        FileOutputStream fos = new FileOutputStream(new File("uncircled.png"));
        baos.writeTo(fos);
        fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //modify temp image with boundary
        LinkedList<Point>[] tempList = circle.getQuads();

        for(int i = 0; i < tempList.length; i++){
            for(Point p: tempList[i]){
                temp.setRGB(p.getMyX(), p.getMyY(), Color.RED.getRGB());

            }
        }


        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(temp, "png", baos );

            //Persist - in this case to a file
            FileOutputStream fos = new FileOutputStream(new File("circled.png"));
            baos.writeTo(fos);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
