package com.esiea.tp4AGame.domain;

import com.esiea.tp4A.domain.Position;

import java.util.Set;

public interface Party {
    Position getRoverPosition();

    Set<Position> radar(int radarSize); // Obstacle have a null Direction
    Set<Position> radar(); // radarObstacle(30);

    int getLaserRange();

    Position goForward();
    Position goBackward();
    Position turnRight();
    Position turnLeft();

    boolean shoot();

    boolean isAlive();
}
