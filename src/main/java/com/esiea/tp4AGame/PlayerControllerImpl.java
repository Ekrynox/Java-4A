package com.esiea.tp4AGame;

import com.esiea.tp4A.MarsRoverImpl;
import com.esiea.tp4A.domain.MarsRover;
import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Party;
import com.esiea.tp4AGame.domain.PlayerController;

import java.util.HashSet;
import java.util.Set;

public class PlayerControllerImpl implements PlayerController {
    private final Set<Position> map;
    private final int mapSize;

    private final MarsRover rover;
    private final Position position;
    private final int laserRange;

    public PlayerControllerImpl(Position position, int laserRange, Set<Position> map, int mapSize) {
        this.mapSize = mapSize;
        this.map = map;

        this.laserRange = Math.max(laserRange, 0);

        this.rover = new MarsRoverImpl(position, laserRange, map, mapSize);
        this.position = getSphericalPos(position);

        this.map.add(this.position);
    }

    @Override
    public PlayerController initialize(Party party, String playerName) {
        return null;
    }

    @Override
    public Position getRoverPosition() {
        return this.position;
    }

    @Override
    public Set<Position> radar() {
        Set<Position> tmp = new HashSet<>();
        for (Position obs : this.map) {
            if (Math.pow(getSphericalPos(obs).getX(), 2) + Math.pow(getSphericalPos(obs).getY(), 2) <= 900) {
                tmp.add(obs);
            }
        }
        return tmp;
    }

    @Override
    public int getLaserRange() {
        return this.laserRange;
    }

    @Override
    public Position move(String command) {
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
