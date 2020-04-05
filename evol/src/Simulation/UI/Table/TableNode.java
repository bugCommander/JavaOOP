package Simulation.UI.Table;

import Simulation.Evolution.Genome;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableNode {

    private SimpleIntegerProperty GID;
    private SimpleIntegerProperty links;
    private SimpleStringProperty genome;


    public String getGenome() {
        return genome.get();
    }

    public SimpleStringProperty genomeProperty() {
        return genome;
    }

    public void setGenome(String genome) {
        this.genome.set(genome);
    }

    public  TableNode(int GID, int links, String genome){
        this.GID = new SimpleIntegerProperty(GID);
        this.links = new SimpleIntegerProperty(links);
        this.genome = new SimpleStringProperty(genome);
    }

    public int getGID() {
        return GID.get();
    }

    public SimpleIntegerProperty GIDProperty() {
        return GID;
    }

    public int getLinks() {
        return links.get();
    }

    public SimpleIntegerProperty linksProperty() {
        return links;
    }



    public void setGID(int GID) {
        this.GID.set(GID);
    }



    public void setLinks(int links) {
        this.links.set(links);
    }



}
