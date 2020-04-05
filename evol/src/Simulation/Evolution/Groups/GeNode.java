package Simulation.Evolution.Groups;

import Simulation.Evolution.Genome;
import javafx.scene.paint.Color;

public class GeNode {
    public GeNode() {

    }



    public Genome getGenome() {
        return genome;
    }

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }

    public void setLinks(int links) {
        this.links = links;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public int getLinks() {
        return links;
    }

    public Color getColor() {
        return color;
    }

    private Genome genome;
    private Color color;
    private int GID;/// индекс группы особей
    private int links;///количество особей
    public void changeLinks(int offset){
        links+=offset;
    }
    public GeNode(Genome genome,Color color,int GID){
        this.genome = genome;
        this.color = color;
        this.GID = GID;
    }
    public GeNode(GeNode shallow){
        this.genome = shallow.genome;
        this.color = shallow.color;
        this.links = shallow.links;
        this.GID = shallow.GID;
    }

}
