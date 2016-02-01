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
    private Button addObsBtn;
    @FXML
    private Button remObsBtn;

    @FXML
    private Slider radSlider;
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
        sliderWeight1.setValue(Logic.weight1);
        sliderWeight2.setValue(Logic.weight2);
        sliderWeight3.setValue(Logic.weight3);
        radSlider.setValue(Logic.n_radius);

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
                stopBtn.setDisable(true);
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
                Logic.setWeight1((sliderWeight1.getValue()));
            }
        });
        sliderWeight2.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Logic.setWeight2((sliderWeight2.getValue()));
            }
        });
        sliderWeight3.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Logic.setWeight3((sliderWeight3.getValue()));

            }
        });
        radSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Logic.setN_radius((int)radSlider.getValue());
                System.out.println("Radius: "+radSlider.getValue());
            }
        });
    }

    public void setMainApp(Main Main) {
        this.Main = Main;
    }
}
