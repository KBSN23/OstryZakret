package org.uoz.uwagaostryzakret.classes;

import java.io.Serializable;

public class GameMessage implements Serializable {
    private String playerId;
    private String action;
    private String payload;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public GameMessage(String playerId, String action, String payload) {
        this.playerId = playerId;
        this.action = action;
        this.payload = payload;
    }


    public GameMessage() {}
}