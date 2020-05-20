package UI.SIgnAuth.Auth;

import UI.SceneSheet;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class MainScene extends SceneSheet {
    public Button getProxyBtn() {
        return proxyBtn;
    }

    public Button getRegBtn() {
        return regBtn;
    }

    Button proxyBtn;
    Button regBtn;

    public Button getSettingsBtn() {
        return settingsBtn;
    }

    Button settingsBtn;


    public MainScene(double h, double w, Image image) {
        super(h, w, image);
        regBtn = new Button("Create new user");
        settingsBtn = new Button("Settings");
        proxyBtn = new Button("Proxy");
        elements.getChildren().addAll(regBtn,settingsBtn,proxyBtn);



    }





}
