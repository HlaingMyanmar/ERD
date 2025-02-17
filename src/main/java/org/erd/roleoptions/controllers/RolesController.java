package org.erd.roleoptions.controllers;

import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.erd.roleoptions.models.Roles;
import org.erd.roleoptions.services.RoleService;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

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
    private TableColumn<Roles, Byte> statusCol;


    private RoleService roleService;


    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableIni();
        getLoadRowData();


    }

    private void getLoadRowData() {

        ObservableList<Roles> data = FXCollections.observableArrayList(roleService.getRoleAllData());


    }

    private void tableIni() {


        roleCol.setCellValueFactory(new PropertyValueFactory<>("role_name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("is_active"));
    }
}
