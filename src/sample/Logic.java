package sample;


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

    public void run(){
        System.out.println("Logic running");
        updateBoids();

    }

    public static void updateBoids() {
        // DUMMY METHOD. This should be the method that runs the "rules" on all boids

        Boid[] boids = Main.getBoids();
        while (true) {
            for (int i = 0; i < boids.length; i++) {

               boids[i].executeRules(boids, 1, 1, 1);

            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

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
