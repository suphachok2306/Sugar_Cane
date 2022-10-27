package controllers;
import com.github.saacsos.FXRouter;
import javafx.scene.control.Alert;
import models.Work;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OwnerWorkController implements Initializable {
    private Work selectedWork;

    @FXML
    private TableView<Work> tableView;

    @FXML
    public TableColumn<Work, String> workName;

    @FXML
    public TableColumn<Work, String> statusWork;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        workName.setCellValueFactory(new PropertyValueFactory<>("WorkName"));
        statusWork.setCellValueFactory(new PropertyValueFactory<>("StatusWork"));
        //add your data to the table here.
        tableView.setItems(works);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedWork = newValue;
            }
        });
    }

    private ObservableList<Work> works = FXCollections.observableArrayList(
            new Work("Crop","Not assigned"),
            new Work("Restoration","Not assigned"),
            new Work("Caring","Not assigned"),
            new Work("Harvest","Not assigned")
    );

    @FXML
    private void confirmWork() {
        if (selectedWork == null){
            Alert error = new Alert(Alert.AlertType.ERROR, " Please select work.");
            error.show();
        }
    }

    @FXML
    private void switchToCheckWork() {
        try {
            if (selectedWork == null){
                Alert error = new Alert(Alert.AlertType.ERROR, " Please select work.");
                error.show();
            }
            else {
                FXRouter.goTo("checkWork", selectedWork);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logoutButton() {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}