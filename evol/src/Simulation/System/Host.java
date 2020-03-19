package Simulation.System;


import Simulation.Evolution.Executor;
import Simulation.Evolution.Genome;

public abstract class Host {
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    int posX;

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return posY;
    }

    int posY;
    int energy;
    int rotation;
    public void changeEnergy(int offset){
        energy += offset;

    }
    public void setRotation(int rot){
        rotation = rot;
    }
    public int getRotation(){
        return rotation;
    }




    public Host(int x, int y, int hp) {
        posX = x;
        posY = y;
        energy = hp;
        rotation = 0;

    }
    public Host(){};

    public abstract void step();
}




