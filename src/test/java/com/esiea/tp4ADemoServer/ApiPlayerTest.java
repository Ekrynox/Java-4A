package com.esiea.tp4ADemoServer;

import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.Position;
import com.esiea.tp4ADemoServer.json.JsonPlayer;
import com.esiea.tp4ADemoServer.json.JsonStatus;
import com.google.gson.Gson;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiPlayerTest {
    private final String schema = "{\n" +
        "    \"$schema\": \"http://json-schema.org/draft-07/schema\",\n" +
        "    \"type\": \"object\",\n" +
        "    \"properties\": {\n" +
        "        \"player\": {\n" +
        "            \"type\": \"object\",\n" +
        "            \"properties\": {\n" +
        "                \"name\": { \"type\": \"string\" },\n" +
        "                \"status\": { \"type\": \"string\", \"enum\": [\"alive\", \"dead\"]},\n" +
        "                \"position\": {\n" +
        "                    \"type\": \"object\",\n" +
        "                    \"properties\": {\n" +
        "                        \"x\": { \"type\": \"integer\" },\n" +
        "                        \"y\": { \"type\": \"integer\" },\n" +
        "                        \"direction\": { \"type\": \"string\", \"enum\": [\"NORTH\", \"EAST\", \"SOUTH\", \"WEST\"]}\n" +
        "                    },\n" +
        "                    \"additionalProperties\": false,\n" +
        "                    \"required\": [\"x\", \"y\", \"direction\"]\n" +
        "                },\n" +
        "                \"laser-range\": { \"type\": \"integer\", \"exclusiveMinimum\": 0 }\n" +
        "            },\n" +
        "            \"additionalProperties\": false,\n" +
        "            \"required\": [\"name\", \"status\", \"position\", \"laser-range\"]\n" +
        "        },\n" +
        "        \"local-map\": {\n" +
        "            \"type\": \"object\",\n" +
        "            \"properties\": {\n" +
        "                \"obstacles\": {\n" +
        "                    \"type\": \"array\",\n" +
        "                    \"items\": {\n" +
        "                        \"type\": \"object\",\n" +
        "                        \"properties\": {\n" +
        "                            \"x\": { \"type\": \"integer\" },\n" +
        "                            \"y\": { \"type\": \"integer\" }\n" +
        "                        },\n" +
        "                        \"additionalProperties\": false,\n" +
        "                        \"required\": [\"x\", \"y\"]\n" +
        "                    }\n" +
        "                },\n" +
        "                \"players\": {\n" +
        "                    \"type\": \"array\",\n" +
        "                    \"items\": {\n" +
        "                        \"type\": \"object\",\n" +
        "                        \"properties\": {\n" +
        "                            \"name\": { \"type\": \"string\" },\n" +
        "                            \"x\": { \"type\": \"integer\" },\n" +
        "                            \"y\": { \"type\": \"integer\" }\n" +
        "                        },\n" +
        "                        \"additionalProperties\": false,\n" +
        "                        \"required\": [\"name\", \"x\", \"y\"]\n" +
        "                    }\n" +
        "                }\n" +
        "            },\n" +
        "            \"additionalProperties\": false,\n" +
        "            \"required\": [\"obstacles\", \"players\"]\n" +
        "        }\n" +
        "    },\n" +
        "    \"additionalProperties\": false,\n" +
        "    \"required\": [\"player\", \"local-map\"]\n" +
        "}\n";

    @RepeatedTest(50)
    void addPlayer() {
        ApiPlayer api = new ApiPlayer();
        assertEquals(201, api.addPlayer("a").getStatus());
        assertEquals(409, api.addPlayer("a").getStatus());

        for (int i = 0; i < 100; i++) {
            assertEquals(201, api.addPlayer("a" + i).getStatus());
        }

        Response resp = api.addPlayer("b");
        assertEquals(201, resp.getStatus());

        JSONObject rawSchema = new JSONObject(new JSONTokener(schema));
        Schema schema = SchemaLoader.load(rawSchema);
        schema.validate(new JSONObject(resp.getEntity().toString()));
    }

    @RepeatedTest(100)
    void getStatus() {
        ApiPlayer api = new ApiPlayer();
        assertEquals(404, api.getStatus("a").getStatus());
        api.addPlayer("a");
        Response resp = api.getStatus("a");
        assertEquals(200, resp.getStatus());

        JSONObject rawSchema = new JSONObject(new JSONTokener(schema));
        Schema schema = SchemaLoader.load(rawSchema);
        schema.validate(new JSONObject(resp.getEntity().toString()));
    }

    @RepeatedTest(100)
    void sendCommand() {
        ApiPlayer api = new ApiPlayer();
        assertEquals(404, api.sendCommand("a", "").getStatus());
        api.addPlayer("a");
        Response resp = api.sendCommand("a", "");
        assertEquals(200, resp.getStatus());

        JSONObject rawSchema = new JSONObject(new JSONTokener(schema));
        Schema schema = SchemaLoader.load(rawSchema);
        schema.validate(new JSONObject(resp.getEntity().toString()));

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

    @Test
    void jsonPlayer() {
        Gson gson = new Gson();
        gson.toJson(new JsonPlayer("a", false, Position.of(0, 10, Direction.NORTH), 5));
        gson.toJson(new JsonPlayer("a", false, Position.of(0, 10, Direction.NORTH), 5));
        gson.toJson(new JsonPlayer("a", true, Position.of(0, 10, Direction.EAST), 5));
        gson.toJson(new JsonPlayer("a", false, Position.of(0, 10, Direction.WEST), 5));
        gson.toJson(new JsonPlayer("a", true, Position.of(0, 10, Direction.SOUTH), 5));
        gson.toJson(new JsonPlayer("a", true, Position.of(0, 10, null), 5));
    }
}
