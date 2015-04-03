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

    private double falseW = 1280.0;

    private static Double myMaxLat = 49.0;//Just North of border WA
    private static Double myMaxLong = -116.8;//Just East of border WA
    private static Double myMinLat = 42.6;//Halfway into OR South
    private static Double myMinLong = -125.5;//A few miles of West coast WA

    private int WAIT_TIME,MINUTES;

    private String BASE_URL = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat="+ myMaxLat +
            "&maxlon=" + myMaxLong + "&minlat=" + myMinLat + "&minlon=" + myMinLong + "&width=" + falseW + "&height=" + falseW;
    private String clearURL = BASE_URL + "&rainsnow=1&reproj.automerc=1";
    private String bgURL = BASE_URL + "&newmaps=1";

    private weatherMap mapOne,mapTwo,splitMap,quarterMap,falseMap;

    private int CLEAR = -16777216;
    private int WIDTH = 2000;
    private int HEIGHT = WIDTH;

    private double THRESHOLD = 1.0;

    public void setUp() throws Exception {
        super.setUp();

        mapOne = new weatherMap("TEST",WIDTH,HEIGHT);
        mapTwo = new weatherMap("TEST_2",WIDTH,HEIGHT);
        splitMap = new weatherMap("SPLIT",WIDTH,HEIGHT);
        quarterMap = new weatherMap("QUARTER",WIDTH,HEIGHT);
        falseMap = new weatherMap("FALSE", ((int) falseW), ((int) falseW));

        mapOne.updateBCThreshold(THRESHOLD);
        mapTwo.updateBCThreshold(THRESHOLD);
        splitMap.updateBCThreshold(THRESHOLD);
        quarterMap.updateBCThreshold(THRESHOLD);

    }

    public int[][] getFullMap(int color){
        int[][] temp = getClearMap(CLEAR);

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                temp[i][j] = color;
            }
        }

        return temp;
    }

    public int[][] getClearMap(int color){
        int[][] temp = new int[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                temp[i][j] = color;
            }
        }

        return temp;
    }

    public int[][] getQuarterMap(int color){
        int[][] temp = getClearMap(CLEAR);



        for (int i = 0; i < WIDTH/2; i++) {
            for (int j = 0; j < HEIGHT/2; j++) {
                temp[i][j] = color;
            }

        }

        return temp;
    }

    public int[][] getSplitMap(int color){
        int[][] temp = getClearMap(CLEAR);

        for (int i = 0; i < WIDTH/2; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                temp[i][j] = color;
            }

        }

        return temp;
    }

    /*
     *BEGIN TESTS 
     */
    
    /*public void testFalseCoverage() throws Exception{
        //410,273

        Boundary theBound = new Boundary(410,273,100);
        falseMap.addBoundary(theBound);
        falseMap.setBGURL(bgURL);
        falseMap.setClearURL(clearURL);
        System.out.println(clearURL);
        falseMap.updateWeather();

        Double coverage = falseMap.getCoverage();

        assertEquals(100.0,coverage);
    }*/

    public void testFullMap() throws Exception{
        Boundary theBound = new Boundary(WIDTH/2,HEIGHT/2,WIDTH/4);
        int[][] redFull = getFullMap(Color.RED.getRGB());
        int[][] blueFull = getFullMap(Color.BLUE.getRGB());
        mapTwo.updateTestCurrentWeather(redFull,theBound);
        mapOne.updateTestCurrentWeather(blueFull,theBound);
        mapOne.writeArray2File(blueFull,"Blue_FULL");
        mapTwo.writeArray2File(redFull,"Red_FULL");

       // Double coverage1 = (double) Math.round(mapOne.getCoverage() * 100) / 100;
       // Double coverage2 = (double) Math.round(mapTwo.getCoverage() * 100) / 100;

        Double coverage1 = mapOne.getCoverage();
        Double coverage2 = mapTwo.getCoverage();

        assertEquals(coverage1, coverage2);
        assertEquals(100.0, coverage1);
        assertEquals(100.0, coverage2);

    }

    public void testSplitMap() throws Exception{
        Boundary theBound = new Boundary(WIDTH/2,HEIGHT/2,WIDTH/4);
        int[][] theArray = getSplitMap(Color.BLUE.getRGB());
        splitMap.writeArray2File(theArray,"SplitMap");
        splitMap.updateTestCurrentWeather(theArray,theBound);

//        Double coverage = (double) Math.round(splitMap.getCoverage() * 100) / 100;
        Double coverage = splitMap.getCoverage();

        assertEquals(50.0, coverage);
    }

    public void testQuarterMap() throws Exception{
        Boundary theBound = new Boundary(WIDTH/2,HEIGHT/2,(WIDTH/2)-1);
        quarterMap.addBoundary(theBound);
        int[][] theArray = getQuarterMap(Color.RED.getRGB());
        quarterMap.writeArray2File(theArray,"QuarterMap");
        quarterMap.updateTestCurrentWeather(theArray,theBound);

        //Double coverage = (double) Math.round(quarterMap.getCoverage() * 100) / 100;

        Double coverage = quarterMap.getCoverage();

        assertEquals(25.0, coverage);

    }

    public void testUpdateWeatherArray() throws Exception {
        Boundary theBound = new Boundary(WIDTH/2,HEIGHT/2,(WIDTH/2)-1);
        mapOne.addBoundary(theBound);
        int[][] theArray = getFullMap(Color.RED.getRGB());
        mapOne.writeArray2File(theArray,"QuarterMap");
        mapOne.updateTestCurrentWeather(theArray,theBound);

       // Double coverage = (double) Math.round(mapOne.getCoverage() * 100) / 100;

        Double coverage = mapOne.getCoverage();

        assertEquals(100.0, coverage);

        theArray = getSplitMap(Color.RED.getRGB());
        mapOne.updateTestCurrentWeather(theArray,theBound);

        coverage = mapOne.getCoverage();


        assertEquals(50.0, coverage);

    }

}