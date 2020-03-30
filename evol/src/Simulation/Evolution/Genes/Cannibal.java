package Simulation.Evolution.Genes;

import Simulation.Evolution.Cells.Cell;
import Simulation.Evolution.Cells.DeadCell;
import Simulation.System.Command;
import Simulation.System.Host;

public class Cannibal implements Command {
    @Override
    public void doStuff(Host host) {
        for(int i = 0;i < 8; ++i){
            if(host.neighbours[i] instanceof DeadCell){
                host.changeEnergy((30));
                host.neighbours[i].changeEnergy(-host.neighbours[i].getEnergy());
                System.out.println("MEEET");

                break;
            }
        }
        host.changeEnergy(-10);

        System.out.println("energy was changed by cannibal");
        System.out.println(host.getEnergy());


    }
}
