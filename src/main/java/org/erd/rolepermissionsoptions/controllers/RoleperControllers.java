package org.erd.rolepermissionsoptions.controllers;

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
    private ComboBox<String> rolelistCmboBox;

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
    }

    private void setComboboxRoleData(){

        List<String> roleName = getRolesData().stream()
                .map(Roles::getRole_name)
                .toList();


        ObservableList<String> roles = FXCollections.observableArrayList(roleName);

        System.out.println(roles.size());


        rolelistCmboBox.setItems(roles);

    }

    private List<Roles> getRolesData(){

        System.out.println(roleService.getRoleAllData().size());

        return  roleService.getRoleAllData();



    }
}



