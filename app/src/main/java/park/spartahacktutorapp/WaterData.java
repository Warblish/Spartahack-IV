package park.spartahacktutorapp;

/**
 * Created by staff on 1/20/2018.
 */

public class WaterData {
    public String food;
    public float blue;
    public float grey;
    public float green;
    public float impact;
    public float quantity = 0;
    public WaterData(String food, float blue, float grey, float green, float impact) {
        this.food = food;
        this.blue = blue;
        this.grey = grey;
        this.green = green;
        this.impact = impact;
    }
}
