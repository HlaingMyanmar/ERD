package org.erd.useroptions.controllers;

import com.jfoenix.controls.JFXCheckBox;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;
import org.erd.roleoptions.models.Roles;
import org.erd.useroptions.models.Users;
import org.erd.useroptions.services.UserServices;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

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

    private final UserServices userServices;
    private final Validator validator;


    public UserControllers(UserServices userServices, Validator validator) {
        this.userServices = userServices;
        this.validator = validator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getConditionCheckbox();

        insertbtn.setOnAction(event -> {


            String name = usertxt.getText();
            String pass = passtxt.getText();
            byte status = (byte) getConditionCheckbox();


            Users user;

            if(!getConditionCheckboxNoSelection()){
                user = new Users(name, pass);
            }
            else{
                user = new Users(name, pass, status);
            }

            if (testRoleValidate(user)){
                boolean isRoleAdded = userServices.addUser(user);
                if (isRoleAdded) {
                    showInformationDialog("အသုံးပြုသူ", "အောင်မြင်သည်။", "အသုံးပြုသူထည့်ခြင်း အောင်မြင်သည်။");

                    setClear();
                } else {
                    showErrorDialog("အသုံးပြုသူ", "မအောင်မြင်ပါ။", "အသုံးပြုသူထည့်ခြင်း မအောင်မြင်ပါ။");
                }
            } else {
                showErrorDialog("အသုံးပြုသူ", "မအောင်မြင်ပါ။", "အသုံးပြုသူအချက်အလက် မမှန်ကန်ပါ။");
            }







        });




    }

    private void setClear() {

        usertxt.setText("");
        passtxt.setText("");
        enablecheckbox.setSelected(false);
        disablecheckbox.setSelected(false);
    }

    private boolean testRoleValidate(Users user) {

        Set<ConstraintViolation<Users>> violations = validator.validate(user);

        if(!violations.isEmpty()){

            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Users> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n\n");

            }


            showErrorDialog("ဒေတာဘေစ် အမှား","ဒေတာ လိုအပ်ချက်များရှိနေသည်။",errorMessages.toString());

            return false;


        }
        else {

            return true;

        }



    }

    private void showErrorDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private void showInformationDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private boolean showConfirmDialog(String title, String header, String content) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;


    }


    private boolean getConditionCheckboxNoSelection() {


        return enablecheckbox.isSelected() || disablecheckbox.isSelected();


    }

    private int getConditionCheckbox() {


        enablecheckbox.setOnAction(event -> {



            enablecheckbox.setSelected(true);
            disablecheckbox.setSelected(false);



        });

        disablecheckbox.setOnAction(event -> {

            enablecheckbox.setSelected(false);
            disablecheckbox.setSelected(true);


        });


        return enablecheckbox.isSelected()  ? 1 : 0;



    }
}
