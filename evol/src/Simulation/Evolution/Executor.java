package Simulation.Evolution;

import Simulation.Evolution.Genes.Attack;
import Simulation.System.Command;
import Simulation.System.CommandContainer;

public class Executor implements Simulation.System.Executable {



    @Override
    public void execute(Command command) {
        command.doStuff();

    }
}
