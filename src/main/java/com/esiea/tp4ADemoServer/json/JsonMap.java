package com.esiea.tp4ADemoServer.json;

public class JsonMap {
    public JsonMap(JsonObstacle[] obstacles, JsonOpponent[] players) {
        this.obstacles = obstacles;
        this.players = players;
    }

    public final JsonObstacle[] obstacles;
    public final JsonOpponent[] players;
}
