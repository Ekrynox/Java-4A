package com.esiea.tp4ADemoServer;

import com.esiea.tp4ADemoServer.json.JsonStatus;
import com.google.gson.Gson;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {

    @Test
    void main() {
        Runnable main = () -> {
            try {
                Server.main(new String[] {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread mainT = new Thread(main);
        mainT.start();

        Gson gson = new Gson();

        Client client = ClientBuilder.newClient();
        assertEquals(404, client.target("http://localhost:8080/api/player/damien").request(MediaType.APPLICATION_JSON).get().getStatus());
        assertEquals(201, client.target("http://localhost:8080/api/player/damien").request(MediaType.APPLICATION_JSON).post(Entity.entity(null, MediaType.APPLICATION_FORM_URLENCODED)).getStatus());
        assertEquals(409, client.target("http://localhost:8080/api/player/damien").request(MediaType.APPLICATION_JSON).post(Entity.entity(null, MediaType.APPLICATION_FORM_URLENCODED)).getStatus());

        Response response = client.target("http://localhost:8080/api/player/damien").request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, response.getStatus());
        String json = response.readEntity(String.class);
        JsonStatus status = gson.fromJson(json, JsonStatus.class);
        assertEquals("damien", status.player.name);

        JSONObject rawSchema = new JSONObject(new JSONTokener("{\n" +
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
            "}\n"));

        Schema schema = SchemaLoader.load(rawSchema);
        schema.validate(new JSONObject(json));

        mainT.stop();
    }
}
