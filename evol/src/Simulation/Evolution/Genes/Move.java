package Simulation.Evolution.Genes;

import Simulation.SimWorld;
import Simulation.System.Command;
import Simulation.System.Host;
import Simulation.System.Type;

import java.io.*;

public class Move implements Command {


    @Override
    public void doStuff(Host host) {

        int rotation = host.getRotation();
        if(host.neighbours[rotation] == null){
            host.setPosX(host.cord[rotation].x);
            host.setPosY(host.cord[rotation].y);
        }



        System.out.print(" X = ");
        System.out.print(host.getPosX());
        System.out.print(" Y = ");
        System.out.print(host.getPosY());
        System.out.println();

        host.changeEnergy(-10);


    }
}

