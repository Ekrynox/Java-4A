package com.esiea.tp4AGame;

import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;
import com.esiea.tp4AGame.domain.Party;
import com.esiea.tp4AGame.domain.PlayerController;

import java.util.*;

public class PartyImpl implements Party {
    private final int mapSize;
    private final Set<Position> map;

    private final int laserRange;

    private final Map<String, Player> players;
    private final Map<String, Player> playersAlive;

    final class Player {
        public final int laserRange;
        public final Position position;

        Player(int laserRange, Position position) {
            this.laserRange = laserRange;
            this.position = position;
        }
    }

    public PartyImpl() {
        int[] mSize = {100, 300, 600};
        this.mapSize = mSize[new Random().nextInt(mSize.length)];

        this.map = new HashSet<>();

        int N = this.mapSize * this.mapSize;
        int nbObs = (int)Math.round(N * 0.15);
        ArrayList<Integer> mapGen = new ArrayList<>(Collections.nCopies(N, 0));
        for (int n = 0; n < nbObs; n++) {
            mapGen.set(n, 1);
        }

        Collections.shuffle(mapGen, new Random());
        int offset = 1 - this.mapSize / 2;
        for(int n = 0; n < mapGen.size(); n++) {
            if (mapGen.get(n) == 1) {
                this.map.add(Position.of(n % this.mapSize + offset, n / this.mapSize + offset, null));
            }
        }

        this.players = new HashMap<>();
        this.playersAlive = new HashMap<>();

        int[] lRange = {5, 30, Integer.MAX_VALUE};
        this.laserRange = lRange[new Random().nextInt(lRange.length)];
    }

    public PartyImpl(int mapSize, PlanetMap map, int laserRange) {
        this.mapSize = mapSize;
        this.map = map.obstaclePositions();

        this.players = new HashMap<>();
        this.playersAlive = new HashMap<>();

        this.laserRange = Math.max(laserRange, 0);
    }

    @Override
    public PlayerController addPlayer(String playerName) {
        if (players.containsKey(playerName) || this.isStarted() || this.mapSize * this.mapSize <= this.map.size()) {
            return null;
        }

        int x = 0, y = 0;
        boolean ok = false;
        while (!ok) {
            x = (int) (Math.random() * this.mapSize) + 1 - (this.mapSize / 2);
            y = (int) (Math.random() * this.mapSize) + 1 - (this.mapSize / 2);
            ok = true;
            for (Position pos : this.map) {
                if (pos.getY() == y && pos.getX() == x) {
                    ok = false;
                    break;
                }
            }
        }

        Direction[] d = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        Position pos = Position.of(x, y, d[new Random().nextInt(d.length)]);
        this.map.add(pos);
        players.put(playerName, new Player(this.laserRange, pos));
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
    public String getWinner() {
        return null;
    }

    @Override
    public Position getRoverPosition(String playerName) {
        if (!this.players.containsKey(playerName)) {
            return null;
        }

        return this.players.get(playerName).position;
    }

    @Override
    public Set<Position> radar(String playerName) {
        if (!this.players.containsKey(playerName)) {
            return null;
        }

        Position pos = this.getRoverPosition(playerName);
        int x = pos.getX();
        int y = pos.getY();
        Set<Position> tmp = new HashSet<>();
        for (Position obs : this.map) {
            int obsX = obs.getX();
            int obsY = obs.getY();
            if (x != obsX || y != obsY) {
                if (Math.abs(x - obsX) <= 15 && Math.abs(y - obsY) <= 15) {
                    tmp.add(obs);
                } else if (Math.abs(x - obsX) <= 15 && Math.abs(y - obsY + (y < obsY ? 1 : -1 ) * this.mapSize) <= 15) {
                    tmp.add(obs);
                } else if (Math.abs(x - obsX + (x < obsX ? 1 : -1 ) * this.mapSize) <= 15 && Math.abs(y - obsY) <= 15) {
                    tmp.add(obs);
                } else if (Math.abs(x - obsX + (x < obsX ? 1 : -1 ) * this.mapSize) <= 15 && Math.abs(y - obsY + (y < obsY ? 1 : -1 ) * this.mapSize) <= 15) {
                    tmp.add(obs);
                }
            }
        }
        return tmp;
    }

    @Override
    public int getLaserRange(String playerName) {
        if (!this.players.containsKey(playerName)) {
            return 0;
        }

        return this.players.get(playerName).laserRange;
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