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
        return null;
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

            pos = getSphericalPos(tmp);
        }

        return pos;
    }

    private Position goForward(Position pos) {
        switch (pos.getDirection()) {
            case NORTH:
                return Position.of(pos.getX(), pos.getY() + 1, pos.getDirection());
            case SOUTH:
                return Position.of(pos.getX(), pos.getY() - 1, pos.getDirection());
            case EAST:
                return Position.of(pos.getX() + 1, pos.getY(), pos.getDirection());
            case WEST:
                return Position.of(pos.getX() - 1, pos.getY(), pos.getDirection());
        }
        return pos;
    }

    private Position goBackward(Position pos) {
        switch (pos.getDirection()) {
            case NORTH:
                return Position.of(pos.getX(), pos.getY() - 1, pos.getDirection());
            case SOUTH:
                return Position.of(pos.getX(), pos.getY() + 1, pos.getDirection());
            case EAST:
                return Position.of(pos.getX() - 1, pos.getY(), pos.getDirection());
            case WEST:
                return Position.of(pos.getX() + 1, pos.getY(), pos.getDirection());
        }
        return pos;
    }

    private Position turnLeft(Position pos) {
        switch (pos.getDirection()) {
            case NORTH:
                return Position.of(pos.getX(), pos.getY(), Direction.WEST);
            case SOUTH:
                return Position.of(pos.getX(), pos.getY(), Direction.EAST);
            case EAST:
                return Position.of(pos.getX(), pos.getY(), Direction.NORTH);
            case WEST:
                return Position.of(pos.getX(), pos.getY(), Direction.SOUTH);
        }
        return pos;
    }

    private Position turnRight(Position pos) {
        switch (pos.getDirection()) {
            case NORTH:
                return Position.of(pos.getX(), pos.getY(), Direction.EAST);
            case SOUTH:
                return Position.of(pos.getX(), pos.getY(), Direction.WEST);
            case EAST:
                return Position.of(pos.getX(), pos.getY(), Direction.SOUTH);
            case WEST:
                return Position.of(pos.getX(), pos.getY(), Direction.NORTH);
        }
        return pos;
    }

    private Position getSphericalPos(Position pos) {
        int x = pos.getX();
        int y = pos.getY();

        if (y == 51) y = -49;
        if (x == 51) x = -49;
        if (y == -50) y = 50;
        if (x == -50) x = 50;

        return Position.of(x, y, pos.getDirection());
    }
}
