package org.uoz.uwagaostryzakret.classes;

public class Coordinate {
    public double x;
    public double y;
    public boolean visible;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
        this.visible = true;
    }


    public Coordinate(double x, double y, boolean visible) {
        this.x = x;
        this.y = y;
        this.visible = visible;
    }
}
