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
    }
}
