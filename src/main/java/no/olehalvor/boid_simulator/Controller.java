package no.olehalvor.boid_simulator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Created by Olli on 25.01.2016.
 */
public class Controller {

    @FXML
    private Button addObsBtn;
    @FXML
    private Button remObsBtn;
    @FXML
    private Button addPredBtn;
    @FXML
    private Button remPredBtn;
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
    @FXML
    private Button loadProfBtn;
    @FXML
    private Button saveProfBtn;
    @FXML
    private ComboBox loadCombo;
    @FXML
    private CheckBox chkMoveBoid;
    @FXML
    private CheckBox chkMovePred;

    ArrayList<Profile> profiles = new ArrayList<Profile>();
    private Main Main;

    public void setSliderWeight1(double w){sliderWeight1.setValue(w);}
    public void setSliderWeight2(double w){sliderWeight2.setValue(w);}
    public void setSliderWeight3(double w){sliderWeight3.setValue(w);}
    public void setRadSlider(double r){radSlider.setValue(r);}



    public Controller() {
    }

    @FXML
    private void initialize() {
        sliderWeight1.setValue(Logic.weight1);
        sliderWeight2.setValue(Logic.weight2);
        sliderWeight3.setValue(Logic.weight3);
        radSlider.setValue(Logic.n_radius);
        resetBtn.setDisable(true);
        stopBtn.setDisable(true);

        chkMoveBoid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Logic.toggeMoveBoid();
                if (Logic.getMovePred()){
                    chkMovePred.setSelected(false);
                    Logic.toggeMovePred();
                }

                Main.getBoidWindow().requestFocus();
            }
        });
        chkMovePred.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Logic.toggeMovePred();
                if (Logic.getMoveBoid()){
                    chkMoveBoid.setSelected(false);
                    Logic.toggeMoveBoid();
                }
                Main.getBoidWindow().requestFocus();
            }
        });

        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                startBtn.setDisable(true);
                resetBtn.setDisable(false);
                stopBtn.setDisable(false);
                Main.startSim((Integer.parseInt(nBoidsField.getText())));
                Main.getBoidWindow().requestFocus();
            }
        });
        //stopBtn.setVisible(false);
        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startBtn.setDisable(false);
                resetBtn.setDisable(true);
                stopBtn.setDisable(true);
                Main.stopSim();
                Logic.removeObstacles();
                Logic.removePredators();
            }
        });
        //resetBtn.setVisible(false);
        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Main.stopSim();
                Logic.removeObstacles();
                Logic.removePredators();
                Main.startSim(Integer.parseInt(nBoidsField.getText()));

            }
        });
        addObsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Logic.addObstacle();
            }
        });
        remObsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Logic.removeObstacles();
            }
        });
        addPredBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Logic.addPredator();
                Main.getBoidWindow().requestFocus();
            }
        });
        remPredBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Logic.removePredators();
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
        loadProfBtn.setVisible(false);
        loadProfBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.loadProfile();

            }
        });
        saveProfBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.saveProfile();
            }
        });

        profiles = Main.getProfiles();
        profiles.add(new Profile("Default",0.0054378,0.3,0.2,300));
        profiles.add(new Profile("In Place",0.2,0.1,0,300));
        profiles.add(new Profile("Black hole",1,0,0,300));
        profiles.add(new Profile("Spread Out",0,0.1,0,300));
        profiles.add(new Profile("Align",0,0.07,0.04,300));
        profiles.add(new Profile("Small Groups",0.01224,0.3,0,86));
        for (Profile p: profiles){
            loadCombo.getItems().add(p.getName());
        }
        loadCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                for (Profile p: profiles){
                    if (p.getName().equals(loadCombo.getValue())){
                        Logic.weight1=p.getWeight1();
                        Logic.weight2=p.getWeight2();
                        Logic.weight3=p.getWeight3();
                        Logic.n_radius=p.getRadius();
                        setSliderWeight1(p.getWeight1());
                        setSliderWeight2(p.getWeight2());
                        setSliderWeight3(p.getWeight3());
                        setRadSlider(p.getRadius());
                    }
                }

                System.out.println("Changed comobxo");
            }
        });
    }

    public void updateCombo(){
        loadCombo.getItems().removeAll();
        loadCombo.getItems().clear();
        for (Profile p: profiles){
            loadCombo.getItems().add(p.getName());
        }
    }

    public void setMainApp(Main Main) {
        this.Main = Main;
    }
}
