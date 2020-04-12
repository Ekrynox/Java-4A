package com.esiea.tp4ADemoServer;

import com.esiea.tp4ADemoServer.json.JsonStatus;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiPlayerTest {
    @Test
    void addPlayer() {
        ApiPlayer api = new ApiPlayer();
        assertEquals(201, api.addPlayer("a").getStatus());
        assertEquals(409, api.addPlayer("a").getStatus());

        assertEquals(201, api.addPlayer("b").getStatus());
    }

    @Test
    void getStatus() {
        ApiPlayer api = new ApiPlayer();
        assertEquals(404, api.getStatus("a").getStatus());
        api.addPlayer("a");
        assertEquals(200, api.getStatus("a").getStatus());
    }

    @Test
    void sendCommand() {
        ApiPlayer api = new ApiPlayer();
        assertEquals(404, api.sendCommand("a", "").getStatus());
        api.addPlayer("a");
        assertEquals(200, api.sendCommand("a", "").getStatus());

        Gson gson = new Gson();
        String direction = gson.fromJson(api.sendCommand("a", "").getEntity().toString(), JsonStatus.class).player.position.direction;
        String directionBis = gson.fromJson(api.sendCommand("a", "r").getEntity().toString(), JsonStatus.class).player.position.direction;

        switch (direction) {
            case "NORTH":
                assertEquals("EAST", directionBis);
                break;
            case "SOUTH":
                assertEquals("WEST", directionBis);
                break;
            case "EAST":
                assertEquals("SOUTH", directionBis);
                break;
            case "WEST":
                assertEquals("NORTH", directionBis);
                break;
        }
    }
}
