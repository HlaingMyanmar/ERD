package org.erd.roleoptions.controllers;

import com.jfoenix.controls.JFXCheckBox;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.erd.roleoptions.models.Roles;
import org.erd.roleoptions.services.RoleService;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
public class RolesController implements Initializable {

    @FXML
    private TableColumn<Roles, String> descCol;

    @FXML
    private TextArea desctxt;

    @FXML
    private JFXCheckBox disablecheckbox;

    @FXML
    private Button editbtn;

    @FXML
    private JFXCheckBox enablecheckbox;

    @FXML
    private Button insertbtn;

    @FXML
    private TableColumn<Roles, String> roleCol;

    @FXML
    private TableView<Roles> roletable;

    @FXML
    private TextField roletxt;

    @FXML
    private TableColumn<Roles, String > statusCol;


    private RoleService roleService;

    private Validator validator;

    public RolesController(RoleService roleService, Validator validator) {
        this.roleService = roleService;
        this.validator = validator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableIni();
        getLoadRowData();

        actionEvent();


    }

    private void actionEvent() {

        getConditionCheckbox();

        insertbtn.setOnAction(event -> {


            String role = roletxt.getText();
            String desc  = desctxt.getText();
            byte status = (byte) getConditionCheckbox();

            Roles roles = new Roles(role, desc, status);


            if(testRoleValidate(roles)){

               roleService.addRole(roles);

            }





        });



    }

    private int getConditionCheckbox() {

        AtomicInteger index = new AtomicInteger();


        enablecheckbox.setOnAction(event -> {

            enablecheckbox.setSelected(true);
            disablecheckbox.setSelected(false);
            index.set(1);


        });

        disablecheckbox.setOnAction(event -> {

            enablecheckbox.setSelected(false);
            disablecheckbox.setSelected(true);
            index.set(0);


        });

    return index.get();
    }

    private void getLoadRowData() {

        List<Roles> rolesList = new ArrayList<>();

//        for(Roles roles : roleService.getRoleAllData())
//        {
//
//            if(roles.getIs_active()==1){
//
//                Roles role = new Roles(roles.getRole_name(),roles.getDescription(),"အသုံးပြုနေသည်။");
//
//                rolesList.add(role);
//
//            }
//            else {
//                Roles roles1 =  new Roles(roles.getRole_name(),roles.getDescription(),"အသုံးမပြုတော့ပါ။");
//
//                rolesList.add(roles1);
//            }
//
//
//        }

        rolesList = roleService.getRoleAllData().stream()
                .map(roles -> {

                    String status = (roles.getIs_active() == 1) ? "active" : "inactive";

                    return new Roles(roles.getRole_name(), roles.getDescription(), status);

                })
                .collect(Collectors.toList());

        ObservableList<Roles> data = FXCollections.observableArrayList(rolesList);

        roletable.setItems(data);


    }

    private void tableIni() {


        roleCol.setCellValueFactory(new PropertyValueFactory<>("role_name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("activation"));
    }

    private boolean testRoleValidate(Roles role) {

        Set<ConstraintViolation<Roles>> violations = validator.validate(role);

        if(!violations.isEmpty()){

            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Roles> violation : violations) {
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
}
