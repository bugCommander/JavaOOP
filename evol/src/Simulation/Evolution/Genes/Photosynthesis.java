package Simulation.Evolution.Genes;

import Simulation.System.Command;
import Simulation.System.Host;

public class Photosynthesis implements Command {



    @Override
    public void doStuff(Host host) {
        host.changeEnergy((int) (Math.random() * 10));
        System.out.println("Photosynthesis");
       /// System.out.println(host.getEnergy());
        host.offsetPointer(heavy_offset);
        host.setActionPoints(0);




    }


}
