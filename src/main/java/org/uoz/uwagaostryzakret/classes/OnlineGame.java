package org.uoz.uwagaostryzakret.classes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Optional;

public class OnlineGame extends Game {
    private int totalScore = 0;

    public OnlineGame() {
        super();

        this.numberOfRounds = 6;
        this.snakes = Options.onlineSnakes;
    }

    @Override
    public void handleKeyboardEvents() {
        History.getCurrent().getFXScene().setOnKeyPressed(event -> {
            snakes.forEach(snake -> {
                if (event.getCode() == snake.getLeftKey()) {
                    GameMessage message = new GameMessage(Options.userID, "is_turning_left", "true");
                    GameClient.sendMessage(JsonUtils.serialize(message));
                }
                if (event.getCode() == snake.getRightKey()) {
                    GameMessage message = new GameMessage(Options.userID, "is_turning_right", "true");
                    GameClient.sendMessage(JsonUtils.serialize(message));
                }
            });
        });

        History.getCurrent().getFXScene().setOnKeyReleased(event -> {
            snakes.forEach(snake -> {
                if (event.getCode() == snake.getLeftKey()) {
                    GameMessage message = new GameMessage(Options.userID, "is_turning_left", "false");
                    GameClient.sendMessage(JsonUtils.serialize(message));
                }
                if (event.getCode() == snake.getRightKey()) {
                    GameMessage message = new GameMessage(Options.userID, "is_turning_right", "false");
                    GameClient.sendMessage(JsonUtils.serialize(message));
                }
            });
        });
    }

    public void checkGameEnded(){
        if(Options.gameEnded) {
            try{
                Options.gameEnded = false;
                Scene newScene = new Scene(Objects.requireNonNull(History.getCurrent()).stage, "Wynik rozgrywki", "result-view.fxml");
                History.add(newScene);
                newScene.setScene();
                newScene.show();
                timeline.stop();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        updateScoreboard();
        handleKeyboardEvents();
        timeline = new Timeline(new KeyFrame(Duration.millis(5), e -> {
            updateScoreboard();
            checkGameEnded();
            draw();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
