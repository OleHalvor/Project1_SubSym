package sample;
import java.util.*;

/**
 * Created by Ole on 22.01.2016.
 */
public class Boid {
    private int x;
    private int y;
    //private double dir;
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
        int count = boids.length;

        for (Boid b : boids){
            double d = Math.sqrt( Math.pow(b.getx()-this.getx(),2) + Math.pow(b.gety()-this.gety(),2) );
            double diffX = this.getx() - b.getx();
            double diffY = this.gety() - b.gety();
            double length = Math.sqrt(Math.pow(diffX, 2)+Math.pow(diffY,2));
            diffX=(diffX/length)/d;
            diffY=(diffY/length)/d;
            goalX = goalX + diffX;
            goalY = goalY + diffY;
            count += 1;
            }

        if (count>0){
            goalX = goalX/count;
            goalY = goalY/count;

        }
        if (Math.sqrt(Math.pow(goalX, 2)+Math.pow(goalY,2)) > 0){
            double length = Math.sqrt(Math.pow(goalX, 2)+Math.pow(goalY,2));
            int maxspeed = 2;
            goalX = (goalX/length)*maxspeed;
            goalY = (goalY/length)*maxspeed;

            goalX = goalX-this.getVelocityX();
            goalY = goalY-this.getVelocityY();
        }


        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(goalX);
        vector.add(goalY);

        return vector;//steerTowards(goalX,goalY);
    }

    private ArrayList<Double> alignment(Boid[] boids){
        if (boids.length<=0){
            ArrayList<Double> vector = new ArrayList<Double>(2);
            vector.add(0.0);
            vector.add(0.0);
        }

        double velX = 0;
        double velY = 0;

        for (Boid b : boids) {
            velX = velY + b.getVelocityX();
            velY = velY + b.getVelocityY();
        }//End forloop

        if (boids.length > 0){
            velX = velX/boids.length;
            velY = velY/boids.length;

            double length = Math.sqrt(Math.pow(velX, 2)+Math.pow(velY,2));
            int maxspeed = 2;
            velX = (velX/length)*maxspeed;
            velY = (velY/length)*maxspeed;

            velX = velX-this.getVelocityX();
            velY = velY-this.getVelocityY();
        }
        else{
            velX=0;
            velY=0;
        }


        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(velX);
        vector.add(velY);

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




        //if (this.velocity>=6)velocity=2;
        this.setX( (int)( this.getx() + (this.getVelocityX()) ) );
        this.setY( (int)( this.gety() + (this.getVelocityY()) ) );
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
    //public double getdir(){return dir;}

    //public void setDir(double d){this.dir = d;}
    public double getVelocityX(){return velocityX;}
    public void setVelocityX(double velocityX) {this.velocityX = velocityX;}
    public double getVelocityY() {return velocityY;}
    public void setVelocityY(double velocityY) {this.velocityY = velocityY;}
}
