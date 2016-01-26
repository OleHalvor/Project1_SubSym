package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by Olli on 25.01.2016.
 */
public class Controller {

    @FXML
    private Button stopBtn;
    @FXML
    private Slider sliderWeight1;
    @FXML
    private Slider sliderWeight2;
    @FXML
    private Slider sliderWeight3;
    @FXML
    private Button resetBtn;
    @FXML
    private TextField nBoidsField;
    @FXML
    private Button startBtn;


    // Reference to the main application.
    private Main Main;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public Controller() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        resetBtn.setDisable(true);
        stopBtn.setDisable(true);
        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startBtn.setDisable(true);
                resetBtn.setDisable(false);
                stopBtn.setDisable(false);
                Main.startSim((Integer.parseInt(nBoidsField.getText())));

            }
        });
        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startBtn.setDisable(false);
                resetBtn.setDisable(true);
                Main.stopSim();

            }
        });
        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.stopSim();
                Main.startSim(Integer.parseInt(nBoidsField.getText()));

            }
        });
        sliderWeight1.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Logic.setWeight1((int)sliderWeight1.getValue());
                System.out.println("weight 1 changed");
            }
        });
        sliderWeight2.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Logic.setWeight2((int)sliderWeight2.getValue());
                System.out.println("weight 2 changed");
            }
        });
        sliderWeight3.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Logic.setWeight3((int)sliderWeight3.getValue());
                System.out.println("weight 3 changed");
            }
        });


    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param Main
     */
    public void setMainApp(Main Main) {
        this.Main = Main;

        // Add observable list data to the table
        //personTable.setItems(mainApp.getPersonData());
    }
}
