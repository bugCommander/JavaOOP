package Simulation;

import Simulation.Evolution.Cell;
import Simulation.Evolution.State;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class SimWorld extends Application {
final int W = 16;
final int H = 16;
final int OFFSET = 40;
final int RSIZE = 20;
Cell[][] world = new Cell[H][W];




    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle( "dafaq" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        primaryStage.setScene( theScene );

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );
        GraphicsContext renderer = canvas.getGraphicsContext2D();
        init_world(renderer);
        drawe_shapes(renderer);


        primaryStage.show();




    }
    private  void init_world(GraphicsContext gc) {
        for(int i = 0; i < W; ++i){
            for(int j = 0; j < H;++j){
                gc.strokeRect(i*RSIZE+OFFSET,j*RSIZE+OFFSET,RSIZE,RSIZE);
            }
            gc.setFill(Color.RED);
            gc.fillRect(OFFSET,OFFSET,RSIZE,RSIZE);
        }


        }
        private void drawe_shapes(GraphicsContext gc){
            for(int i = 0;i < W;++i){
                for(int j = 0;j < H; ++j){
                    if(world[i][j] == null ||world[i][j].state == State.EMPTY) {
                        gc.setFill(Color.WHITE);
                        gc.fillRect(i * RSIZE + OFFSET, j * RSIZE + OFFSET, RSIZE, RSIZE);
                    }
                    else if(world[i][j].state == State.ALIVE){
                        gc.setFill(Color.GREEN);
                        gc.fillRect(i * RSIZE + OFFSET, j * RSIZE + OFFSET, RSIZE, RSIZE);

                    }
                    else if(world[i][j].state == State.DEAD){
                        gc.setFill(Color.GREY);
                        gc.fillRect(i * RSIZE + OFFSET, j * RSIZE + OFFSET, RSIZE,RSIZE);

                    }
                    }
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
