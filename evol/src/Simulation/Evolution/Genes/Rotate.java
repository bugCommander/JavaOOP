package Simulation.Evolution.Genes;

import Simulation.System.Command;
import Simulation.System.Host;

public class Rotate implements Command {



    @Override
    public void doStuff(Host host) {
        host.setRotation((int) (Math.random() * 8));
       /// System.out.println("rotation");
       /// System.out.println(host.getRotation());
        host.changeEnergy(-5);
        host.offsetPointer(move_command);
        host.setActionPoints(host.getActionPoints() -1);



    }
}

