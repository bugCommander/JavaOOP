package Simulation.Evolution.Genes;

import Simulation.System.Command;
import Simulation.System.Host;

public class Move implements Command {


    @Override
    public void doStuff(Host host) {
        int rotation = host.getRotation();
        switch (rotation) {
            case (0):
                host.setPosY(host.getPosY()-1);


                break;
            case (1):
                host.setPosX(host.getPosX()+1);
                host.setPosY(host.getPosY() -1);

                break;
            case (2):
                host.setPosX(host.getPosX()+1);
                break;
            case (3):
                host.setPosX(host.getPosX()+1);
                host.setPosY(host.getPosY() +1);

                break;
            case (4):
                host.setPosY(host.getPosY() +1);
                break;
            case (5):
                host.setPosX(host.getPosX()-1);
                host.setPosY(host.getPosY() +1);
                break;
            case (6):
                host.setPosX(host.getPosX()-1);

                break;
            case (7):
                host.setPosX(host.getPosX()-1);
                host.setPosY(host.getPosY() -1);
                break;

        }


    }
}