package com.esiea.tp4AGame;

import com.esiea.tp4A.PlanetMapImpl;
import com.esiea.tp4A.domain.MarsRover;
import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Party;
import com.esiea.tp4AGame.domain.PlayerController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PartyImpl implements Party {
    private final int mapSize;
    private final Set<Position> map;

    private final Map<String, Player> players;
    private final Map<String, Player> playersAlive;

    final class Player {
        Player() {

        }
    }

    public PartyImpl() {
        this(100, new PlanetMapImpl());
    }

    public PartyImpl(int mapSize, PlanetMap map) {
        this.mapSize = mapSize;
        this.map = map.obstaclePositions();

        this.players = new HashMap<>();
        this.playersAlive = new HashMap<>();
    }

    @Override
    public PlayerController addPlayer(String playerName) {
        if (players.containsKey(playerName) || this.isStarted()) {
            return null;
        }

        players.put(playerName, new Player());
        PlayerController controller = new PlayerControllerImpl();
        return controller.initialize(this, playerName);
    }

    @Override
    public boolean start() {
        if (this.players.size() < 2 || this.isStarted()) {
            return false;
        }

        this.playersAlive.putAll(this.players);
        return true;
    }

    @Override
    public boolean isStarted() {
        return this.playersAlive.size() > 0;
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
