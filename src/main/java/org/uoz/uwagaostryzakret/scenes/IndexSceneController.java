package org.uoz.uwagaostryzakret.scenes;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.uoz.uwagaostryzakret.classes.Controller;

import java.io.IOException;

public class IndexSceneController extends Controller {
    @FXML
    protected void onStartButtonClick() throws IOException {
        SecondScene s2 = new SecondScene(stage, "Page 2", "second-view.fxml");
        s2.setScene();
        s2.show();
    }
}
