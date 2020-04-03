package Simulation.Evolution.Cells;

import Simulation.Evolution.Executor;
import Simulation.Evolution.Genome;
import Simulation.System.Executable;
import Simulation.System.Host;

import java.util.ArrayList;

public class DeadCell extends Host {
    @Override
    public void offsetPointer(int offset) {

    }

    @Override
    public void makeNeighbours(ArrayList<Host> cells, int hSize, int wSize) {

    }

    public DeadCell(int x, int y, int hp) {
        super(x, y, hp);
    }
    @Override
    public void step(Executable executor) throws Exception {
        if(!(executor instanceof Executor)){
            throw new Exception("executor should be Exec");
        }
        changeEnergy(-10);
        ///Thread.sleep(50);

        System.out.println("DEAD CELL");

    }
}
