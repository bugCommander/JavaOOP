package Simulation.Evolution.Cells;

import Simulation.Evolution.Executor;
import Simulation.Evolution.Genome;
import Simulation.System.Executable;
import Simulation.System.Host;
import Simulation.System.Pair;

import java.util.ArrayList;

public class Cell extends Host {
    public Genome genome;




    @Override
    public void makeNeighbours(ArrayList<Host> cells, int hSize, int wSize) {
        neighbours = new Host[8];
            for(int i = 0;i <8;++i){
                neighbours[i] = null;
            }
     cord = fill_cord();
            for(int i = 0;i<8;++i){
                cord[i].fix(wSize,hSize);
            }



        for(Host iterator: cells){
            for(int i = 0; i < 8;++i) {
                if (iterator.getPosX() == cord[i].x & iterator.getPosY() == cord[i].y) {
                    neighbours[i] = iterator;
                }
            }


        }

    }

    public Cell(int x, int y, int hp) {
        super(x, y, hp);
        genome = new Genome();
    }
    @Override
   public void offsetPointer(int offset){
        pointer+=offset;
        int mod = genome.size();
        if(pointer >= mod){
            pointer %=mod;
        }
    }

  public   Cell makeChild() {
        for(int i = 0; i < 8;++i){
            if(neighbours[i]== null){
               /// assert neighbours[i] != null;
                Cell aux = new Cell(cord[i].x,cord[i].y,100);
                aux.genome.addAllCommands(this.genome);

                neighbours[i] = aux;
                return aux;

            }

        }


        return null;
    }



    public void step(Executable executor) throws Exception {
        if (!(executor instanceof Executor)) {
            throw new Exception("executor should be Exec");
        }

            executor.execute(genome.getCommand(this.pointer), this);

        if(getActionPoints()>0){
            executor.execute(genome.getCommand(this.pointer), this);
        }
        setActionPoints(2);
       /// ++pointer;
       /// if(pointer >=genome.size()) {
           /// pointer = 0;
      ///  }

           /// Thread.sleep(50);



    }



    private  Pair[] fill_cord(){
        Pair []cord = new Pair[8];
        int posX = this.getPosX();
        int posY = this.getPosY();
        for(int i = 0; i < 8;++i){
            cord[i] = new Pair();
        }
        cord[0].x = posX;
        cord[0].y = posY-1;
        cord[1].x = posX+1;
        cord[1].y = posY- 1;
        cord[2].x = posX+1;
        cord[2].y = posY;
        cord[3].x = posX+1;
        cord[3].y = posY+1;
        cord[4].x = posX;
        cord[4].y = posY+1;
        cord[5].x = posX-1;
        cord[5].y = posY+1;
        cord[6].x = posX -1;
        cord[6].y = posY;
        cord[7].x = posX -1;
        cord[7].y = posY -1;

        return cord;
    }
}




