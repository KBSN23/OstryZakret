package org.uoz.uwagaostryzakret.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.uoz.uwagaostryzakret.classes.Controller;
import org.uoz.uwagaostryzakret.classes.Options;
import org.uoz.uwagaostryzakret.classes.Snake;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ResultSceneController extends Controller {
    @FXML
    Label winnerLabel;

    @FXML
    ListView<Label> resultsListView;

    @FXML
    public void handleBackToMenuClick(){
        backToMenu();
    }

    @Override
    public void init(){
        Snake gameWinner = Options.resultSnakes.stream()
                .max(Comparator.comparingInt(snake -> snake.getScore()))
                .orElse(null);

        winnerLabel.setText(gameWinner.name + " Wygrał!");

        ObservableList<Label> labels = FXCollections.observableArrayList();

        Options.resultSnakes.forEach(snake -> {
            Label label = new Label();
            label.setText(snake.name + " wynik: " + snake.getScore());
            labels.add(label);
        });

        resultsListView.setItems(labels);
    }
}