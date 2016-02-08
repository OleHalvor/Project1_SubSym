package sample;

import java.util.ArrayList;

/**
 * Created by Ole on 01.02.2016.
 */
public class Predator {
    private int x;
    private int y;
    private double velocityX, velocityY;

    public Predator(int x, int y, double velocityX, double velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }



    public void executeRules(Boid[] boids, Predator[] predators, ArrayList<Obstacle> obstaclesArr){
        Obstacle[] obstacles = new Obstacle[obstaclesArr.size()];
        obstacles = obstaclesArr.toArray(obstacles);
        ArrayList<Double> cohesion = cohesion(boids);
        ArrayList<Double> separation = separation(predators);
        ArrayList<Double> collisionAvoidance = collisionAvoidance(obstacles);

        int limit = 4;
        double new_x = this.getVelocityX() + 1*cohesion.get(0) + 10*collisionAvoidance.get(0) + 1*separation.get(0);
        double new_y = this.getVelocityY() + 1*cohesion.get(1) + 10*collisionAvoidance.get(1) + 1*separation.get(1);
        double new_total_velocity = Math.abs(Math.sqrt(Math.pow(new_x,2)+Math.pow(new_y,2)));
        if (new_total_velocity > limit){
            double ratio = limit/new_total_velocity;
            new_x = new_x * ratio;
            new_y = new_y * ratio;
        }
        this.setVelocityX(new_x);
        this.setVelocityY(new_y);

        this.setX( (int)( this.getX() + (this.getVelocityX()) ) );
        this.setY( (int)( this.getY() + (this.getVelocityY()) ) );
        double w = Main.getBoidWindowWidth();
        double h = Main.getBoidWindowHeight();
        if (this.getX() >= w){
            this.setX(0);
        }
        else if (this.getX() <=0) {
            this.setX((int)w);
        }
        if (this.getY() >= h){
            this.setY(0);
        }
        else if (this.getY() <= 0){
            this.setY((int)h);
        }
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

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    private ArrayList<Double> cohesion(Boid[] boids){

        if(boids.length<=0){
            ArrayList<Double> vector = new ArrayList<Double>(2);
            vector.add(0.0);
            vector.add(0.0);
            return vector;
        }
        Boid best_boid = boids[0];
        for (Boid b: boids){
            if (Logic.pred_distance(this,b)<Logic.pred_distance(this,best_boid)){
                best_boid = b;
            }
        }

        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add((double)best_boid.getx()-this.getX());
        vector.add((double)best_boid.gety()-this.getY());

        return vector; //steerTowards(goalX,goalY);
    }

    private ArrayList<Double> separation(Predator[] predators){

        double goalX = 0;
        double goalY = 0;

        if (predators.length<=0){
            ArrayList<Double> vector = new ArrayList<Double>(2);
            vector.add(0.0);
            vector.add(0.0);


            return vector; //steerTowards(goalX,goalY);
        }

        for (Predator p : predators){
            if(p==this) continue;
            goalX = goalX + p.getX();
            goalY = goalY+ p.getY();
        }//end Forloop

        goalX = goalX / (predators.length);
        goalY = goalY / (predators.length);


        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(this.getX()-goalX);
        vector.add(this.getX()-goalY);


        return vector; //steerTowards(goalX,goalY);
    }

    /* -- Start Collision Avoidance -- */
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
            boolean collision = lineIntersectsCircle(ahead, ahead2, currentObstacle);

            if(collision && ( closest==null ||
                    distance(this.getX(), this.getY(), currentObstacle.getX(), currentObstacle.getY()) <
                            distance(this.getX(), this.getY(), closest.getX(), closest.getY()) ) ){
                closest = currentObstacle;
            }
        }
        return closest;
    }

    private ArrayList<Double> collisionAvoidance(Obstacle[] obstacles){
        double lentgh = Math.sqrt( Math.pow(this.getVelocityX(),2)+Math.pow(this.getVelocityY(),2) );
        int maxSeeAhead = 20;
        ArrayList<Double> ahead = new ArrayList<Double>();
        ArrayList<Double> ahead2 = new ArrayList<Double>();
        ahead.add( this.getX()+( (this.getVelocityX()/lentgh)*maxSeeAhead ) );
        ahead.add( this.getY()+( (this.getVelocityY()/lentgh)*maxSeeAhead ) );
        ahead2.add (this.getX()+( (this.getVelocityX()/lentgh)*maxSeeAhead*0.5 ) );
        ahead2.add( this.getY()+( (this.getVelocityY()/lentgh)*maxSeeAhead*0.5 ) );

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
    /* -- End Collision Avoidance -- */
}
