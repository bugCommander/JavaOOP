package Simulation;

import java.util.ArrayList;

public class Cell {
    public int mark;
    public ArrayList<Integer> genome;
    public State state; /// 0 - пусто 1 - жив 2 - мертв
    public int energy;
    public int x,y;
    Cell(){
        state = State.alive;
        energy = 10;
        mark = 0;
    }
    void cellMove(){

    }
    interface commandInterface{
        


    }


}

 enum State  {
    empty,
    dead,
    alive

}

