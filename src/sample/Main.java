package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) {
        final Circle circle = new Circle(150, Color.web("Black", 0.05));
        final Group root = new Group();
        root.getChildren().add(circle);
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                circle.setTranslateX(circle.getTranslateX()+1);
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}