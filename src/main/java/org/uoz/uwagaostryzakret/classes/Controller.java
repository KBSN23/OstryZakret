package org.uoz.uwagaostryzakret.classes;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

abstract public class Controller {
    protected Stage stage;
    protected History history;
    protected Options globalOptions;


    public void init(){

    };

    public void setup(Stage stage){
        this.stage = stage;
        this.history = new History();
        this.globalOptions = new Options();
    };



    public void setup(Stage stage, History history, Options options){
        this.stage = stage;
        this.history = history;
        this.globalOptions = options;
    };

    public void openNewScene(String title, String fxml) throws IOException {
        Scene newScene = new Scene(stage, title, fxml, history, globalOptions);
        newScene.setScene();
        newScene.show();
    }


    public void backToPreviousScene() {
        Scene previousScene = history.getPrevious();
        previousScene.setScene();
        previousScene.show();
    }

    public void backToMenu() {
        Scene firstScene = history.getFirst();
        firstScene.setScene();
        firstScene.show();
    }

    public void exit(){
        stage.close();
    }
}
