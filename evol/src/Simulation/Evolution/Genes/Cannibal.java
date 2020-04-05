package Simulation.Evolution.Genes;

import Simulation.Evolution.Cells.DeadCell;
import Simulation.System.Command;
import Simulation.System.Host;

public class Cannibal implements Command {
    @Override
    public void doStuff(Host host) {
        Host target = host.neighbours[host.getRotation()];
            if(target instanceof DeadCell){
                host.changeEnergy((30));
                target.changeEnergy(-target.getEnergy());
               /// System.out.print("MEEET");

            }

        host.changeEnergy(-10);

       /// System.out.println(" Cannibal");
        host.offsetPointer(heavy_offset);
        host.setActionPoints(0);




    }
}
