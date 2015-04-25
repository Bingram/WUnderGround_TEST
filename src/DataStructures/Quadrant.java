package DataStructures;

/**
 * Created by b on 4/15/15.
 */
public class Quadrant {

    private PointList level0,level1,level2,level3;

    /**
     * Quadrant contains different levels of boundary edges
     * These edges are a PointList, with level 0 being the outer most
     * boundary edge, and level 3 being the inner most edge.
     */
    public Quadrant(){
        level0=level1=level2=level3= new PointList();
    }

    public PointList getLevel0() {
        return level0;
    }

    public void setLevel0(PointList level0) {
        this.level0 = level0;
    }

    public PointList getLevel1() {
        return level1;
    }

    public void setLevel1(PointList level1) {
        this.level1 = level1;
    }

    public PointList getLevel2() {
        return level2;
    }

    public void setLevel2(PointList level2) {
        this.level2 = level2;
    }

    public PointList getLevel3() {
        return level3;
    }

    public void setLevel3(PointList level3) {
        this.level3 = level3;
    }


}
