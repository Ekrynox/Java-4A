package com.esiea.tp4AGame;

import com.esiea.tp4A.PlanetMapImpl;
import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Party;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PartyImplTest {
    @Test
    void addPlayer() {
        Party party1 = new PartyImpl();
        assertNotNull(party1.addPlayer("a"));
        assertNull(party1.addPlayer("a"));
        assertNotNull(party1.addPlayer("b"));
        assertNotNull(party1.addPlayer("c"));

        Party party2 = new PartyImpl(2, new PlanetMapImpl(), 0);
        assertNotNull(party2.addPlayer("a"));
        assertNotNull(party2.addPlayer("b"));
        assertNotNull(party2.addPlayer("c"));
        assertNotNull(party2.addPlayer("d"));
        assertNull(party2.addPlayer("e"));
    }

    @Test
    void getLaserRange() {
        Party party1 = new PartyImpl(100, new PlanetMapImpl(), 5);
        party1.addPlayer("a");
        assertEquals(5, party1.getLaserRange("a"));

        Party party2 = new PartyImpl(100, new PlanetMapImpl(), 5);
        assertEquals(0, party2.getLaserRange("a"));

        Party party3 = new PartyImpl(100, new PlanetMapImpl(), -10);
        party3.addPlayer("a");
        assertEquals(0, party3.getLaserRange("a"));

        Party party4 = new PartyImpl(100, new PlanetMapImpl(), Integer.MAX_VALUE);
        party4.addPlayer("a");
        assertEquals(Integer.MAX_VALUE, party4.getLaserRange("a"));
    }

    @RepeatedTest(100)
    void getRoverPosition() {
        Party party1 = new PartyImpl(100, new PlanetMapImpl(), 5);
        party1.addPlayer("a");
        assertNotNull(party1.getRoverPosition("a"));
        assertNotNull(party1.getRoverPosition("a").getDirection());
        assertNull(party1.getRoverPosition("b"));
        party1.addPlayer("b");
        assertFalse(party1.getRoverPosition("b").getX() == party1.getRoverPosition("a").getX() && party1.getRoverPosition("b").getY() == party1.getRoverPosition("a").getY());
    }

    @RepeatedTest(100)
    void radar() {
        PlanetMapImpl map = new PlanetMapImpl();
        map.addObstacle(0, 0);
        map.addObstacle(-5, 4);
        map.addObstacle(-3, 0);
        map.addObstacle(-4, 10);
        map.addObstacle(-4, 1);
        map.addObstacle(-9, 3);
        map.addObstacle(-14, 15);
        map.addObstacle(-14, -14);
        map.addObstacle(15, -14);
        map.addObstacle(15, 15);

        Party party1 = new PartyImpl(30, map, 0);
        party1.addPlayer("a");
        party1.addPlayer("b");
        party1.addPlayer("c");
        party1.addPlayer("d");
        assertNull(party1.radar("e"));
        assertNotNull(party1.radar("a"));

        Set<Position> tmp = party1.radar("a");
        assertEquals(map.obstaclePositions().size() - 1, tmp.size());

        for (Position pos : map.obstaclePositions()) {
            tmp.removeIf(tmpPos -> pos.getX() == tmpPos.getX() && pos.getY() == tmpPos.getY() && pos.getDirection() == tmpPos.getDirection() && pos.getDirection() == null);
        }
        assertEquals(3, tmp.size());

        Position tmpPos = party1.getRoverPosition("a");
        for (Position pos : tmp) {
            assertFalse(pos.getX() == tmpPos.getX() && pos.getY() == tmpPos.getY() && pos.getDirection() == tmpPos.getDirection());
            assertNotNull(pos.getDirection());
        }


        PlanetMapImpl map2 = new PlanetMapImpl();
        tmp = map2.obstaclePositions();

        int N = 100*100;
        int nbObs = (int)Math.round(N * 0.15);
        ArrayList<Integer> mapGen = new ArrayList<>(Collections.nCopies(N, 0));
        for (int n = 0; n < nbObs; n++) {
            mapGen.set(n, 1);
        }

        Collections.shuffle(mapGen, new Random());
        int offset = 1 - 100 / 2;
        for(int n = 0; n < mapGen.size(); n++) {
            if (mapGen.get(n) == 1) {
                tmp.add(Position.of(n % 100 + offset, n / 100 + offset, null));
            }
        }
        Party party2 = new PartyImpl(100, map2, 0);
        party2.addPlayer("a");
        assertNotEquals(map2.obstaclePositions().size(), party2.radar("a"));
    }

    @RepeatedTest(100)
    void isAlive() {
        Party party = new PartyImpl(10, new PlanetMapImpl(), Integer.MAX_VALUE);
        assertFalse(party.isAlive("a"));
        party.addPlayer("a");
        assertTrue(party.isAlive("a"));
        party.addPlayer("b");
        assertTrue(party.isAlive("b"));

        Position posA = party.getRoverPosition("a");
        Position posB = party.getRoverPosition("b");

        if (posA.getDirection() == Direction.WEST || posA.getDirection() == Direction.EAST) {
            if (posA.getY() == posB.getY()) {
                party.move("a", "r");
            }
        } else if (posA.getDirection() == Direction.SOUTH || posA.getDirection() == Direction.NORTH) {
            if (posA.getX() == posB.getX()) {
                party.move("a", "r");
            }
        }
        party.move("a", "s");
        assertFalse(party.isAlive("a"));
        assertTrue(party.isAlive("b"));

        Position pos = party.getRoverPosition("a");
        assertEquals(pos, party.move("a", "f"));

        assertEquals("b", party.getWinner());
    }

    private int floorPos(int x, int mapSize) {
        return Math.floorMod(x - 1 + (mapSize / 2), mapSize) + 1 - (mapSize / 2);
    }

    @RepeatedTest(100)
    void move() {
        int mapSize = (int) (Math.random() * 90 + 10);

        PlanetMapImpl map = new PlanetMapImpl();
        Party party = new PartyImpl(mapSize, map, Integer.MAX_VALUE);
        assertNull(party.move("a", "f"));
        party.addPlayer("a");
        Position pos1 = party.getRoverPosition("a");
        Position pos2 = party.move("a", "fr");
        assertTrue(pos1.getX() == pos2.getX() ^ pos1.getY() == pos2.getY());
        assertNotEquals(pos1.getDirection(), pos2.getDirection());
        pos1 = pos2;

        party.addPlayer("b");

        pos2 = party.getRoverPosition("b");
        Position finalPos = pos2;
        map.obstaclePositions().removeIf(tmpPos -> tmpPos.getX() == finalPos.getX() && tmpPos.getY() == finalPos.getY() && tmpPos.getDirection() == finalPos.getDirection());

        pos2 = party.move("a", "frf");
        switch (pos1.getDirection()) {
            case NORTH:
                assertEquals(floorPos(pos1.getX() + 1, mapSize), pos2.getX());
                assertEquals(floorPos(pos1.getY() + 1, mapSize), pos2.getY());
                assertEquals(Direction.EAST, pos2.getDirection());
                break;
            case EAST:
                assertEquals(floorPos(pos1.getX() + 1, mapSize), pos2.getX());
                assertEquals(floorPos(pos1.getY() - 1, mapSize), pos2.getY());
                assertEquals(Direction.SOUTH, pos2.getDirection());
                break;
            case SOUTH:
                assertEquals(floorPos(pos1.getX() - 1, mapSize), pos2.getX());
                assertEquals(floorPos(pos1.getY() - 1, mapSize), pos2.getY());
                assertEquals(Direction.WEST, pos2.getDirection());
                break;
            case WEST:
                assertEquals(floorPos(pos1.getX() - 1, mapSize), pos2.getX());
                assertEquals(floorPos(pos1.getY() + 1, mapSize), pos2.getY());
                assertEquals(Direction.NORTH, pos2.getDirection());
                break;
        }

        pos1 = party.getRoverPosition("a");
        assertEquals(pos2.getX(), pos1.getX());
        assertEquals(pos2.getY(), pos1.getY());
        assertEquals(pos2.getDirection(), pos1.getDirection());
    }

    @RepeatedTest(100)
    void moveKill() {
        Party party = new PartyImpl(10, new PlanetMapImpl(), Integer.MAX_VALUE);
        party.addPlayer("a");
        party.addPlayer("b");
        assertEquals("", party.getWinner());

        Position posA = party.getRoverPosition("a");
        Position posB = party.getRoverPosition("b");

        if(posA.getX() == posB.getX()) {
            if (posA.getDirection() == Direction.WEST || posA.getDirection() == Direction.EAST) {
                party.move("a", "r");
            }
        } else if(posA.getY() == posB.getY()) {
            if (posA.getDirection() == Direction.NORTH || posA.getDirection() == Direction.SOUTH) {
                party.move("a", "l");
            }
        } else {
            if (posA.getDirection() == Direction.WEST || posA.getDirection() == Direction.EAST) {
                party.move("a", "r");
            }

            if (posB.getDirection() == Direction.NORTH || posB.getDirection() == Direction.SOUTH) {
                party.move("b", "r");
            }

            while (posB.getX() != posA.getX()) {
                posB = party.move("b", "f");
            }
        }

        party.move("a", "s");
        assertEquals("a", party.getWinner());
    }

    @RepeatedTest(100)
    void getWinner() {
        Party party = new PartyImpl(10, new PlanetMapImpl(), Integer.MAX_VALUE);
        assertEquals("", party.getWinner());
        party.addPlayer("a");
        assertEquals("a", party.getWinner());
        party.addPlayer("b");
        assertEquals("", party.getWinner());

        Position posA = party.getRoverPosition("a");
        Position posB = party.getRoverPosition("b");

        if (posA.getDirection() == Direction.WEST || posA.getDirection() == Direction.EAST) {
            if (posA.getY() == posB.getY()) {
                party.move("a", "r");
            }
        } else if (posA.getDirection() == Direction.SOUTH || posA.getDirection() == Direction.NORTH) {
            if (posA.getX() == posB.getX()) {
                party.move("a", "r");
            }
        }
        party.move("a", "s");
        assertEquals("b", party.getWinner());
    }

    @RepeatedTest(10)
    void getPlayerNameByPosition() {
        Party party = new PartyImpl();
        assertEquals("", party.getPlayerNameByPosition(Position.of(0, 0, Direction.NORTH)));
        assertEquals("", party.getPlayerNameByPosition(Position.of(-5, 10, Direction.EAST)));
        party.addPlayer("a");
        party.addPlayer("b");
        party.addPlayer("c");
        assertEquals("a", party.getPlayerNameByPosition(party.getRoverPosition("a")));
        assertEquals("b", party.getPlayerNameByPosition(party.getRoverPosition("b")));
        assertEquals("c", party.getPlayerNameByPosition(party.getRoverPosition("c")));
        assertEquals("a", party.getPlayerNameByPosition(party.getRoverPosition("a")));

        assertEquals("", party.getPlayerNameByPosition(Position.of(party.getRoverPosition("a").getX() + 1, party.getRoverPosition("a").getY(), party.getRoverPosition("a").getDirection())));
        assertEquals("", party.getPlayerNameByPosition(Position.of(party.getRoverPosition("a").getX(), party.getRoverPosition("a").getY() + 1, party.getRoverPosition("a").getDirection())));
    }
}
