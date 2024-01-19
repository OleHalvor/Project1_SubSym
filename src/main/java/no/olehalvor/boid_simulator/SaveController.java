package no.olehalvor.boid_simulator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Created by Olli on 01.02.2016.
 */
public class SaveController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField cohesionField;
    @FXML
    private TextField separationField;
    @FXML
    private TextField alignmentField;
    @FXML
    private TextField radiusField;
    @FXML
    private Button saveBtn;



    private Main Main;


    public SaveController() {
        System.out.println("wawt");
    }

    public void initialize() {

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Profile profile = new Profile(nameField.getText(),Logic.getCohesionWeight(),Logic.getSeparationWeight(), Logic.getAlignmentWeight(), Logic.getRadius());
                Main.addProfile(profile);
                System.out.println(Main.getProfiles());
                System.out.println("knapp");
                Node  source = (Node)  event.getSource();
                Stage stage  = (Stage) source.getScene().getWindow();
                Main.updateCombo();
                stage.close();
            }
        });
        nameField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    Profile profile = new Profile(nameField.getText(),Logic.getCohesionWeight(),Logic.getSeparationWeight(), Logic.getAlignmentWeight(), Logic.getRadius());
                    Main.addProfile(profile);
                    System.out.println(Main.getProfiles());
                    System.out.println("knapp");
                    Node  source = (Node)  event.getSource();
                    Stage stage  = (Stage) source.getScene().getWindow();
                    Main.updateCombo();
                    stage.close();
                }
            }
        });

        cohesionField.setText((Double.toString(Logic.getCohesionWeight())));
        separationField.setText((Double.toString(Logic.getSeparationWeight())));
        alignmentField.setText((Double.toString(Logic.getAlignmentWeight())));
        radiusField.setText((Double.toString(Logic.getRadius())));

    }

    public void setMainApp(Main Main) {
        this.Main = Main;
    }





}
