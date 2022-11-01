package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.IOException;

public class LoginController {
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordField;
    @FXML private ChoiceBox<String> roleChoiceBox;

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) throws IOException{
        if(userNameTextField.getText().equals("10") || passwordField.getText().equals("10")){
            return;
        }
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