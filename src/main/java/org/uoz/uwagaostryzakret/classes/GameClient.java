package org.uoz.uwagaostryzakret.classes;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class GameClient {
    private static WebSocket webSocket;

    public static void connectToServer() {
        /**
         * TODO:
         * Offline game nie działa
         * CSS
         * Zmiana nazwy lobby nie działa
         * Usuwanie wiersza nagłówkowego w liście lobby online game
         * Start tylko dla hosta
         * Wybór koloru
         * W online game punkty powinny sie aktualizować
         * W online game przycisk wyjścia z gry dla każdego użytkownika, jak użytkownik naciska to wywala gre (każdego użytkownika z gry)
         * W online game nad scoreboardem runda sie inkrementuje
         * Czyszczenie logów
         */
        webSocket = HttpClient.newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create("ws://localhost:8080/game"), new WebSocket.Listener() {
                    @Override
                    public void onOpen(WebSocket webSocket) {
                        System.out.println("Connected to server!");
                        WebSocket.Listener.super.onOpen(webSocket);
                    }

                    private StringBuilder messageBuffer = new StringBuilder();

                    @Override
                    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                        messageBuffer.append(data);

                        if (last) {
                            String completeMessage = messageBuffer.toString();
                            messageBuffer.setLength(0);

                            try {
                                System.out.println("Message received: " + completeMessage);
                                GameMessage gameMessage = JsonUtils.deserialize(completeMessage, GameMessage.class);

                                switch (gameMessage.getAction()) {
                                    case "get_all_lobbies":
                                        Options.lobbies = JsonUtils.deserialize(
                                                gameMessage.getPayload(),
                                                new TypeReference<ArrayList<Lobby>>() {}
                                        );
                                        break;
                                    case "get_user_data":
                                        Options.userID = gameMessage.getPayload();
                                        break;
                                    case "game_started":
                                        ArrayList<Snake> snakes = JsonUtils.deserialize(
                                                gameMessage.getPayload(),
                                                new TypeReference<ArrayList<Snake>>() {}
                                        );
                                        Options.onlineSnakes = snakes;
                                        Options.gameStarted = true;
                                        break;
                                    case "send_winner":
//                                        Options.onlineWinner = gameMessage.getPayload();
                                        break;
                                    case "next_round":
                                        try{
                                            Canvas canvas = (Canvas) History.getCurrent().getFXScene().lookup("#gameCanvas");
                                            canvas.getGraphicsContext2D().clearRect(0, 0, 800, 800);
                                        }
                                        catch(Exception e){
                                            // TODO: exception
                                        }
                                        break;
                                    case "end_game":
                                        Options.resultSnakes = Options.onlineSnakes;
                                        Options.gameEnded = true;
                                        break;
                                    case "get_snakes":
                                        Options.onlineSnakes.clear();

                                        ArrayList<Snake> getSnakes = JsonUtils.deserialize(
                                                gameMessage.getPayload(),
                                                new TypeReference<ArrayList<Snake>>() {}
                                        );

                                        Options.onlineSnakes.addAll(getSnakes);
                                        break;
                                    default:
                                        System.err.println("Unknown action: " + gameMessage.getAction());
                                        break;
                                }
                            } catch (Exception e) {
                                System.err.println("Error deserializing JSON: " + e.getMessage());
                            }
                        }

                        return WebSocket.Listener.super.onText(webSocket, data, last);
                    }

                    @Override
                    public void onError(WebSocket webSocket, Throwable error) {
                        System.err.println("WebSocket error: " + error.getMessage());
                    }
                }).join();
    }

    public static void sendMessage(String message) {
        webSocket.sendText(message, true);
    }
}