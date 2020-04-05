package com.esiea.tp4A.domain;

public interface MarsRover {

    MarsRover initialize(Position position);

    MarsRover updateMap(PlanetMap map);

    MarsRover configureLaserRange(int range);

    Position move(String command);
}
