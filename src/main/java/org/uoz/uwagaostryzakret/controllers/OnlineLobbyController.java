package org.uoz.uwagaostryzakret.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.uoz.uwagaostryzakret.classes.*;

public class OnlineLobbyController extends Controller {
    @FXML
    ListView<HBox> lobbiesList;

    @FXML
    TextField userNameField;

    @FXML
    public void refresh(){
        GameMessage message = new GameMessage(String.valueOf(Options.userID), "get_all_lobbies", "" );
        String json = JsonUtils.serialize(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.4));

        GameClient.sendMessage(json);
        pause.setOnFinished(event -> {
            updateList();
            userNameField.setText(Options.userID);
        });
        pause.play();
    }

    @FXML
    public void setUserName(){
        String newName = userNameField.getText();
        GameMessage message = new GameMessage(String.valueOf(Options.userID), "set_user_name", newName);
        String json = JsonUtils.serialize(message);
        Options.userID = newName;

        GameClient.sendMessage(json);
    }

    @FXML
    public void createNewLobby() throws Exception{
        String roomID = String.valueOf(Options.userID);
        GameMessage message = new GameMessage(roomID, "create_lobby", "");
        String json = JsonUtils.serialize(message);

        GameClient.sendMessage(json);
        Options.currentLobby = roomID;

        this.openNewScene("Pokój", "online-lobby-create.fxml");
    }

    public void joinLobby(String hostName) throws Exception{
        GameMessage message = new GameMessage(String.valueOf(Options.userID), "join_lobby", hostName);
        String json = JsonUtils.serialize(message);
        GameClient.sendMessage(json);
        Options.currentLobby = hostName;

        this.openNewScene("Pokój", "online-lobby-create.fxml");
    }

    public void updateList() {
        // TODO: Powinno nie usuwać wiersza nagłówkowego
        lobbiesList.getItems().clear();
        Options.lobbies.forEach(lobby -> {
            HBox headerRow = new HBox();
            Label headerLabel1 = new Label();
            Label headerLabel2 = new Label();

            headerLabel1.setText("Nazwa pokoju");
            headerLabel2.setText("Nazwa hosta");

            headerRow.getChildren().add(headerLabel1);
            headerRow.getChildren().add(headerLabel2);

            HBox row = new HBox();
            Label roomName = new Label();
            Label hostName = new Label();
            Button joinButton = new Button();

            roomName.setText(lobby.lobbyName);
            hostName.setText(lobby.hostName);

            headerLabel1.getStyleClass().add("lobbyList_element");
            headerLabel2.getStyleClass().add("lobbyList_element");

            roomName.getStyleClass().add("lobbyList_element");
            hostName.getStyleClass().add("lobbyList_element");
            joinButton.setText("Dołącz");

            joinButton.setOnAction(event -> {
                try {
                    joinLobby(lobby.hostName);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });


            row.getChildren().add(roomName);
            row.getChildren().add(hostName);
            row.getChildren().add(joinButton);

            lobbiesList.getItems().add(headerRow);
            lobbiesList.getItems().add(row);
        });
    }

    @FXML
    public void handleBackButtonClick(){
        backToPreviousScene();
    }

    @Override
    public void init(){
        GameClient.connectToServer();
        refresh();
    }
}
