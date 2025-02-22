package org.erd.rolepermissionsoptions.controllers;

import com.jfoenix.controls.JFXComboBox;
import jakarta.validation.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.erd.permissionsoptions.models.Permissions;
import org.erd.permissionsoptions.services.PermissionsService;
import org.erd.roleoptions.models.Roles;
import org.erd.roleoptions.services.RoleService;
import org.erd.rolepermissionsoptions.models.RolePermissions;
import org.erd.rolepermissionsoptions.services.RoleperService;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class RoleperControllers implements Initializable {
    @FXML
    private Button insertbtn;

    @FXML
    private TableColumn<Permissions,String> perCol;

    @FXML
    private TableView<Permissions> permissiontable;

    @FXML
    private Button removebtn;

    @FXML
    private JFXComboBox<String> rolelistCmboBox;


    @FXML
    private TableColumn<RolePermissions,String> roleperCol;

    @FXML
    private TableView<RolePermissions> rolepermissiontable;

    private final RoleperService roleperService;

    private final Validator validator;

    private final RoleService roleService;

    private final PermissionsService permissionsService;

    public RoleperControllers(RoleperService roleperService, Validator validator, RoleService roleService, PermissionsService permissionsService) {
        this.roleperService = roleperService;
        this.validator = validator;
        this.roleService = roleService;
        this.permissionsService = permissionsService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getRolesData();
        setComboboxRoleData();

        rolelistCmboBox.setOnKeyReleased(event -> {


            selectedRolesgetPermissionName(role)



        });

    }

    private ObservableList<String> selectedRolesgetPermissionName(String roleName){



        int id = getRoleID(roleName);


        return FXCollections.observableArrayList(roleperService.getRolePerAllData()
                .stream()
                .filter(rp -> rp.getRoles().getRole_id() == id)
                .map(RolePermissions::getPermission)
                .map(Permissions::getPermission_name).toString()).sorted();


    }

    private void setComboboxRoleData(){

        List<String> roleName = getRolesData().stream()
                .map(Roles::getRole_name)
                .toList();

        ObservableList<String> roles = FXCollections.observableArrayList(roleName);

        rolelistCmboBox.setItems(roles);



    }

    private List<Roles> getRolesData(){

        return  roleService.getRoleAllData();
    }

    private int getRoleID(String roleName){


        return getRolesData().stream()
                .filter(roles -> roles.getRole_name().equals(roleName))
                .map(Roles::getRole_id).findFirst().orElse(-1);

    }
}



