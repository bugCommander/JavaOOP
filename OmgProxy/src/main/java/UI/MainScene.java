package UI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainScene extends SceneSheet {
    public Button getRegBtn() {
        return regBtn;
    }

    public Button getLogBtn() {
        return logBtn;
    }

    public Button getTryBtn() {
        return tryBtn;
    }

   private Button regBtn;
   private Button logBtn;
    private Button tryBtn;

    public MainScene(double h, double w, Image image) {
        super(h, w, image);
        regBtn = new Button("Sign In");
        logBtn = new Button("Log In");
        tryBtn = new Button("Try");
        elements.getChildren().addAll(regBtn,logBtn,tryBtn);
    }




}
