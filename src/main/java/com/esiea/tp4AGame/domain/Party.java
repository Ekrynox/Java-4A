package com.esiea.tp4AGame.domain;

public interface Party {
    Player addPlayer(String playerName);

    boolean startParty();

    boolean isFinish();
    String getWinner();
}
