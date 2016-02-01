package sample;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ole on 22.01.2016.
 */
public class Logic extends Thread {

    public static double weight1 = 0.01;
    public static double weight2 = 3;
    public static double weight3 = 0.01;
    public static int n_radius = 300;

    public void run(){
        updateBoids();
    }
    private static double boid_distance(Boid b1, Boid b2){
        return Math.abs((Math.sqrt(Math.pow((b2.getx()-b1.getx()), 2) + Math.pow(b2.gety()-b1.gety(),2))));
    }

    public static Boid[] neighbours( Boid[] boids, Boid boid, int radius){
        ArrayList<Boid> best = new ArrayList<Boid>();
        for (Boid b: boids){
                if (boid_distance(b,boid) < radius){
                    best.add(b);
            }
        }
        Boid[] best_boids = new Boid[best.size()];
        best_boids = best.toArray(best_boids);
        return best_boids;
    }

    public static void updateBoids() {
        ArrayList<Boid[]> neighbours = new ArrayList<Boid[]>();
        Boid[] boids = Main.getBoids();
        while (true) {
            for (int i = 0; i < boids.length; i++) {
                neighbours.add(neighbours(boids,boids[i],n_radius));
                neighbours.add(neighbours(boids,boids[i],n_radius/50));
                neighbours.add(neighbours(boids,boids[i],n_radius/2));
                boids[i].executeRules(neighbours, weight1, weight2, weight3);
                neighbours = new ArrayList<Boid[]>();
            }
            try {
                Thread.sleep(14);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setN_radius(int r){n_radius = r;}

    public static void setWeight1(double w){
        weight1 = w;
    }

    public static void setWeight2(double w){
        weight2 = w;
    }

    public static void setWeight3(double w){
        weight3 = w;
    }

}
