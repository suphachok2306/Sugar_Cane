package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private User currentUser;

    @FXML private TextField userNameTextField;
    @FXML private TextField passwordField;
    @FXML private Label missdata,wrongdata,nodata;

    String role;
    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;


    public LoginController(){
        con = Connect.ConnectDB();
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent){
        missdata.setVisible(false);
        wrongdata.setVisible(false);
        nodata.setVisible(false);
        if(userNameTextField.getText().equals("") || passwordField.getText().equals("")){
            missdata.setVisible(true);
        }else if(CheckUser()){
            System.out.println("gg");
            System.out.println(role);
            if(role.equals("1")){
                try {
                    FXRouter.goTo("owner");
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า register ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            } else if(role.equals("2")){
                try {
                    FXRouter.goTo("employee");
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า register ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        }else{
            System.out.println("no");
        }
    }

    public boolean CheckUser() {
        try {
            String SQL = "SELECT * FROM users";
            rs = con.prepareStatement(SQL).executeQuery();
            while (rs.next()) {
                if (userNameTextField.getText().equals(rs.getString("username"))) {
                    if (passwordField.getText().equals(rs.getString("pass"))) {
                        role = rs.getString("role_id");
                        return true;
                    }
                    else {
                        wrongdata.setVisible(true);
                        return false;
                    }
                } else if (!userNameTextField.getText().equals(rs.getString("username"))) {
                    nodata.setVisible(true);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}