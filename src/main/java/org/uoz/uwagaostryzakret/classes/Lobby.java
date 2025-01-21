package org.uoz.uwagaostryzakret.classes;

import java.util.ArrayList;

public class Lobby {
    public String lobbyName = "";
    public String hostName;
    public ArrayList<String> usersList = new ArrayList<>();

    public ArrayList<String> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<String> usersList) {
        this.usersList = usersList;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public Lobby(){}
    public Lobby(String hostName){
        this.hostName = hostName;
    }
}
