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

    /* --  Start Boid Rules  -- */
    private ArrayList<Double> rule1(Boid[] boids){
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

    private ArrayList<Double> rule2(Boid[] boids){
        double x = 0;
        double y = 0;
        for (Boid b : boids){
            if (!b.equals(this)){
                int diffX = Math.abs(b.getx()-this.getx());
                int diffY = Math.abs(b.gety()-this.gety());
                if (Math.sqrt(Math.pow(diffX, 2)+Math.pow(diffY,2)) < 100){
                    x = x - (b.getx() - this.getx());
                    y = y - (b.getx() - this.gety());
                }
            }
        }//End Forloop

        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(x);
        vector.add(y);

        return vector;
    }

    private ArrayList<Double> rule3(Boid[] boids){
        double x = 0;
        double y = 0;
        for (Boid b : boids) {
            if (!b.equals(this)) {
                x = x + b.getVelocityX();
                y = y + b.getVelocityY();
            }
        }//End forloop
        x = x / (boids.length - 1);
        y = y / (boids.length - 1);

        x = (x - this.getVelocityX()) / 8;
        y = (y - this.getVelocityY()) / 8;

        ArrayList<Double> vector = new ArrayList<Double>(2);
        vector.add(x);
        vector.add(y);

        return vector;
    }
    /* --  End Boid Rules  -- */

    /* -- This method executes all the rules of a boid -- */
    public void executeRules(Boid[] boids, int w1, int w2, int w3){
        ArrayList<Double> rule1 = rule1(boids);
        ArrayList<Double> rule2 = rule2(boids);
        ArrayList<Double> rule3 = rule3(boids);

        this.setVelocityX(this.getVelocityX()+(rule1.get(0)*w1)+(rule2.get(0)*w2)+(rule3.get(0)*w3));
        this.setVelocityY(this.getVelocityY()+(rule1.get(1)*w1)+(rule2.get(1)*w2)+(rule3.get(1)*w3));

        double vLim = 5;
        double Velocity = Math.sqrt(Math.pow(this.getVelocityX(),2)+Math.pow(this.getVelocityY(),2));
        if (Velocity > vLim){
            this.setVelocityX((this.getVelocityX()/Velocity)*vLim);
            this.setVelocityY((this.getVelocityY()/Velocity)*vLim);
        }

        this.setX(this.getx()+(int)this.getVelocityX());
        this.setY(this.gety()+(int)this.getVelocityY());
    }

    /* -- Getters and Setters -- */
    public int getx(){return x;}
    public int gety(){return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public int getdir(){return dir;}

    public double getVelocityX(){return velocityX;}
    public double getVelocityY(){return velocityY;}
    public void setVelocityX(double velocityX) {this.velocityX = velocityX;}
    public void setVelocityY(double velocityY) {this.velocityY = velocityY;}
}
