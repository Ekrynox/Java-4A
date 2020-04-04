package com.esiea.tp4A;
import com.esiea.tp4A.domain.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.lang.Math.round;

class PlanetMapImplTest {

    @Test
    void mapInitialization(){
        PlanetMapImpl planetMapImpl = new PlanetMapImpl();
        int [][] map = planetMapImpl.getMap();
        for(int x = 0; x < planetMapImpl.getSize(); x++){
            for(int y =0; y < planetMapImpl.getSize();y++){
                Assertions.assertThat(map[x][y]).isEqualTo(0);
            }
        }
    }

    @Test

    void getMap(){
        PlanetMapImpl planetMap = new PlanetMapImpl();
        int [][] map = planetMap.getMap();
        int mapSize = planetMap.getSize();
        System.out.println(mapSize);
        planetMap.setTileInfos(10,20,2);
        planetMap.setTileInfos(5,5,1);
        int [][] map2 = new int[mapSize][mapSize];
        map2[10][20]=2;
        map2[5][5]=1;
        Assertions.assertThat(map).isEqualTo(map2);
    }

    @Test

    void getTileInfos(){
        PlanetMapImpl planetMap = new PlanetMapImpl();
        Assertions.assertThat(planetMap.getTileInfos(0,0)).isEqualTo(0);
        planetMap.setTileInfos(0,0,1);
        Assertions.assertThat(planetMap.getTileInfos(0,0)).isEqualTo(1);
        planetMap.setTileInfos(0,0,0);
        Assertions.assertThat(planetMap.getTileInfos(0,0)).isEqualTo(0);
    }


    @Test

    void setTileInfos(){
        PlanetMapImpl planetMap = new PlanetMapImpl();
        Assertions.assertThat(planetMap.getTileInfos(0,0)).isEqualTo(0);
        planetMap.setTileInfos(0,0,1);
        Assertions.assertThat(planetMap.getTileInfos(0,0)).isEqualTo(1);
        planetMap.setTileInfos(0,0,0);
        Assertions.assertThat(planetMap.getTileInfos(0,0)).isEqualTo(0);
    }


    @Test

    void spawnObstacles(){
        PlanetMapImpl planetMap = new PlanetMapImpl();
        planetMap.spawnObstacles();
        int area = planetMap.getSize()*planetMap.getSize();
        Set<Position> obstaclesPositions;
        obstaclesPositions = planetMap.obstaclePositions();
        Assertions.assertThat(obstaclesPositions.size()).isEqualTo(round(0.15 * area));

    }

    @Test

    void displayMap(){
        PlanetMapImpl planetMap = new PlanetMapImpl();
        planetMap.spawnObstacles();
        planetMap.displayMap();
    }

}
