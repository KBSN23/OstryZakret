package org.uoz.uwagaostryzakret.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.uoz.uwagaostryzakret.classes.Controller;
import org.uoz.uwagaostryzakret.classes.Game;
import org.uoz.uwagaostryzakret.classes.OfflineGame;
import org.uoz.uwagaostryzakret.classes.Options;

import java.util.concurrent.atomic.AtomicInteger;

public class OfflineGameController extends Controller {
    @FXML
    private ListView gameInfoList;

    @FXML
    private Label roundInfo;

    @Override
    public void init(){
        Game game = new OfflineGame();
        game.start();
    }
}
