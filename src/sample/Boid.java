package sample;

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

    public void update_position(Boid[] boids, int weight1, int weight2, int wight3){
        //regel 1
        //regel 2 ++
        /*
        vector centreOfMass = 0;
        vector c = 0;
        vector pvj = 0;
        for (Boid boid : boids){
            centreOfMass += boid.position
            //
            if (abs(boid.position - this.position) > 100){
                c = c - (boid.position - this.position)
            }
            //
            pvj += boid.velocity

        }
        centreOfMass = centreOfMass/len(boids)-1
        pvj = pvj / len(boids)-1
        pvj = (pvj - bj.velocity) / 8

        this.velocity = velcity + (centreOfMass*weight1) + (c*weight2) + (pvj*weight3)
        this.position = position + velocity

         */
    }

    public int getx(){return x;}
    public int gety(){return y;}
    public int getdir(){return dir;}
    public double getvelocity(){return velocity;}

    public void setx(int x){this.x=x;}
    public void sety(int y){this.y=y;}
}
