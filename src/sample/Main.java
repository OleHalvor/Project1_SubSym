package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Random;


public class Main extends Application {

    private Stage primaryStage;
    private static Pane boidWindow;
    private Circle[] boidsCircle;
    private static Boid[] boids;
    private BorderPane rootLayout;

    public static double getBoidWindowWidth(){
        return boidWindow.getWidth();
    }

    public static double getBoidWindowHeight(){
        return boidWindow.getHeight();
    }

    public static Boid[] getBoids(){
        return boids;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Boid Simulation");
        initRootLayout();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("boidWindow.fxml"));
        boidWindow = null;
        try {
            boidWindow = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootLayout.setCenter(boidWindow);
        boidWindow.setId("bw");
    }

    public void stopSim(){
        for (int i=0; i<boidsCircle.length; i++){
            boidsCircle[i].setRadius(0.01);
        }
        try {
            logic.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    Logic logic = new Logic();
    public void startSim(int nBoids){
        boidsCircle = new Circle[nBoids];
        boids = new Boid[nBoids];
        final Random random = new Random();
        System.out.println("BOIDWINDOW W AND H: "+boidWindow.getWidth()+" "+ boidWindow.getHeight());

        for (int i=0; i<nBoids; i++) {
            double width = random.nextDouble()*boidWindow.getWidth();
            int w = (int) width;
            double height = random.nextDouble()*boidWindow.getHeight();
            int h = (int) height;
            boids[i] = new Boid(0,0,random.nextDouble()*6,random.nextInt(10)+1);
        }

        for (int i=0; i<nBoids; i++) {
            boidsCircle[i] = new Circle(boids[i].getx(),boids[i].gety(),2,Color.web("Black", 1));
            boidWindow.getChildren().add(boidsCircle[i]);
        }

        Logic logic = new Logic();
        logic.setDaemon(true);
        System.out.println("Starting boids");
        logic.start();

        for (int i=0; i<boids.length; i++){
            boids[i].setX(random.nextInt((int)boidWindow.getWidth()));
            boids[i].setY(random.nextInt((int)boidWindow.getHeight()));
        }

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //This is the animation loop. Called as often as possible up to the frequency of the monitor.
                for (int i=0; i<boids.length; i++){
                    boidsCircle[i].setLayoutX(boids[i].getx());
                    boidsCircle[i].setLayoutY(boids[i].gety());
                }
            }
        }.start();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("sample.fxml"));
            rootLayout = (BorderPane) loader.load();
            // Give the controller access to the main app.
            Controller controller = loader.getController();
            controller.setMainApp(this);



            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add("sample/styl.css");


            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}