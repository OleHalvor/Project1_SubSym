package no.olehalvor.boid_simulator;

/**
 * Created by Olli on 01.02.2016.
 */
public class Profile {
    private String name;
    private double weight1;
    private double weight2;
    private double weight3;
    private int radius;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight1() {
        return weight1;
    }

    public void setWeight1(double weight1) {
        this.weight1 = weight1;
    }

    public double getWeight2() {
        return weight2;
    }

    public void setWeight2(double weight2) {
        this.weight2 = weight2;
    }

    public double getWeight3() {
        return weight3;
    }

    public void setWeight3(double weight3) {
        this.weight3 = weight3;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Profile(String name, double w1, double w2, double w3, int radius){
        this.name = name;
        this.weight1 = w1;
        this.weight2 = w2;
        this.weight3 = w3;
        this.radius = radius;

    }


}
