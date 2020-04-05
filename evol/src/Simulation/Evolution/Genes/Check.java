package Simulation.Evolution.Genes;

import Simulation.Evolution.Cells.Cell;
import Simulation.Evolution.Cells.DeadCell;
import Simulation.System.Command;
import Simulation.System.Host;

public class Check implements Command {


    @Override
    public void doStuff(Host host) {
        Host checkingCell = host.neighbours[host.getRotation()];
       /// System.out.println("Check");
        host.setActionPoints(host.getActionPoints() -1);
        if (checkingCell == null) {
            host.offsetPointer(move_command);
            return;
        }
        if (checkingCell instanceof DeadCell) {
            host.offsetPointer(anotherType_command);
            return;
        }
        if (checkingCell instanceof Cell && checkingCell.getGID() == host.getGID()) {
            host.offsetPointer(yourType_command+1);
            return;

        }
        if (checkingCell instanceof Cell) {
            host.offsetPointer(yourType_command);
            return;

        }

    }
}
