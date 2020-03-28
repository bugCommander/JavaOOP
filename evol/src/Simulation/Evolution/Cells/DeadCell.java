package Simulation.Evolution.Cells;

import Simulation.Evolution.Executor;
import Simulation.Evolution.Genome;
import Simulation.System.Executable;
import Simulation.System.Host;

public class DeadCell extends Host {
    public DeadCell(int x, int y, int hp) {
        super(x, y, hp);
    }
    @Override
    public void step(Executable executor) throws Exception {
        if(!(executor instanceof Executor)){
            throw new Exception("executor should be Exec");
        }
        changeEnergy(-10);

    }
}
