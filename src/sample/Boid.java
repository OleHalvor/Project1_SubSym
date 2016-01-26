package sample;
import java.util.*;
/**
 * Created by Ole on 22.01.2016.
 */
public class Boid {
    private int x;
    private int y;
    private int dir;
    private double velocityX;
    private double velocityY;
    private boolean alive;

    public Boid(int x, int y, int dir, double velocityX, double velocityY){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.alive = true;
    }
    public ArrayList<Double> rule1(Boid[] boids, int weight1){
        double x = 0;
        double y = 0;

        for (Boid b : boids){
            if (!b.equals(this)){
                x = x + b.getx();
                y = y + b.gety();
            }
        }//end Forloop

        x = x / (boids.length - 1);
        y = y / (boids.length - 1);

        x = (x - this.getx())/100;
        y = (y - this.gety())/100;

        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(x);
        vector.add(y);

        return vector;
    }


    public void update_position(Boid[] boids, int weight1, int weight2, int weight3) {
        //regel 1
        //regel 2 ++
        List centreOfMAss = new ArrayList<Double>(2);
        List c = new ArrayList<Double>(2);
        List pvj = new ArrayList<Double>(2);
        //
    }

    public int getx(){return x;}
    public int gety(){return y;}
    public int getdir(){return dir;}
    public double getvelocityX(){return velocityX;}
    public double getvelocityY(){return velocityY;}

    public void setx(int x){this.x=x;}
    public void sety(int y){this.y=y;}
}
