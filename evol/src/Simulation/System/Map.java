package Simulation.System;

public interface Map {
    boolean isEmpty(int x,int y);
    Type type(int x,int y);
    void addItem(int x,int y,Type item);

}
