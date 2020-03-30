package Simulation.Evolution.Genes;

import Simulation.SimWorld;
import Simulation.System.Command;
import Simulation.System.Host;
import Simulation.System.Type;

import java.io.*;

public class Move implements Command {
    int W, H;


    @Override
    public void doStuff(Host host) {
        W = host.world.getW();
        H = host.world.getH();
        int rotation = host.getRotation();
        int posX = host.getPosX(), posY = host.getPosY();
        int newX = 0, newY = 0;

        switch (rotation) {
            case (0):
                newX = posX;
                newY = posY - 1;
                break;
            case (1):
                newX = posX + 1;
                newY = posY - 1;


                break;
            case (2):
                newX = posX + 1;
                newY = posY;
                break;
            case (3):
                newX = posX + 1;
                newY = posY + 1;
                break;
            case (4):
                newX = posX;
                newY = posY + 1;

                break;
            case (5):
                newX = posX - 1;
                newY = posY + 1;

                break;
            case (6):
                newX = posX - 1;
                newY = posY;

                break;
            case (7):
                newX = posX - 1;
                newY = posY - 1;

                break;

        }
        if (newX < 0) {
            newX += W;
        } else if (newX >= W) {
            newX -= W;
        }

        if (newY < 0) {
            newY += H;
        } else if (newY >= H) {
            newY -= H;
        }
        if (host.world.isEmpty(newX, newY)) {
            host.world.addItem(posX, posY, Type.EMPTY);
            host.setPosX(newX);
            host.setPosY(newY);
            host.world.addItem(host.getPosX(), host.getPosY(), Type.CELL);
        }


        System.out.print(" X = ");
        System.out.print(host.getPosX());
        System.out.print(" Y = ");
        System.out.print(host.getPosY());
        System.out.println();

        host.changeEnergy(-10);


    }
}

