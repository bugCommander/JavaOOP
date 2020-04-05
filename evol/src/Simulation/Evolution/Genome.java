package Simulation.Evolution;

import Simulation.Evolution.Genes.*;
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



    public  String convert(){
        StringBuilder string = new StringBuilder();
        for(Command pos: genom) {
            if (pos instanceof Photosynthesis) {
                string.append("1");
                continue;
            }
            if (pos instanceof Rotate) {
                string.append("2");
                continue;
            }
            if (pos instanceof Check){
                string.append("3");
            continue;
        }
            if(pos instanceof Move) {
                string.append("4");
                continue;
            }
            if(pos instanceof Cannibal){
                string.append("5");
                continue;
            }
            if(pos instanceof Attack){
                string.append("6");
                continue;
            }



        }
        return string.toString();
    }


}

