package org.uoz.uwagaostryzakret.controllers;

import javafx.fxml.FXML;
import org.uoz.uwagaostryzakret.classes.Controller;

import java.io.IOException;

public class IndexSceneController extends Controller {

    @FXML
    protected void onStartButtonClick() throws IOException {
        openNewScene("Uwaga ostry zakręt (Offline)", "offline-game.fxml");
    }

    @FXML
    protected void onOptionsButtonClick() throws IOException {
        openNewScene("Ustawienia gry", "options.fxml");
    }

    @FXML
    protected   void onExitButtonClick() throws  IOException {
        exit();
    }
}
