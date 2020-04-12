package com.esiea.tp4ADemoServer;

import com.esiea.tp4A.domain.Position;
import com.esiea.tp4ADemoServer.json.*;
import com.esiea.tp4AGame.PartyImpl;
import com.esiea.tp4AGame.domain.Party;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

@Path("/player/{playerName}")
@Produces(MediaType.APPLICATION_JSON)
public class ApiPlayer {
    public final Party party = new PartyImpl();
    private final Gson gson = new Gson();

    private JsonStatus createStatus(String playerName) {
        Set<JsonObstacle> jsonObstacle = new HashSet<>();
        Set<JsonOpponent> jsonOpponents = new HashSet<>();

        Set<Position> map = party.radar(playerName);
        for (Position obs : map) {
            if (obs.getDirection() != null) {
                jsonOpponents.add(new JsonOpponent(party.getPlayerNameByPosition(obs), obs.getX(), obs.getY()));
            } else {
                jsonObstacle.add(new JsonObstacle(obs.getX(), obs.getY()));
            }
        }

        JsonPlayer jsonPlayer = new JsonPlayer(playerName, party.isAlive(playerName), party.getRoverPosition(playerName), party.getLaserRange(playerName));
        JsonMap jsonMap = new JsonMap(jsonObstacle.toArray(JsonObstacle[]::new), jsonOpponents.toArray(JsonOpponent[]::new));
        return new JsonStatus(jsonPlayer, jsonMap);
    }

    @POST
    public Response addPlayer(@PathParam("playerName") String playerName) {
        if (party.addPlayer(playerName) == null) {
            return Response.status(409).build();
        }

        String json = gson.toJson(createStatus(playerName));
        return Response.status(201).entity(json).build();
    }

    @GET
    public Response getStatus(@PathParam("playerName") String playerName) {
        if (party.getRoverPosition(playerName) == null) {
            return Response.status(404).build();
        }

        String json = gson.toJson(createStatus(playerName));
        return Response.status(200).entity(json).build();
    }

    @PATCH
    @Path("/{command}")
    public Response sendCommand(@PathParam("playerName") String playerName, @PathParam("command") String command) {
        if (party.getRoverPosition(playerName) == null) {
            return Response.status(404).build();
        }

        party.move(playerName, command);
        String json = gson.toJson(createStatus(playerName));
        return Response.status(200).entity(json).build();
    }
}
