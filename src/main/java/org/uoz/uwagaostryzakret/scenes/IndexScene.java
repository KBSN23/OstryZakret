package org.uoz.uwagaostryzakret.scenes;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.uoz.uwagaostryzakret.classes.Scene;

import java.io.IOException;

public class IndexScene extends Scene {
    public IndexScene(Stage stage, String title, String resourceName) throws IOException {
        super(stage, title, resourceName);
    }

    @Override
    public void setupController() {
        IndexSceneController controller =  loader.getController();
        controller.setup(stage);
    }
}
