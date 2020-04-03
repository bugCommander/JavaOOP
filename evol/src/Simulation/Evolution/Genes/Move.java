package Simulation.Evolution.Genes;

import Simulation.System.Command;
import Simulation.System.Host;

public class Move implements Command {


    @Override
    public void doStuff(Host host) {

        if(host.neighbours[ host.getRotation()] == null){
            host.setPosX(host.cord[ host.getRotation()].x);
            host.setPosY(host.cord[ host.getRotation()].y);
        }




        System.out.println("Move");

        host.changeEnergy(-10);
        host.offsetPointer(heavy_offset);
        host.setActionPoints(0);




    }
}

