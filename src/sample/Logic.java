package sample;

/**
 * Created by Ole on 22.01.2016.
 */
public class Logic extends Thread {

    private static Boid[] boids;
    private static int weight1;
    private static int weight2;
    private static int weight3;

    public void run(){
        System.out.println("Logic running");
        updateBoids();

    }

    public static void updateBoids(){
        // DUMMY METHOD. This should be the method that runs the "rules" on all boids

        Boid[] boids = Main.getBoids();
        while(true){
            for(int i=0; i<boids.length; i++) {
                boids[i].setx(boids[i].getx() + 5);
                boids[i].sety(boids[i].gety() + 5);
            }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
    }
    //TODO: Generate boids
    //TODO: Update boids
    //TODO: Get method for boid positions

}
