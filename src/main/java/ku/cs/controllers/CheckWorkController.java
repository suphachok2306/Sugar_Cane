package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import ku.cs.models.Work;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        //hTime.setVisible(false);
        try {
            String SQL = "SELECT * FROM work";
            rs = con.prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                if (selectedWork.getWorkName().equals(rs.getString("work_name"))) {
                    workName.setText(selectedWork.getWorkName());
                    sDate.setText(selectedWork.getDateStart());
                    if(selectedWork.getWorkName().equals("Harvest")){
                        hTime.setVisible(true);
                        hTimeLabel.setText(rs.getString("harvested_times"));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void notPassButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("owner");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void passButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("owner");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
