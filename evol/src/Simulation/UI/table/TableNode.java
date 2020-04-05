package Simulation.UI.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableNode {

    private SimpleIntegerProperty GID;
    private SimpleIntegerProperty links;
    ////private SimpleStringProperty genome;
    public  TableNode(int GID, int links){
        this.GID = new SimpleIntegerProperty(GID);
        this.links = new SimpleIntegerProperty(links);
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
