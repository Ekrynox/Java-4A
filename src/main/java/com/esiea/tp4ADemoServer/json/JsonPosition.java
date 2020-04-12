package com.esiea.tp4ADemoServer.json;

public class JsonPosition {
    public JsonPosition(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public final int x;
    public final int y;
    public final String direction;
}
