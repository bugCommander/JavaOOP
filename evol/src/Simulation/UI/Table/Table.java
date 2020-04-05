package Simulation.UI.Table;

import Simulation.Evolution.Groups.GeNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Table {
    boolean deleteFlag = false;

    TableView<TableNode> table;
    ObservableList<TableNode> tableData = FXCollections.observableArrayList(new TableNode(1,1,"23411111"));
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public TableView<TableNode> getTable() {
        return table;
    }
    public void clearEmptystrings(){
        tableData.removeIf(tableDatum -> tableDatum.getLinks() == 0);


        }


    public ObservableList<TableNode> getTableData() {
        return tableData;
    }
    public void changeLinks(int GID,int offset){

        for (TableNode tableDatum : tableData) {
            if (GID == tableDatum.getGID()) {
                tableDatum.setLinks(tableDatum.getLinks() + offset);
                if(tableDatum.getLinks() <=0){
                    tableDatum.setLinks(0);
                    if(deleteFlag) {
                        tableData.remove(tableDatum);
                    }
                }
                return;
            }

        }

    }
    public void addRow(GeNode item){

        tableData.add(new TableNode(item.getGID(),item.getLinks(),item.getGenome().convert()));

    }

    public Table(){
        table = new TableView<>(tableData);
        TableColumn<TableNode, Integer> GIDcol = new TableColumn<>("GID");
        TableColumn<TableNode, Integer> Linkscol = new TableColumn<>("Links");
        TableColumn<TableNode, String> gencol = new TableColumn<>("Genome");

        GIDcol.setCellValueFactory(new PropertyValueFactory<>("GID"));
        Linkscol.setCellValueFactory(new PropertyValueFactory<>("Links"));
        gencol.setCellValueFactory(new PropertyValueFactory<>("Genome"));
        table.getColumns().add(GIDcol);
        table.getColumns().add(Linkscol);
        table.getColumns().add(gencol);
    }


}
