package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    private int harvestedTimes = 0;

    private int countDoneForExport = 1;


    private LocalDateTime dateStartWork = LocalDateTime.now();
    //ใส่ไว้ในหน้า check
    /*private LocalDateTime dateWorkDone = LocalDateTime.now();
    String DoneDate = dateWorkDone.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a"));*/


    String startDate = dateStartWork.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a "));

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

        //times.setText((harvestedTimes) + " / 4");
    }

    public void showData() {
        String sql = "SELECT work_name,status_name,date_start,date_done,harvested_times FROM work";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            works = new ArrayList<>();
            while (rs.next()) {
                //////////harvestedTimes
                if (rs.getString("harvested_times").equals("1")){
                    harvestedTimes = 1;
                }
                else if (rs.getString("harvested_times").equals("2")){
                    harvestedTimes = 2;
                }
                else if (rs.getString("harvested_times").equals("3")){
                    harvestedTimes = 3;
                }
                else if (rs.getString("harvested_times").equals("4")){
                    harvestedTimes = 4;
                }else if (rs.getString("harvested_times").equals("0")){
                    harvestedTimes = 0;
                }

                ////////countDoneForExport

                if (rs.getString("work_name").equals("Crop , Repair")){
                    if (rs.getString("status_name").equals("Done.")){
                        countDoneForExport = 1;}

                }else if (rs.getString("work_name").equals("Weed")){
                    if (rs.getString("status_name").equals("Done.")){
                        countDoneForExport = 2;}
                }else if (rs.getString("work_name").equals("Fertilize")){
                    if (rs.getString("status_name").equals("Done.")){
                        countDoneForExport = 3;}
                }else if (rs.getString("work_name").equals("Harvest")){
                    if (rs.getString("status_name").equals("Done.")){
                        countDoneForExport = 4;}

                if (!rs.getString("status_name").equals("Done.")){
                        countDoneForExport = 0;}
                }


                /////////////i
                if (rs.getString("work_name").equals("Crop , Repair")){
                    if (!rs.getString("status_name").equals("Not assign.")){
                        i = 2;}

                }else if (rs.getString("work_name").equals("Weed")){
                    if (!rs.getString("status_name").equals("Not assign.")){
                        i = 3;}
                }else if (rs.getString("work_name").equals("Fertilize")){
                    if (!rs.getString("status_name").equals("Not assign.")){
                        i = 4;}
                }else if (rs.getString("work_name").equals("Harvest")){
                    if (!rs.getString("status_name").equals("Not assign.")){
                        }
                }

                //////////////tableView

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

            if (selectedWork.getWorkName().equals("Crop , Repair")) {
                if (i == 1){
                    statusCrop();
                    //i = 2;
                }
            }

            if (selectedWork.getWorkName().equals("Weed")) {
                if (i == 2){
                    statusRestoration();
                    //i = 3;
                }
                else if (selectedWork.getStatusName().equals("Not assign.")){
                    System.out.println(i);
                    Alert error = new Alert(Alert.AlertType.ERROR, "Do the Crop , Repair first.");
                    error.show();
                }
            }

            if (selectedWork.getWorkName().equals("Fertilize")) {
                if (i == 3){
                    statusCaring();
                    //i = 4;
                }
                else if (selectedWork.getStatusName().equals("Not assign.")){
                    System.out.println(i);
                    Alert error = new Alert(Alert.AlertType.ERROR, "Do the Crop , Repair and Weed first.");
                    error.show();
                }
            }

            if (selectedWork.getWorkName().equals("Harvest")) {
                if (i == 4){
                    statusHarvest();
                }
                else if (selectedWork.getStatusName().equals("Not assign.")){
                    System.out.println(i);
                    System.out.println(i + "  dfgdf");
                    Alert error = new Alert(Alert.AlertType.ERROR, "Do the Crop , Repair and Weed and Fertilize first.");
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
        if (countDoneForExport == 4){
            System.out.println(countDoneForExport + "countexport");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setContentText("Do you want to export sugar cane " + (harvestedTimes) + " /" + " 4 ?");
        System.out.println(harvestedTimes);
        Optional<ButtonType> result = alert.showAndWait();

        if (harvestedTimes == 1){
            if(result.get() == ButtonType.OK) {
                i = 1;
                System.out.println("ok export" + "i = " + i);
                System.out.println(harvestedTimes + " test1");

                pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? , date_done = ?  WHERE work_id = \"2\" OR work_id = \"3\" OR work_id = \"4\"");
                //pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? , date_done = ? ");
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
        }else if (harvestedTimes == 2 || harvestedTimes == 3){
            if(result.get() == ButtonType.OK) {
                i = 1;
                System.out.println("ok export" + "i = " + i);

                System.out.println(harvestedTimes + " test2");

                //pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? , date_done = ?");
                pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? , date_done = ?  WHERE work_id = \"2\" OR work_id = \"3\" OR work_id = \"4\"");
                pst.setString(2,null);
                pst.setString(3,null);
                pst.setString(1,"Not assign.");
                pst.executeUpdate();
                updateData();
            }
            if (result.get() == ButtonType.CANCEL){
                System.out.println("cancel export");
            }
        }

        else if (harvestedTimes == 4){
            if(result.get() == ButtonType.OK) {
                i = 1;
                System.out.println("ok export" + "i = " + i);

                System.out.println(harvestedTimes + " test2");
                //harvestedTimes = 0;

                //pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? , date_done = ?");
                pst = con.prepareStatement("UPDATE work SET status_name = ? , date_start = ? , date_done = ?, harvested_times = \"0\" ");
                pst.setString(2,null);
                pst.setString(3,null);
                pst.setString(1,"Not assign.");

                pst.executeUpdate();
                updateData();

            }
            if (result.get() == ButtonType.CANCEL){
                System.out.println("cancel export");
            }
        }
        }
        else {
            Alert alertExport = new Alert(Alert.AlertType.ERROR);
            alertExport.setTitle("");
            alertExport.setContentText("Complete all works first.");
            alertExport.show();

            System.out.println(countDoneForExport + "test3");
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