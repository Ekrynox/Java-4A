package com.esiea.tp4A;

import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.MarsRover;
import com.esiea.tp4A.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MarsRoverImplTest {

    @ParameterizedTest(name = "{0} {1} {2}")
    @DisplayName("MarsRover Initialize")
    @CsvSource({"0, 0, NORTH", "2, 29, SOUTH", "4, 6, EAST", "7, 16, WEST", "-9, 19, NORTH"})
    void initialize(int x, int y, Direction dir) throws NoSuchFieldException, IllegalAccessException {
        MarsRoverImpl marsRover = new MarsRoverImpl();
        MarsRover newMarsRover = marsRover.initialize(Position.of(x, y, dir));

        final Field field = marsRover.getClass().getDeclaredField("position");
        field.setAccessible(true);
        Position position = (Position)field.get(newMarsRover);

        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
        assertEquals(dir, position.getDirection());
    }

    @ParameterizedTest(name = "{0} {1} {2}")
    @DisplayName("MarsRover Move")
    @CsvSource({
        "'b', 0, 0, 0, -1, NORTH", "'rb', 0, 0, -1, 0, EAST", "'rrb', 0, 0, 0, 1, SOUTH", "'rrrb', 0, 0, 1, 0, WEST", "'rrrrb', 0, 0, 0, -1, NORTH", "'f', 0, 0, 0, 1, NORTH", "'rf', 0, 0, 1, 0, EAST", "'rrf', 0, 0, 0, -1, SOUTH", "'rrrf', 0, 0, -1, 0, WEST", "'rrrrf', 0, 0, 0, 1, NORTH",
        "'llllb', 0, 0, 0, -1, NORTH", "'lllb', 0, 0, -1, 0, EAST", "'llb', 0, 0, 0, 1, SOUTH", "'lb', 0, 0, 1, 0, WEST", "'b', 0, 0, 0, -1, NORTH", "'llllf', 0, 0, 0, 1, NORTH", "'lllf', 0, 0, 1, 0, EAST", "'llf', 0, 0, 0, -1, SOUTH", "'lf', 0, 0, -1, 0, WEST", "'f', 0, 0, 0, 1, NORTH",
        "'abcdefghijklmnopqrstuvwxyz', -10, -10, -10, -10, NORTH",
        "'fflb', -10, 0, -9, 2, WEST", "'f', 5, 0, 5, 1, NORTH", "'rr', 0, 0, 0, 0, SOUTH", "'lfb', 0, 0, 0, 0, WEST", "'f', 0, 50, 0, -49, NORTH", "'b', 0, -49, 0, 50, NORTH", "'rf', 50, 0, -49, 0, EAST"})
    void move(String command, int posX, int posY, int x, int y, Direction dir) {
        MarsRoverImpl marsRover = new MarsRoverImpl();
        MarsRover newMarsRover = marsRover.initialize(Position.of(posX, posY, Direction.NORTH));

        Position position = newMarsRover.move(command);
        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
        assertEquals(dir, position.getDirection());
    }

    @Test
    @DisplayName("MarsRover Move with other case")
    void moveComplex() {
        int x = (int) (Math.random() * 100) - 49;
        int y = (int) (Math.random() * 100) - 49;

        MarsRoverImpl marsRover = new MarsRoverImpl();
        MarsRover newMarsRover = marsRover.initialize(Position.of(x, y, null));

        Position position = newMarsRover.move("lrbfs");
        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
        assertNull(position.getDirection());
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("MarsRover Configure Laser Range")
    @CsvSource({"0", "1", "30", "100", "-1"})
    void configureLaserRange(int range) throws NoSuchFieldException, IllegalAccessException {
        MarsRoverImpl marsRover = new MarsRoverImpl();
        MarsRover newMarsRover = marsRover.configureLaserRange(range);

        final Field field = marsRover.getClass().getDeclaredField("laserRange");
        field.setAccessible(true);

        assertEquals(range, field.get(newMarsRover));
    }

    @Test
    @DisplayName("MarsRover Update Map")
    void updateMap() throws NoSuchFieldException, IllegalAccessException {
        MarsRoverImpl marsRover = new MarsRoverImpl();
        PlanetMapImpl map = new PlanetMapImpl(); //A Map with 15% obstacle randomly placed
        MarsRover newMarsRover = marsRover.updateMap(map);
        Set<Position> obstacle = map.obstaclePositions();

        final Field obstacleField = marsRover.getClass().getDeclaredField("obstacle");
        obstacleField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<Position> tmp = (Set<Position>) obstacleField.get(newMarsRover);

        assertEquals(obstacle.size(), tmp.size());
        for (Position obs : obstacle) {
            Iterator<Position> iter = tmp.iterator();
            while (iter.hasNext()) {
                Position pos = iter.next();
                if (obs.getX() == pos.getX() && obs.getY() == pos.getY() && obs.getDirection() == pos.getDirection()) {
                    iter.remove();
                    break;
                }
            }
        }
        assertEquals(0, tmp.size());
    }
}
