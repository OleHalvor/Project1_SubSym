package sample;
import java.util.*;
/**
 * Created by Ole on 22.01.2016.
 */
public class Boid {
    private int x;
    private int y;
    private int dir;
    private double velocity;
    private boolean alive;

    public Boid(int x, int y, int dir, double velocity){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.velocity = velocity;
        this.alive = true;
    }

    /* --  Start Boid Rules  -- */
    private ArrayList<Double> cohesion(Boid[] boids){
        double goalX = 0;
        double goalY = 0;

        for (Boid b : boids){
            goalX = goalX + b.getx();
            goalY = goalY+ b.gety();
        }//end Forloop

        goalX = goalX / (boids.length);
        goalY = goalY / (boids.length);


        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(goalX);
        vector.add(goalY);
        return vector;
    }

    private ArrayList<Double> separation(Boid[] boids){
        double goalX = 0;
        double goalY = 0;

        for (Boid b : boids){
            goalX = goalX + this.getx() - b.getx();
            goalY = goalY + this.gety() - b.gety();
        }

        goalX = goalX/boids.length;
        goalY = goalY/boids.length;

        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(goalX);
        vector.add(goalY);

        return vector;
    }

    private ArrayList<Double> alignment(Boid[] boids){
        double dDir = 0;
        double dVel = 0;
        for (Boid b : boids) {
            dDir = dDir + b.getdir() - this.getdir();
            dVel = dVel + b.getVelocity() - this.getVelocity();
        }//End forloop
        dDir = dDir/boids.length;
        dVel = dVel/boids.length;

        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(dDir);
        vector.add(dVel);

        return vector;
    }
    /* --  End Boid Rules  -- */

    /* -- This method executes all the rules of a boid -- */

    public void executeRules(ArrayList<Boid[]> neighbours, double w1, double w2, double w3){
        //The rules now use different arrays of boids to calculate positions
        ArrayList<Double> cohesion = cohesion(neighbours.get(0));
        ArrayList<Double> separation = separation(neighbours.get(1));
        ArrayList<Double> alignment = alignment(neighbours.get(2));
    }

    /* -- Getters and Setters -- */
    public int getx(){return x;}
    public int gety(){return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public int getdir(){return dir;}

    public double getVelocity(){return velocity;}
    public void setVelocity(double velocity) {this.velocity = velocity;}
}
