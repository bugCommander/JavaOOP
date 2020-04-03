package Simulation.System;

public interface CommandContainer {

     void addCommand(Command command);
     void swapCommand(int pos, Command command);
     Command getCommand(int pos);
     int size();


}
