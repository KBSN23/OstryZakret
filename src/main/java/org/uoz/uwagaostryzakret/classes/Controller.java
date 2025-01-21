package org.uoz.uwagaostryzakret.classes;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

abstract public class Controller {
    protected Stage stage;

    public abstract void init();

    public void setup(Stage stage){
        this.stage = stage;
    };

    public void openNewScene(String title, String fxml) throws IOException {
        Scene newScene = new Scene(stage, title, fxml);
        History.add(newScene);
        newScene.setScene();
        newScene.show();
    }

    public void backToPreviousScene() {
        Scene previousScene = History.getPrevious();
        previousScene.setScene();
        previousScene.show();
    }

    public void backToMenu() {
        Scene firstScene = History.getFirst();
        firstScene.setScene();
        firstScene.show();
    }

    public void exit(){
        stage.close();
    }
}
