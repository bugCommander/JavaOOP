package Simulation.Evolution;

import Simulation.System.Host;
import Simulation.System.*;
public class Cell implements Host {
    int posX;
    int posY;
    int energy;
    CommandContainer genome;
    CellState statemod = new CellState();
    public void step(){
   }
}
class CellState{
    enum State{
        DEAD,
        ALIVE,
        EMPTY
    }
    State state;
    public void setState(State state){
        this.state = state;

    }
    public State getState(){
        return this.state;
    }
}
