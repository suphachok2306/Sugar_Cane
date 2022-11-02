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

    private String currentUsername;

    private int i = 1;
    private int p = 2;
    private int po = 3;
    private int por = 4;


    //private int i = 1; //ไว้เช็คปุ่ม Send

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
        }else if (selectedWork.getStatusName().equals("Not pass and rework.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Wait for check.\" WHERE work_id = \"1\"");
            pst.executeUpdate();
            updateData();
        }else if (selectedWork.getStatusName().equals("Done.")) {
            p = 2;
            //i = 2;
            System.out.println(p + " test");
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
        }else if (selectedWork.getStatusName().equals("Not pass and rework.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Wait for check.\" WHERE work_id = \"2\"");
            pst.executeUpdate();
            updateData();
        }else if (selectedWork.getStatusName().equals("Done.")) {
            //i = 3;
            po = 3;
            System.out.println(po + " test");
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
        }else if (selectedWork.getStatusName().equals("Not pass and rework.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Wait for check.\" WHERE work_id = \"3\"");
            pst.executeUpdate();
            updateData();
        }else if (selectedWork.getStatusName().equals("Done.")) {
            //i = 4;
            por = 4;
            System.out.println(por + " test");
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
        }else if (selectedWork.getStatusName().equals("Not pass and rework.")) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Wait for check.\" WHERE work_id = \"4\"");
            pst.executeUpdate();
            updateData();
        }else if (selectedWork.getStatusName().equals("Done.")) {
            //i = 0;
            p = 0;
            po = 0;
            por = 0;
            System.out.println(p + " test");
            System.out.println(po + " test2");
            System.out.println(por + " test3");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.currentUsername = (String) FXRouter.getData();
        userName.setText("USERNAME : "+ currentUsername);
        updateData();
    }

    public void showData() {

        String sql = "SELECT work_name,status_name FROM work";
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
        if (selectedWork == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, " Please select work.");
            error.show();
        } else {
            if (selectedWork.getWorkName().equals("Crop")) {
                statusCrop();
                if (selectedWork.getStatusName().equals("Not pass and rework.")) {
                    statusCrop();
                } else if (selectedWork.getStatusName().equals("Wait for check.")) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Waiting for the farm owner to check works.");
                    error.show();
                } else if (selectedWork.getStatusName().equals("Done.")) {
                    //i = 2;
                    //System.out.println(i);
                }
            }

            if (selectedWork.getWorkName().equals("Restoration")) {
                //if (i == 2) {
                if (p == 2) {
                    //System.out.println(i);
                    statusRestoration();
                }
                if (i == 1){
                    //System.out.println(i);
                    if (selectedWork.getStatusName().equals("Assigned.")) {
                        Alert error = new Alert(Alert.AlertType.ERROR, "Do the crop first.");
                        error.show();
                    }
                }
                if (selectedWork.getStatusName().equals("Not pass and rework.")) {
                    statusRestoration();
                } else if (selectedWork.getStatusName().equals("Wait for check.")) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Waiting for the farm owner to check works.");
                    error.show();
                } else if (selectedWork.getStatusName().equals("Done.")) {
                    //i = 3;
                    //System.out.println(i);
                }
            }

            if (selectedWork.getWorkName().equals("Caring")) {
                //if (i == 3) {
                if (po == 3){
                    statusCaring();
                }
                if (i == 1 || i == 2){
                    //System.out.println(i);
                    if (selectedWork.getStatusName().equals("Assigned.")) {
                        Alert error = new Alert(Alert.AlertType.ERROR, "Do the crop and restoration first.");
                        error.show();
                    }
                }
                if (selectedWork.getStatusName().equals("Not pass and rework.")) {
                    statusCaring();
                } else if (selectedWork.getStatusName().equals("Wait for check.")) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Waiting for the farm owner to check works.");
                    error.show();
                } else if (selectedWork.getStatusName().equals("Done.")) {
                    //i = 4;
                    //System.out.println(i);
                }
            }

            if (selectedWork.getWorkName().equals("Harvest")) {
                //if (i == 4) {
                if (por == 4){
                    statusHarvest();
                }
                if (i == 1 || i == 2 || i == 3 || i == 0){
                    //System.out.println(i);
                    if (selectedWork.getStatusName().equals("Assigned.")) {
                        Alert error = new Alert(Alert.AlertType.ERROR, "Do the crop and restoration and caring first.");
                        error.show();
                    }
                }
                if (selectedWork.getStatusName().equals("Not pass and rework.")) {
                    statusHarvest();
                } else if (selectedWork.getStatusName().equals("Wait for check.")) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Waiting for the farm owner to check works.");
                    error.show();
                } else if (selectedWork.getStatusName().equals("Done.")) {
                    //i = 0;
                    //System.out.println(i);
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