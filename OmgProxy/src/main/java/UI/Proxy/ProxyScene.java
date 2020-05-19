package UI.Proxy;

import Proxy.Proxy;
import UI.SceneSheet;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;

public class ProxyScene extends SceneSheet {
    Label selectedLbl;
    Button selectBtn;
    RadioButton turnOn;
    RadioButton turnOff;

    public Button getBack() {
        return back;
    }

    Button back;
    public ProxyScene(double h, double w, Image image) throws IOException {
        super(h, w, image);
        back = new Button("Back");
         selectedLbl = new Label();
        selectedLbl.setTextFill(Color.rgb(255,0,0));
        selectedLbl.setFont(new Font("Arial", 30));

        selectBtn = new Button("Select");

         turnOn = new RadioButton("Turn On");
         turnOff = new RadioButton("Turn OFF");

        ToggleGroup group = new ToggleGroup();
        turnOn.setToggleGroup(group);
        turnOff.setToggleGroup(group);
        RadioButton startSel = turnOff;
        selectedLbl.setText("Server Status: " + startSel.getText());
        selectBtn.setOnAction(event -> {
            RadioButton selection = (RadioButton) group.getSelectedToggle();
            selectedLbl.setText("Server Status: " + selection.getText());
            if(selection.getText().equals(turnOn.getText())){
                try {
                    proxy = new ProxyDealer();
                    proxy.createThread(1080);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                proxy.closeThread();

            }



        });

        FlowPane radioButtons = new FlowPane(Orientation.VERTICAL, 10, 10);
        radioButtons.getChildren().addAll(turnOn, turnOff, selectBtn, selectedLbl);
        radioButtons.setPadding(new Insets(40));
        elements.getChildren().addAll(back,radioButtons);

    }
}
