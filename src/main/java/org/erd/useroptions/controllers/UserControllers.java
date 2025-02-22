package org.erd.useroptions.controllers;

import com.jfoenix.controls.JFXCheckBox;
import jakarta.validation.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.erd.useroptions.models.Users;
import org.erd.useroptions.services.UserServices;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class UserControllers implements Initializable {

    @FXML
    private JFXCheckBox disablecheckbox;

    @FXML
    private JFXCheckBox enablecheckbox;

    @FXML
    private Button insertbtn;

    @FXML
    private PasswordField passtxt;

    @FXML
    private TextField usertxt;



    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
