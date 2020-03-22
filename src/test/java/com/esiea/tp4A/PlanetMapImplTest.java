package com.esiea.tp4A.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanetMapImplTest {
    @Test
    void mapCreation(){
        PlanetMapImpl planetMapImpl = new PlanetMapImpl();
        Assertions.assertThat(planetMapImpl).isEqualTo(new PlanetMapImpl());
    }

    @Test
    void obstaclePositions() {
    }
}
