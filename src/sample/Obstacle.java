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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
