package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {

    private static Stage primaryStage;
    private static BorderPane rootLayout;
    private static Pane boidWindow;
    private static Circle[] boidsCircle;
    private static Boid[] boids;
    private static Line[] lines;
    private final static ArrayList<String> input = new ArrayList<String>();
    private static ArrayList<Circle> obstacleCircles = new ArrayList<Circle>();
    private static ArrayList<Circle> predatorCircles = new ArrayList<Circle>();
    private Logic logic = new Logic();
    private Controller controller;
    private VBox root;
    public static ArrayList<Profile> profiles = new ArrayList<Profile>();

    public static void main(String[] args) {
        launch(args);
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

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("sample.fxml"));
            rootLayout = loader.load();
            controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add("sample/styl.css");
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    String code = event.getCode().toString();
                    if(!input.contains(code))input.add(code);
                }
            });
            scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    String code = event.getCode().toString();
                    input.remove( code );
                }
            });
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSim(int nBoids){

        Line line = new Line();
        line.setStartX(100.0);
        line.setStartY(100.0);
        line.setEndX(100.0);
        line.setEndY(200.0);
        //boidWindow.getChildren().add(line);


        lines = new Line[nBoids];
        boidsCircle = new Circle[nBoids];
        boids = new Boid[nBoids];
        final Random random = new Random();
        System.out.println("BOIDWINDOW W AND H: "+boidWindow.getWidth()+" "+ boidWindow.getHeight());

        for (int i=0; i<nBoids; i++) {
            double width = random.nextDouble()*boidWindow.getWidth();
            int w = (int) width;
            double height = random.nextDouble()*boidWindow.getHeight();
            int h = (int) height;
            boids[i] = new Boid(0,0,random.nextInt(10)-5,random.nextInt(10)-5);

        }

        for (int i=0; i<nBoids; i++) {
            boidsCircle[i] = new Circle(boids[i].getx(),boids[i].gety(),2,Color.web("Black", 1));
            boidWindow.getChildren().add(boidsCircle[i]);
            //lines[i] = new Line(boids[i].getx(),boids[i].gety(),boids[i].getVelocityX(),boids[i].getVelocityY());
            lines[i] = new Line();
            boidWindow.getChildren().add(lines[i]);


        }

        Logic logic = new Logic();
        logic.setDaemon(true);
        System.out.println("Starting boids");
        logic.start();

        for (int i=0; i<boids.length; i++){
            boids[i].setX(random.nextInt((int)boidWindow.getWidth()));
            boids[i].setY(random.nextInt((int)boidWindow.getHeight()));
        }

        boidWindow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().toString().equals("PRIMARY")){
                    Logic.addObstacle((int)event.getX(),(int)event.getY());
                }
            }
        });



        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //This is the animation loop. Called as often as possible up to the frequency of the monitor.
                for (String s:input) System.out.println(s);

                for (int i=0; i<boids.length; i++){
                    boidsCircle[i].setLayoutX(boids[i].getx());
                    boidsCircle[i].setLayoutY(boids[i].gety());
                    lines[i].setStartX(boids[i].getx());
                    lines[i].setStartY(boids[i].gety());

                    lines[i].setEndX((boids[i].getx()+1.5*boids[i].getVelocityX()));
                    lines[i].setEndY((boids[i].gety()+1.5*boids[i].getVelocityY()));
                }

                for (int i=0; i<predatorCircles.size(); i++){
                    predatorCircles.get(i).setLayoutX(Logic.getPredators().get(i).getX());
                    predatorCircles.get(i).setLayoutY(Logic.getPredators().get(i).getY());
                }


            }
        }.start();
    }

    public void stopSim(){
        for (int i=0; i<lines.length; i++){
            lines[i].setVisible(false);
        }
        for (int i=0; i<boidsCircle.length; i++){
            boidsCircle[i].setVisible(false);
        }
        try {
            logic.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logic = new Logic();

    }

    public static void addObstacleCircle(int x, int y, int radius){
        Circle obstacle = new Circle(x,y,radius,Color.web("Blue", 1));
        obstacleCircles.add(obstacle);
        boidWindow.getChildren().add(obstacle);
    }

    public static void removeObstacles(){
        for (Circle c: obstacleCircles){
            c.setRadius(0);
        }
    }

    public static void addPredatorCircle(int x, int y, int radius){
        Circle predator = new Circle(0,0,radius,Color.web("Brown",1));
        predatorCircles.add(predator);
        boidWindow.getChildren().add(predator);
    }

    public static void removePredators(){
        for (Circle c: predatorCircles){
            c.setRadius(0);
        }
        predatorCircles = new ArrayList<Circle>();
    }

    public static Boid[] getBoids(){
        return boids;
    }

    public static Pane getBoidWindow(){
        return boidWindow;
    }

    public static double getBoidWindowWidth(){
        return boidWindow.getWidth();
    }

    public static double getBoidWindowHeight(){
        return boidWindow.getHeight();
    }

    public static ArrayList<String> getInput(){
        return input;
    }

    public static ArrayList<Profile> getProfiles(){
        return profiles;
    }

    public static void addProfile(Profile profile){
        profiles.add(profile);
    }

    public void loadProfile(){
        //Not in use. Comobox replaced this window
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("loadWeights.fxml"));
            root = loader.load();
            LoadController controller2 = loader.getController();
            controller2.profiles = profiles;
            controller2.setMainApp(this);
            loader.setController(controller);
            Stage stage = new Stage();
            stage.setTitle("Load Profile");
            stage.setScene(new Scene(root));
            stage.setX(0);
            stage.setY(0);
            stage.show();
            controller2.setMainCOntroller(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProfile(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("saveWeights.fxml"));
            root = loader.load();
            SaveController controller = loader.getController();
            controller.setMainApp(this);
            loader.setController(controller);
            Stage stage = new Stage();
            stage.setTitle("Save Profile");
            stage.setScene(new Scene(root));
            stage.setX(0);
            stage.setY(150);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCombo(){
        controller.profiles = Main.profiles;
        controller.updateCombo();
    }

}