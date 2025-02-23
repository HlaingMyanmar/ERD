package org.erd.useroptions.controllers;

import jakarta.validation.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.erd.useroptions.models.Users;
import org.erd.useroptions.services.UserServices;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class UserDashboardControllers implements Initializable {

    @FXML
    private TableColumn<Users, Date> DateCol;

    @FXML
    private Button addbtn;

    @FXML
    private Button editbtn;

    @FXML
    private TableColumn<Users, Integer> idCol;

    @FXML
    private TableColumn<Users, String> nameCol;

    @FXML
    private TableColumn<Users, String> passCol;

    @FXML
    private Button removebtn;

    @FXML
    private Button setrolebtn;

    @FXML
    private TableColumn<Users, String> statusCol;

    @FXML
    private TableView<Users> userdbtable;


    private final UserServices userServices;

    private final Validator validator;

    private List<Users> usersList;


    public UserDashboardControllers(UserServices userServices, Validator validator) {
        this.userServices = userServices;
        this.validator = validator;
        usersList = new ArrayList<>();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getLodRowData();

        tableInill();


    }

    private void tableInill() {

        idCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        //DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("activation"));
    }

    private void getLodRowData() {

        ObservableList<Users> usersObservableList = FXCollections.observableArrayList(userServices.getAllData());



        ObservableList<Users> userList = FXCollections.observableArrayList(userServices.getAllData().stream()
                .map(users -> {

                    String status = (users.getIs_active()==1)?"active":"inactive";
                    return new Users(users.getUser_id(),users.getUser_name(),users.getPassword(),users.getCreated_at(),status);
                })
                .collect(Collectors.toList()));


        userdbtable.setItems(userList);



    }
}
