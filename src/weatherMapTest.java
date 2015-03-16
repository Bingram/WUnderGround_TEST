import DataStructures.Boundary;
import junit.framework.TestCase;

import java.awt.*;

public class weatherMapTest extends TestCase {

    /*private int[][] currentWeather;
    private BoundaryBundle myBoundaries;
    private int myImageWidth,myImageHeight;
    private BufferedImage boundaryImage;

    private String bgURL,clearURL;

    private String mapName,bgName,clearName;

    private aRGBConverter myConverter;

    private BoundaryChecker boundaryChecker;*/

    private weatherMap mapOne,mapTwo,splitMap,quarterMap;

    private int CLEAR = -16777216;
    private int WIDTH = 300;
    private int HEIGHT = WIDTH;

    public void setUp() throws Exception {
        super.setUp();

        mapOne = new weatherMap("TEST",WIDTH,HEIGHT);
        mapTwo = new weatherMap("TEST_2",WIDTH,HEIGHT);
        splitMap = new weatherMap("SPLIT",WIDTH,HEIGHT);
        quarterMap = new weatherMap("QUARTER",WIDTH,HEIGHT);





    }

    public int[][] getFullMap(int color){
        int[][] temp = new int[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                temp[i][j] = color;
            }
        }

        return temp;
    }

    public int[][] getQuarterMap(int color){
        int[][] temp = new int[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH/2; i++) {
            for (int j = 0; j < HEIGHT/2; j++) {
                temp[i][j] = color;
            }

            for (int k = HEIGHT/2; k < HEIGHT; k++) {
                temp[i][k] = CLEAR;
            }

        }

        for (int i = WIDTH/2; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                temp[i][j] = CLEAR;
            }
        }

        return temp;
    }

    public int[][] getSplitMap(int color){
        int[][] temp = new int[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                temp[i][j] = color;
            }

        }
/*
        for (int i = WIDTH/2; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                temp[i][j] = CLEAR;
            }
        }*/

        return temp;
    }

    public void testFullMap() throws Exception{
        Boundary theBound = new Boundary(WIDTH/2,HEIGHT/2,WIDTH/4);
        mapTwo.updateTestCurrentWeather(getFullMap(CLEAR),theBound);
        mapOne.updateTestCurrentWeather(getFullMap(Color.BLUE.getRGB()),theBound);

    }

    public void testSplitMap() throws Exception{
        Boundary theBound = new Boundary(WIDTH/2,HEIGHT/2,WIDTH/4);
        int[][] theArray = getSplitMap(Color.BLUE.getRGB());
        splitMap.writeArray2File(theArray,"SplitMap");
        splitMap.updateTestCurrentWeather(theArray,theBound);
    }

    public void testQuarterMap() throws Exception{
        Boundary theBound = new Boundary(WIDTH/2,HEIGHT/2,(WIDTH/2)-1);
        quarterMap.addBoundary(theBound);
        int[][] theArray = getQuarterMap(Color.RED.getRGB());
        quarterMap.writeArray2File(theArray,"QuarterMap");
        quarterMap.updateTestCurrentWeather(theArray,theBound);

        Double coverage = quarterMap.getCoverage();


        assertEquals(0.0, coverage);

    }

    public void testUpdateWeatherArray() throws Exception {

    }

    public void testUpdateWeather() throws Exception {

    }

    public void testGetCoverage() throws Exception {

    }

    public void testUpdateBG() throws Exception {

    }

    public void testDrawBound() throws Exception {

    }

    public void testAddBoundary() throws Exception {

    }
}