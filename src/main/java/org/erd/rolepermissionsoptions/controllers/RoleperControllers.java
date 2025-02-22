package org.erd.rolepermissionsoptions.controllers;

import com.jfoenix.controls.JFXComboBox;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import org.erd.permissionsoptions.models.Permissions;
import org.erd.permissionsoptions.services.PermissionsService;
import org.erd.roleoptions.models.Roles;
import org.erd.roleoptions.services.RoleService;
import org.erd.rolepermissionsoptions.models.RolePermissions;
import org.erd.rolepermissionsoptions.services.RoleperService;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RoleperControllers implements Initializable {
    private final Roles roles;
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
    private TableView<Permissions> rolepermissiontable;

    private final RoleperService roleperService;

    private final Validator validator;

    private final RoleService roleService;

    private final PermissionsService permissionsService;

    public RoleperControllers(RoleperService roleperService, Validator validator, RoleService roleService, PermissionsService permissionsService, Roles roles) {
        this.roleperService = roleperService;
        this.validator = validator;
        this.roleService = roleService;
        this.permissionsService = permissionsService;
        this.roles = roles;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {




        getRolesData();
        setComboboxRoleData();

        permissintableIni();

        rolelistCmboBox.setOnKeyPressed(event -> {

            if(event.getCode() == KeyCode.ENTER) {

                permissiontable.getItems().clear();

                getAllDataLoad(rolelistCmboBox.getValue());

            }


        });

        insertbtn.setOnAction(event -> {


           Permissions selectedIndex =  rolepermissiontable.getSelectionModel().getSelectedItem();

           Permissions permission =  getPermission(selectedIndex.getPermission_name());

           Roles role = getRole(rolelistCmboBox.getValue());




           roleperService.addRolePer(new RolePermissions(role, permission));

           showInformationDialog("ခွင့်ပြုချက်များ", "အောင်မြင်သည်", "ခွင့်ပြုချက်အောင်မြင်သည်။");

            getAllDataLoad(rolelistCmboBox.getValue());


        });


        removebtn.setOnAction(event -> {

            Permissions selectedIndex =  permissiontable.getSelectionModel().getSelectedItem();

            Permissions permission =  getPermission(selectedIndex.getPermission_name());

            Roles role = getRole(rolelistCmboBox.getValue());






            boolean b = roleperService.deleteRolePer(new RolePermissions(getRolePermissionID(role.getRole_id(),permission.getPermission_id()),role, permission));


            if(b){

                showInformationDialog("ခွင့်ပြုချက်များ", "အောင်မြင်သည်", "ဖျက်ပြီးပါပြီ");

                getAllDataLoad(rolelistCmboBox.getValue());

            }





        });

    }

    private void getAllDataLoad(String role){

        permissiontable.setItems(selectedRolesgetPermissionName(role));

        rolepermissiontable.setItems(selectedRoleswithoutPermissionName(role));

    }

    private void permissintableIni() {

        perCol.setCellValueFactory(new PropertyValueFactory<>("permission_name"));
        roleperCol.setCellValueFactory(new PropertyValueFactory<>("permission_name"));


    }

    private ObservableList<Permissions> selectedRolesgetPermissionName(String roleName){



        int id = getRoleID(roleName);


        return FXCollections.observableArrayList(roleperService.getRolePerAllData()
                .stream()
                .filter(rp -> rp.getRoles().getRole_id() == id)
                .map(RolePermissions::getPermission).toList());



    }

    private void setComboboxRoleData(){

        List<String> roleName = getRolesData().stream()
                .map(Roles::getRole_name)
                .toList();

        ObservableList<String> roles = FXCollections.observableArrayList(roleName);

        rolelistCmboBox.setItems(roles);



    }

    private ObservableList<Permissions>selectedRoleswithoutPermissionName(String roleName){

        int id = getRoleID(roleName);

        /*
        Get Permission for the role
         */
        List<Permissions> rolePermission = roleperService.getRolePerAllData()
                .stream()
                .filter(rp -> rp.getRoles().getRole_id() == id)
                .map(RolePermissions::getPermission).toList();

        /*
        Get All Permission
         */
        List<Permissions> allPermission = permissionsService.getAllData();


        /*
            Get Permission id from rolePermission for the role
         */
        Set<Integer> rolePermissionIds = rolePermission.stream()
                .map(Permissions::getPermission_id).collect(Collectors.toSet());


        // Filter all permission to exclude those the role already has
        List<Permissions> withoutPermissionForRole = allPermission.stream()
                        .filter(permissions -> !rolePermissionIds.contains(permissions.getPermission_id()))
                        .collect(Collectors.toList());


        return FXCollections.observableArrayList(withoutPermissionForRole);







    }

    private List<Roles> getRolesData(){

        return  roleService.getRoleAllData();
    }

    private List<Permissions>getPermissionsData(){

        return permissionsService.getAllData();
    }

    private int getRoleID(String roleName){


        return getRolesData().stream()
                .filter(roles -> roles.getRole_name().equals(roleName))
                .map(Roles::getRole_id).findFirst().orElse(-1);

    }

    private Permissions getPermission(String permissionName){

        return permissionsService.getAllData().stream()
                .filter(permissions -> permissions.getPermission_name().equals(permissionName))
                .findFirst().orElse(null);


    }

    private Roles getRole(String roleName){


        return getRolesData().stream()
                .filter(roles -> roles.getRole_name().equals(roleName))
                .findFirst().orElse(null);

    }

    private int getRolePermissionID(int roleID, int permissionID){

        return  roleperService.getRolePerAllData().stream()
                .filter(rolePermissions -> rolePermissions.getRoles().getRole_id()==roleID && rolePermissions.getPermission().getPermission_id()==permissionID)
                .map(RolePermissions::getRole_permission_id).findFirst().orElse(-1);


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
}



