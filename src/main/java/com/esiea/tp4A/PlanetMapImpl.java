package com.esiea.tp4A;

import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;

import java.util.HashSet;
import java.util.Set;
import java.lang.Math;
import static java.lang.Math.round;

public class PlanetMapImpl implements PlanetMap {

    private final int [][] map;
    private final int size;

    private void clearMap() {
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.map[y][x] = 0;
            }
        }
    }

    private void randomObstacle() {
        int x, y;
        while (true) {
            x = (int) (Math.random() * this.size);
            y = (int) (Math.random() * this.size);

            if (this.map[y][x] == 0) {
                this.map[y][x] = 1;
                break;
            }
        }
    }

    public PlanetMapImpl(int size, int nbObstacle) {
        this.size = size;
        this.map = new int[this.size][this.size];

        clearMap();

        int obstaclesToSpawn = Math.min(nbObstacle, size * size);
        for (int n = 0; n < obstaclesToSpawn; n++) {
            randomObstacle();
        }
    }

    public PlanetMapImpl() {
        this(100, 0);
    }

    @Override
    public Set<Position> obstaclePositions() {
        Set<Position> positions = new HashSet<>();
        int x, y;
        int offset = 1 - this.size / 2;

        for (y = 0; y < this.size; y++) {
            for (x = 0; x <this.size; x++) {
                if (map[y][x] == 1) {
                    positions.add(Position.of(x + offset, y  + offset, null));
                }
            }
        }

        return positions;
    }
}
