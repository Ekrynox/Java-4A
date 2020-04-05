package com.esiea.tp4A;

import com.esiea.tp4A.domain.Direction;
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
        marsRover.initialize(Position.of(x, y, dir));

        final Field field = marsRover.getClass().getDeclaredField("position");
        field.setAccessible(true);
        Position position = (Position)field.get(marsRover);

        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
        assertEquals(dir, position.getDirection());
    }

    @ParameterizedTest(name = "{0} {1} {2} {3}")
    @DisplayName("MarsRover Move")
    @CsvSource({"'fflb', -10, 0, -9, 2, WEST", "'f', 5, 0, 5, 1, NORTH", "'rr', 0, 0, 0, 0, SOUTH", "'lfb', 0, 0, 0, 0, WEST", "'f', 0, 50, 0, -49, NORTH", "'b', 0, -49, 0, 50, NORTH", "'rf', 50, 0, -49, 0, EAST"})
    void move(String command, int posX, int posY, int x, int y, Direction dir) {
        MarsRoverImpl marsRover = new MarsRoverImpl();
        marsRover.initialize(Position.of(posX, posY, Direction.NORTH));
        Position position = marsRover.move(command);
        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
        assertEquals(dir, position.getDirection());
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("MarsRover Configure Laser Range")
    @CsvSource({"0", "1", "30", "100", "-1"})
    void configureLaserRange(int range) throws NoSuchFieldException, IllegalAccessException {
        MarsRoverImpl marsRover = new MarsRoverImpl();
        marsRover.configureLaserRange(range);

        final Field field = marsRover.getClass().getDeclaredField("laserRange");
        field.setAccessible(true);

        assertEquals(range, field.get(marsRover));
    }
}
