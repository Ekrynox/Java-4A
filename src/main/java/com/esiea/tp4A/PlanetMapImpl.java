package com.esiea.tp4A;

import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;

import java.util.HashSet;
import java.util.Set;

public class PlanetMapImpl implements PlanetMap {
    private final Set<Position> obstacle;

    public PlanetMapImpl() {
        obstacle = new HashSet<>();
    }

    @Override
    public Set<Position> obstaclePositions() {
        return obstacle;
    }

    public boolean addObstacle(int x, int y) {
        for (Position pos : obstacle) {
            if (pos.getX() == x && pos.getY() == y) {
                return false;
            }
        }

        obstacle.add(Position.of(x, y, null));
        return true;
    }
}
