package UI.Proxy;

import Proxy.Proxy;
import UI.SceneSheet;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.io.IOException;

public class ProxyScene extends SceneSheet {
    Button turnOn;
    Button turnOff;
    Thread serverThread;


    public Button getBack() {
        return back;
    }

    Button back;

    public ProxyScene(double h, double w, Image image) throws IOException {
        super(h, w, image);
        back = new Button("Back");
        turnOn = new Button("Turn On");
        turnOff = new Button("Turn OFF");
        elements.getChildren().addAll(back,turnOn,turnOff);
        elements.setPadding((new Insets(10, 10, 10, 10)));


    }


   public void initTurnOnBtn( ) {
        turnOn.setOnAction(e -> {
            try {

                    serverThread = new Thread(new Proxy(1080), "serverThread");
                    serverThread.start();

            } catch (IOException ex) {
                ex.printStackTrace();
            }


        });


    }

   public void initTurnOffBtn(){
        turnOff.setOnAction(e -> {

                System.out.println("close  thread");
                serverThread.interrupt();




        });

    }
}