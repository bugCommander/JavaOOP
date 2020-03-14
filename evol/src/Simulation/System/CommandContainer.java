package Simulation.System;

public interface CommandContainer {

     void  apply(Host host, Executor executor);
     void addCommand(Command command);
}
