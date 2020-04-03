package Simulation.Evolution;

import Simulation.System.Command;
import Simulation.System.CommandContainer;

import java.io.IOException;
import java.util.ArrayList;

public class Genome implements CommandContainer {
    ArrayList<Command> genom;
    public Genome(){
        genom = new ArrayList<>();

    }
    public void  addAllCommands(Genome other) {
        genom.addAll(other.genom);
    }

    @Override
    public void addCommand(Command command) {
        genom.add(command);

    }

    @Override
    public void swapCommand(int pos, Command command) {
        genom.set(pos,command);

    }

    @Override
    public Command getCommand(int pos) {
        return genom.get(pos);
    }

    @Override
    public int size() {
        return genom.size();
    }


}

