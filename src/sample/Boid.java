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

    /* --  Start SIMPLE Boid Rules  -- */
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

            goalX = goalX + b.getx();
            goalY = goalY+ b.gety();
        }//end Forloop

        goalX = goalX / (boids.length);
        goalY = goalY / (boids.length);


        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(this.getx()-goalX);
        vector.add(this.gety()-goalY);


        return vector; //steerTowards(goalX,goalY);
    }

    private ArrayList<Double> alignment(Boid[] boids){
        if (boids.length<=0){
            ArrayList<Double> vector = new ArrayList<Double>(2);
            vector.add(0.0);
            vector.add(0.0);
            return vector;
        }


        int x_tot = 0;
        int y_tot = 0;
        for (Boid b: boids){
            x_tot+=b.getVelocityX();
            y_tot+=b.getVelocityY();
        }
        double x_avg = x_tot/boids.length;
        double y_avg = y_tot/boids.length;
        ArrayList<Double> vector = new ArrayList<Double>();
        vector.add(x_avg);
        vector.add(y_avg);
        return vector;
    }
    /* --  End SIMPLE Boid Rules  -- */

    /* -- Start Collision Avoidance -- */
    private double distance(int x1, int y1, Obstacle o){return Math.sqrt( Math.pow(x1-x2,2)+Math.pow(y1-y2,2) );}

    private Boolean lineIntersectsCircle(double[] ahead,double[] ahead2, Obstacle o){
        if(distance(o.getx(), o.gety(), ahead[0], ahead[1]) <= o.getradius() || distance(o.getx(), o.gety(), ahead2[0], ahead2[1]) <= o.getradius()){
            return true;
        }
        return false;
    }

    private Obstacle findClosestObstacle(double[] ahead,double[] ahead2, Obstacle[] obstacles){
        Obstacle closest = null;

        for (int i=0; i<obstacles.length; i++){
            Obstacle currentObstacle = obstacles[i];
            boolean collision = lineIntersectsCircle(ahead, ahead2, currentObstacle);

            if(collision && ( closest==null ||
                    distance(this.getx(), this.gety(), currentObstacle.getx(), currentObstacle.gety()) <
                    distance(this.getx(), this.gety(), closest.getx(), closest.gety()) ) ){
                closest = currentObstacle;
            }
        }
        return closest;
    }

    private ArrayList<Double> collisionAvoidance(Obstacle[] obstacles){
        ahead = ...;
        ahead2 = ...;

        Obstacle closest = findClosestObstacle(ahead, ahead2, obstacles);

        double avoidanceX = 0;
        double avoidanceY = 0;

        if (closest != null){
            avoidanceX = avoidanceX - closest.getx();
            avoidanceY = avoidanceY - closest.gety();
        }
    }
    /* -- End Collision Avoidance -- */

    /* -- This method executes all the rules of a boid -- */
    public void executeRules(ArrayList<Boid[]> neighbours, double w1, double w2, double w3){
        //The rules now use different arrays of boids to calculate positions
        ArrayList<Double> cohesion = cohesion(neighbours.get(0));
        ArrayList<Double> separation = separation(neighbours.get(1));
        ArrayList<Double> alignment = alignment(neighbours.get(2));

        int limit = 2;
        double new_x =this.getVelocityX() + w1*cohesion.get(0) + w2*separation.get(0) + w3*alignment.get(0);
        double new_y = this.getVelocityY() + w1*cohesion.get(1) + w2*separation.get(1) + w3*alignment.get(1);
        double new_total_velocity = Math.abs(Math.sqrt(Math.pow(new_x,2)+Math.pow(new_y,2)));
        if (new_total_velocity>limit){
            double ratio = limit/new_total_velocity;
            new_x = new_x * ratio;
            new_y = new_y * ratio;
        }
        this.setVelocityX(new_x);
        this.setVelocityY(new_y);


        this.setX( (int)( this.getx() + (this.getVelocityX()) ) );
        this.setY( (int)( this.gety() + (this.getVelocityY()) ) );
        //System.out.println("BEFORE X: "+ this.x+"   Y: "+this.y);
        double w = Main.getBoidWindowWidth();
        double h = Main.getBoidWindowHeight();
        if (this.getx() >= w){
            this.setX(0);
        }
        else if (this.getx() <=0) {
            this.setX((int)w);
        }
        if (this.gety() >= h){
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
