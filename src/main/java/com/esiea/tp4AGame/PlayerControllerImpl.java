package com.esiea.tp4AGame;

import com.esiea.tp4A.MarsRoverImpl;
import com.esiea.tp4A.domain.MarsRover;
import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Party;
import com.esiea.tp4AGame.domain.PlayerController;

import java.util.HashSet;
import java.util.Set;

public class PlayerControllerImpl implements PlayerController {
    private final Party party;
    private final String playerName;


    private PlayerControllerImpl(Party party, String playerName) {
        this.party = party;
        this.playerName = playerName;
    }

    public PlayerControllerImpl() {
        this.party = null;
        this.playerName = null;
    }

    @Override
    public PlayerController initialize(Party party, String playerName) {
        return new PlayerControllerImpl(party, playerName);
    }

    @Override
    public Position getRoverPosition() {
        return null;
    }

    @Override
    public Set<Position> radar() {
        return null;
    }

    @Override
    public int getLaserRange() {
        return 0;
    }

    @Override
    public Position move(String command) {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
