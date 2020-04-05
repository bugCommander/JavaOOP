package Simulation;

import Simulation.Evolution.Cells.Cell;
import Simulation.Evolution.Cells.DeadCell;
import Simulation.Evolution.Executor;
import Simulation.Evolution.Genes.*;
import Simulation.Evolution.Groups.Gelist;
import Simulation.System.Command;
import Simulation.System.Host;
import Simulation.UI.table.Table;
import Simulation.UI.table.TableNode;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SimWorld extends Application {
    final int W = 16;
    final int H = 16;
    final int OFFSET = 40;
    final int RSIZE = 20;
    int iteration = 0;
    long pause = 0;
    ArrayList<Host> cells = new ArrayList<>();
    Executor executor = new Executor();
    ArrayList<Command> availableGenes = new ArrayList<>();
    Gelist groups = new Gelist();
    Table table = new Table();


    @Override
    public void init() throws Exception {
        super.init();
        availableGenes.add(new Photosynthesis());
        availableGenes.add(new Rotate());
        availableGenes.add(new Check());
        availableGenes.add(new Move());
        availableGenes.add(new Cannibal());
        availableGenes.add(new Attack());
        CreateCell(W / 2, H / 2, 150);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("dafaq");
        primaryStage.setHeight(600);
        primaryStage.setWidth(1024);

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);


        FlowPane Simbar = new FlowPane();
        javafx.scene.control.Button btnStart = new javafx.scene.control.Button("Start");
        javafx.scene.control.Button btnPause = new javafx.scene.control.Button("Pause");
        javafx.scene.control.Button btnRestart = new javafx.scene.control.Button("Restart");
        Simbar.getChildren().add(btnStart);
        Simbar.getChildren().add(btnPause);
        Simbar.getChildren().add(btnRestart);
        Simbar.setLayoutX(OFFSET);
        root.getChildren().add(Simbar);


        javafx.scene.control.Slider delay = new javafx.scene.control.Slider(0, 25, 0);
        delay.setShowTickMarks(true);
        delay.setShowTickLabels(true);
        delay.setBlockIncrement(2.0);
        delay.setMajorTickUnit(5.0);
        delay.setMinorTickCount(4);


        FlowPane settings = new FlowPane();
        settings.setAlignment(Pos.BOTTOM_LEFT);
        settings.setLayoutY(400);
        settings.setLayoutX(OFFSET);
        javafx.scene.control.Label Ladelay = new javafx.scene.control.Label("delay");
        settings.getChildren().addAll(delay, Ladelay);
        root.getChildren().add(settings);






        root.getChildren().add(table.getTable());



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


        init();
        btnStart.setOnAction(e -> {
            at.start();

        });
        btnPause.setOnAction(e -> {
            at.stop();

        });
        btnRestart.setOnAction(e -> {
            cells.clear();
            groups.clear();
            CreateCell(W / 2, H / 2, 150);
            ///table = new Table();
            table.getTableData().clear();
            iteration = 0;
            at.start();

        });
        delay.valueProperty().addListener((changed, oldValue, newValue) -> pause = newValue.intValue());
        ;


        primaryStage.show();


    }



    public void CreateCell(int x, int y, int energy) {
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
            long now = System.currentTimeMillis();
            long timer = System.currentTimeMillis();
            while (timer < now + pause) {
                timer = System.currentTimeMillis();
            }

            if (cell.getEnergy() <= 0) {
                if (cell instanceof Cell) {
                    groups.Deletelink(cell.getGID());
                    table.changeLinks(cell.getGID(),-1);


                    deferred.add(new DeadCell(cell.getPosX(), cell.getPosY(), 100));

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
                        table.changeLinks(cell.getGID(),-1);


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
                            table.addRow(groups.get(aux.getGID()));
                        } else {
                            aux.setGID(cell.getGID());
                            groups.inclink(cell.getGID());
                            table.changeLinks(cell.getGID(),+1);
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
