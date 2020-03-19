package Simulation.Evolution;

import Simulation.System.Host;

public class Cell implements Host {
    int posX;
    int posY;
    int energy;
    Executor mind;
    Genome genome;
    CellState statemod;
    Cell(int x,int y){
        posX = x;
        posY = y;
        energy = 10;
        genome = new Genome();
        mind = new Executor();
        statemod = new CellState();
        statemod.setState(CellState.State.ALIVE);
    }

    public void step() {

    }
}

class CellState {
    enum State {
        DEAD,
        ALIVE,
        EMPTY
    }

    State state;

    public void setState(State state) {
        this.state = state;

    }

    public State getState() {
        return this.state;
    }
}
