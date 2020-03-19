package Simulation.Evolution;

import Simulation.System.Command;
import Simulation.System.Host;

 public class Executor implements Simulation.System.Executable {
   /*
    Cell data;
    public Executor(Cell host){
        data = host;
    }

    */



    @Override
    public  void execute(Command command, Host host) throws Exception {
        if(!(host instanceof Cell)){
            throw new Exception("host should be a object of Cell");
        }

        Cell data = (Cell) host;
        command.doStuff(data);



    }
}
