package sample;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ole on 22.01.2016.
 */
public class Logic extends Thread {

    public static double weight1 = 0.001;
    public static double weight2 = 0.3;
    public static double weight3 = 0.1;
    public static double weight4 = 0.3;
    public static int n_radius = 300;


    public static double getCohesionWeight(){
        return weight1;
    }
    public static double getSeparationWeight(){
        return weight2;
    }
    public static double getAlignmentWeight(){
        return weight3;
    }
    public static double getCollisionAvoidanceWeight(){ return weight4; }
    public static int getRadius(){
        return n_radius;
    }


    private static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private static ArrayList<Predator> predators = new ArrayList<Predator>();


    public static ArrayList<Predator> getPredators(){
        return predators;
    }
    public static void addObstacle(){
        System.out.println("Obstacle added");
        Random random = new Random();
        int w = (int) Main.getBoidWindowWidth();
        int h = (int) Main.getBoidWindowHeight();

        int obstacleRadius = random.nextInt(20)+10;
        int obstacleX = random.nextInt(w);
        int obstacleY = random.nextInt(h);


        Obstacle obstacle = new Obstacle(obstacleX,obstacleY,obstacleRadius);
        obstacles.add(obstacle);

        Main.addObstacleCircle(obstacleX,obstacleY,obstacleRadius-7);
    }

    public static void removeObstacles(){
        System.out.println("Obstacles removed");
        Main.removeObstacles();
        obstacles = new ArrayList<Obstacle>();

    }

    public static void addPredator(){
        System.out.println("Predator added");
        Random random = new Random();
        int w = (int) Main.getBoidWindowWidth();
        int h = (int) Main.getBoidWindowHeight();

        int predatorRadius = 6;
        int predatorX = random.nextInt(w);
        int predatorY = random.nextInt(h);

        int predatorXVel = random.nextInt(10)-5;
        int predatorYVel = random.nextInt(10)-5;


        Predator predator = new Predator(predatorX,predatorY,predatorXVel,predatorYVel);
        predators.add(predator);

        Main.addPredatorCircle(predatorX,predatorY,predatorRadius);

    }

    public static void removePredators(){
        System.out.println("Predators removed");
        Main.removePredators();
        predators = new ArrayList<Predator>();

    }


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

    private static double pred_distance(Predator p, Boid b2){
        return Math.abs((Math.sqrt(Math.pow((b2.getx()-p.getX()), 2) + Math.pow(b2.gety()-p.getY(),2))));
    }
    private static double pred_distance(Predator p, Predator b2){
        return Math.abs((Math.sqrt(Math.pow((b2.getX()-p.getX()), 2) + Math.pow(b2.getY()-p.getY(),2))));
    }

    public static Predator[] pred_neighbours( Predator[] predators, Boid boid, int radius){
        ArrayList<Predator> best = new ArrayList<Predator>();
        for (Predator p: predators){
            if (pred_distance(p,boid) < radius){
                best.add(p);
            }
        }
        Predator[] best_preds = new Predator[best.size()];
        best_preds = best.toArray(best_preds);
        return best_preds;
    }

    public static Predator[] pred_neighbours( Predator[] predators, Predator predator, int radius){
        ArrayList<Predator> best = new ArrayList<Predator>();
        for (Predator p: predators){
            if ((pred_distance(p,predator) < radius)&&(!p.equals(predator))){
                best.add(p);
            }
        }
        Predator[] best_preds = new Predator[best.size()];
        best_preds = best.toArray(best_preds);
        return best_preds;
    }

    public static Boid[] pred_boid_neighbours( Boid[] boids, Predator pred, int radius){
        ArrayList<Boid> best = new ArrayList<Boid>();
        for (Boid b: boids){
            if (pred_distance(pred,b) < radius){
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
                neighbours.add(neighbours(boids,boids[i],n_radius/20));
                neighbours.add(neighbours(boids,boids[i],n_radius/2));
                Predator[] pred_array = new Predator[predators.size()];
                pred_array = predators.toArray(pred_array);
                //boids[i].executeRules(neighbours, weight1, weight2, weight3);
                Obstacle[] o_array = new Obstacle[obstacles.size()];
                o_array = obstacles.toArray(o_array);


                boids[i].executeRules(neighbours, o_array, (pred_neighbours(pred_array,boids[i],n_radius/8)), weight1, weight2, weight3, weight4);
                neighbours = new ArrayList<Boid[]>();
            }
            for (int i=0; i<predators.size(); i++){
                Predator[] pred_array = new Predator[predators.size()];
                pred_array = predators.toArray(pred_array);
                predators.get(i).executeRules(pred_boid_neighbours(boids,predators.get(i),200),pred_neighbours(pred_array,predators.get(i),25),obstacles);
                //predators.get(i).executeRules(pred_boid_neighbours(boids,predators.get(i),200),pred_array,obstacles);
                System.out.println("updating predator #"+i);
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
