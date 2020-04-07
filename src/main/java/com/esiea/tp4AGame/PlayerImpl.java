package com.esiea.tp4AGame;

import com.esiea.tp4A.MarsRoverImpl;
import com.esiea.tp4A.domain.MarsRover;
import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Player;

import java.util.Set;

public class PlayerImpl implements Player {
    private final int mapSize;
    private final Set<Position> map;
    private final MarsRover rover;
    private final int laserRange;
    private final Position position;

    public PlayerImpl(Position position, int laserRange, Set<Position> map, int mapSize) {
        this.mapSize = mapSize;
        this.map = map;

        this.laserRange = Math.max(laserRange, 0);

        this.rover = new MarsRoverImpl(position, laserRange, map, mapSize);
        this.position = getSphericalPos(position);

        for (Position pos : this.map) {
            Position tmp = getSphericalPos(pos);
            if (tmp.getX() == this.position.getX() && tmp.getY() == this.position.getY() && tmp.getDirection() == this.position.getDirection()) {
                return;
            }
        }

        this.map.add(position);
    }

    @Override
    public Position getRoverPosition() {
        return this.position;
    }

    @Override
    public Set<Position> radar() {
        return null;
    }

    @Override
    public int getLaserRange() {
        return this.laserRange;
    }

    @Override
    public Player move(String command) {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    private Position getSphericalPos(Position pos) {
        int x = pos.getX();
        int y = pos.getY();

        x = Math.floorMod(x - 1 + (mapSize / 2), mapSize) + 1 - (mapSize / 2);
        y = Math.floorMod(y - 1 + (mapSize / 2), mapSize) + 1 - (mapSize / 2);

        return Position.of(x, y, pos.getDirection());
    }
}
