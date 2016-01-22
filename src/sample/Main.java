package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.Random;


public class Main extends Application {

    private static final int number_of_boids = 20;
    final Circle[] boids = new Circle[number_of_boids];
    private final Random random = new Random();

    @Override
    public void start(final Stage primaryStage) {

        Group root = new Group();
        Scene scene = new Scene(root, 800, 800, Color.WHITE);

        for (int i=0; i<number_of_boids; i++) {
            boids[i] = new Circle(random.nextDouble()*scene.getWidth(),random.nextDouble()*scene.getHeight(),25,Color.web("Black", 1));
            root.getChildren().add(boids[i]);
        }

        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Her skjer animering
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}