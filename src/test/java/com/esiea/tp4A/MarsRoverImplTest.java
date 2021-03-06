package com.esiea.tp4A;

import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.MarsRover;
import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MarsRoverImplTest {

    @ParameterizedTest(name = "{0} ({1}, {2})")
    @DisplayName("MarsRover Initialize")
    @CsvSource({"0, 0, NORTH", "2, 29, SOUTH", "4, 6, EAST", "7, 16, WEST", "-9, 19, NORTH"})
    void initialize(int x, int y, Direction dir) throws NoSuchFieldException, IllegalAccessException {
        MarsRover marsRover = new MarsRoverImpl();
        MarsRover newMarsRover = marsRover.initialize(Position.of(x, y, dir));

        final Field field = marsRover.getClass().getDeclaredField("position");
        field.setAccessible(true);
        Position position = (Position)field.get(newMarsRover);

        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
        assertEquals(dir, position.getDirection());
    }

    @ParameterizedTest(name = "{0} ({1}, {2}) ({3}, {4}) {5}")
    @DisplayName("MarsRover Move")
    @CsvSource({"'', 0, 0, 0, 0, NORTH",
        "'b', 0, 0, 0, -1, NORTH", "'rb', 0, 0, -1, 0, EAST", "'rrb', 0, 0, 0, 1, SOUTH", "'rrrb', 0, 0, 1, 0, WEST", "'rrrrb', 0, 0, 0, -1, NORTH", "'f', 0, 0, 0, 1, NORTH", "'rf', 0, 0, 1, 0, EAST", "'rrf', 0, 0, 0, -1, SOUTH", "'rrrf', 0, 0, -1, 0, WEST", "'rrrrf', 0, 0, 0, 1, NORTH",
        "'llllb', 0, 0, 0, -1, NORTH", "'lllb', 0, 0, -1, 0, EAST", "'llb', 0, 0, 0, 1, SOUTH", "'lb', 0, 0, 1, 0, WEST", "'b', 0, 0, 0, -1, NORTH", "'llllf', 0, 0, 0, 1, NORTH", "'lllf', 0, 0, 1, 0, EAST", "'llf', 0, 0, 0, -1, SOUTH", "'lf', 0, 0, -1, 0, WEST", "'f', 0, 0, 0, 1, NORTH",
        "'abcdefghijklmnopqrstuvwxyz', -10, -10, -10, -10, NORTH",
        "'fflb', -10, 0, -9, 2, WEST", "'f', 5, 0, 5, 1, NORTH", "'rr', 0, 0, 0, 0, SOUTH", "'lfb', 0, 0, 0, 0, WEST", "'f', 0, 50, 0, -49, NORTH", "'b', 0, -49, 0, 50, NORTH", "'rf', 50, 0, -49, 0, EAST"})
    void move(String command, int posX, int posY, int x, int y, Direction dir) {
        MarsRover marsRover = new MarsRoverImpl();
        MarsRover newMarsRover = marsRover.initialize(Position.of(posX, posY, Direction.NORTH));

        Position position = newMarsRover.move(command);
        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
        assertEquals(dir, position.getDirection());
    }

    @Test
    @DisplayName("MarsRover Move Null")
    void moveNull() {
        int x = (int) (Math.random() * 100) - 49;
        int y = (int) (Math.random() * 100) - 49;

        MarsRover marsRover = new MarsRoverImpl();
        MarsRover newMarsRover = marsRover.initialize(Position.of(x, y, null));

        Position position = newMarsRover.move("lrbfs");
        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
        assertNull(position.getDirection());
    }

    @ParameterizedTest(name = "{0} ({1}, {2}) {3}")
    @DisplayName("MarsRover Move Obstacle")
    @CsvSource({"'f', 0, 0, NORTH", "'fflb', 1, 0, WEST", "'rflflf', 1, 1, WEST", "'rflflfrflfrb', 0, 2, NORTH",
        "'sf', 0, 1, NORTH", "'fsflb', 1, 1, WEST", "'rflflsf', 0, 1, WEST", "'bbsfff', 0, 0, NORTH", "'lsrf', 0, 0, NORTH", "'llsb', 0, 0, SOUTH", "'rslf', 0, 0, NORTH",
        "'lsffffsffff', -5, 0, WEST", "'lsffffsfffsf', -6, 0, WEST"})
    void moveObstacle(String command, int x, int y, Direction dir) {
        MarsRover marsRover = new MarsRoverImpl();
        marsRover = marsRover.initialize(Position.of(0, 0, Direction.NORTH));
        marsRover = marsRover.configureLaserRange(2);

        PlanetMapImpl map = new PlanetMapImpl();
        map.addObstacle(0, 1);
        map.addObstacle(-5, 0);
        map.addObstacle(-6, 0);
        marsRover = marsRover.updateMap(map);

        Position position = marsRover.move(command);
        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
        assertEquals(dir, position.getDirection());
    }

    @ParameterizedTest(name = "{0}, {1}")
    @DisplayName("MarsRover Configure Laser Range")
    @CsvSource({"0, 0", "1, 1", "30, 30", "100, 100", "-1, 0"})
    void configureLaserRange(int range, int erange) throws NoSuchFieldException, IllegalAccessException {
        MarsRover marsRover = new MarsRoverImpl();
        MarsRover newMarsRover = marsRover.configureLaserRange(range);

        final Field field = marsRover.getClass().getDeclaredField("laserRange");
        field.setAccessible(true);

        assertEquals(erange, field.get(newMarsRover));
    }

    @Test
    @DisplayName("MarsRover Update Map")
    void updateMap() throws NoSuchFieldException, IllegalAccessException {
        MarsRover marsRover = new MarsRoverImpl();
        PlanetMap map = new PlanetMapImpl(); //A Map with 15% obstacle randomly placed
        MarsRover newMarsRover = marsRover.updateMap(map);
        Set<Position> obstacle = map.obstaclePositions();

        final Field obstacleField = marsRover.getClass().getDeclaredField("obstacle");
        obstacleField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<Position> tmp = (Set<Position>) obstacleField.get(newMarsRover);

        assertEquals(obstacle.size(), tmp.size());
        for (Position obs : obstacle) {
            tmp.removeIf(pos -> obs.getX() == pos.getX() && obs.getY() == pos.getY() && obs.getDirection() == pos.getDirection());
        }
        assertEquals(0, tmp.size());
    }
}
