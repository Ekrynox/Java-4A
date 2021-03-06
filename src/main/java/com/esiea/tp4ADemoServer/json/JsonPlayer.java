package com.esiea.tp4ADemoServer.json;

import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.Position;
import com.google.gson.annotations.SerializedName;

public class JsonPlayer {
    public JsonPlayer(String name, boolean isAlive, Position position, int laser_range) {
        this.name = name;
        this.status = isAlive ? "alive" : "dead";

        String direction = "";
        Direction positionDirection = position.getDirection();
        if (positionDirection == Direction.NORTH) {
            direction = "NORTH";
        } else if (positionDirection == Direction.SOUTH) {
            direction = "SOUTH";
        } else if (positionDirection == Direction.EAST) {
            direction = "EAST";
        } else if (positionDirection == Direction.WEST) {
            direction = "WEST";
        }
        this.position = new JsonPosition(position.getX(), position.getY(), direction);

        this.laser_range = laser_range;
    }

    public final String name;
    public final String status;
    public final JsonPosition position;

    @SerializedName(value = "laser-range")
    public final int laser_range;
}
