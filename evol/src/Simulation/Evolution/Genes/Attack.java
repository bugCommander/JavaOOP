package Simulation.Evolution.Genes;

import Simulation.Evolution.Cells.Cell;
import Simulation.System.Command;
import Simulation.System.Host;

public class Attack implements Command {


    @Override
    public void doStuff(Host host) {
        Host target = host.neighbours[host.getRotation()];
        if(target instanceof Cell){
            target.changeEnergy(-target.getEnergy());
           /// System.out.print("someone is dead");
        }

        host.changeEnergy(-10);

        ///System.out.println("Attack");
        ///System.out.println(host.getEnergy());
        host.offsetPointer(heavy_offset);
        host.setActionPoints(0);



    }
}
