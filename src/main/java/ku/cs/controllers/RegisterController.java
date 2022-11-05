package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;
import java.io.IOException;

public class RegisterController {
    @FXML private TextField nameTextField;
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneTextField;
    @FXML private Label usernameLabel,emailLabel,warningLabel;

    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;


    public RegisterController(){
        con = Connect.ConnectDB();
    }



    @FXML
    public void handleRegisterButton(ActionEvent event) {
        int count = 0;

        if(Checkname(nameTextField.getText())){
            count += 1;
        } else{
            warningLabel.setVisible(true);
        }

        if(CheckUserName(userNameTextField.getText())){
            if(CheckSameUser()){
                usernameLabel.setVisible(true);
            } else{
                usernameLabel.setVisible(false);
                count += 1;
            }
        } else{
            warningLabel.setVisible(true);
        }

        if(Checkpassword(passwordTextField.getText())){
            count += 1;
        } else{
            warningLabel.setVisible(true);
        }

        if(Checkemail(emailTextField.getText())){
            if(CheckSameEmail()){
                emailLabel.setVisible(true);
            } else{
                emailLabel.setVisible(false);
                count += 1;
            }
        } else{
            warningLabel.setVisible(true);
        }

        if(Checkphone(userNameTextField.getText())){
            count += 1;
        } else{
            warningLabel.setVisible(true);
        }

        if (count == 5) {
            try{
                String sql = "INSERT INTO users(name,username,pass,email,tel,role_id)values(?,?,?,?,?,?)";
                pst = con.prepareStatement(sql);
                pst.setString(1,nameTextField.getText());
                pst.setString(2,userNameTextField.getText());
                pst.setString(3,passwordTextField.getText());
                pst.setString(4,emailTextField.getText());
                pst.setString(5,phoneTextField.getText());
                pst.setString(6,"2");
                pst.executeUpdate();
                Alert error = new Alert(Alert.AlertType.ERROR, " Registration complete.");
                error.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            warningLabel.setVisible(false);
            clearAllText();
        }
    }

    public boolean Checkname(String name){

        if(name.contains("0") || name.contains("1") || name.contains("2") || name.contains("3") || name.contains("4") || name.contains("5") || name.contains("6") || name.contains("7") || name.contains("8") || name.contains("9")){
            return false;
        }
        if (name.equals("")){
            return false;
        }
        return true;
    }

    public boolean CheckUserName(String userName){

        if(userName.contains(" ")){
            return false;
        }
        if (userName.equals("")){
            return false;
        }
        return true;
    }

    public boolean Checkpassword(String password){
        if(password.equals("")){
            return false;
        }
        if(password.contains(" ")){
            return false;
        }
        return true;
    }

    public boolean Checkemail(String email){
        if(email.equals("")){
            return false;
        }
        if (email.contains(" ")){
            return false;
        }
        return true;
    }

    public boolean Checkphone(String phone){
        if(phone.equals("")){
            return false;
        }
        if (phone.contains(" ")){
            return false;
        }
        return true;
    }

    public boolean CheckSameUser(){
        try {
            String SQL = "SELECT * FROM users";
            rs = con.createStatement().executeQuery(SQL);
            while (rs.next()){
                if(userNameTextField.getText().equals(rs.getString("username"))) {
                    return true;
                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public boolean CheckSameEmail(){
        try {
            String SQL = "SELECT * FROM users";
            rs = con.createStatement().executeQuery(SQL);
            while (rs.next()){
                if(emailTextField.getText().equals(rs.getString("email"))) {
                    return true;
                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public void clearAllText(){
        nameTextField.setText("");userNameTextField.setText("");passwordTextField.setText("");emailTextField.setText("");phoneTextField.setText("");
    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
