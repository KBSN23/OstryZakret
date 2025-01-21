package org.uoz.uwagaostryzakret.classes;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class OfflineGame extends Game {
    public OfflineGame() {
        super();
        Options.snakes.forEach(snake -> {
            snake.setScore(0);
        });

        this.numberOfRounds = Options.numberOfRounds;
        this.snakes = Options.snakes;
    }

    @Override
    public void handleKeyboardEvents() {
        History.getCurrent().getFXScene().setOnKeyPressed(event -> {
            snakes.forEach(snake -> {
                if (event.getCode() == snake.getLeftKey()) {
                    snake.isTurningLeft = true;
                }
                if (event.getCode() == snake.getRightKey()) {
                    snake.isTurningRight = true;
                }
            });
        });

        History.getCurrent().getFXScene().setOnKeyReleased(event -> {
            snakes.forEach(snake -> {
                if (event.getCode() == snake.getLeftKey()) {
                    snake.isTurningLeft = false;
                }
                if (event.getCode() == snake.getRightKey()) {
                    snake.isTurningRight = false;
                }
            });
        });
    }

    @Override
    public void start() {
        updateScoreboard();
        randomSnakePosition();
        draw();
        handleKeyboardEvents();
        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        pause.setOnFinished(event -> {
            timeline = new Timeline(new KeyFrame(Duration.millis(25), e -> {
                moveSnakes();
                draw();
                handleSnakeCollisions();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
        pause.play();
    }

    private void randomSnakePosition() {
        snakes.forEach(snake -> {
            int x = (int) (Math.random() * (750 - 50 + 1)) + 50;
            int y = (int) (Math.random() * (750 - 50 + 1)) + 50;


            double randomRadian = 2 * Math.PI * Math.random() - Math.PI;
            snake.direction = randomRadian;

            Coordinate coord = new Coordinate(x, y);
            snake.coords.addFirst(coord);
        });
    }

    private void moveSnakes() {
        snakes.forEach(snake -> {
            if (snake.isTurningRight) snake.direction += Math.toRadians(TURNING_SPEED);
            if (snake.isTurningLeft) snake.direction -= Math.toRadians(TURNING_SPEED);
        });

        snakes.forEach(snake -> {
            if (snake.isAlive) {
                int snakeSize = snake.coords.size();
                double x = snake.coords.getFirst().x + snake.speed * Math.cos(snake.direction);
                double y = snake.coords.getFirst().y + snake.speed * Math.sin(snake.direction);

                Coordinate coord = new Coordinate(x, y, snakeSize % 100 <= 90);
                snake.coords.addFirst(coord);
            }
        });
    }

    public void endRound(Optional<Snake> winner)
    {
        ctx.clearRect(0, 0, 800, 800);
        if(timeline != null)
        {
            timeline.stop();
        }

        snakes.forEach(snake -> {
            snake.isTurningLeft = false;
            snake.isTurningRight = false;
            snake.isAlive = true;
            snake.coords.clear();
        });

        winner.ifPresent(Snake::increaseScore);;

        if(Options.numberOfRounds <= currentRound)
        {
            Snake gameWinner = snakes.stream()
                    .max(Comparator.comparingInt(snake -> snake.getScore()))
                    .orElse(null);

            long numberOfWinners = snakes.stream().filter(snake ->
                    snake.getScore() == gameWinner.getScore()
            ).count();

            if(numberOfWinners == 1){
                try{
                    Options.resultSnakes = Options.snakes;
                    Scene newScene = new Scene(Objects.requireNonNull(History.getCurrent()).stage, "Wynik rozgrywki", "result-view.fxml");
                    History.add(newScene);
                    newScene.setScene();
                    newScene.show();
                    timeline.stop();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                return;
            }
        }


        currentRound++;
        updateScoreboard();
        start();
    }

    private void handleSnakeCollisions() {
        final double COLLISION_THRESHOLD = 2;

        // End game if only one snake is alive
        Optional<Snake> snakeAlive = snakes.stream().filter(snake -> snake.isAlive).findFirst();
        if (snakes.stream().filter(snake -> snake.isAlive).count() < 2) {
            endRound(snakeAlive);
            return;
        }

        snakes.forEach(snake -> {
            Coordinate head = snake.coords.getFirst();

            if (!head.visible) return;

            if (head.x < 0 || head.x > 800 || head.y < 0 || head.y > 800) {
                snake.kill();
            }

            for (int i = 5; i < snake.coords.size(); i++) {
                Coordinate coord = snake.coords.get(i);
                double distance = Math.sqrt(Math.pow(head.x - coord.x, 2) + Math.pow(head.y - coord.y, 2));
                if (distance < COLLISION_THRESHOLD && coord.visible) {
                    snake.kill();
                    break;
                }
            }

            snakes.forEach(otherSnake -> {
                if (snake != otherSnake) {
                    otherSnake.coords.forEach(coord -> {
                        double distance = Math.sqrt(Math.pow(head.x - coord.x, 2) + Math.pow(head.y - coord.y, 2));
                        if (distance < COLLISION_THRESHOLD && coord.visible) {
                            snake.kill();
                        }
                    });
                }
            });
        });
    }
}
