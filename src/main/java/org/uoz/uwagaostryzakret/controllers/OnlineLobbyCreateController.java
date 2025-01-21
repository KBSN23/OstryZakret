package org.uoz.uwagaostryzakret.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.uoz.uwagaostryzakret.classes.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class OnlineLobbyCreateController extends Controller {
    private Lobby currentLobby;
    private Timeline timeline;
    private int previousUsers = -99;

    @FXML
    ColorPicker colorPicker;

    @FXML
    Button startGameButton;

    @FXML
    ListView<HBox> usersList;
    
    @FXML
    TextField lobbyNameField;

    @FXML
    public void handleBackButtonClick(){
        GameMessage message = new GameMessage(String.valueOf(Options.userID), "leave_lobby", currentLobby.hostName);
        String json = JsonUtils.serialize(message);
        GameClient.sendMessage(json);
        Options.lobbies.removeIf(lobby -> lobby.hostName.equals(Options.userID));
    }

    @FXML
    public void handleChangeLobbyName(){
        String newLobbyName = lobbyNameField.getText();

        GameMessage message = new GameMessage(String.valueOf(Options.userID), "set_lobby_name", newLobbyName);
        String json = JsonUtils.serialize(message);
        GameClient.sendMessage(json);
    }

    @FXML
    public void startGame() {
        GameMessage message = new GameMessage(String.valueOf(Options.userID), "start_game", currentLobby.hostName);
        String json = JsonUtils.serialize(message);
        GameClient.sendMessage(json);

    }

    public void handleColorChange(){
        GameMessage message = new GameMessage(Options.userID, "set_user_color", colorPicker.getValue().toString());
        GameClient.sendMessage(JsonUtils.serialize(message));
    }

    public void updateUsersList(){
        if(currentLobby.usersList.size() == previousUsers){
            return;
        }

        startGameButton.setDisable(!Objects.equals(currentLobby.hostName, Options.userID));

        previousUsers = currentLobby.usersList.size();
        ArrayList<String> allUsers = new ArrayList<>();

        ArrayList<String> users = currentLobby.usersList;
        allUsers.add(currentLobby.hostName + " 👑");
        allUsers.addAll(users);

        usersList.getItems().clear();
        allUsers.forEach(user -> {
            HBox row = new HBox();

            Label heading = new Label();
            heading.getStyleClass().add("usersList_element");
            heading.setText("Nazwa użytkownika");

            Label userName = new Label();
            userName.getStyleClass().add("usersList_element");
            userName.setText(user);



            row.getChildren().add(userName);

            usersList.getItems().add(row);
        });

    }

    @Override
    public void init() {
         colorPicker.setValue(Color.BLUE);
         timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    updateUsersList();

                    Options.lobbies.forEach(lobby -> {
                        if(Objects.equals(lobby.hostName, Options.currentLobby)){
                            currentLobby = lobby;
                        }
                    });
                    AtomicBoolean lobbyStillExists = new AtomicBoolean(false);
                    Options.lobbies.forEach(lobby -> {
                        if(Objects.equals(lobby.hostName, Options.currentLobby)){
                            currentLobby = lobby;
                            lobbyStillExists.set(true);
                        }
                    });
                    if (!lobbyStillExists.get() || (!currentLobby.usersList.contains(Options.userID) && !currentLobby.hostName.equals(Options.userID)) ){
                        timeline.stop();
                        backToPreviousScene();
                    }

                    if(Options.gameStarted){
                        try {
                            timeline.stop();
                            openNewScene("Online Game", "online-game.fxml");
                        }
                        catch (Exception e){
                            // TODO: Error
                        }
                    }
                })
        );


        timeline.setCycleCount(Timeline.INDEFINITE);


        timeline.play();
        Options.lobbies.forEach(lobby -> {
            if(Objects.equals(lobby.hostName, Options.currentLobby)){
                currentLobby = lobby;
            }
        });

        lobbyNameField.setText(currentLobby.lobbyName);
        updateUsersList();
    }
}
