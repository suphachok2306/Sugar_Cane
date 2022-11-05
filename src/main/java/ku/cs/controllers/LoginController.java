package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private String currentUsername;

    @FXML private TextField userNameTextField;
    @FXML private PasswordField password;
    @FXML private Label missdata,wrongdata,nodata;

    String role;
    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;


    public LoginController(){
        con = Connect.ConnectDB();
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        missdata.setVisible(false);
        wrongdata.setVisible(false);
        nodata.setVisible(false);
        if(userNameTextField.getText().equals("") || password.getText().equals("")){
            missdata.setVisible(true);
        }else if(CheckUser()){
            System.out.println("gg");
            System.out.println(role);
            if(role.equals("1")){
                try {
                    FXRouter.goTo("owner");
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า owner ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            } else if(role.equals("2")){
                try {
                    currentUsername = userNameTextField.getText();
                    FXRouter.goTo("employee",currentUsername);
                } catch (IOException e) {
                    System.out.println(currentUsername + " TT");
                    System.err.println("ไปที่หน้า employee ไม่ได้");
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
                    nodata.setVisible(false);
                    if (password.getText().equals(rs.getString("pass"))) {
                        role = rs.getString("role_id");
                        return true;
                    } else {
                        wrongdata.setVisible(true);
                        return false;
                    }
                }else{
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