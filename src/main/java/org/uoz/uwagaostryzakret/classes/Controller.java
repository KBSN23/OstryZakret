package org.uoz.uwagaostryzakret.classes;

import javafx.stage.Stage;

abstract public class Controller {
    protected Stage stage;


    public void setup(Stage stage){
        this.stage = stage;
    };
}
