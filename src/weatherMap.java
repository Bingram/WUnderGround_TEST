import DataStructures.*;
import DataStructures.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Brian on 2/12/2015.
 *
 * An object that can retrieve and analyze current weather
 * conditions relative to a Boundary area as chosen by user
 */
public class weatherMap implements Runnable{

    private int[][] currentWeather;
    private BoundaryBundle myBoundaries;
    private int myImageWidth,myImageHeight;
    private BufferedImage boundaryImage;

    private String bgURL,clearURL;

    private String mapName,bgName,clearName;

    private aRGBConverter myConverter;

    private BoundaryChecker boundaryChecker;

    private weatherMap(){
        myImageWidth = 1;
        myImageHeight = 1;
        currentWeather = new int[myImageWidth][myImageHeight];
        myConverter = new aRGBConverter();
        boundaryChecker = new BoundaryChecker();
        myBoundaries = new BoundaryBundle();
        boundaryImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        mapName = "BLANK";
        bgName = mapName + "-BG";
        clearName = mapName + "-CLEAR";
    }

    public weatherMap(String theName, int theWidth, int theHeight){
        myImageWidth = theWidth;
        myImageHeight = theHeight;
        currentWeather = new int[myImageWidth][myImageHeight];
        myConverter = new aRGBConverter();
        boundaryChecker = new BoundaryChecker();
        myBoundaries = new BoundaryBundle();
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

        boundaryChecker.updateWeather(currentWeather);

        //TESTING
        writeImageFile(tempImage,clearName);

    }

    public void updateTestCurrentWeather(int[][] weather, Boundary theBound){
        currentWeather = weather;
        boundaryChecker = new BoundaryChecker(currentWeather,theBound);

        boundaryChecker.updateWeather(currentWeather);
        boundaryChecker.fullCheckOuter();
    }

    public void updateWeather() throws IOException{
        BufferedImage clearImage = getImageFromURL(clearURL);//get latest clear image

        Boundary currentBound = myBoundaries.getBoundary(0);


        //TESTING
        writeImageFile(clearImage,clearName);

        currentWeather = myConverter.get2DArray(clearImage);//update weather array

        boundaryChecker = new BoundaryChecker(myConverter.get2DArray(clearImage),currentBound);

        boundaryChecker.updateWeather(myConverter.get2DArray(clearImage));//update boundary checker array

        boundaryImage = getImageFromURL(bgURL);//update BG Image

        writeImageFile(boundaryImage,bgName);//update current BG file

        updateBoundaryImage();//add bounds to current full BG map

        boundaryChecker.run();


    }

    public void writeArray2File(int[][] theArray, String fileName){
        try {
            BufferedImage img = new BufferedImage(theArray.length,theArray.length,BufferedImage.TYPE_INT_ARGB);
            try {
                img = ImageIO.read(new File(fileName + ".png"));
            } catch (IOException e) {
            }



            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    int rgb = theArray[i][j];
                    img.setRGB(i, j, rgb);
                }
            }

            // retrieve image
            File outputfile = new File(fileName + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
        }
    }


    public double getCoverage(){
        return boundaryChecker.fullCheckOuter();
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

        updateBoundaryImage();//add bounds to current full BG map

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


        //TODO add filtering for wunderground URL only? OR leave vanilla?
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
     * Draw outer boundary for current Bundle
     */
    private void updateBoundaryImage(){


        drawBound(myBoundaries.getBoundary(0));

        try {

            writeImageFile(boundaryImage,"CurrentBounds-" + mapName);


        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Draws a boundary on the boundaryImage BufferedImage
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

    //temporary limitation of 100 px radius
    public void addBoundary(Boundary theBound){

        myBoundaries.addBoundary(theBound);
        boundaryChecker.setMyBoundary(theBound);

    }


    /**
     * Write a given BufferedImage to a file with the String fileName
     *
     * @param theImage BufferedImage to write to file
     * @param fileName String of file name to write
     * @throws IOException
     */
    public void writeImageFile(BufferedImage theImage,String fileName) throws IOException{
        try {
            File imageFile = new File(fileName + ".png");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(theImage, "png", baos );

            //Persist - in this case to a file for later reference

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
     *
     * @param fileName String of file name to read
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

    /**
     * Does this do a deep copy?
     *
     * @param theImage BufferedImage to copy?
     * @return BufferedImage deep copy?
     */
    private BufferedImage copyImage(BufferedImage theImage){
        BufferedImage temp = new BufferedImage(myImageWidth,myImageHeight, theImage.getType());

        int row,col,width,height;
        width = theImage.getWidth();
        height = theImage.getHeight();

        for(row = col = 0; row < height; ){
            temp.setRGB(row,col, theImage.getRGB(row,col));
            col++;
            if(col == width){
                col = 0;
                row++;
            }

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
