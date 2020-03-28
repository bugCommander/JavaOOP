package Simulation;

import Simulation.Evolution.Cells.Cell;
import Simulation.Evolution.Cells.DeadCell;
import Simulation.Evolution.Executor;
import Simulation.Evolution.Genes.Move;
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
    ArrayList<Host> cells = new ArrayList<>();
    Executor executor = new Executor();


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("dafaq");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);
        GraphicsContext renderer = canvas.getGraphicsContext2D();
        firstCell();
        ///run(renderer);
         AnimationTimer at = new AnimationTimer(){
            @Override
            public void handle(long now) {
                try {
                    update();
                    drawWorld(renderer);
                    draw(renderer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };



        at.start();
        primaryStage.show();


    }

    private void firstCell() {
        System.out.print("SUKKAA");
        cells.add(new Cell(1, 1, 100));
        Cell aux = (Cell) cells.get(0);
        aux.genome.addCommand(new Rotate());
        aux.genome.addCommand(new Move());



    }

    private void drawWorld(GraphicsContext gc) {
       //// gc.setFill(Color.WHITE);
        for (int i = 0; i < W; ++i) {
            for (int j = 0; j < H; ++j) {
                gc.strokeRect(i * RSIZE + OFFSET, j * RSIZE + OFFSET, RSIZE, RSIZE);
            }

        }


    }

    private void update() throws Exception {
        for (Host cell : cells) {
            cell.step(executor);
        }


    }

    private boolean collision(Host a, Host b) {
        return a.getPosX() == b.getPosX() && a.getPosY() == b.getPosY();

    }

    private void CheckCollision() {
        for (int i = 0; i < cells.size(); ++i) {
            for (int j = i + 1; j < cells.size(); ++j) {
                if (collision(cells.get(i), cells.get(j))) {
                    return;////позже
                }


            }

        }

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
        /*
        for(int i = 0; i < W;++i) {
            for (int j = 0; j < H; ++j) {
                cells[i][j] = new Cell();
            }
            System.out.print("\n");
        }

         */


    }
}
