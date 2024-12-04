package org.uoz.uwagaostryzakret.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.uoz.uwagaostryzakret.classes.Controller;
import org.uoz.uwagaostryzakret.classes.Options;
import org.uoz.uwagaostryzakret.classes.Snake;

import java.security.Key;
import java.util.concurrent.atomic.AtomicInteger;

public class OptionsSceneController extends Controller {

    @FXML
    private TextField numberOfRounds;

    @FXML
    private ListView<HBox> snakesList;

    @FXML
    private Button saveButton;


    public void validate(){
        boolean hasDuplicateColors = globalOptions.snakes.stream()
                .map(Snake::getColor)
                .anyMatch(color ->
                        globalOptions.snakes.stream()
                                .filter(s -> s.getColor().equals(color))
                                .count() > 1
                );


        boolean hasDuplicateNames = globalOptions.snakes.stream()
                .map(Snake::getName)
                .anyMatch(name ->
                        globalOptions.snakes.stream()
                                .filter(s -> s.getName().equals(name))
                                .count() > 1
                );


        boolean hasDuplicateKeys = globalOptions.snakes.stream()
                .anyMatch(snake -> {
                    if (snake.getRightKey() == snake.getLeftKey()) {
                        return true;
                    }

                    return globalOptions.snakes.stream()
                            .anyMatch(otherSnake -> {
                                if (snake == otherSnake) return false;

                                KeyCode snakeRight = snake.getRightKey();
                                KeyCode snakeLeft = snake.getLeftKey();
                                KeyCode otherRight = otherSnake.getRightKey();
                                KeyCode otherLeft = otherSnake.getLeftKey();

                                return (snakeRight == otherRight || snakeRight == otherLeft ||
                                        snakeLeft == otherRight || snakeLeft == otherLeft);
                            });
                });

        if(hasDuplicateColors ||  hasDuplicateNames || hasDuplicateKeys ){
            saveButton.setDisable(true);
        }
        else{
            saveButton.setDisable(false);
        }
    }


    @Override
    public void init() {
        ObservableList<HBox> items = snakesList.getItems();
        HBox header = items.get(0);

        items.clear();
        items.add(header);

        numberOfRounds.focusedProperty().addListener((observable, oldValue, focus) -> {
            if (!focus) {
                handleNumberOfRoundsInput();
            }
        });

        numberOfRounds.setText(Integer.toString(globalOptions.numberOfRounds));


        globalOptions.snakes.forEach(snake -> {
            HBox box = new HBox();

            ColorPicker colorPicker = new ColorPicker();
            TextField namePicker = new TextField();
            TextField leftKeyPicker = new TextField();
            TextField rightKeyPicker = new TextField();
            Button removeButton = new Button();

            removeButton.setText("Usuń");
            namePicker.setText(snake.name);
            leftKeyPicker.setText(snake.leftKey);
            rightKeyPicker.setText(snake.rightKey);
            colorPicker.setValue(snake.getColor());

            removeButton.setOnAction(event -> {
                globalOptions.snakes.remove(snake);
                init();
                validate();
            });

            if(globalOptions.snakes.size() == 2){
                removeButton.setDisable(true);
            }

            leftKeyPicker.setOnKeyPressed(event -> {
                leftKeyPicker.setText(event.getCode().toString());
                snake.setLeftKey( event.getCode());
                validate();
            });

            rightKeyPicker.setOnKeyPressed(event -> {
                rightKeyPicker.setText(event.getCode().toString());
                snake.setRightKey(event.getCode());
                validate();
            });

            rightKeyPicker.setTextFormatter(new TextFormatter<String>(change -> {
                change.setText(change.getText().toUpperCase());
                return change;
            }));

            leftKeyPicker.setTextFormatter(new TextFormatter<String>(change -> {
                change.setText(change.getText().toUpperCase());
                return change;
            }));

            namePicker.setOnKeyReleased(event -> {
                snake.name = namePicker.getText();
                validate();
            });

            colorPicker.setOnAction(event -> {
                snake.setColor(colorPicker.getValue());
                validate();
            });

            box.setAlignment(Pos.CENTER);
            box.setSpacing(20);

            colorPicker.getStyleClass().add("snakesList_element");
            namePicker.getStyleClass().add("snakesList_element");
            leftKeyPicker.getStyleClass().add("snakesList_element");
            rightKeyPicker.getStyleClass().add("snakesList_element");


            rightKeyPicker.setEditable(false);
            leftKeyPicker.setEditable(false);

            box.getChildren().add(namePicker);
            box.getChildren().add(colorPicker);
            box.getChildren().add(leftKeyPicker);
            box.getChildren().add(rightKeyPicker);
            box.getChildren().add(removeButton);
            snakesList.getItems().add(box);
        });


    }

    @FXML
    public void handleNumberOfRoundsInput(){
        final int MIN_VALUE = 1;
        final int MAX_VALUE = 99;
        int value = MIN_VALUE;

        try {
            value = Integer.parseInt(numberOfRounds.getText());
            if (value < MIN_VALUE) {
                numberOfRounds.setText(String.valueOf(MIN_VALUE));
                value = MIN_VALUE;
            } else if (value > MAX_VALUE) {
                numberOfRounds.setText(String.valueOf(MAX_VALUE));
                value = MAX_VALUE;
            }
        } catch (NumberFormatException e) {
            numberOfRounds.setText(String.valueOf(MIN_VALUE));

        }
        globalOptions.numberOfRounds = value;
    }

    @FXML
    public void handleAddSnake(){
        Snake newSnake = new Snake(Color.RED, KeyCode.A, KeyCode.D, "Snake" + globalOptions.snakes.size() + 1);
        this.globalOptions.snakes.add(newSnake);
        this.init();
        this.validate();
    }

    @FXML
    public void handleRemoveSnake(){

    }

    @FXML
    public void handleBackButtonClick(){
        Options.saveOptionsToFile(globalOptions, "options.ser");
        backToPreviousScene();
    }

}
