package com.esiea.tp4A.domain;

import java.math.MathContext;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.lang.Math;
import static java.lang.Math.round;

public class PlanetMapImpl implements PlanetMap {

    private final int [][] map;
    private final int [] sizeType = {100, 300, 600};
    private final int size;

    public PlanetMapImpl() {
        Random rand = new Random();
        size = rand.nextInt(sizeType.length);
        this.map = new int[sizeType[size]][sizeType[size]];
    }

    public int[][] getMap() {
        return map;
    }

    public int getSize() {
        return size;
    }

    public int getTileInfos(int x, int y) {
        return map[y][x];
    }


    public void displayMap(){
        for(int y=-(getSize()/2);y<=(getSize()/2);y++){
            for(int x=-(getSize()/2);x<=(getSize()/2);x++){
                System.out.print(getTileInfos(x, y));
            } System.out.println();
        }
    }

    public void spawnObstacles() {
        int obstaclesToSpawn = (int) round(0.15 * this.getSize());
        while (obstaclesToSpawn > 0) {
            for (int x = 0; x < this.getSize(); x++) {
                for (int y = 0; y < this.getSize(); y++) {
                    int rand=(int)(Math.random() *((100 - 1) + 1)) +1;
                    if ((obstaclesToSpawn > 0 && rand == 50)) {
                        map[x][y] = 1;
                        obstaclesToSpawn--;
                    }
                }
            }
        }
    }


    @Override
    public Set<Position> obstaclePositions() {
        Set<Position> positions = new HashSet<>();
        for(int x=0; x<this.getSize(); x++) {
            for(int y=0; y<this.getSize(); y++) {
            if(map[x][y] == 1) {
                Position position = new Position.FixedPosition(y,x,null);positions.add(position);
            } }
        }return positions;
    }
}
