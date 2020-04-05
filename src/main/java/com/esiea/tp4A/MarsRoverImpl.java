package com.esiea.tp4A;

import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.MarsRover;
import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;

import java.util.HashSet;
import java.util.Set;

public class MarsRoverImpl implements MarsRover {
    private final Position position;
    private final int laserRange;
    private final Set<Position> obstacle;
    private final int mapSize = 100;

    public MarsRoverImpl() {
         this.position = Position.of(0, 0, Direction.NORTH);
         this.laserRange = 0;
         this.obstacle = new HashSet<>();
    }

    public MarsRoverImpl(Position position, int laserRange, Set<Position> obstacle) {
        this.position = position;
        this.laserRange = laserRange;
        this.obstacle = obstacle;
    }

    @Override
    public MarsRover initialize(Position position) {
        return new MarsRoverImpl(position, this.laserRange, this.obstacle);
    }

    @Override
    public MarsRover updateMap(PlanetMap map) {
        return new MarsRoverImpl(this.position, this.laserRange, map.obstaclePositions());
    }

    @Override
    public MarsRover configureLaserRange(int range) {
        return new MarsRoverImpl(this.position, range, this.obstacle);
    }

    @Override
    public Position move(String command) {
        Position pos = this.position;
        Position tmp;

        for (char c : command.toCharArray()) {
            switch (c) {
                case 'f':
                    tmp = goForward(pos);
                    break;
                case 'b':
                    tmp = goBackward(pos);
                    break;
                case 'l':
                    tmp = turnLeft(pos);
                    break;
                case 'r':
                    tmp = turnRight(pos);
                    break;
                default:
                    tmp = pos;
                    break;
            }
            tmp = getSphericalPos(tmp);

            if (checkIfCanMove(tmp)) {
                pos = tmp;
            }
        }

        return pos;
    }

    private Position goForward(Position pos) {
        Direction dir = pos.getDirection();
        if (dir == Direction.NORTH) return Position.of(pos.getX(), pos.getY() + 1, pos.getDirection());
        if (dir == Direction.SOUTH) return Position.of(pos.getX(), pos.getY() - 1, pos.getDirection());
        if (dir == Direction.EAST) return Position.of(pos.getX() + 1, pos.getY(), pos.getDirection());
        if (dir == Direction.WEST) return Position.of(pos.getX() - 1, pos.getY(), pos.getDirection());
        return pos;
    }

    private Position goBackward(Position pos) {
        Direction dir = pos.getDirection();
        if (dir == Direction.NORTH) return Position.of(pos.getX(), pos.getY() - 1, pos.getDirection());
        if (dir == Direction.SOUTH) return Position.of(pos.getX(), pos.getY() + 1, pos.getDirection());
        if (dir == Direction.EAST) return Position.of(pos.getX() - 1, pos.getY(), pos.getDirection());
        if (dir == Direction.WEST) return Position.of(pos.getX() + 1, pos.getY(), pos.getDirection());
        return pos;
    }

    private Position turnLeft(Position pos) {
        Direction dir = pos.getDirection();
        if (dir == Direction.NORTH) return Position.of(pos.getX(), pos.getY(), Direction.WEST);
        if (dir == Direction.SOUTH) return Position.of(pos.getX(), pos.getY(), Direction.EAST);
        if (dir == Direction.EAST) return Position.of(pos.getX(), pos.getY(), Direction.NORTH);
        if (dir == Direction.WEST) return Position.of(pos.getX(), pos.getY(), Direction.SOUTH);
        return pos;
    }

    private Position turnRight(Position pos) {
        Direction dir = pos.getDirection();
        if (dir == Direction.NORTH) return Position.of(pos.getX(), pos.getY(), Direction.EAST);
        if (dir == Direction.SOUTH) return Position.of(pos.getX(), pos.getY(), Direction.WEST);
        if (dir == Direction.EAST) return Position.of(pos.getX(), pos.getY(), Direction.SOUTH);
        if (dir == Direction.WEST) return Position.of(pos.getX(), pos.getY(), Direction.NORTH);
        return pos;
    }

    private Position getSphericalPos(Position pos) {
        int x = pos.getX();
        int y = pos.getY();

        x = Math.floorMod(x - 1 + (mapSize / 2), mapSize) + 1 - (mapSize / 2);
        y = Math.floorMod(y - 1 + (mapSize / 2), mapSize) + 1 - (mapSize / 2);

        return Position.of(x, y, pos.getDirection());
    }

    private boolean checkIfCanMove(Position pos) {
        for (Position obs : this.obstacle) {
            if (obs.getX() == pos.getX() && obs.getY() == pos.getY()) {
                return false;
            }
        }

        return true;
    }
}
