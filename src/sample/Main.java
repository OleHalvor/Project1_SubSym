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


    private Stage primaryStage;
    private BorderPane rootLayout;
    private Circle[] boids;
    private int numberOfBoids = 20;

    private ObservableList<Boid2> boidData = FXCollections.observableArrayList();

    public ObservableList<Boid2> getPersonData() {
        return boidData;
    }

    public int getNBoids(){
        return numberOfBoids;
    }


    public void setNBoids(int nBoids){
        this.numberOfBoids = nBoids;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        boidData.add(new Boid2(10,200,3,10));
        boidData.add(new Boid2(100,70,3,10));
        boidData.add(new Boid2(20,200,3,10));
        boidData.add(new Boid2(200,70,3,10));


        initRootLayout();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("boidWindow.fxml"));
        Pane boidWindow = null;
        try {
            boidWindow = (Pane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set person overview into the center of root layout.
        rootLayout.setCenter(boidWindow);
        // Give the controller access to the main app.
        Controller controller = loader.getController();
        controller.setMainApp(this);


        //Circle circle = new Circle(50,50,25,Color.web("Black", 1));
        //boidWindow.getChildren().add(circle);

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