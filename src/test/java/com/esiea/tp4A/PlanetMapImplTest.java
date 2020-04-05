package com.esiea.tp4A;
import com.esiea.tp4A.domain.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.*;

class PlanetMapImplTest {

    @Test
    void mapInitialization() throws NoSuchFieldException, IllegalAccessException {
        PlanetMapImpl map = new PlanetMapImpl();

        Field sizeField = map.getClass().getDeclaredField("size");
        sizeField.setAccessible(true);
        int size = sizeField.getInt(map);

        assertFalse(size <= 0);

        Field gridField = map.getClass().getDeclaredField("map");
        gridField.setAccessible(true);
        int [][] grid = (int[][]) gridField.get(map);

        assertEquals(size, grid.length);
        assertEquals(size, grid[0].length);

        int c = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (grid[y][x] == 1) {
                    c++;
                }
            }
        }

        assertFalse(c + 1 < round(0.15 * size * size));
    }

    @Test
    void obstaclePositions() throws NoSuchFieldException, IllegalAccessException {
        PlanetMapImpl map = new PlanetMapImpl();
        Set<Position> obstacle = map.obstaclePositions();
        Field gridField = map.getClass().getDeclaredField("map");
        gridField.setAccessible(true);
        int [][] grid = (int[][]) gridField.get(map);

        int offset = 1 - grid.length / 2;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 1) {
                    assertNotEquals(0, obstacle.size());
                    Iterator<Position> iter = obstacle.iterator();
                    while (iter.hasNext()) {
                        Position pos = iter.next();
                        if (pos.getX() == x + offset && pos.getY() == y + offset) {
                            iter.remove();
                        }
                    }
                }
            }
        }

        assertEquals(0, obstacle.size());
    }
}
