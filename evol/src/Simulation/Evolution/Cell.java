package Simulation.Evolution;

import Simulation.System.Host;

public class Cell extends Host {
    ///Executor mind;
    Genome genome;
    public State state;

    public Cell() {
    }

    ;

    public Cell(int x, int y, int hp) {
        super(x, y, hp);
        genome = new Genome();
        /// mind = new Executor(this);
        state = State.ALIVE;
    }


    void checkState() {

    }

    public void step() {

    }
}


