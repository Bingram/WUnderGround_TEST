/**
 * Created by b on 3/12/15.
 */
public class WundergroundTest2 {

    private static WeatherController myController;

    public static void main(String[] args){
        myController = new WeatherController("TEST");

        myController.setBoundary(-122.70667,47.6339, 100);

        myController.run();
    }
}
