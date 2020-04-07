package com.esiea.tp4AGame.domain;

import com.esiea.tp4A.domain.Position;

import java.util.Set;

public interface PlayerController {
    /** Link the PlayerController to a Rover in a party.
     * @param party the party
     * @param playerName the Rover's id
     * @return the new PlayerController with the link
     */
    PlayerController initialize(Party party, String playerName);

    /** Get the position of the rover
     * @return the actual position of the rover, return the last position if the rover have been destroyed
     */
    Position getRoverPosition();

    /** Get the list of obstacles and players around the player
     * @return the list of the position, if the direction is null, it is an obstacle, else it is a player
     */
    Set<Position> radar();

    /** Get the laser's range
     * @return the laser's range
     */
    int getLaserRange();

    /** Control the rover
     * @param command the actions to do: s: shoot, f: forward, b: backward, l: turn left, r: turn right
     * @return return the new position
     */
    Position move(String command);

    /** Get the state of the player
     * @return true if the player is alive
     */
    boolean isAlive();
}
