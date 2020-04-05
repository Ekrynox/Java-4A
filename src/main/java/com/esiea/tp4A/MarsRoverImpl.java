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

    public MarsRoverImpl() {
         this.position = Position.of(0, 0, Direction.NORTH);
         this.laserRange = 0;
    }

    public MarsRoverImpl(Position position, int laserRange) {
        this.position = position;
        this.laserRange = laserRange;
    }

    @Override
    public MarsRover initialize(Position position) {
        return new MarsRoverImpl(position, this.laserRange);
    }

    @Override
    public MarsRover updateMap(PlanetMap map) {
        return null;
    }

    @Override
    public MarsRover configureLaserRange(int range) {
        return new MarsRoverImpl(this.position, range);
    }

    @Override
    public Position move(String command) {
        int new_x = this.position.getX();
        int new_y = this.position.getY();
        Direction new_D = this.position.getDirection();

        int i;

        for (char c : command.toCharArray()) {
            i = (c == 'f' ? 1 : (c == 'b' ? -1 : 0));

            if (new_D.equals(Direction.NORTH)) new_y += i;
            if (new_D.equals(Direction.SOUTH)) new_y -= i;
            if (new_D.equals(Direction.EAST)) new_x += i;
            if (new_D.equals(Direction.WEST)) new_x -= i;

            if (c == 'l') {
                if (new_D.equals(Direction.NORTH)) new_D = Direction.WEST;
                else if (new_D.equals(Direction.SOUTH)) new_D = Direction.EAST;
                else if (new_D.equals(Direction.EAST)) new_D = Direction.NORTH;
                else if (new_D.equals(Direction.WEST)) new_D = Direction.SOUTH;
            }
            if (c == 'r') {
                if (new_D.equals(Direction.NORTH)) new_D = Direction.EAST;
                else if (new_D.equals(Direction.SOUTH)) new_D = Direction.WEST;
                else if (new_D.equals(Direction.EAST)) new_D = Direction.SOUTH;
                else if (new_D.equals(Direction.WEST)) new_D = Direction.NORTH;
            }

            if (new_y == 51) new_y = -49;
            if (new_x == 51) new_x = -49;
            if (new_y == -50) new_y = 50;
            if (new_x == -50) new_y = 50;

        }

        return Position.of(new_x, new_y, new_D);
    }
}
