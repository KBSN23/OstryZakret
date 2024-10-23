package org.uoz.uwagaostryzakret.scenes;

import javafx.stage.Stage;
import org.uoz.uwagaostryzakret.classes.Scene;

import java.io.IOException;

public class SecondScene extends Scene {
    public SecondScene(Stage stage, String title, String resourceName) throws IOException {
        super(stage, title, resourceName);
    }

    @Override
    public void setupController() {
        SecondSceneController controller = loader.getController();
        controller.setup(stage);
    }
}
