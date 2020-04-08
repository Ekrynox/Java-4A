package com.esiea.tp4AGame;

import com.esiea.tp4A.PlanetMapImpl;
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
    @RepeatedTest(20)
    void addPlayer() {
        Party party1 = new PartyImpl();
        assertNotNull(party1.addPlayer("a"));
        assertNull(party1.addPlayer("a"));
        assertNotNull(party1.addPlayer("b"));

        party1.start();
        assertNull(party1.addPlayer("c"));

        Party party2 = new PartyImpl(2, new PlanetMapImpl(), 0);
        assertNotNull(party2.addPlayer("a"));
        assertNotNull(party2.addPlayer("b"));
        assertNotNull(party2.addPlayer("c"));
        assertNotNull(party2.addPlayer("d"));
        assertNull(party2.addPlayer("e"));
    }

    @Test
    void start() {
        Party party1 = new PartyImpl();
        assertFalse(party1.start());

        party1.addPlayer("a");
        assertFalse(party1.start());

        party1.addPlayer("aa");
        assertTrue(party1.start());

        assertFalse(party1.start());
    }

    @Test
    void isStarted() {
        Party party1 = new PartyImpl();
        assertFalse(party1.isStarted());

        party1.addPlayer("a");
        assertFalse(party1.isStarted());
        party1.addPlayer("aa");
        assertFalse(party1.isStarted());

        party1.start();
        assertTrue(party1.isStarted());
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

    @Test
    void getRoverPosition() {
        Party party1 = new PartyImpl(100, new PlanetMapImpl(), 5);
        party1.addPlayer("a");
        assertNotNull(party1.getRoverPosition("a"));
        assertNull(party1.getRoverPosition("b"));
        party1.addPlayer("b");
        assertFalse(party1.getRoverPosition("b").getX() == party1.getRoverPosition("a").getX() && party1.getRoverPosition("b").getY() == party1.getRoverPosition("a").getY());
    }

    @RepeatedTest(20)
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

    @Test
    void isAlive() {
    }
}
