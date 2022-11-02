package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordField;
    @FXML private ChoiceBox<String> roleChoiceBox;
    @FXML private Label missdata,wrongdata,nodata;

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
//            try {
//                FXRouter.goTo("owner");
//            } catch (IOException e) {
//                System.err.println("ไปที่หน้า owner ไม่ได้");
//                System.err.println("ให้ตรวจสอบการกำหนด route");
//            }
            System.out.println("gg");
            try {
                FXRouter.goTo("register");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า register ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }else{
            System.out.println("no");
        }
    }

    public boolean CheckUser(){
        try {
            String SQL = "SELECT * FROM users";
            rs = con.prepareStatement(SQL).executeQuery();
            while (rs.next()){
                if(userNameTextField.getText().equals(rs.getString("username"))) {
                    if(passwordField.getText().equals(rs.getString("pass")))
                        return true;
                    else {
                        wrongdata.setVisible(true);
                        return false;
                    }
                }else if(!userNameTextField.getText().equals(rs.getString("username"))){
                    nodata.setVisible(true);
                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
//        try {
//            String SQL = "SELECT * FROM users WHERE username = '"+userNameTextField.getText()+"'";
//            pst = con.prepareStatement(SQL);
//            rs = pst.executeQuery();
//            if(passwordField.getText().equals(rs.getString("pass"))){
//                return true;
//            }
////            while (rs.next()){
////                if(userNameTextField.getText().equals(rs.getString("username"))) {
////                    if(passwordField.getText().equals(rs.getString("pass"))){
////                        return true;
////                    }else{
////                        wrongdata.setVisible(true);
////                    }
////                }else{
////                    nodata.setVisible(true);
////                }
////            }
//        } catch (SQLException ex){
//            ex.printStackTrace();
//        }
//        return false;
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