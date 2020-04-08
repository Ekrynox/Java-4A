package com.esiea.tp4AGame;

import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Party;
import com.esiea.tp4AGame.domain.PlayerController;

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
        if (this.party == null) {
            return null;
        }
        return this.party.getRoverPosition(this.playerName);
    }

    @Override
    public Set<Position> radar() {
        if (this.party == null) {
            return null;
        }
        return this.party.radar(this.playerName);
    }

    @Override
    public int getLaserRange() {
        if (this.party == null) {
            return 0;
        }
        return this.party.getLaserRange(this.playerName);
    }

    @Override
    public Position move(String command) {
        if (this.party == null) {
            return null;
        }
        return this.party.move(this.playerName, command);
    }

    @Override
    public boolean isAlive() {
        if (this.party == null) {
            return false;
        }
        return this.party.isAlive(this.playerName);
    }
}
