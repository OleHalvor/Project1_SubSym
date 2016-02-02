package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by Olli on 01.02.2016.
 */
public class LoadController {


    @FXML
    private TextField cohesionField;
    @FXML
    private TextField separationField;
    @FXML
    private TextField alignmentField;
    @FXML
    private TextField radiusField;
    @FXML
    private ComboBox profCombo;


    private Controller mainController;
    private Main Main;
    public static ArrayList<Profile> profiles = new ArrayList<Profile>();



    public void setMainCOntroller(Controller c){
        this.mainController = c;
    }

    public LoadController() {
        System.out.println("wawt");
    }

    private void fillProfile(String pName){
        System.out.println("pName: "+pName);
        for (Profile p: profiles){
            if (p.getName().equals(pName)){
                cohesionField.setText(Double.toString(p.getWeight1()));
                separationField.setText(Double.toString(p.getWeight2()));
                alignmentField.setText(Double.toString(p.getWeight3()));
                radiusField.setText(Double.toString(p.getRadius()));
            }
        }
    }

    public void initialize() {
        profiles = Main.getProfiles();
        for (Profile p: profiles){
            profCombo.getItems().add(p.getName());
        }
        profCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                fillProfile((String)profCombo.getValue());
                Logic.weight1=Double.parseDouble(cohesionField.getText());
                Logic.weight2=Double.parseDouble(separationField.getText());
                Logic.weight3=Double.parseDouble(alignmentField.getText());
                Logic.n_radius=(int)Double.parseDouble(radiusField.getText());

                mainController.setSliderWeight1(Double.parseDouble(cohesionField.getText()));
                mainController.setSliderWeight2(Double.parseDouble(separationField.getText()));
                mainController.setSliderWeight3(Double.parseDouble(alignmentField.getText()));
                mainController.setRadSlider(Double.parseDouble(radiusField.getText()));
                System.out.println("Changed comobxo");
            }
        });
        System.out.println(profiles);
        profCombo.getItems().add("ger");
        cohesionField.setText((Double.toString(Logic.getCohesionWeight())));
        separationField.setText((Double.toString(Logic.getSeparationWeight())));
        alignmentField.setText((Double.toString(Logic.getAlignmentWeight())));
        radiusField.setText((Double.toString(Logic.getRadius())));

    }

    public void setMainApp(Main Main) {
        this.Main = Main;
        System.out.println("main app set");
    }





}
