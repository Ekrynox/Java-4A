package com.esiea.tp4AGame.domain;

import com.esiea.tp4A.domain.Position;

import java.util.Set;

public interface Player {
    Position getRoverPosition();

    Set<Position> radar(); // Obstacle have a null Direction

    int getLaserRange();

    Position move(String command);

    boolean shoot();

    boolean isAlive();
}
