package Simulation.UI.table;

import Simulation.Evolution.Groups.GeNode;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Table {
    public TableView<TableNode> getTable() {
        return table;
    }

    public ObservableList<TableNode> getTableData() {
        return tableData;
    }
    public void changeLinks(int GID,int offset){

        for (TableNode tableDatum : tableData) {
            if (GID == tableDatum.getGID()) {
                tableDatum.setLinks(tableDatum.getLinks() + offset);
                if(tableDatum.getLinks() <0){
                    tableDatum.setLinks(0);
                }
                return;
            }

        }

    }
    public void addRow(GeNode item){

        tableData.add(new TableNode(item.getGID(),item.getLinks()));

    }

    TableView<TableNode> table;
    ObservableList<TableNode> tableData = FXCollections.observableArrayList(new TableNode(1,1));
    public Table(){
        table = new TableView<>(tableData);
        TableColumn<TableNode, Integer> GIDcol = new TableColumn<>("GID");
        TableColumn<TableNode, Integer> Linkscol = new TableColumn<>("Links");
        GIDcol.setCellValueFactory(new PropertyValueFactory<>("GID"));
        Linkscol.setCellValueFactory(new PropertyValueFactory<>("Links"));
        table.getColumns().add(GIDcol);
        table.getColumns().add(Linkscol);
        table.setLayoutX(500);
    }


}
