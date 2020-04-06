package com.esiea.tp4AGame;

import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Party;

import java.util.Set;

public class PartyImpl implements Party {
    @Override
    public Position getRoverPosition() {
        return null;
    }

    @Override
    public Set<Position> radar(int radarSize) {
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
    public Position goForward() {
        return null;
    }

    @Override
    public Position goBackward() {
        return null;
    }

    @Override
    public Position turnRight() {
        return null;
    }

    @Override
    public Position turnLeft() {
        return null;
    }

    @Override
    public boolean shoot() {
        return false;
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
