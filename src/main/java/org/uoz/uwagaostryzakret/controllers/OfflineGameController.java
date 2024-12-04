package org.uoz.uwagaostryzakret.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.uoz.uwagaostryzakret.classes.Controller;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.uoz.uwagaostryzakret.classes.Game;
import org.uoz.uwagaostryzakret.classes.Snake;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class OfflineGameController extends Controller {
    @FXML
    private Canvas myCanvas;

    @FXML
    private ListView gameInfoList;

    @FXML
    private Label roundInfo;

    @Override
    public void init(){
        GraphicsContext ctx = myCanvas.getGraphicsContext2D();

        Scene scene = stage.getScene();

        Consumer<Snake> handleWin = winner -> {
                try {
                    openNewScene("Wynik rozgrywki", "result-view.fxml");
                }
                catch (IOException err){
                    System.out.println(err);
                }
        };

       Runnable updateGameList = () -> {
            gameInfoList.getItems().clear();
            globalOptions.snakes.forEach(snake -> {

                HBox box = new HBox();
                Label nameLabel = new Label();
                Label scoreLabel = new Label();

                nameLabel.getStyleClass().add("gameList_element");
                scoreLabel.getStyleClass().add("gameList_element");

                nameLabel.setText(snake.name);
                scoreLabel.setText(String.valueOf(snake.score));

                box.getChildren().add(nameLabel);
                box.getChildren().add(scoreLabel);

                gameInfoList.getItems().add(box);
            });

            AtomicInteger scoreSum = new AtomicInteger();

           globalOptions.snakes.forEach(snake -> {
               scoreSum.addAndGet(snake.score);
           });

           if(globalOptions.numberOfRounds > scoreSum.get()){
               roundInfo.setTextFill(Color.WHITE);
               roundInfo.setText("Runda: " + String.valueOf(scoreSum.get()));
           }
           else {
               roundInfo.setTextFill(Color.RED);
               roundInfo.setText("Dogrywka!");
           }
        };

        Game game = new Game(ctx, scene, globalOptions, handleWin, updateGameList);

        globalOptions.snakes.forEach(snake -> {
            game.addSnake(snake);
            snake.score = 0;
        });

        updateGameList.run();
        game.start();
    }
}
