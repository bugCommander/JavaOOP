package Simulation;

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
final int W = 32;
final int H = 32;


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle( "dafaq" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        primaryStage.setScene( theScene );

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );
        GraphicsContext renderer = canvas.getGraphicsContext2D();
        //// отрисовка? квадратики fill // strok /// гребаная джава
        init_world(renderer);










        primaryStage.show();




    }
    private  void init_world(GraphicsContext gc) {
        for(int i = 0; i < W; ++i){
            for(int j = 0; j < H;++j){
                gc.strokeRect(i*10+40,j*10+40,40,40);
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
