package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Random;


public class Main extends Application {



    private Circle[] boidsCircle;
    private Boid[] boids;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private int numberOfBoids = 20;

    private ObservableList<Boid> boidData = FXCollections.observableArrayList();

    public ObservableList<Boid> getPersonData() {
        return boidData;
    }

    public int getNBoids(){
        return numberOfBoids;
    }


    public void setNBoids(int nBoids){
        this.numberOfBoids = nBoids;
    }

    private Pane boidWindow;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        initRootLayout();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("boidWindow.fxml"));
        boidWindow = null;
        try {
            boidWindow = (Pane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set person overview into the center of root layout.
        rootLayout.setCenter(boidWindow);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Her skjer animering
            }
        }.start();

        //Circle circle = new Circle(50,50,25,Color.web("Black", 1));
        //boidWindow.getChildren().add(circle);
    }

    public void startSim(int nBoids){
        boidsCircle = new Circle[nBoids];
        boids = new Boid[nBoids];
        final Random random = new Random();

        for (int i=0; i<nBoids; i++) {
            double width = random.nextDouble()*boidWindow.getWidth();
            int w = (int) width;
            double height = random.nextDouble()*boidWindow.getHeight();
            int h = (int) height;
            boids[i] = new Boid(w,h,5,10);
        }

        for (int i=0; i<nBoids; i++) {
            boidsCircle[i] = new Circle(boids[i].getx(),boids[i].gety(),10,Color.web("Black", 1));
            boidWindow.getChildren().add(boidsCircle[i]);
        }

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