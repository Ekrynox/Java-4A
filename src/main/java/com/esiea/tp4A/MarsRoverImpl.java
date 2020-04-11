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
    private final int mapSize;

    public MarsRoverImpl() {
        this(100);
    }

    public MarsRoverImpl(int mapSize) {
         this.position = Position.of(0, 0, Direction.NORTH);
         this.laserRange = 0;
         this.obstacle = new HashSet<>();
         this.mapSize = mapSize;
    }

    public MarsRoverImpl(Position position, int laserRange, Set<Position> obstacle, int mapSize) {
        this.laserRange = laserRange;
        this.obstacle = obstacle;
        this.mapSize = mapSize;
        this.position = getSphericalPos(position);
    }

    @Override
    public MarsRover initialize(Position position) {
        return new MarsRoverImpl(position, this.laserRange, this.obstacle, this.mapSize);
    }

    // Update the map given as parameter and returns a new rover based on it
    @Override
    public MarsRover updateMap(PlanetMap map) {
        return new MarsRoverImpl(this.position, this.laserRange, map.obstaclePositions(), this.mapSize);
    }

    // Returns a new rover object with the given range
    @Override
    public MarsRover configureLaserRange(int range) {
        return new MarsRoverImpl(this.position, Math.max(range, 0), this.obstacle, this.mapSize);
    }

    // Return a new position for this object
    @Override
    public Position move(String command) {
        Position pos = this.position;
        Position tmp;

        for (char c : command.toCharArray()) {
            switch (c) {
                case 'f':
                    tmp = getSphericalPos(goForward(pos));
                    if (checkIfCanMove(tmp)) {
                        pos = tmp;
                    }
                    break;
                case 'b':
                    tmp = getSphericalPos(goBackward(pos));
                    if (checkIfCanMove(tmp)) {
                        pos = tmp;
                    }
                    break;
                case 'l':
                    pos = turnLeft(pos);
                    break;
                case 'r':
                    pos = turnRight(pos);
                    break;
                case 's':
                    shoot(pos);
                    break;
                default:
                    break;
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
        int x = Math.floorMod(pos.getX() - 1 + (mapSize / 2), mapSize) + 1 - (mapSize / 2);
        int y = Math.floorMod(pos.getY() - 1 + (mapSize / 2), mapSize) + 1 - (mapSize / 2);

        return Position.of(x, y, pos.getDirection());
    }

    // Returns true if no obstacle are in the way
    private boolean checkIfCanMove(Position pos) {
        Position tmp = getSphericalPos(pos);
        for (Position obs : this.obstacle) {
            Position tmpObs = getSphericalPos(obs);
            if (tmpObs.getX() == tmp.getX() && tmpObs.getY() == tmp.getY()) {
                return false;
            }
        }

        return true;
    }

    // Shoot in the current direction and eventually destroy facing obstacles
    private void shoot(Position pos) {
        pos = getSphericalPos(pos);
        int x = pos.getX();
        int y = pos.getY();

        Direction dir = pos.getDirection();
        int x_offset = dir == Direction.EAST ? 1 : (dir == Direction.WEST ? -1 : 0);
        int y_offset = dir == Direction.NORTH ? 1 : (dir == Direction.SOUTH ? -1 : 0);

        for (int c = 0; c < this.laserRange; c++) {
            x += x_offset;
            y += y_offset;
            Position tmp = getSphericalPos(Position.of(x, y, dir));
            if (this.obstacle.removeIf(tmpObs -> tmp.getY() == getSphericalPos(tmpObs).getY() && tmp.getX() == getSphericalPos(tmpObs).getX())) {
                return;
            }
        }
    }
}
