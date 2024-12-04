package org.uoz.uwagaostryzakret.classes;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

public class Options implements Serializable {
    public int numberOfRounds = 1;
    public ArrayList<Snake> snakes = new ArrayList<Snake>();



    public Options(){
        snakes.add(new Snake(Color.RED, KeyCode.A, KeyCode.D, "Snake1"));
        snakes.add(new Snake(Color.BLUE, KeyCode.LEFT, KeyCode.RIGHT, "Snake2"));
        snakes.add(new Snake(Color.GREEN, KeyCode.H, KeyCode.J, "Snake3"));
        snakes.add(new Snake(Color.VIOLET, KeyCode.OPEN_BRACKET, KeyCode.CLOSE_BRACKET, "Snake4"));
    }

    public static void saveOptionsToFile(Options options, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(options);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania opcji: " + e.getMessage());
        }
    }

    public static Options loadOptionsFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Options) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd podczas wczytywania opcji: " + e.getMessage());
            return null;
        }
    }
}
