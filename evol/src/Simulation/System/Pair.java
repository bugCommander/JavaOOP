package Simulation.System;

public class Pair {
    public int x;
    public int y;
    public void fix(int modX, int modY){
        if(x >= modX){
            x -=modX;
        }else if(x <0){
            x +=modX;
        }
        if(y >= modY){
            y -=modY;
        }else if(y <0){
            y +=modY;
        }

    }

}