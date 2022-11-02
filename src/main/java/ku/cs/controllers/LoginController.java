package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ku.cs.models.User;

import java.awt.*;
import java.io.IOException;

public class LoginController {

    private User currentUser;

    @FXML private TextField userNameTextField;
    @FXML private TextField passwordField;


    @FXML
    public void handleLoginButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("employee",currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //}
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