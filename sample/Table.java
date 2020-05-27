package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Table {
    private TableView<DotItem> table;

    public Table(ObservableList<DotItem> list){
        table = new TableView<>(list);
        TableColumn<DotItem, Double> x = new TableColumn<>("x");
        TableColumn<DotItem, Double> y = new TableColumn<>("y");
        x.setCellValueFactory(new PropertyValueFactory<>("x"));
        y.setCellValueFactory(new PropertyValueFactory<>("y"));
        x.setMinWidth(200);
        y.setMinWidth(200);
        table.getColumns().setAll(x,y);
    }

    public TableView<DotItem> getTable() {
        return table;
    }

    public void addItem(DotItem item)
    {
        table.getItems().add(item);
    }
    public void clear(){
        table.getItems().clear();
    }
    public void setItems(ObservableList<DotItem> list){
        table.getItems().clear();
        table.getItems().setAll(list);
    }
}
