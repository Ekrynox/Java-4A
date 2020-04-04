package com.esiea.tp4A;

import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;

import java.math.MathContext;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.lang.Math;
import static java.lang.Math.round;

public class PlanetMapImpl implements PlanetMap {

    private final int [][] map;
    private final int [] size = {100, 300, 600};
    private final int sizeIndex;

    public PlanetMapImpl() {
        Random rand = new Random();
        sizeIndex = rand.nextInt(size.length);
      //  System.out.println("sizeIndex : " + sizeIndex);
        this.map = new int[size[sizeIndex]][size[sizeIndex]];
      //  System.out.println("size : " +size[sizeIndex]);
    }

    public int[][] getMap() {
        return map;
    }

    public int getSize() {
        return size[sizeIndex];
    }

    public int getTileInfos(int x, int y) {
        return map[x][y];
    }
    public void setTileInfos(int x, int y, int value){ map[x][y] = value; }

    public void spawnObstacles() {
        int obstaclesToSpawn = (int) round(0.15 * (this.getSize()*this.getSize()));
        System.out.println("Map size = " + this.getSize() + "\n Map area : "+ (this.getSize()*this.getSize()) +"\nObstacles to spawn = " + obstaclesToSpawn);
        while (obstaclesToSpawn > 0) {
            for (int x = 0; x < this.getSize(); x++) {
                for (int y = 0; y < this.getSize(); y++) {
                    int rand=(int)(Math.random() *((100 - 1) + 1)) +1;
                    if ((obstaclesToSpawn > 0 && rand > 80)) {
                        map[x][y] = 1;
                     //   System.out.println("\n Spawn at x: " + x + "y: " + y);
                        obstaclesToSpawn--;
                    }
                }
            }
        }
    }

    public void displayMap(){
        for(int x=0;x<this.getSize();x++){
            for(int y=0;y<this.getSize();y++){
                System.out.print(getTileInfos(x, y));
            } System.out.println();
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
