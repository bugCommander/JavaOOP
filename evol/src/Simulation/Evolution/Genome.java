package Simulation.Evolution;

import Simulation.System.Command;
import Simulation.System.CommandContainer;
import Simulation.System.Executable;
import Simulation.System.Host;

import java.util.ArrayList;

public class Genome implements CommandContainer {
    ArrayList<Command> genom;
    Genome(){
        genom = new ArrayList<>();

    }

    @Override
    public void addCommand(Command command) {
        genom.add(command);

    }

    @Override
    public void insertCommand(int pos, Command command) {
        genom.add(pos,command);

    }

    @Override
    public Command getCommand(int pos) {
        return genom.get(pos);
    }
}

