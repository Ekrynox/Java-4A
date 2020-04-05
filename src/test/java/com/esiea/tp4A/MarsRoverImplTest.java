package com.esiea.tp4A;

import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.MarsRover;
import com.esiea.tp4A.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @ParameterizedTest(name = "{0} {1} {2} {3}")
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
}
