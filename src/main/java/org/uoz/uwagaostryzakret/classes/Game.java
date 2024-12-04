package org.uoz.uwagaostryzakret.classes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.canvas.Canvas;
import javafx.util.Duration;

public class Game {
    private final Runnable handleRoundEnd;
    private ArrayList<Snake> snakes;
    private GraphicsContext ctx;
    private Scene scene;
    private final double TURNING_SPEED = 3;
    private Timeline timeline;
    private Options options;
    private int currentRound = 1;
    private Consumer<Snake> handleWin;

    public void addSnake(Snake snake){
        this.snakes.add(snake);
    }

    public void randomSnakePosition(){
        snakes.forEach(snake -> {
            int x = (int)(Math.random() * (750 - 50 + 1)) + 50;
            int y = (int)(Math.random() * (750 - 50 + 1)) + 50;

            double randomRadian = 2 * Math.PI * Math.random() - Math.PI;
            snake.direction = randomRadian;

            Coordinate coord = new Coordinate(x, y);

            snake.coords.addFirst(coord);
        });

    }

    public Game(GraphicsContext canvas, Scene scene, Options globalOptions, Consumer<Snake> handleWin, Runnable handleRoundEnd){
        this.ctx = canvas;
        this.scene = scene;
        this.snakes = new ArrayList<>();
        this.options = globalOptions;
        this.handleWin = handleWin;
        this.handleRoundEnd = handleRoundEnd;
    }


    public void moveSnakes(){
        snakes.forEach(snake -> {
            if(snake.isTurningRight) snake.direction += Math.toRadians(TURNING_SPEED);
            if(snake.isTurningLeft) snake.direction -= Math.toRadians(TURNING_SPEED);
        });


        snakes.forEach(snake -> {
            if(snake.isAlive){
                int snakeSize = snake.coords.size();
                double x = snake.coords.getFirst().x + snake.speed * Math.cos(snake.direction);
                double y = snake.coords.getFirst().y + snake.speed * Math.sin(snake.direction);

                Coordinate coord = new Coordinate(x, y, snakeSize % 100 <= 90);

                snake.coords.addFirst(coord);
            }
        });
    }

    public void handleKeyboardEvents(){
        scene.setOnKeyPressed(event -> {
            snakes.forEach(snake -> {
                if(event.getCode() == snake.getLeftKey() && !snake.isTurningLeft){
                    snake.isTurningLeft = true;
                }

                if(event.getCode() == snake.getRightKey() && !snake.isTurningRight){
                    snake.isTurningRight = true;
                }
            });
        });

        scene.setOnKeyReleased(event -> {
            snakes.forEach(snake -> {
                if(event.getCode() == snake.getLeftKey()){
                    snake.isTurningLeft = false;
                }

                if(event.getCode() == snake.getRightKey()){
                    snake.isTurningRight = false;
                }
            });
        });
    }

    public void handleSnakeCollisions() {
        final double COLLISION_THRESHOLD = 2;


        // end game if one snake left
        Optional<Snake> snakeAlive = snakes.stream().filter(snake -> snake.isAlive).findFirst();
        if (snakeAlive.isPresent() && snakes.stream().filter(snake -> snake.isAlive).count() == 1) {
            endRound(snakeAlive.get());
        }

        snakes.forEach(snake -> {
            Coordinate head = snake.coords.getFirst();

            if(!head.visible) return;

            // Check if the snake is out of bounds
            snake.coords.forEach(coord -> {
                if (coord.x < 0 || coord.x > 800 || coord.y < 0 || coord.y > 800) {
                    snake.kill();
                }
            });

            // Check if the snake collides with itself
            for (int i = 5; i < snake.coords.size(); i++) {
                Coordinate coord = snake.coords.get(i);
                double distance = Math.sqrt(Math.pow(head.x - coord.x, 2) + Math.pow(head.y - coord.y, 2));
                if (distance < COLLISION_THRESHOLD) {
                    if(coord.visible) {
                        snake.kill();
                    }
                    break;
                }
            }

            // Check if the snake collides with other snakes
            snakes.forEach(otherSnake -> {
                if (snake != otherSnake) {
                    otherSnake.coords.forEach(coord -> {
                        double distance = Math.sqrt(Math.pow(head.x - coord.x, 2) + Math.pow(head.y - coord.y, 2));
                        if (distance < COLLISION_THRESHOLD) {
                            if(coord.visible) {
                                snake.kill();
                            }
                        }
                    });
                }
            });
        });
    }

    public void start(){
        randomSnakePosition();
        draw();
        handleKeyboardEvents();

        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        pause.setOnFinished(event -> {
            timeline = new Timeline(new KeyFrame(Duration.millis(25), timelineEvent -> {
                moveSnakes();
                handleSnakeCollisions();
                draw();
            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });

        pause.play();
    }


    public void draw(){
        final int SNAKE_SIZE = 5;

        snakes.forEach(snake -> {
            Coordinate currectSnakeCoords = snake.coords.getFirst();

            ctx.setFill(snake.getColor());
            if(currectSnakeCoords.visible){
                ctx.fillOval(currectSnakeCoords.x, currectSnakeCoords.y,  SNAKE_SIZE, SNAKE_SIZE);
            }
        });
    }


    public void endRound(Snake winner)
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
        winner.increaseScore();

        if(options.numberOfRounds <= currentRound)
        {
            Snake gameWinner = snakes.stream()
                    .max(Comparator.comparingInt(snake -> snake.score))
                    .orElse(null);

            long numberOfWinners = snakes.stream().filter(snake ->
                snake.score == gameWinner.score
            ).count();

            if(numberOfWinners == 1){
                handleWin.accept(gameWinner);
                return;
            }
        }


        currentRound++;
        handleRoundEnd.run();
        start();
    }
}

