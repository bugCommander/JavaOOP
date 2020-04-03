package Simulation.Evolution.Groups;

import Simulation.Evolution.Cells.Cell;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Gelist {
    ////private int size;
    private ArrayList<GeNode> NodeList = new ArrayList<>();
    ////private Boolean[] GIDarray;

/*
    public Gelist(int h, int w) {
        size = h * w;
        NodeList = new ArrayList<>();
        ////GIDarray = new Boolean[size];
        for (int i = 0; i < size; ++i) {
            GIDarray[i] = false;
        }

    }



    int findFreeGID() throws Exception {
        int freeGID;
        for (int i = 0; i < size; ++i) {
            if (!GIDarray[i]) {
                freeGID = i;
                return freeGID;
            }
        }
        throw new Exception("FULL GID LIST");

    }

 */

    public Color findColor(int GID) throws Exception {
        for (GeNode node : NodeList) {
            if (node.getGID() == GID) {
                return node.getColor();
            }
        }
        throw new Exception("CAN'T FIND COLOR");


    }



    public void inclink(int GID) throws Exception {
        for (GeNode node : NodeList) {
            if (node.getGID() == GID) {
                node.changeLinks(+1);
            }
        }



    }

    public void CreateGroup(Cell obj) {
        int newGiD = NodeList.size();
        int scalar = (newGiD + 51) % 255;
        GeNode aux = new GeNode(obj.genome, Color.rgb(scalar, 255 - scalar, 255 - scalar), newGiD);
        obj.setGID(newGiD);
        aux.changeLinks(+1);
        NodeList.add(aux);
    }

    public void Deletelink(int GID) {
        for (GeNode node : NodeList) {
            if (node.getGID() == GID) {
                node.changeLinks(-1);

                return;
            }
        }

    }





}






