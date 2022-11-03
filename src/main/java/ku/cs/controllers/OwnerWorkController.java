package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.Work;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OwnerWorkController implements Initializable {

    private Work selectedWork;

    @FXML
    private TableView<Work> tableView;

    @FXML
    public TableColumn<Work, String> workName;

    @FXML
    public TableColumn<Work, String> statusName;

    @FXML
    public TableColumn<Work, String> dateStart;

    @FXML
    public TableColumn<Work, String> dateDone;

    private int i = 1; //ไว้เช็คปุ่ม Confirm


    private LocalDateTime dateStartWork = LocalDateTime.now();
    //ใส่ไว้ในหน้า check
    /*private LocalDateTime dateWorkDone = LocalDateTime.now();
    String DoneDate = dateWorkDone.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a"));*/


    String startDate = dateStartWork.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a"));

    private ArrayList<Work> works;
    private ObservableList<Work> workObservableList;

    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public OwnerWorkController() {
        con = Connect.ConnectDB();
        showData();
    }
    public void updateData(){
        workName.setCellValueFactory(new PropertyValueFactory<>("WorkName"));
        statusName.setCellValueFactory(new PropertyValueFactory<>("StatusName"));
        dateStart.setCellValueFactory(new PropertyValueFactory<>("DateStart"));
        dateDone.setCellValueFactory(new PropertyValueFactory<>("DateDone"));

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
            pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? WHERE work_id = \"1\"");
            pst.setString(2,startDate);
            pst.setString(1,"Assigned.");
            pst.executeUpdate();
            updateData();
        }
    }

    public void statusRestoration() throws SQLException {
        if (selectedWork.getStatusName().equals("Not assign.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? WHERE work_id = \"2\"");
            pst.setString(2,startDate);
            pst.setString(1,"Assigned.");
            pst.executeUpdate();
            updateData();
        }
    }

    public void statusCaring() throws SQLException {
        if (selectedWork.getStatusName().equals("Not assign.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? WHERE work_id = \"3\"");
            pst.setString(2,startDate);
            pst.setString(1,"Assigned.");
            pst.executeUpdate();
            updateData();
        }
    }

    public void statusHarvest() throws SQLException {
        if (selectedWork.getStatusName().equals("Not assign.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? WHERE work_id = \"4\"");
            pst.setString(2,startDate);
            pst.setString(1,"Assigned.");
            pst.executeUpdate();
            updateData();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateData();
    }

    public void showData() {
        String sql = "SELECT work_name,status_name,date_start,date_done FROM work";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            works = new ArrayList<>();
            while (rs.next()) {
                works.add(new Work(rs.getString("work_name"),rs.getString("status_name")
                        ,rs.getString("date_start"),rs.getString("date_done")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OwnerWorkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void confirmWork() throws SQLException {
        if (selectedWork == null){
            Alert error = new Alert(Alert.AlertType.ERROR, " Please select work.");
            error.show();
        }
        else {
            if (selectedWork.getStatusName().equals("Assigned.")){
                Alert error = new Alert(Alert.AlertType.INFORMATION, "Wait for the work from the employee to send it.");
                error.show();
            }

            if (selectedWork.getWorkName().equals("Crop")) {
                if (i == 1){
                    statusCrop();
                    i+=1;
                }
            }

            if (selectedWork.getWorkName().equals("Restoration")) {
                if (i == 2){
                    statusRestoration();
                    i+=1;
                }
                else if (selectedWork.getStatusName().equals("Not assign.")){
                    Alert error = new Alert(Alert.AlertType.ERROR, "Do the crop first.");
                    error.show();
                }
            }

            if (selectedWork.getWorkName().equals("Caring")) {
                if (i == 3){
                    statusCaring();
                    i+=1;
                }
                else if (selectedWork.getStatusName().equals("Not assign.")){
                    Alert error = new Alert(Alert.AlertType.ERROR, "Do the crop and restoration first.");
                    error.show();
                }
            }

            if (selectedWork.getWorkName().equals("Harvest")) {
                if (i == 4){
                    statusHarvest();
                    i+=1;
                }
                else if (selectedWork.getStatusName().equals("Not assign.")){
                    Alert error = new Alert(Alert.AlertType.ERROR, "Do the crop and restoration and caring first.");
                    error.show();
                }
            }
        }
    }

    @FXML
    private void switchToCheckWork() {
        try {
            if (selectedWork == null){
                Alert error = new Alert(Alert.AlertType.ERROR, " Please select work.");
                error.show();
            }else if (selectedWork.getStatusName().equals("Done.")) {
                Alert error = new Alert(Alert.AlertType.ERROR, "This work already finish.");
                error.show();
            }else if (!selectedWork.getStatusName().equals("Wait for check.")) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Wait for farmer to finish works.");
                error.show();
            } else if (selectedWork.getStatusName().equals("Wait for check.")) {
                FXRouter.goTo("check",selectedWork);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exportSugarCane() throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setContentText("Do you want to export sugar cane ?");
        Optional<ButtonType> result = alert.showAndWait();

        //if(harvestTimes == 4)

        if(result.get() == ButtonType.OK) {

            i = 1;
            System.out.println("ok export" + "i = " + i);

            pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? , date_done = ?");
            pst.setString(2,null);
            pst.setString(3,null);
            pst.setString(1,"Not assign.");
            pst.executeUpdate();
            updateData();
            //FXRouter.goTo("Summary");
        }
        if (result.get() == ButtonType.CANCEL){
            System.out.println("cancel export");
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