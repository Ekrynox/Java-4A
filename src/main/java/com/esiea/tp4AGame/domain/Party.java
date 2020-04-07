package com.esiea.tp4AGame.domain;

import java.util.Set;

public interface Party {
    /** Create a new player with a name, if the name is not taken
     * @param playerName The name of the player in the game
     * @return An object to control the player
     */
    Player addPlayer(String playerName);

    /** Start the party if there is at least 2 players
     * @return true if the party is started
     */
    boolean startParty();

    /** Get the name of all alive players
     * @return A list of the name
     */
    Set<String> getAlivePlayers();

    /** Get the state of the game
     * @return the name of the winner, if empty string the game is not finished
     */
    String getWinner();
}
