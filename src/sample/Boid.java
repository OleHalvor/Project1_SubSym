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
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.alive = true;
    }

    public boolean getAlive(){return alive;}

    public void setAlive(Boolean alive){this.alive = alive;}

    public boolean executeRules(ArrayList<Boid[]> neighbours, Obstacle[] obstacles, Predator[] predators, double w1, double w2, double w3, double w4){
        //The rules now use different arrays of boids to calculate positions
        if (amIDead(predators))return false;
        ArrayList<Double> cohesion = cohesion(neighbours.get(0));
        ArrayList<Double> separation = separation(neighbours.get(1));
        ArrayList<Double> alignment = alignment(neighbours.get(2));
        ArrayList<Double> collisionAvoidance = collisionAvoidance(obstacles);
        ArrayList<Double> avoidPredators = avoidPredators(predators);

        int limit = 4;
        double new_x = this.getVelocityX() + w1*cohesion.get(0) + w2*separation.get(0) + w3*alignment.get(0) + w4*collisionAvoidance.get(0) + avoidPredators.get(0);
        double new_y = this.getVelocityY() + w1*cohesion.get(1) + w2*separation.get(1) + w3*alignment.get(1) + w4*collisionAvoidance.get(1) + avoidPredators.get(1);
        double new_total_velocity = Math.abs(Math.sqrt(Math.pow(new_x,2)+Math.pow(new_y,2)));
        if (new_total_velocity > limit){
            double ratio = limit/new_total_velocity;
            new_x = new_x * ratio;
            new_y = new_y * ratio;
        }
        this.setVelocityX(new_x);
        this.setVelocityY(new_y);

        this.setX( (int)( this.getx() + (this.getVelocityX()) ) );
        this.setY( (int)( this.gety() + (this.getVelocityY()) ) );
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
        return true;
    }

    private boolean amIDead(Predator[] predators){
        for (Predator p: predators){
            if (Logic.pred_distance(p,this)<15){
                return true;

            }
        }
        return false;
    }

    private ArrayList<Double> avoidPredators(Predator[] predators){
        double goalX = 0;
        double goalY = 0;
        if(predators.length==0){
            ArrayList<Double> vector = new ArrayList<Double>(2);
            vector.add(0.0);
            vector.add(0.0);
            return vector;
        }

        for (Predator p : predators){

            goalX = goalX + p.getX();
            goalY = goalY+ p.getY();
        }

        goalX = goalX / (predators.length);
        goalY = goalY / (predators.length);

        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(this.getx()-goalX);
        vector.add(this.gety()-goalY);

        return vector;
    }

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
            goalX = goalX + b.getx();
            goalY = goalY+ b.gety();
        }
        goalX = goalX / (boids.length);
        goalY = goalY / (boids.length);
        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(this.getx()-goalX);
        vector.add(this.gety()-goalY);
        return vector;
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

    private double distance(double x1, double y1, double x2, double y2){return Math.sqrt( Math.pow(x1-x2,2)+Math.pow(y1-y2,2) );}

    private Boolean lineIntersectsCircle(ArrayList<Double> ahead, ArrayList<Double> ahead2, Obstacle o){
        if(distance(o.getX(), o.getY(), ahead.get(0), ahead.get(1)) <= o.getRadius() || distance(o.getX(), o.getY(), ahead2.get(0), ahead2.get(1)) <= o.getRadius()){
            return true;
        }
        return false;
    }

    private Obstacle findClosestObstacle(ArrayList<Double> ahead, ArrayList<Double> ahead2, Obstacle[] obstacles){
        Obstacle closest = null;

        for (int i=0; i<obstacles.length; i++){
            Obstacle currentObstacle = obstacles[i];

            if(lineIntersectsCircle(ahead, ahead2, currentObstacle) && ( closest==null ||
                    distance(this.getx(), this.gety(), currentObstacle.getX(), currentObstacle.getY()) <
                    distance(this.getx(), this.gety(), closest.getX(), closest.getY()) ) ){
                closest = currentObstacle;
            }
        }
        return closest;
    }

    private ArrayList<Double> collisionAvoidance(Obstacle[] obstacles){
        double length = Math.sqrt( Math.pow(this.getVelocityX(),2)+Math.pow(this.getVelocityY(),2) );
        int maxSeeAhead = 20;
        ArrayList<Double> ahead = new ArrayList<Double>();
        ArrayList<Double> ahead2 = new ArrayList<Double>();
        ahead.add( this.getx()+( (this.getVelocityX()/length)*maxSeeAhead ) );
        ahead.add( this.gety()+( (this.getVelocityY()/length)*maxSeeAhead ) );
        ahead2.add (this.getx()+( (this.getVelocityX()/length)*maxSeeAhead*0.5 ) );
        ahead2.add( this.gety()+( (this.getVelocityY()/length)*maxSeeAhead*0.5 ) );

        Obstacle closest = findClosestObstacle(ahead, ahead2, obstacles);

        double avoidanceX = 0;
        double avoidanceY = 0;

        if (closest != null){
            avoidanceX = ahead.get(0) - closest.getX();
            avoidanceY = ahead.get(1) - closest.getY();
        }
        else {
            avoidanceX = 0;
            avoidanceY = 0;
        }

        ArrayList<Double> vector = new ArrayList<Double>();
        vector.add(avoidanceX);
        vector.add(avoidanceY);
        return vector;
    }

    public int getx(){return x;}
    public int gety(){return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public double getVelocityX(){return velocityX;}
    public void setVelocityX(double velocityX) {this.velocityX = velocityX;}
    public double getVelocityY() {return velocityY;}
    public void setVelocityY(double velocityY) {this.velocityY = velocityY;}
}
