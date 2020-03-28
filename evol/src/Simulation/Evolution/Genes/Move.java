package Simulation.Evolution.Genes;

import Simulation.System.Command;
import Simulation.System.Host;

public class Move implements Command {
    final int W = 16;
    final int H = 16;


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


        System.out.print(" X = ");
        System.out.print(host.getPosX());
        System.out.print(" Y = ");
       System.out.print(host.getPosY());
       System.out.println();
       if(host.getPosX() < 0 ){
           host.setPosX(host.getPosX()+W);
       }
        if(host.getPosY() < 0 ){
            host.setPosY(host.getPosY()+H);
        }
        if(host.getPosX()>=W){
            host.setPosX(host.getPosX() - W);
        }
        if(host.getPosY()>=H){
            host.setPosY(host.getPosY() - H);
        }



    }
}