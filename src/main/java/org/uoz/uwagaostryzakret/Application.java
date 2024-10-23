package org.uoz.uwagaostryzakret;

import javafx.stage.Stage;
import org.uoz.uwagaostryzakret.scenes.IndexScene;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        IndexScene index = new IndexScene(stage, "Menu", "menu-view.fxml");
        index.setScene();
        index.show();

        index.setupController();
    }

    public static void main(String[] args) {
        launch();
    }
}