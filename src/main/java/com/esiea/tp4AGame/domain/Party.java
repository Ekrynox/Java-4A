package com.esiea.tp4AGame.domain;

import com.esiea.tp4A.domain.Position;

import java.util.Set;

public interface Party {
    /** Create a new player with a name, if the name is not taken
     * @param playerName The name of the player in the game
     * @return An object to control the player
     */
    PlayerController addPlayer(String playerName);

    /** Start the party if there is at least 2 players
     * @return return the new instance of the party
     */
    boolean start();

    /** Get if the party has been started
     * @return true if the party have been started
     */
    boolean isStarted();

    /** Get the state of the game
     * @return the name of the winner, if empty string the game is not finished
     */
    String getWinner();



    /** Get the position of the rover
     * @param playerName The name of the player
     * @return the actual position of the rover, return the last position if the rover have been destroyed
     */
    Position getRoverPosition(String playerName);

    /** Get the list of obstacles and players around the player
     * @param playerName The name of the player
     * @return the list of the position, if the direction is null, it is an obstacle, else it is a player
     */
    Set<Position> radar(String playerName);

    /** Get the laser's range
     * @param playerName The name of the player
     * @return the laser's range
     */
    int getLaserRange(String playerName);

    /** Control the rover
     * @param playerName The name of the player
     * @param command the actions to do: s: shoot, f: forward, b: backward, l: turn left, r: turn right
     * @return return the new position
     */
    Position move(String playerName, String command);

    /** Get the state of the player
     * @param playerName The name of the player
     * @return true if the player is alive
     */
    boolean isAlive(String playerName);
}
