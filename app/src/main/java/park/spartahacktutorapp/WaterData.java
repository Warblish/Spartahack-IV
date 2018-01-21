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
    public int id;
    public int quantity = 0;
    public WaterData(String food, float blue, float green, float grey, float impact, int id) {
        this.food = food;
        this.blue = blue;
        this.grey = grey;
        this.green = green;
        this.impact = impact;
        this.id = id;
    }
}
