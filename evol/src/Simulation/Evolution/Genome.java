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
    public void  copy(Genome other) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        ////genom = new ArrayList<>();
        for(Command iterator : other.genom){
            genom.add(iterator.copy());
        }
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

    @Override
    public int size() {
        return genom.size();
    }


}

