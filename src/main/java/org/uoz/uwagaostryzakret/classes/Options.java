package org.uoz.uwagaostryzakret.classes;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

public class Options implements Serializable {
    public static String userID = "";
    public static int numberOfRounds = 1;
    public static ArrayList<Snake> snakes = new ArrayList<>();
    public static ArrayList<Snake> resultSnakes = new ArrayList<>();
    public static ArrayList<Lobby> lobbies = new ArrayList<>();
    public static String currentLobby = "";
    public static boolean gameStarted = false;
    public static ArrayList<Snake> onlineSnakes = new ArrayList<>();
    public static boolean gameEnded = false;

    private Options() {
        throw new UnsupportedOperationException("Instantiation not permitted");
    }

    static {
        snakes.add(new Snake(Color.RED, KeyCode.A, KeyCode.D, "Snake1"));
        snakes.add(new Snake(Color.BLUE, KeyCode.LEFT, KeyCode.RIGHT, "Snake2"));
        snakes.add(new Snake(Color.GREEN, KeyCode.H, KeyCode.J, "Snake3"));
        snakes.add(new Snake(Color.VIOLET, KeyCode.OPEN_BRACKET, KeyCode.CLOSE_BRACKET, "Snake4"));
    }

    public static void saveOptionsToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            OptionsSerializable serializableOptions = new OptionsSerializable(userID, numberOfRounds, snakes);
            oos.writeObject(serializableOptions);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania opcji: " + e.getMessage());
        }
    }

    public static void loadOptionsFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            OptionsSerializable serializableOptions = (OptionsSerializable) ois.readObject();
            userID = serializableOptions.userID;
            numberOfRounds = serializableOptions.numberOfRounds;
            snakes = serializableOptions.snakes;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd podczas wczytywania opcji: " + e.getMessage());
        }
    }

    private static class OptionsSerializable implements Serializable {
        private final String userID;
        private final int numberOfRounds;
        private final ArrayList<Snake> snakes;

        public OptionsSerializable(String userID, int numberOfRounds, ArrayList<Snake> snakes) {
            this.userID = userID;
            this.numberOfRounds = numberOfRounds;
            this.snakes = snakes;
        }
    }
}
