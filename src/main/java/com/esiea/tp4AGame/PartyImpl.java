package com.esiea.tp4AGame;

import com.esiea.tp4A.PlanetMapImpl;
import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Party;
import com.esiea.tp4AGame.domain.PlayerController;

import java.util.Set;

public class PartyImpl implements Party {
    private final int mapSize;
    private Set<Position> map;

    public PartyImpl() {
        this(100, new PlanetMapImpl());
    }

    public PartyImpl(int mapSize, PlanetMap map) {
        this.mapSize = mapSize;
        this.map = map.obstaclePositions();
    }

    @Override
    public PlayerController addPlayer(String playerName) {
        return null;
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public Set<String> getAlivePlayers() {
        return null;
    }

    @Override
    public String getWinner() {
        return null;
    }

    @Override
    public Position getRoverPosition(String playerName) {
        return null;
    }

    @Override
    public Set<Position> radar(String playerName) {
        return null;
    }

    @Override
    public int getLaserRange(String playerName) {
        return 0;
    }

    @Override
    public Position move(String playerName, String command) {
        return null;
    }

    @Override
    public boolean isAlive(String playerName) {
        return false;
    }
}
