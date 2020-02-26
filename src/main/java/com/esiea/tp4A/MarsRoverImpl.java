package com.esiea.tp4A;

import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.MarsRover;
import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;

public class MarsRoverImpl implements MarsRover {
    @Override
    public MarsRover initialize(Position position) {
        this.position = position;
        return this;
    }

    @Override
    public MarsRover updateMap(PlanetMap map) {
        return null;
    }

    @Override
    public MarsRover configureLaserRange(int range) {
        return null;
    }

    @Override
    public Position move(String command) {
        return null;
    }



    private Position position = Position.of(0, 0, Direction.NORTH);
    public Position getPosition() {
        return position;
    }
}
