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
 * Created by Brian on 5/4/2015.
 */
public class RadarMap implements Runnable {

    private int[][] currentWeather;
    private int myImageWidth,myImageHeight;
    private BufferedImage boundaryImage;
    private String bgURL,clearURL;
    private String mapName,bgName,clearName;
    private aRGBConverter myConverter;

    private RadarMap(){
        myImageWidth = 1;
        myImageHeight = 1;
        currentWeather = new int[myImageWidth][myImageHeight];

        myConverter = new aRGBConverter();

        boundaryImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        mapName = "BLANK";
        bgName = mapName + "-BG";
        clearName = mapName + "-CLEAR";
    }

    public RadarMap(String theName, int theWidth, int theHeight){
        myImageWidth = theWidth;
        myImageHeight = theHeight;
        currentWeather = new int[myImageWidth][myImageHeight];
        myConverter = new aRGBConverter();
        boundaryImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        mapName = theName;
        bgName = mapName + "-BG";
        clearName = mapName + "-CLEAR";
    }

    /**
     * Updates the current weather array used to for bounds checking
     *
     * @param theUrl String for the URL of a clear weather map
     * @throws IOException when file or URL grab encounters an error
     */
    public void updateWeatherArray(String theUrl) throws IOException {

        BufferedImage tempImage = getImageFromURL(theUrl);

        currentWeather = myConverter.get2DArray(tempImage);

        writeImageFile(tempImage,clearName);

    }


    public void updateWeather() throws IOException{
        BufferedImage clearImage = getImageFromURL(clearURL);//get latest clear image

        //TESTING
        writeImageFile(clearImage,clearName);

        currentWeather = myConverter.convertTo2DBRUTEFORCE(clearImage);

        boundaryImage = getImageFromURL(bgURL);//update BG Image

        writeImageFile(boundaryImage,bgName);//update current BG file

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
     * Updates the current weather imagery with full BG
     *
     * @param theUrl String for the URL of a full BG weather map
     * @throws IOException when file or URL grab encounters an error
     */
    public void updateBG(String theUrl) throws IOException {

        boundaryImage = getImageFromURL(theUrl);//update Buffered Image

        writeImageFile(boundaryImage,bgName);//update current BG file

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
     * Draws a boundary on the boundaryImage BufferedImage
     * Diagnostic for now
     *
     * @param theBound Boundary to draw on BufferedImage of map
     */
    public void drawBound(Boundary theBound) {

        PointList thePoints = theBound.getMyPoints();

        Node current = thePoints.getNode(0);

        while(current.getMyNext() != null){
            Point p = (Point)current.getMyItem();

            int x = p.getMyX();
            int y = p.getMyY();

            try {

                boundaryImage.setRGB(x, y, Color.RED.getRGB());

            } catch (Exception e){
                System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                        "encountered an Error \nERROR:: " + e.toString());

            }

            current = current.getMyNext();

        }

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
     * Reads a given file and returns a BufferedImage of the file
     * Accepts full filename as file is assumed to be in root
     *
     * @param fileName String of full path to file
     * @return BufferedImage of file
     * @throws IOException
     */
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
    }

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
