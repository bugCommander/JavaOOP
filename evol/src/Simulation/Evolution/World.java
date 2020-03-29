package Simulation.Evolution;

import Simulation.System.Map;
import Simulation.System.Type;

public class World implements Map {
    Type[][] matrix;

    public int getW() {
        return W;
    }

    int W;

    public int getH() {
        return H;
    }

    int H;
    @Override
    public boolean isEmpty(int x, int y) {
        return matrix[x][y] == Type.EMPTY;
    }

    @Override
    public Type type(int x, int y) {
        return matrix[x][y];
    }

    @Override
    public void addItem(int x, int y,Type item) {
        matrix[x][y] = item;


    }

    public World(int W,int H){
        this.H = H;
        this.W = W;
        matrix = new Type[W][H];
        for(int i = 0; i <W; i++){
            for(int j = 0; j < H; ++j){
                matrix[i][j] = Type.EMPTY;
            }
        }
    }
}
