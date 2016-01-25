package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

//Model boid class
public class Boid2 {

    private IntegerProperty x;
    private IntegerProperty y;
    private IntegerProperty velocity;
    private IntegerProperty direction;
    /**
     * Default constructor.
     */

    public Boid2(int x, int y, int velocity, int direction) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);

        // Some initial dummy data, just for convenient testing.
        this.velocity = new SimpleIntegerProperty(velocity);
        this.direction = new SimpleIntegerProperty(1234);
    }

    public int getx() {
        return x.get();
    }

    public void setx(int x) {
        this.x.set(x);
    }

    public IntegerProperty xproperty() {
        return x;
    }

}