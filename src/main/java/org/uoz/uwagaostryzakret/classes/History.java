package org.uoz.uwagaostryzakret.classes;

import java.util.ArrayList;

public class History {
    private static final ArrayList<Scene> sceneHistory = new ArrayList<>();

    private History() {
        throw new UnsupportedOperationException("Instantiation not permitted");
    }

    public static void add(Scene scene) {
        sceneHistory.add(scene);
    }

    public static Scene getPrevious() {
        if (sceneHistory.size() > 1) {
            sceneHistory.remove(sceneHistory.size() - 1);
            return sceneHistory.get(sceneHistory.size() - 1);
        }
        return null;
    }

    public static Scene getFirst() {
        if (!sceneHistory.isEmpty()) {
            sceneHistory.subList(1, sceneHistory.size()).clear();
            return sceneHistory.getFirst(); // Poprawione z getFirst() na get(0), bo ArrayList nie ma getFirst()
        }
        return null;
    }

    public static Scene getCurrent() {
        if (!sceneHistory.isEmpty()) {
            return sceneHistory.get(sceneHistory.size() - 1);
        }
        return null;
    }
}
