package com.esiea.tp4AGame;

import com.esiea.tp4A.PlanetMapImpl;
import com.esiea.tp4AGame.domain.Party;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartyImplTest {
    @Test
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
}
