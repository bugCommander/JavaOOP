package Simulation;

import Simulation.Evolution.Cells.Cell;
import Simulation.Evolution.Cells.DeadCell;
import Simulation.Evolution.Executor;
import Simulation.Evolution.Genes.Cannibal;
import Simulation.Evolution.Genes.Move;
import Simulation.Evolution.Genes.Photosynthesis;
import Simulation.Evolution.Genes.Rotate;
import Simulation.System.Host;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SimWorld extends Application {
    final int W = 16;
    final int H = 16;
    final int OFFSET = 40;
    final int RSIZE = 20;
    int iteration = 0;
    ArrayList<Host> cells = new ArrayList<>();
    Executor executor = new Executor();


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("dafaq");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);
        run(canvas);
        primaryStage.show();


    }

    private void run(Canvas canvas) {
        GraphicsContext renderer = canvas.getGraphicsContext2D();
        CreateCell(8, 8, 100);
        cells.add(new DeadCell(7, 7, 100));
        cells.add(new DeadCell(7, 8, 100));
        cells.add(new DeadCell(8, 7, 100));


        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    update();
                    renderer.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    draw(renderer);
                    drawWorld(renderer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        at.start();
    }


    public void CreateCell(int x, int y, int energy) {
        Cell aux = new Cell(x, y, energy);
        aux.genome.addCommand(new Cannibal());
        aux.genome.addCommand(new Move());
        aux.genome.addCommand(new Rotate());


        cells.add(aux);


    }

    private void drawWorld(GraphicsContext gc) {
        for (int i = 0; i < W; ++i) {
            for (int j = 0; j < H; ++j) {
                gc.strokeRect(i * RSIZE + OFFSET, j * RSIZE + OFFSET, RSIZE, RSIZE);
            }

        }


    }

    private void update() throws Exception {
        ArrayList<Host> deferred = new ArrayList<>();

        for (Host cell : cells) {
            if (cell.getEnergy() <= 0) {
                if (cell instanceof Cell) {
                    deferred.add(new DeadCell(cell.getPosX(), cell.getPosY(), 100));
                }

                cell.setEnable(false);
            } else {
                cell.makeNeighbours(cells, H, W);
                cell.step(executor);

                if (cell.getEnergy() >= 150 && cell instanceof Cell) {
                    cell.changeEnergy(-100);
                    Cell aux = ((Cell) cell).makeChild();
                    if (aux == null) {
                        deferred.add(new DeadCell(cell.getPosX(), cell.getPosY(), 100));
                        cell.setEnable(false);
                    } else {
                        deferred.add(aux);
                    }

                }


            }
        }
        for (int i = 0; i < cells.size(); ++i) {
            if (!cells.get(i).isEnable()) {
                cells.remove(cells.get(i));
            }
        }
        int count = deferred.size();
        for (int i = 0; i < count; ++i) {
            cells.add(deferred.remove(0));
        }
        ++iteration;


    }


    private void draw(GraphicsContext gc) {
        for (Host cell : cells) {
            if (!cell.isEnable()) {
                gc.setFill(Color.WHITE);
            } else if (cell instanceof Cell) {
                gc.setFill(Color.GREEN);
            } else if (cell instanceof DeadCell) {
                gc.setFill(Color.GREY);
            }
            gc.fillRect(cell.getPosX() * RSIZE + OFFSET, cell.getPosY() * RSIZE + OFFSET, RSIZE, RSIZE);

        }
    }


    public static void main(String[] args) {

        launch(args);


    }
}
