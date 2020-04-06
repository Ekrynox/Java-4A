package com.esiea.tp4A;

import com.esiea.tp4A.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlanetMapImplTest {

    @Test
    void obstaclePositions() throws NoSuchFieldException, IllegalAccessException {
        PlanetMapImpl map = new PlanetMapImpl();
        Field field = map.getClass().getDeclaredField("obstacle");
        field.setAccessible(true);
        assertEquals(map.obstaclePositions(), field.get(map));
    }

    @Test
    void addObstacle() {
        PlanetMapImpl map = new PlanetMapImpl();
        Set<Position> obs = new HashSet<>();

        int c = 0;
        int x, y;
        for (int n = 0; n < 1000; n++) {
            x = (int) (Math.random() * 50);
            y = (int) (Math.random() * 50);

            obs.add(Position.of(x, y, null));

            if (map.addObstacle(x, y)) {
                c++;
            }
        }
        assertEquals(c, map.obstaclePositions().size());

        for (Position pos : map.obstaclePositions()) {
            obs.removeIf(tmpPos -> tmpPos.getY() == pos.getY() && tmpPos.getX() == pos.getX());
        }

        assertEquals(0, obs.size());
    }
}
