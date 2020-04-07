package com.esiea.tp4AGame;

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
}
