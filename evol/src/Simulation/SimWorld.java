package Simulation;

import Simulation.Evolution.Cells.Cell;
import Simulation.Evolution.Cells.DeadCell;
import Simulation.Evolution.Executor;
import Simulation.Evolution.Groups.GeNode;
import Simulation.Evolution.Groups.Gelist;
import Simulation.Evolution.Genes.*;
import Simulation.System.Command;
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
    ArrayList<Command> availableGenes = new ArrayList<>();
    Gelist groups = new Gelist();


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("dafaq");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);
        run(canvas);
        primaryStage.show();


    }

    private void run(Canvas canvas) throws Exception {
        availableGenes.add(new Photosynthesis());
        availableGenes.add(new Rotate());
        availableGenes.add(new Check());
        availableGenes.add(new Move());
        availableGenes.add(new Cannibal());
        availableGenes.add(new Attack());
        CreateCell(8, 8, 150);


        GraphicsContext renderer = canvas.getGraphicsContext2D();


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


    public void CreateCell(int x, int y, int energy)  {
        Cell aux = new Cell(x, y, energy);
        aux.genome.addCommand(availableGenes.get(1));
        aux.genome.addCommand(availableGenes.get(2));
        aux.genome.addCommand(availableGenes.get(3));
        aux.genome.addCommand(availableGenes.get(0));
        aux.genome.addCommand(availableGenes.get(0));
        aux.genome.addCommand(availableGenes.get(0));
        aux.genome.addCommand(availableGenes.get(0));
        aux.genome.addCommand(availableGenes.get(0));
        groups.CreateGroup(aux);
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
        ++iteration;
        System.out.print("ХОД :");
        System.out.println(iteration);


        for (Host cell : cells) {
            ////System.out.print("особь");
            if (cell.getEnergy() <= 0) {
                if (cell instanceof Cell) {
                    groups.Deletelink(cell.getGID());
                    deferred.add(new DeadCell(cell.getPosX(), cell.getPosY(), 100));
                    groups.Deletelink(cell.getGID());

                }

                cell.setEnable(false);
            } else {
                cell.makeNeighbours(cells, H, W);
                cell.step(executor);

                if (cell.getEnergy() >= 150 && cell instanceof Cell) {
                    System.out.println("размножение");
                    cell.changeEnergy(-100);
                    Cell aux = ((Cell) cell).makeChild();
                    if (aux == null) {
                        groups.Deletelink(cell.getGID());

                        deferred.add(new DeadCell(cell.getPosX(), cell.getPosY(), 100));
                        cell.setEnable(false);


                    } else {
                        deferred.add(aux);
                        int bones = (int) (Math.random() * 20);
                        if (bones > 13) {
                            int pos = (int) (Math.random() * aux.genome.size());
                            int gene = (int) (Math.random() * availableGenes.size());
                            System.out.println(pos);
                            System.out.println(gene);
                            aux.genome.swapCommand(pos, availableGenes.get(gene));
                            groups.CreateGroup(aux);
                        } else {
                            aux.setGID(cell.getGID());
                            groups.inclink(cell.getGID());
                        }


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


    }


    private void draw(GraphicsContext gc) throws Exception {
        for (Host cell : cells) {
            if (!cell.isEnable()) {
                gc.setFill(Color.WHITE);
            } else if (cell instanceof Cell) {
                gc.setFill(groups.findColor(cell.getGID()));
                ///  gc.setFill(Color.GREEN);

            } else if (cell instanceof DeadCell) {
                gc.setFill(Color.BLACK);
            }
            gc.fillRect(cell.getPosX() * RSIZE + OFFSET, cell.getPosY() * RSIZE + OFFSET, RSIZE, RSIZE);

        }
    }


    public static void main(String[] args) {

        launch(args);


    }
}
