import Proxy.Proxy;
import UI.*;
import UI.Proxy.ProxyScene;
import UI.SIgnAuth.Auth.AuthScene;
import UI.SIgnAuth.Reg.RegScene;
import UI.SIgnAuth.Users;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;




public class Main extends Application {
    AuthScene authScene;
    MainScene mainScene;
    static ProxyScene proxyScene;
    RegScene regScene;
    ArrayList<Image> images = new ArrayList<>();
    Users userlist = new Users();
    final  double H = 400;
    final  double W = 400;

    public Main() {
    }

    @Override
    public void init() throws Exception {
        super.init();
        userlist.getDataFromFile();

        images.add(new Image("file:src/background/socks.jpg", W, H, false,true));
        ///add reg background;
        images.add(new Image("file:src/background/delaem-igrushku-iz-noska-3.png",W,H,false,true));
        ///add loggin background;
        images.add(new Image("file:src/background/depositphotos_32936495-stock-photo-cute-sock-puppet-on-green.jpg", W, H, false,true));
        ///add proxy background;
        images.add(new Image("file:src/background/sock-puppet.jpg", W, H, false,true));



    }

    public static void main(String[] args) {
        launch(args);
        if(proxyScene.getServerThread().isAlive())
        proxyScene.getServerThread().interrupt();


    }


    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("ProxyServerApp");
        stage.setHeight(H);
        stage.setWidth(W);
        mainScene = new MainScene(stage.getMaxHeight(),stage.getMaxWidth(),images.get(0));
        regScene = new RegScene(stage.getMaxHeight(),stage.getMaxWidth(),images.get(1));
        authScene = new AuthScene(stage.getMaxHeight(),stage.getMaxWidth(),images.get(2));
        proxyScene = new ProxyScene(stage.getMaxHeight(),stage.getMaxWidth(),images.get(3));
        mainScene.initSwitchButton(mainScene.getRegBtn(),stage,regScene.getScene());
        mainScene.initSwitchButton(mainScene.getLogBtn(),stage,authScene.getScene());
        mainScene.initSwitchButton(mainScene.getTryBtn(),stage,proxyScene.getScene());
        regScene.initSwitchButton(regScene.getBack(),stage,mainScene.getScene());
        regScene.InitSigBtn(userlist);
        authScene.initSwitchButton(authScene.getBack(),stage,mainScene.getScene());
        authScene.initLogbtn(userlist,stage,proxyScene.getScene());
        proxyScene.initSwitchButton(proxyScene.getBack(),stage,mainScene.getScene());
        proxyScene.initTurnOnBtn();
        proxyScene.initTurnOffBtn();


        stage.setScene(mainScene.getScene());

        stage.show();
    }
}
