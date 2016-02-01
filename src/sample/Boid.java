package sample;
import java.util.*;

/**
 * Created by Ole on 22.01.2016.
 */
public class Boid {
    private int x;
    private int y;
    private double dir;
    private double velocityX, velocityY;
    private boolean alive;

    public Boid(int x, int y, double velocityX, double velocityY){
        this.x = x;
        this.y = y;
        //this.dir = dir; //radianer
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.alive = true;
    }

    /*private double steerTowards(double goalX, double goalY){
        double[] goalVector= new double[2];
        goalVector[0]=goalX-this.x;
        goalVector[1]=goalY-this.y;

        double[] currentVector=new double[2];
        currentVector[0]=this.getVelocity() * Math.sin(this.dir);
        currentVector[1]=this.getVelocity() * Math.cos(this.dir);

        return Math.atan2(currentVector[1],currentVector[0]) - Math.atan2(goalVector[1],goalVector[0]);
        //return Math.atan2(goalVector[1],goalVector[0]) - Math.atan2(currentVector[1],currentVector[0]);
    }*/

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
        vector.add(goalX-this.getx());
        vector.add(goalY-this.gety());


        return vector; //steerTowards(goalX,goalY);
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
        vector.add(goalX-this.getx());
        vector.add(goalY-this.gety());

        return vector;//steerTowards(goalX,goalY);
    }

    private ArrayList<Double> alignment(Boid[] boids){
        double dDir = 0;
        double dVelX = 0;
        double dVelY = 0;
        for (Boid b : boids) {
            dDir = dDir + b.getdir() - this.getdir();
            dVelX = dVelX + b.getVelocityX() - this.getVelocityX();
            dVelY = dVelY + b.getVelocityY() - this.getVelocityY();
        }//End forloop
        dDir = dDir/boids.length;
        dVelX = dVelX/boids.length;
        dVelY = dVelY/boids.length;

        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(Math.sin(dDir)*dVelX);
        vector.add(Math.cos(dDir)*dVelY);

        return vector;
    }
    /* --  End Boid Rules  -- */

    /* -- This method executes all the rules of a boid -- */

    public void executeRules(ArrayList<Boid[]> neighbours, double w1, double w2, double w3){
        //The rules now use different arrays of boids to calculate positions
        ArrayList<Double> cohesion = cohesion(neighbours.get(0));
        ArrayList<Double> separation = separation(neighbours.get(1));
        ArrayList<Double> alignment = alignment(neighbours.get(2));

        this.setVelocityX(this.getVelocityX() + w1*cohesion.get(0) + w2*separation.get(0) + w3*alignment.get(0));
        this.setVelocityY(this.getVelocityY() + w1*cohesion.get(1) + w2*separation.get(1) + w3*alignment.get(1));


        //System.out.println("Direction: " +this.dir);
        //System.out.println("Velocity: " +this.velocity);



        //if (this.velocity>=6)velocity=2;
        this.setX( (int)( this.getx() + (this.getVelocityX()) ) );
        this.setY( (int)( this.gety() + (this.getVelocityY()) ) );
        //System.out.println("BEFORE X: "+ this.x+"   Y: "+this.y);
        double w = Main.getBoidWindowWidth();
        double h = Main.getBoidWindowHeight();
        int r = 2;
        if (this.getx() > w-1){
            this.setX(1);
        }
        if (this.getx() < 1) {
            this.setX((int)w-1);
        }
        if (this.gety() > h-1){
            this.setY(1);
        }
        else if (this.gety() < 1){
            this.setY((int)h-1);
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
    public double getVelocityX(){return velocityX;}
    public void setVelocityX(double velocityX) {this.velocityX = velocityX;}
    public double getVelocityY() {return velocityY;}
    public void setVelocityY(double velocityY) {this.velocityY = velocityY;}
}
