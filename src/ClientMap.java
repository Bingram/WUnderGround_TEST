import DataStructures.Boundary;
import DataStructures.Node;
import DataStructures.Point;
import DataStructures.PointList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Brian on 5/10/2015.
 *
 * Client side version of the image/map updater
 *
 * This class will interact with the server to retrieve
 * the current imagery information.
 *
 * Ideally there wil be a check of the file timestamp
 * prior to actually grabbing the iamge from the server.
 *
 * This should save some time and ensure that the server
 * doesn't get slammed by multiple image requests if clients
 * are out of sync with server update schedule.
 */
public class ClientMap implements Runnable{

    private int[][] currentWeather;
    private int myImageWidth,myImageHeight;
    private BufferedImage weatherImage,fullImage;
    private String bgURL,clearURL;
    private String mapName,bgName,clearName;
    private aRGBConverter myConverter;

    public ClientMap(String theName, int theWidth, int theHeight){
        myImageWidth = theWidth;
        myImageHeight = theHeight;
        currentWeather = new int[myImageWidth][myImageHeight];
        myConverter = new aRGBConverter();
        weatherImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        fullImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        mapName = theName;
        bgName = mapName + "-BG";
        clearName = mapName + "-CLEAR";
    }

    public void updateWeather() throws IOException{
        BufferedImage clearImage = getImageFromURL(clearURL);//get latest clear image

        //TESTING
        writeImageFile(clearImage,clearName);

        currentWeather = myConverter.convertTo2DBRUTEFORCE(clearImage);

        weatherImage = getImageFromURL(bgURL);//update BG Image

        writeImageFile(weatherImage,bgName);//update current BG file

    }

    /**
     * Updates the current weather imagery with full BG
     *
     * @param theUrl String for the URL of a full BG weather map
     * @throws IOException when file or URL grab encounters an error
     */
    public void updateBG(String theUrl) throws IOException {

        fullImage = getImageFromURL(theUrl);//update Buffered Image

        writeImageFile(fullImage,bgName);//update current BG file

    }

    /**
     * Diagnostic testing for printing current array model
     * to an image file, as opposed to the buffered image
     * pulled from the URL.
     *
     * @param theArray 2D array to be printed
     * @param fileName Name of resulting image file (no ext.)
     */
    public void writeArray2File(int[][] theArray, String fileName){
        try {
            BufferedImage img = new BufferedImage(theArray.length,theArray.length,BufferedImage.TYPE_INT_ARGB);
            try {
                img = ImageIO.read(new File(fileName + ".png"));
            } catch (IOException e) {
                System.out.println("EXCEPTION - Reading File: " + e);
            }


            for (int i = 0; i < theArray.length; i++) {
                for (int j = 0; j < theArray[0].length; j++) {
                    int rgb = theArray[i][j];
                    img.setRGB(i, j, rgb);
                }
            }

            // retrieve image
            File outputfile = new File(fileName + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
            System.out.println("EXCEPTION - Writing File: " + e);
        }
    }

    /**
     * Accepts a given URL and retrieves the image returned as a Buffered Image
     *
     * @param theUrl String of the URL to retrieve image
     * @return Buffered Image of image retrieved from URL
     * @throws IOException
     */
    public BufferedImage getImageFromURL(String theUrl) throws IOException {

        BufferedImage tempImage = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);

        // add filtering for wunderground URL only? OR leave vanilla?
        // Leave Vanilla
        try {

            URL imageURL = new URL(theUrl);

            tempImage = ImageIO.read(imageURL);

        } catch (Exception e){
            System.out.println("no image returned");
            System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                    "encountered an Error \nERROR:: " + e.toString());
        }

        return tempImage;

    }

    /**
     * Write a given BufferedImage to a file with the String fileName
     *
     * @param theImage BufferedImage to write to file
     * @param fileName String of file name to write (no ext.)
     * @throws IOException
     */
    public void writeImageFile(BufferedImage theImage,String fileName) throws IOException{
        try {
            File imageFile = new File(fileName + ".png");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(theImage, "png", baos );

            FileOutputStream fos = new FileOutputStream(imageFile);
            baos.writeTo(fos);
            fos.close();

        } catch (Exception e){
            System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                    "encountered an Error \nERROR:: " + e.toString());
        }

    }

    /**
     * Draws a boundary on the weatherImage BufferedImage
     * Diagnostic for now
     *
     * @param //theBound Boundary to draw on BufferedImage of map
     *//*
    public void drawBound(Boundary theBound) {

        PointList thePoints = theBound.getMyPoints();

        Node current = thePoints.getNode(0);

        while(current.getMyNext() != null){
            Point p = (Point)current.getMyItem();

            int x = p.getMyX();
            int y = p.getMyY();

            try {

                weatherImage.setRGB(x, y, Color.RED.getRGB());

            } catch (Exception e){
                System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                        "encountered an Error \nERROR:: " + e.toString());

            }

            current = current.getMyNext();

        }

    }*/

    /*
    *//**
     * Reads a given file and returns a BufferedImage of the file
     * Accepts full filename as file is assumed to be in root
     *
     * //@param fileName String of full path to file
     * @return BufferedImage of file
     * @throws IOException
     *//*
    public BufferedImage readImageFile(String fileName) throws IOException{

        BufferedImage temp = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);

        try {

            // open image
            File imageFile = new File(fileName);
            temp = ImageIO.read(imageFile);

        } catch (Exception e){
            System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                    "encountered an Error \nERROR:: " + e.toString());
        }

        return temp;
    }*/

    public int getMyImageWidth() {
        return myImageWidth;
    }

    public int getMyImageHeight() {
        return myImageHeight;
    }

    public void setClearURL(String theURL) {
        clearURL = theURL;
    }

    public void setBGURL(String theURL) {
        bgURL = theURL;
    }

    @Override
    public void run() {
        try {
            updateWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
