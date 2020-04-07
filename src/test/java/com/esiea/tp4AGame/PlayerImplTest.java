package com.esiea.tp4AGame;

import com.esiea.tp4A.PlanetMapImpl;
import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerImplTest {

    @ParameterizedTest(name = "{0} ({1}, {2})")
    @CsvSource({"0, 0, NORTH", "2, 29, SOUTH", "4, 6, EAST", "7, 16, WEST", "-9, 19, NORTH"})
    void getRoverPosition(int x, int y, Direction dir) {
        Player player = new PlayerImpl(Position.of(x, y, dir), 0, new HashSet<>(), 100);
        Position pos = player.getRoverPosition();
        assertEquals(x, pos.getX());
        assertEquals(y, pos.getY());
        assertEquals(dir, pos.getDirection());
    }

    @Test
    void radar() {
        Set<Position> map = new HashSet<>();
        map.add(Position.of(10, 0, Direction.NORTH));
        map.add(Position.of(-5, 4, null));
        map.add(Position.of(10, -10, Direction.SOUTH));
        map.add(Position.of(31, 0, Direction.SOUTH));
        map.add(Position.of(0, 31, Direction.SOUTH));
        map.add(Position.of(22, 22, Direction.SOUTH));

        Player player = new PlayerImpl(Position.of(0, 0, Direction.WEST), 0, map, 100);
        Set<Position> tmp = player.radar();

        map.removeIf(pos -> Math.pow(pos.getX(), 2) + Math.pow(pos.getY(), 2) > 900);
        assertEquals(map.size(), tmp.size());

        for (Position obs : tmp) {
            map.removeIf(pos -> pos.getY() == obs.getY() && pos.getX() == obs.getX() && pos.getDirection() == obs.getDirection());
        }
        assertEquals(0, map.size());
        assertEquals(0, player.radar().size());
    }

    @ParameterizedTest(name = "{0} {1}")
    @CsvSource({"0, 0", "-1, 0", "10, 10", "9565, 9565", "-900, 0"})
    void getLaserRange(int range, int erange) {
        Player player = new PlayerImpl(Position.of(0, 0, Direction.NORTH), range, new HashSet<>(), 100);
        assertEquals(erange, player.getLaserRange());
    }
}
