package Simulation.Evolution.Cells;

import Simulation.Evolution.Executor;
import Simulation.Evolution.Genome;
import Simulation.System.Executable;
import Simulation.System.Host;

public class Cell extends Host {
     public Genome genome;
    public Cell() {
    }

    ;

    public Cell(int x, int y, int hp) {
        super(x, y, hp);
        genome = new Genome();
        /// mind = new Executor(this);
    }



    public void step(Executable executor) throws Exception {
        if(!(executor instanceof Executor)){
            throw new Exception("executor should be Exec");
        }
        for(int i = 0;i < genome.size();++i){
            executor.execute(genome.getCommand(i),this);
        }


    }
}


