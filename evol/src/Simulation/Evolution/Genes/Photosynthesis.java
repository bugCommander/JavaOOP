package Simulation.Evolution.Genes;

import Simulation.System.Command;
import Simulation.System.Host;

public class Photosynthesis implements Command {


    @Override
    public void doStuff(Host host) {
        host.changeEnergy((int) (Math.random() * 10));
        System.out.println("energy increase");


    }
}
