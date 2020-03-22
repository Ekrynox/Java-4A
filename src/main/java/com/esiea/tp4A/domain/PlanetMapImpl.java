package com.esiea.tp4A.domain;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PlanetMapImpl implements PlanetMap {

    private final int [][] map;
    private final int[] sizeType = {100, 300, 600};
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


    @Override
    public Set<Position> obstaclePositions() {
        return null;
    }
}
