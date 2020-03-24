package com.esiea.tp4A;
import com.esiea.tp4A.domain.PlanetMapImpl;
import com.esiea.tp4A.domain.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlanetMapImplTest {
    @Test
    void mapCreation(){
        PlanetMapImpl planetMapImpl = new PlanetMapImpl();
        Assertions.assertThat(planetMapImpl).isEqualTo(new PlanetMapImpl());
    }

    @Test
    void mapInitialization(){
        PlanetMapImpl planetMapImpl = new PlanetMapImpl();
        int [][] map = planetMapImpl.getMap();
        for(int x = 0; x < planetMapImpl.getSize(); x++){
            for(int y =0; y < planetMapImpl.getSize();y++){
                Assertions.assertThat(x).isEqualTo(y);
            }
        }
    }

}
