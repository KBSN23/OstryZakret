package org.uoz.uwagaostryzakret.classes;

import java.util.ArrayList;

public class History {
    final private ArrayList<Scene> sceneHistory = new ArrayList<>();

    public void add(Scene scene) {
        sceneHistory.add(scene);
    }

    public Scene getPrevious() {
        if (sceneHistory.size() > 1) {
            sceneHistory.remove(sceneHistory.size() - 1);
            return sceneHistory.get(sceneHistory.size() - 1);
        }
        return null;
    }

    public Scene getFirst() {
        sceneHistory.subList(1, sceneHistory.size()).clear();
        return sceneHistory.getFirst();
    }

    public Scene getCurrent() {
        return sceneHistory.get(sceneHistory.size() - 1);
    }
}
