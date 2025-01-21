package org.uoz.uwagaostryzakret.classes;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public abstract class Game {
    protected ArrayList<Snake> snakes;
    protected GraphicsContext ctx;
    protected final double TURNING_SPEED = 3;
    protected Timeline timeline;
    protected int currentRound = 1;
    protected int numberOfRounds = 0;

    public Game() {
        Canvas canvas = (Canvas) History.getCurrent().getFXScene().lookup("#gameCanvas");
        this.ctx = canvas.getGraphicsContext2D();
        this.snakes = new ArrayList<>();
    }

    public abstract void handleKeyboardEvents();

    public void updateScoreboard(){
        ListView gameInfoList = (ListView) History.getCurrent().getFXScene().lookup("#gameInfoList");
        Label roundInfo = (Label) History.getCurrent().getFXScene().lookup("#roundInfo");


        gameInfoList.getItems().clear();
        snakes.forEach(snake -> {

            HBox box = new HBox();
            Label nameLabel = new Label();
            Label scoreLabel = new Label();

            nameLabel.getStyleClass().add("gameList_element");
            scoreLabel.getStyleClass().add("gameList_element");

            nameLabel.setText(snake.name);
            scoreLabel.setText(String.valueOf(snake.getScore()));

            box.getChildren().add(nameLabel);
            box.getChildren().add(scoreLabel);

            gameInfoList.getItems().add(box);
        });

        AtomicInteger scoreSum = new AtomicInteger();

        snakes.forEach(snake -> {
            scoreSum.addAndGet(snake.getScore());
        });

        if(this.numberOfRounds > scoreSum.get()){
            roundInfo.setTextFill(Color.WHITE);
            roundInfo.setText("Runda: " + String.valueOf(scoreSum.get()));
        }
        else {
            roundInfo.setTextFill(Color.RED);
            roundInfo.setText("Dogrywka!");
        }
    }

    public void draw() {
        final int SNAKE_SIZE = 5;
        try{
            snakes.forEach(snake -> {
                Coordinate head = snake.coords.getFirst();
                ctx.setFill(snake.getColor());
                if (head.visible) {
                    ctx.fillOval(head.x, head.y, SNAKE_SIZE, SNAKE_SIZE);
                }
            });
        } catch (NoSuchElementException e){
            timeline.stop();
        }
    }

    public abstract void start();
}

