package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import ku.cs.models.Work;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CheckWorkController {
    @FXML private Label workName,sDate,hTime,hTimeLabel;

    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public Work selectedWork;

    public CheckWorkController() {
        con = Connect.ConnectDB();
    }

    public void initialize() {
        this.selectedWork = (Work) FXRouter.getData();
        hTime.setVisible(false);
        hTimeLabel.setVisible(false);
        try {
            String SQL = "SELECT * FROM work";
            rs = con.prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                if (selectedWork.getWorkName().equals(rs.getString("work_name"))) {
                    workName.setText(rs.getString("work_name"));
                    sDate.setText(rs.getString("date_start"));
                    if(selectedWork.getWorkName().equals("Harvest")){
                        hTime.setVisible(true);
                        hTimeLabel.setVisible(true);
                        hTimeLabel.setText(rs.getString("harvested_times"));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void notPassButton(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setContentText("Do you want to fix this assignment?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Not pass and rework.\" WHERE work_name = '"+selectedWork.getWorkName()+"'");
            pst.executeUpdate();
            try {
                FXRouter.goTo("owner");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า register ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }

        if (result.get() == ButtonType.CANCEL){
            System.out.println("cancel export");
        }
    }
    @FXML
    public void passButton(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setContentText("Do you want to fix this assignment?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            pst = con.prepareStatement("UPDATE work SET status_name = \"Done.\" WHERE work_name = '"+selectedWork.getWorkName()+"'");
            pst.executeUpdate();
            try {
                FXRouter.goTo("owner");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า register ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
        if (result.get() == ButtonType.CANCEL){
            System.out.println("cancel export");
        }
    }
}
