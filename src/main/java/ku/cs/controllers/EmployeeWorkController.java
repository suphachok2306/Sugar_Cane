package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.User;
import ku.cs.models.Work;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EmployeeWorkController implements Initializable {

    private Work selectedWork;

    private User currentUser;

    private int i = 1; //ไว้เช็คปุ่ม Send

    @FXML private Label userName;

    @FXML
    private TableView<Work> tableView;

    @FXML
    public TableColumn<Work, String> workName;

    @FXML
    public TableColumn<Work, String> statusName;


    private ArrayList<Work> works;
    private ObservableList<Work> workObservableList;

    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public EmployeeWorkController() {
        con = Connect.ConnectDB();
        showData();
    }
    public void updateData(){
        workName.setCellValueFactory(new PropertyValueFactory<>("WorkName"));
        statusName.setCellValueFactory(new PropertyValueFactory<>("StatusName"));

        showData();
        workObservableList = FXCollections.observableArrayList(works);
        tableView.setItems(workObservableList);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedWork = newValue;
            }
        });
    }

    public void statusCrop() throws SQLException {
        if (selectedWork.getStatusName().equals("Not assign.")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Waiting for the farm owner to assign works.");
            error.show();
        } else if (selectedWork.getStatusName().equals("Assigned.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Wait for check.\" WHERE work_id = \"1\"");
            pst.executeUpdate();
            updateData();
                /*}else if (selectedWork.getStatusName().equals("Wait for check.")) {
                    pst = con.prepareStatement("UPDATE worklist SET status_name = \"Not pass and rework.\" WHERE work_id = \"1\"");
                    pst.executeUpdate();
                    UpdateData();*/
        } else if (selectedWork.getStatusName().equals("Wait for check.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Done.\" WHERE work_id = \"1\"");
            pst.executeUpdate();
            updateData();
        }
    }

    public void statusRestoration() throws SQLException {
        if (selectedWork.getStatusName().equals("Not assign.")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Waiting for the farm owner to assign works.");
            error.show();
        } else if (selectedWork.getStatusName().equals("Assigned.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Wait for check.\" WHERE work_id = \"2\"");
            pst.executeUpdate();
            updateData();
                /*}else if (selectedWork.getStatusName().equals("Wait for check.")) {
                    pst = con.prepareStatement("UPDATE worklist SET status_name = \"Not pass and rework.\" WHERE work_id = \"2\"");
                    pst.executeUpdate();
                    UpdateData();*/
        } else if (selectedWork.getStatusName().equals("Wait for check.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Done.\" WHERE work_id = \"2\"");
            pst.executeUpdate();
            updateData();
        }
    }

    public void statusCaring() throws SQLException {
        if (selectedWork.getStatusName().equals("Not assign.")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Waiting for the farm owner to assign works.");
            error.show();
        } else if (selectedWork.getStatusName().equals("Assigned.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Wait for check.\" WHERE work_id = \"3\"");
            pst.executeUpdate();
            updateData();
                /*}else if (selectedWork.getStatusName().equals("Wait for check.")) {
                    pst = con.prepareStatement("UPDATE work SET status_name = \"Not pass and rework.\" WHERE work_id = \"3\"");
                    pst.executeUpdate();
                    UpdateData();*/
        } else if (selectedWork.getStatusName().equals("Wait for check.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Done.\" WHERE work_id = \"3\"");
            pst.executeUpdate();
            updateData();
        }
    }

    public void statusHarvest() throws SQLException {
        if (selectedWork.getStatusName().equals("Not assign.")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Waiting for the farm owner to assign works.");
            error.show();
        } else if (selectedWork.getStatusName().equals("Assigned.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Wait for check.\" WHERE work_id = \"4\"");
            pst.executeUpdate();
            updateData();
                /*}else if (selectedWork.getStatusName().equals("Wait for check.")) {
                    pst = con.prepareStatement("UPDATE work SET status_name = \"Not pass and rework.\" WHERE work_id = \"4\"");
                    pst.executeUpdate();
                    UpdateData();*/
        } else if (selectedWork.getStatusName().equals("Wait for check.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Done.\" WHERE work_id = \"4\"");
            pst.executeUpdate();
            updateData();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.currentUser = (User) FXRouter.getData();
        //userID.setText("USERNAME : "+ currentUser.getU_name());
        updateData();
    }

    public void showData() {
        //userID.setText("USER_ID : "+ currentUser.getUser_id());

        String sql = "SELECT work_name,status_name FROM work";
        //String sql2 = "SELECT user_id FROM users";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            works = new ArrayList<>();
            while (rs.next()) {
                works.add(new Work(rs.getString("work_name"),rs.getString("status_name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OwnerWorkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void sendWork() throws SQLException {
        if (selectedWork == null){
            Alert error = new Alert(Alert.AlertType.ERROR, " Please select work.");
            error.show();
        }
        else {
            if (selectedWork.getWorkName().equals("Crop")) {
                statusCrop();
                i+=1;
            }

            if (selectedWork.getWorkName().equals("Restoration")) {
                if (i == 2){
                    statusRestoration();
                    i+=1; }
                else {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Do the crop first.");
                    error.show();
                }
            }

            if (selectedWork.getWorkName().equals("Caring")) {
                if (i == 3){
                    statusCaring();
                    i+=1; }
                else {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Do the crop and restoration first.");
                    error.show();
                }
            }

            if (selectedWork.getWorkName().equals("Harvest")) {
                if (i == 4){
                    statusHarvest();}
                else {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Do the crop and restoration and caring first.");
                    error.show();
                }
            }
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