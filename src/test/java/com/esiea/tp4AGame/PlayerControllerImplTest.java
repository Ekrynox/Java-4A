package com.esiea.tp4AGame;

import com.esiea.tp4A.PlanetMapImpl;
import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Party;
import com.esiea.tp4AGame.domain.PlayerController;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerImplTest {

    @Test
    void initialize() {
        PlayerController player = new PlayerControllerImpl();
        assertNull(player.getRoverPosition());

        Party party = new PartyImpl();
        party.addPlayer("a");

        player = player.initialize(party, "a");
        assertNotNull(player.getRoverPosition());
    }

    @RepeatedTest(50)
    void getRoverPosition() {
        PlayerController player = new PlayerControllerImpl();
        assertNull(player.getRoverPosition());

        Party party = new PartyImpl();
        party.addPlayer("a");

        player = player.initialize(party, "a");
        assertEquals(party.getRoverPosition("a"), player.getRoverPosition());
    }

    @RepeatedTest(50)
    void radar() {
        PlayerController player = new PlayerControllerImpl();
        assertNull(player.radar());

        Party party = new PartyImpl();
        party.addPlayer("a");

        player = player.initialize(party, "a");
        assertEquals(party.radar("a").size(), player.radar().size());

        Set<Position> radar = player.radar();
        for (Position pos : party.radar("a")) {
            radar.removeIf(tmpPos -> tmpPos.getDirection() == pos.getDirection() && tmpPos.getX() == pos.getX() && tmpPos.getY() == pos.getY());
        }
        assertEquals(0, radar.size());
    }

    @RepeatedTest(50)
    void getLaserRange() {
        PlayerController player = new PlayerControllerImpl();
        assertEquals(0, player.getLaserRange());

        Party party = new PartyImpl();
        party.addPlayer("a");

        player = player.initialize(party, "a");
        assertEquals(party.getLaserRange("a"), player.getLaserRange());
    }

    @RepeatedTest(50)
    void move() {
        PlayerController player = new PlayerControllerImpl();
        assertNull(player.move("frblf"));

        Party party = new PartyImpl(100, new PlanetMapImpl(), 0);
        party.addPlayer("a");

        player = player.initialize(party, "a");

        Position pos1 = party.getRoverPosition("a");
        party.move("a", "frblfflffffrb");
        Position pos2 = player.move("flbbbbrbbrflb");

        assertEquals(pos1.getDirection(), pos2.getDirection());
        assertEquals(pos1.getX(), pos2.getX());
        assertEquals(pos1.getY(), pos2.getY());
    }

    @RepeatedTest(50)
    void isAlive() {
        PlayerController player = new PlayerControllerImpl();
        assertFalse(player.isAlive());

        Party party = new PartyImpl();
        party.addPlayer("a");

        player = player.initialize(party, "a");
        assertEquals(party.isAlive("a"), player.isAlive());

        player = player.initialize(party, "b");
        assertEquals(party.isAlive("b"), player.isAlive());
    }
}
