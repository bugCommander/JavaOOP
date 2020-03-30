package Simulation.Evolution.Genes;

import Simulation.System.Command;
import Simulation.System.Host;

public class Rotate implements Command {

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void doStuff(Host host) {
        host.setRotation((int) (Math.random() * 8));
        System.out.println("rotation");
        System.out.println(host.getRotation());
        host.changeEnergy(-5);

    }
}

