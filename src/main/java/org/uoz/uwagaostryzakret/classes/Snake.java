package org.uoz.uwagaostryzakret.classes;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

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
    public int score;

    private double R;
    private double G;
    private double B;


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

    public Color getColor(){
        return Color.color(R, G, B);
    }

    public KeyCode getLeftKey(){
        return KeyCode.valueOf(leftKey);
    }

    public KeyCode getRightKey(){
        return KeyCode.valueOf(rightKey);
    }

    public void setLeftKey(KeyCode code){
        this.leftKey = code.toString();
    }

    public void setRightKey(KeyCode code){
        this.rightKey = code.toString();
    }

    public void setColor(Color color){
        this.R = color.getRed();
        this.G = color.getGreen();
        this.B = color.getBlue();
    }
}
