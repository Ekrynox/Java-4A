package com.esiea.tp4A;
import com.esiea.tp4A.domain.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlanetMapImplTest {

    @ParameterizedTest(name = "{0} {1}")
    @CsvSource({"100, 0", "100, 300", "300, 1000", "600, 10000", "100, 90000000"})
    void mapInitialization(int gridSize, int nbObstacle) throws NoSuchFieldException, IllegalAccessException {
        PlanetMapImpl map = new PlanetMapImpl(gridSize, nbObstacle);

        Field sizeField = map.getClass().getDeclaredField("size");
        sizeField.setAccessible(true);
        int size = sizeField.getInt(map);

        assertEquals(gridSize, size);

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

        assertEquals(Math.min(size * size, nbObstacle), c);
    }

    @ParameterizedTest(name = "{0} {1}")
    @CsvSource({"100, 0", "100, 300", "300, 1000", "600, 10000", "100, 90000000"})
    void obstaclePositions(int gridSize, int nbObstacle) throws NoSuchFieldException, IllegalAccessException {
        PlanetMapImpl map = new PlanetMapImpl(gridSize, nbObstacle);
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
