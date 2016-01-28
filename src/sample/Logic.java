package sample;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ole on 22.01.2016.
 */
public class Logic extends Thread {

    final static Random random = new Random();

    private static Boid[] boids;
    private static int weight1;
    private static int weight2;
    private static int weight3;
    private static int n_radius = 750;

    public void run(){
        System.out.println("Logic running");
        updateBoids();

    }

    private static double boid_distance(Boid b1, Boid b2){
        return (Math.sqrt(Math.pow((b2.getx()-b1.getx()), 2)+Math.pow(b2.gety()-b1.gety(),2)));
    }

    public static Boid[] neighbours( Boid[] boids, Boid boid,int radius){
        ArrayList<Boid> best = new ArrayList<Boid>();
        for (Boid b: boids){
            if(!b.equals(boid)){
                if (boid_distance(b,boid)<radius){
                    best.add(b);
                }
            }
        }
        Boid[] best_boids = new Boid[best.size()];
        best_boids = best.toArray(best_boids);
        return best_boids;
    }

    public static void updateBoids() {

        Boid[] boids = Main.getBoids();
        while (true) {
            for (int i = 0; i < boids.length; i++) {
                //System.out.println("neighbour size: "+neighbours(boids,boids[i],200).length);
                boids[i].executeRules(neighbours(boids,boids[i],n_radius), 1, 1, 1);
                //boids[i].executeRules(boids, 1, 1, 1);

            }
            try {
                Thread.sleep(14);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setN_radius(int r){n_radius = r;}

    public static void setWeight1(int w){
        weight1 = w;
    }

    public static void setWeight2(int w){
        weight2 = w;
    }

    public static void setWeight3(int w){
        weight3 = w;
    }

    //TODO: Generate boids
    //TODO: Update boids
    //TODO: Get method for boid positions

}
