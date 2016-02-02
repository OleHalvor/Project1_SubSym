package sample;

/**
 * Created by Ole on 01.02.2016.
 */
public class Obstacle {

    private int x;
    private int y;
    private double radius;


    public Obstacle(int x, int y, double radius){
        this.x = x;
        this.y = y;
        this.radius = radius;

    }

    public int getX() {
        return x;
    }

    public void setx(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void sety(int y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setradius(double radius) {
        this.radius = radius;
    }
}
