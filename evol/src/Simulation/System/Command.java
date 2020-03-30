package Simulation.System;

import java.io.IOException;

public interface Command  {
    void doStuff(Host host);
    Command copy( ) throws IOException, ClassNotFoundException;


}
