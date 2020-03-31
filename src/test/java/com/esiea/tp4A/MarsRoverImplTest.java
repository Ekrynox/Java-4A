package com.esiea.tp4A;

import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

class MarsRoverImplTest {

    @DisplayName("MarsRover Initialize")
    @ParameterizedTest(name = "{0} {1} {2}")
    @CsvSource({"0, 0, NORTH", "2, 29, SOUTH", "4, 6, EAST", "7, 16, WEST", "-9, 19, NORTH"})
    void initialize(int x, int y, Direction dir) {
        MarsRoverImpl marsRover = new MarsRoverImpl();
        marsRover.initialize(Position.of(x, y, dir));
        assertEquals(x, marsRover.getPosition().getX());
        assertEquals(y, marsRover.getPosition().getY());
        assertEquals(dir, marsRover.getPosition().getDirection());
    }

    @DisplayName("MarsRover Move")
    @ParameterizedTest(name = "{0} {1} {2} {3}")
    @CsvSource({"'f,f,l,b',1,2,WEST","'f',0,1,NORTH","'r,r',0,0,SOUTH","'a,l,f,b',0,0,WEST"})
    void move(String command, int x, int y, Direction dir) {
        MarsRoverImpl marsRover = new MarsRoverImpl();
        marsRover.initialize(Position.of(0,0,Direction.NORTH));
        Position position = marsRover.move(command);
        assertEquals(x,position.getX());
        assertEquals(y,position.getY());
        assertEquals(dir,position.getDirection());
    }
}
