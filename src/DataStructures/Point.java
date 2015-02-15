package DataStructures;

/**
 * Created by Brian on 2/15/2015.
 */
public class Point{

    private int myX, myY, myColor;

    public Point(){
        myX=myY=myColor=-1;
    }

    public Point(int x, int y){
        myColor = 0;
        myX = x;
        myY = y;
    }

    public Point(int x, int y, int color){
        myColor = color;
        myX = x;
        myY = y;
    }


    public int getMyX() {
        return myX;
    }

    public void setMyX(int x) {
        this.myX = x;
    }

    public int getMyY() {
        return myY;
    }

    public void setMyY(int y) {
        this.myY = y;
    }

    public int getMyColor() {
        return myColor;
    }

    public void setMyColor(int myColor) {
        this.myColor = myColor;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * myX * myY * myColor;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        Point p = new Point();

        if(o == null || o.getClass() != getClass()){
            System.err.println(getClass()+" not a valid comparison with " + o.getClass());
        } else {
            p = (Point) o;//object is of correct class, cast it
        }

        if(p.getMyX()==myX && p.getMyY()==myY && p.getMyColor()==myColor){
            result = true;
        }
        return result;
    }
}
