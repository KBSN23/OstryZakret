package org.uoz.uwagaostryzakret;

import javafx.stage.Stage;
import org.uoz.uwagaostryzakret.classes.History;
import org.uoz.uwagaostryzakret.classes.Options;
import org.uoz.uwagaostryzakret.classes.Scene;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        final History history = new History();
        final Options globalOptions = Options.loadOptionsFromFile("options.ser");
//        final Options globalOptions = new Options();

        Scene index = new Scene(stage, "Menu", "menu-view.fxml", history, globalOptions);
        index.setScene();
        index.show();
    }

    public static void main(String[] args) {
        launch();
    }
}