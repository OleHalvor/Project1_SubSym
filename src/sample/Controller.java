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

        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startBtn.setDisable(true);
                Main.startSim((Integer.parseInt(nBoidsField.getText())));

            }
        });
        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startBtn.setDisable(false);
                Main.stopSim();

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
