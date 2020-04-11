package com.esiea.tp4AGame.json;

import com.google.gson.annotations.SerializedName;

public class JsonStatus {
    public JsonStatus(JsonPlayer player, JsonMap local_map) {
        this.player = player;
        this.local_map = local_map;
    }

    public final JsonPlayer player;

    @SerializedName(value = "local-map")
    public final JsonMap local_map;
}
