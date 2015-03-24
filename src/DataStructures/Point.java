package DataStructures;

/**
 * Created by Brian on 2/15/2015.
 */
public class Point{

    public int myX, myY;

    public Point(){
        myX=myY=-1;
    }

    public Point(int x, int y){

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

    public String toString(){

        return "(" + myX + "," + myY + ")";
    }

    @Override
    public int hashCode() {
        return super.hashCode() * myX * myY;
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

        if(p.getMyX()==myX && p.getMyY()==myY){
            result = true;
        }
        return result;
    }
}
