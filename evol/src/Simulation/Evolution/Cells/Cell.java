package Simulation.Evolution.Cells;

import Simulation.Evolution.Executor;
import Simulation.Evolution.Genome;
import Simulation.Evolution.World;
import Simulation.System.Executable;
import Simulation.System.Host;
import Simulation.System.Type;

public class Cell extends Host {
    public Genome genome;


    public Cell(int x, int y, int hp) {
        super(x, y, hp);
        genome = new Genome();
    }

  public   Cell makeChild() {
        int W = world.getW();
        int H = world.getH();
        int posX = this.getPosX();
        int posY = this.getPosY();
        int newX = 0;
        int newY = 0;
        for (int i = 0; i < 8; ++i) {
            switch (i) {
                case (0):
                    newX = posX;
                    newY = posY-1;

                    break;
                case (1):
                    newX = posX+1;
                    newY = posY -1;


                    break;
                case (2):
                    newX = posX+1;
                    newY = posY;
                    break;
                case (3):
                    newX = posX+1;
                    newY = posY+1;
                    break;
                case (4):
                    newX = posX;
                    newY = posY+1;

                    break;
                case (5):
                    newX = posX-1;
                    newY = posY +1;

                    break;
                case (6):
                    newX = posX-1;
                    newY = posY;

                    break;
                case (7):
                    newX = posX-1;
                    newY = posY-1;

                    break;


            }
            if(newX<0){
                newX +=W;
            }else if(newX >=W){
                newX -=W;
            }

            if(newY < 0){
                newY +=H;
            }
            else if(newY >=H){
                newY -=H;
            }
            if(world.isEmpty(newX,newY)){
                Cell aux = new Cell(newX,newY,100);
                aux.genome = this.genome.copy(this.genome);
                return aux;


            }
        }

        return null;
    }


    public void step(Executable executor) throws Exception {
        if (!(executor instanceof Executor)) {
            throw new Exception("executor should be Exec");
        }
        for (int i = 0; i < genome.size(); ++i) {
            executor.execute(genome.getCommand(i), this);
            if (this.getEnergy() <= 0) { ///осторожно
                break;

            }
            Thread.sleep(100);
        }


    }
}


