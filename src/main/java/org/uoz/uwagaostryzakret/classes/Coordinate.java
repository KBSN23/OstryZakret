package org.uoz.uwagaostryzakret.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {
    public double x;
    public double y;
    public boolean visible;

    @JsonCreator
    public Coordinate(
            @JsonProperty("x") double x,
            @JsonProperty("y") double y,
            @JsonProperty("visible") boolean visible
    ) {
        this.x = x;
        this.y = y;
        this.visible = visible;
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
        this.visible = true;
    }
}
