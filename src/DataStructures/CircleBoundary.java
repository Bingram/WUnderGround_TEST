package DataStructures;

import java.util.LinkedList;

/**
 * Created by Brian on 2/15/2015.
 */
public class CircleBoundary {

    private int myLat, myLon;


    private LinkedList<Point>[] quads =  new LinkedList[8];

    public CircleBoundary(){
        myLat = myLon = 0;
        setupQuads();
    }

    public CircleBoundary(int lat, int lon, int radius){

        myLat = lat;
        myLon = lon;

        setupQuads();

        makeCircle(lat,lon,radius);

    }

    private void setupQuads(){
        for(int i = 0; i<8; i++){
            quads[i] = new LinkedList<Point>();
        }
    }

    private void makeCircle(final int centerX, final int centerY, final int radius) {
        int d = (5 - radius * 4)/4;
        int x = 0;
        int y = radius;

        do {

            quads[0].add(new Point(centerX + x, centerY + y));
            quads[1].add(new Point(centerX + x, centerY - y));
            quads[2].add(new Point(centerX - x, centerY + y));
            quads[3].add(new Point(centerX - x, centerY - y));
            quads[4].add(new Point(centerX + y, centerY + x));
            quads[5].add(new Point(centerX + y, centerY - x));
            quads[6].add(new Point(centerX - y, centerY + x));
            quads[7].add(new Point(centerX - y, centerY - x));

            if (d < 0) {
                d += 2 * x + 1;
            } else {
                d += 2 * (x - y) + 1;
                y--;
            }
            x++;
        } while (x <= y);

        makeCenter(centerX, centerY, 3);//small circle for center

    }

    private void makeCenter(int centerX, int centerY, int radius) {
        int d = (5 - radius * 4)/4;
        int x = 0;
        int y = radius;

        do {

            quads[0].add(new Point(centerX + x, centerY + y));
            quads[1].add(new Point(centerX + x, centerY - y));
            quads[2].add(new Point(centerX - x, centerY + y));
            quads[3].add(new Point(centerX - x, centerY - y));
            quads[4].add(new Point(centerX + y, centerY + x));
            quads[5].add(new Point(centerX + y, centerY - x));
            quads[6].add(new Point(centerX - y, centerY + x));
            quads[7].add(new Point(centerX - y, centerY - x));

            if (d < 0) {
                d += 2 * x + 1;
            } else {
                d += 2 * (x - y) + 1;
                y--;
            }
            x++;
        } while (x <= y);
    }

    public LinkedList<Point>[] getQuads() {
        return quads;
    }

    public Point getCenter(){
        return new Point(myLon,myLat);
    }
}
