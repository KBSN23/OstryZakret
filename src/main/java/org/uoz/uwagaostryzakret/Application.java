package org.uoz.uwagaostryzakret;

import javafx.stage.Stage;
import org.uoz.uwagaostryzakret.classes.History;
import org.uoz.uwagaostryzakret.classes.Scene;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene index = new Scene(stage, "Menu", "menu-view.fxml");
        History.add(index);
        index.setScene();
        index.show();
    }

    public static void main(String[] args) {
        launch();
    }
}