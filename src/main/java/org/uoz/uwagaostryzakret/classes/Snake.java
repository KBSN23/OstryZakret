package org.uoz.uwagaostryzakret.classes;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Snake implements Serializable {
    public ArrayList<Coordinate> coords = new ArrayList<>();
    public double speed = 2;
    public double direction;
    public boolean isTurningLeft;
    public boolean isTurningRight;
    public String leftKey;
    public String rightKey;
    public boolean isAlive = true;
    public String name;
    private int score;

    private double R;
    private double G;
    private double B;

    public int getScore() {
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void kill(){
        this.isAlive = false;
    }

    public Snake(Color color, KeyCode leftKey, KeyCode rightKey, String name) {
        this.leftKey = leftKey.toString();
        this.rightKey = rightKey.toString();
        this.name = name;

        this.R = color.getRed();
        this.G = color.getGreen();
        this.B = color.getBlue();
    }

    public String getName(){
        return name;
    }

    public void increaseScore(){
        this.score ++;
    }

    @JsonIgnore
    public Color getColor(){
        return Color.color(R, G, B);
    }

    @JsonIgnore
    public KeyCode getLeftKey(){
        return KeyCode.valueOf(leftKey);
    }

    @JsonIgnore
    public KeyCode getRightKey(){
        return KeyCode.valueOf(rightKey);
    }

    @JsonIgnore
    public void setLeftKey(KeyCode code){
        this.leftKey = code.toString();
    }

    @JsonIgnore
    public void setRightKey(KeyCode code){
        this.rightKey = code.toString();
    }

    @JsonIgnore
    public void setColor(Color color){
        this.R = color.getRed();
        this.G = color.getGreen();
        this.B = color.getBlue();
    }

    @JsonProperty("B")
    public void setB(double b) {
        B = b;
    }

    @JsonProperty("G")
    public void setG(double g) {
        G = g;
    }

    @JsonProperty("R")
    public void setR(double r) {
        R = r;
    }

    @JsonProperty("R")
    public double getR() {
        return R;
    }

    @JsonProperty("B")
    public double getB() {
        return B;
    }

    @JsonProperty("G")
    public double getG() {
        return G;
    }

    @JsonCreator
    public Snake(
            @JsonProperty("name") String name,
            @JsonProperty("direction") double direction,
            @JsonProperty("coords") ArrayList<Coordinate> coords,
            @JsonProperty("isTurningLeft") boolean isTurningLeft,
            @JsonProperty("isTurningRight") boolean isTurningRight,
            @JsonProperty("isAlive") boolean isAlive,
            @JsonProperty("score") int score,
            @JsonProperty("r") double R,
            @JsonProperty("g") double G,
            @JsonProperty("b") double B
    ) {
        this.name = name;
        this.direction = direction;
        this.coords = coords;
        this.isTurningLeft = isTurningLeft;
        this.isTurningRight = isTurningRight;
        this.isAlive = isAlive;
        this.leftKey = "A";
        this.rightKey = "D";
        this.score = score;

        this.R = R;
        this.G = G;
        this.B = B;
    }
}
