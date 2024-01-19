module no.olehalvor.boid_simulator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens no.olehalvor.boid_simulator to javafx.fxml;
    exports no.olehalvor.boid_simulator;
}