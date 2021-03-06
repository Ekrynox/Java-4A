package com.esiea.tp4AGame.domain;

import com.esiea.tp4A.domain.Position;

import java.util.Set;

public interface Party {
    /** Create a new player with a name, if the name is not taken
     * @param playerName The name of the player in the game
     * @return An object to control the player
     */
    PlayerController addPlayer(String playerName);

    /** Get the state of the game
     * @return the name of the winner, if empty string the game is not finished
     */
    String getWinner();

    /** Get the position of the rover
     * @param playerName The name of the player
     * @return the actual position of the rover, return the last position if the rover have been destroyed, return null if unknown player
     */
    Position getRoverPosition(String playerName);

    /** Get the list of obstacles and players around the player
     * @param playerName The name of the player
     * @return the list of the position, if the direction is null, it is an obstacle, else it is a player, return null if unknown player
     */
    Set<Position> radar(String playerName);

    /** Get the laser's range
     * @param playerName The name of the player
     * @return the laser's range, return 0 if unknown player or laser's range is 0
     */
    int getLaserRange(String playerName);

    /** Control the rover
     * @param playerName The name of the player
     * @param command the actions to do: s: shoot, f: forward, b: backward, l: turn left, r: turn right
     * @return return the new position, return null if unknown player
     */
    Position move(String playerName, String command);

    /** Get the state of the player
     * @param playerName The name of the player
     * @return true if the player is alive, return false if unknown player or player is dead
     */
    boolean isAlive(String playerName);

    /** Get the name of the player at a specific position
     * @param position the position to look at
     * @return return the name of the player at a specific position if it is alive
     */
    String getPlayerNameByPosition(Position position);
}
