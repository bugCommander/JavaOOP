package Simulation.System;

public interface Command  {
    int move_command = 1;
    int heavy_offset = 1;
    int yourType_command = 3;
    int anotherType_command =2;

    void doStuff(Host host);


}
