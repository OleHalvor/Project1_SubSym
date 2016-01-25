package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * Created by Olli on 25.01.2016.
 */
public class Controller {

    @FXML
    private Label nBoids;
    @FXML
    private Slider nBoidsSlider;


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
        nBoids.setText(Integer.toString(Main.getNBoids()));
        nBoidsSlider.setValue(Main.getNBoids());
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
