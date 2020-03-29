package Simulation.System;


import Simulation.Evolution.Executor;
import Simulation.Evolution.Genome;
import Simulation.Evolution.World;

public abstract class Host {
    public World world = null;

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    boolean enable;

    public boolean isEnable() {
        return enable;
    }

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

    public int getEnergy() {
        return energy;
    }

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
        enable = true;
        posX = x;
        posY = y;
        energy = hp;
        rotation = 0;

    }
    public Host(){};

    public abstract void step(Executable executor) throws Exception;
}




