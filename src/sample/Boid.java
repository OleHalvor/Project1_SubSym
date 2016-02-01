package sample;
import java.util.*;

/**
 * Created by Ole on 22.01.2016.
 */
public class Boid {
    private int x;
    private int y;
    private double dir;
    private double velocity;
    private boolean alive;

    public Boid(int x, int y, double dir, double velocity){
        this.x = x;
        this.y = y;
        this.dir = dir; //radianer
        this.velocity = velocity;
        this.alive = true;
    }

    private double steerTowards(double goalX, double goalY){
        double[] goalVector= new double[2];
        goalVector[0]=goalX-this.x;
        goalVector[1]=goalY-this.y;

        double[] currentVector=new double[2];
        currentVector[0]=this.getVelocity() * Math.sin(this.dir);
        currentVector[1]=this.getVelocity() * Math.cos(this.dir);

        return Math.atan2(currentVector[1],currentVector[0]) - Math.atan2(goalVector[1],goalVector[0]);
        //return Math.atan2(goalVector[1],goalVector[0]) - Math.atan2(currentVector[1],currentVector[0]);
    }

    /* --  Start Boid Rules  -- */
    private double cohesion(Boid[] boids){
        if (boids.length<=0){
            return 0;
        }
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


        return steerTowards(goalX,goalY);
    }

    private double separation(Boid[] boids){
        if (boids.length<=0){
            return 0;
        }
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

        return steerTowards(goalX,goalY);
    }

    private ArrayList<Double> alignment(Boid[] boids){
        if (boids.length<=0){
            ArrayList<Double> vector = new ArrayList<Double>(2);
            vector.add(0.0);
            vector.add(0.0);
        }
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
        double cohesion = cohesion(neighbours.get(0));
        double separation = separation(neighbours.get(1));
        ArrayList<Double> alignment = alignment(neighbours.get(2));

        this.setVelocity(this.getVelocity()+w3*alignment.get(1));

        this.setDir(this.getdir() + w1*cohesion + w2*separation + w3*alignment.get(0));



        if (this.velocity>=6)velocity=2;
        this.x=((int)(this.getx() + (this.getVelocity() * Math.sin(this.dir))));
        this.y=((int)(this.gety() + (this.getVelocity() * Math.cos(this.dir))));
        //System.out.println("BEFORE X: "+ this.x+"   Y: "+this.y);
        double w = Main.getBoidWindowWidth();
        double h = Main.getBoidWindowHeight();
        if (this.getx() >= w-2){
            this.setX(0);
        }
        else if (this.getx() <= 0) {
            this.setX((int)w);
        }
        if (this.gety() >= h-2){
            this.setY(0);
        }
        else if (this.gety() <= 0){
            this.setY((int)h);
        }
        //System.out.println("AFTER  X: "+ this.x+"   Y: "+this.y);


    }

    /* -- Getters and Setters -- */
    public int getx(){return x;}
    public int gety(){return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public double getdir(){return dir;}

    public void setDir(double d){this.dir = d;}
    public double getVelocity(){return velocity;}
    public void setVelocity(double velocity) {this.velocity = velocity;}
}
