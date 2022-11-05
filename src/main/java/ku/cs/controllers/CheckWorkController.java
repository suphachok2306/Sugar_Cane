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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckWorkController {
    @FXML private Label workName,sDate,hTime,hTimeLabel;


    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public Work selectedWork;

    public CheckWorkController() {
        con = Connect.ConnectDB();
    }

    private LocalDateTime dateWorkDone = LocalDateTime.now();
    String DoneDate = dateWorkDone.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a "));

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
                        int h = Integer.parseInt(rs.getString("harvested_times"));
                        h += 1;
                        hTimeLabel.setText(String.valueOf(h));
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
        alert.setContentText("Do you want to finish this assignment?");
        Optional<ButtonType> result = alert.showAndWait();

        try {
            String SQL = "SELECT * FROM work";
            rs = con.prepareStatement(SQL).executeQuery();
            if (result.get() == ButtonType.OK) {
                while (rs.next()) {
                    if (selectedWork.getWorkName().equals(rs.getString("work_name"))) {
                        pst = con.prepareStatement("UPDATE work SET status_name = ? , date_done = ? , harvested_times = ? WHERE work_name = '"+selectedWork.getWorkName()+"'");
                        //pst = con.prepareStatement("UPDATE work SET status_name = ? , date_done = ? , harvested_times = ? WHERE work_name = ?");
                        if (!rs.getString("work_name").equals("Harvest")) {
                            pst.setString(2, DoneDate);
                            pst.setString(1, "Done.");
                            pst.setString(3,rs.getString("harvested_times"));
//                            pst.setString(4, selectedWork.getWorkName());
                            pst.executeUpdate();
                            try {
                                FXRouter.goTo("owner");
                            } catch (IOException e) {
                                System.err.println("ไปที่หน้า register ไม่ได้");
                                System.err.println("ให้ตรวจสอบการกำหนด route");
                            }
                            break;
                        } else if (rs.getString("work_name").equals("Harvest")) {
                            if (rs.getString("harvested_times").equals("0")) {
                                pst.setString(3, String.valueOf(1));
                                pst.setString(2, DoneDate);
                                pst.setString(1,"Done.");
                                pst.executeUpdate();
                            } else if (rs.getString("harvested_times").equals("1")) {
                                pst.setString(3, String.valueOf(2));
                                pst.setString(2, DoneDate);
                                pst.setString(1,"Done.");
                                pst.executeUpdate();
                            } else if (rs.getString("harvested_times").equals("2")) {
                                pst.setString(3, String.valueOf(3));
                                pst.setString(2, DoneDate);
                                pst.setString(1,"Done.");
                                pst.executeUpdate();
                            } else if (rs.getString("harvested_times").equals("3")) {
                                pst.setString(3, String.valueOf(4));
                                pst.setString(2, DoneDate);
                                pst.setString(1, "Done.");
                                pst.executeUpdate();
                            }
                            try {
                                FXRouter.goTo("owner");
                            } catch (IOException e) {
                                System.err.println("ไปที่หน้า register ไม่ได้");
                                System.err.println("ให้ตรวจสอบการกำหนด route");
                            }
                        }
                    }
                }
            }else if (result.get() == ButtonType.CANCEL) {
                System.out.println("cancel export");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    @FXML
    public void backButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("owner");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}