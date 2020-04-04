package com.esiea.tp4A;

import com.esiea.tp4A.domain.Direction;
import com.esiea.tp4A.domain.MarsRover;
import com.esiea.tp4A.domain.PlanetMap;
import com.esiea.tp4A.domain.Position;

public class MarsRoverImpl implements MarsRover {
    @Override
    public MarsRover initialize(Position position) {
        this.position = position;
        return this;
    }

    @Override
    public MarsRover updateMap(PlanetMap map) {
        return null;
    }

    @Override
    public MarsRover configureLaserRange(int range) {
        return null;
    }

    @Override
    public Position move(String command) {
        int new_x = this.position.getX(); int new_y = this.position.getY(); Direction new_D = this.position.getDirection();
        for(int i=0 ; i<command.length() ; i++){
            switch (command.charAt(i)){
                case 'f':
                    if(new_D.equals(Direction.NORTH)) new_y++;
                    if(new_D.equals(Direction.SOUTH)) new_y--;
                    if(new_D.equals(Direction.EAST)) new_x++;
                    if(new_D.equals(Direction.WEST)) new_x--;
                    break;
                case 'b':
                    if(new_D.equals(Direction.NORTH)) new_y--;
                    if(new_D.equals(Direction.SOUTH)) new_y++;
                    if(new_D.equals(Direction.EAST)) new_x--;
                    if(new_D.equals(Direction.WEST)) new_x++;
                    break;
                case 'l':
                    if(new_D.equals(Direction.NORTH)) new_D = Direction.WEST;
                    else if(new_D.equals(Direction.SOUTH)) new_D = Direction.EAST;
                    else if(new_D.equals(Direction.EAST)) new_D = Direction.NORTH;
                    else if(new_D.equals(Direction.WEST)) new_D = Direction.SOUTH;
                    break;
                case 'r':
                    if(new_D.equals(Direction.NORTH)) new_D = Direction.EAST;
                    else if(new_D.equals(Direction.SOUTH)) new_D = Direction.WEST;
                    else if(new_D.equals(Direction.EAST)) new_D = Direction.SOUTH;
                    else if(new_D.equals(Direction.WEST)) new_D = Direction.NORTH;
                    break;
                default :
                    break;
            }
        }
        return Position.of(new_x,new_y,new_D);
    }


    private Position position = Position.of(0, 0, Direction.NORTH);
    public Position getPosition() {
        return position;
    }
}
