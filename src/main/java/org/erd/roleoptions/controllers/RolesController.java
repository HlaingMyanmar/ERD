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
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import org.erd.roleoptions.models.Roles;
import org.erd.roleoptions.services.RoleService;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.*;
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

    private final RoleService roleService;

    private final Validator validator;

    private List<Roles> rolesList;

    public RolesController(RoleService roleService, Validator validator) {
        this.roleService = roleService;
        this.validator = validator;
        rolesList = new ArrayList<>();
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

            Roles roles;

            if (!getConditionCheckboxNoSelection()) {
                roles = new Roles(role, desc);
            } else {
                roles = new Roles(role, desc, status);
            }

            if (testRoleValidate(roles)){
                boolean isRoleAdded = roleService.addRole(roles);
                if (isRoleAdded) {
                    showInformationDialog("ရာထူး", "အောင်မြင်သည်။", "ရာထူးထည့်ခြင်း အောင်မြင်သည်။");

                    setClear();
                } else {
                    showErrorDialog("ရာထူး", "မအောင်မြင်ပါ။", "ရာထူးထည့်ခြင်း မအောင်မြင်ပါ။");
                }
            } else {
                showErrorDialog("ရာထူး", "မအောင်မြင်ပါ။", "ရာထူးအချက်အလက် မမှန်ကန်ပါ။");
            }

        });

        roletable.setOnKeyPressed(event -> {


            if (event.getCode() == KeyCode.DELETE) {

                Roles role = roletable.getSelectionModel().getSelectedItem();

                    int id = rolesList.stream()
                            .filter(roles -> roles.getRole_name().equals(role.getRole_name()))
                            .map(Roles::getRole_id)
                            .findFirst().orElse(-1);

                boolean confirmed = showConfirmDialog("ရာထူး","အချက်အလက်","အိုင်ဒီ "+id+ "\n"+"ရာထူး : "+role.getRole_name()+" ကိုဖျက်မှာသေချာပြီလား");

                if ( confirmed){

                    roleService.deleteById(id);

                    showInformationDialog("ရာထူး", "အောင်မြင်သည်။", "ဖျက်ခြင်း အောင်မြင်သည်။");
                    setClear();


                }
                else {

                    showErrorDialog("ရာထူး", "မအောင်မြင်ပါ။", "ရာထူးပြုပြင်ခြင်း အချက်အလက် မမှန်ကန်ပါ။");

                }






            }

        });


        roletable.setOnMouseClicked(event -> {

            Roles role = roletable.getSelectionModel().getSelectedItem();


            if (event.getClickCount() == 2) {


                roletxt.setText(role.getRole_name());
                desctxt.setText(role.getDescription());
                if(role.getActivation().equals("သုံးနေဆဲ")){

                    enablecheckbox.setSelected(true);
                    disablecheckbox.setSelected(false);

                }
                else {
                    disablecheckbox.setSelected(true);
                    enablecheckbox.setSelected(false);
                }

                editbtn.setOnAction(event1 -> {


                    int id = rolesList.stream()
                            .filter(roles -> roles.getRole_name().equals(role.getRole_name()))
                            .map(Roles::getRole_id)
                            .findFirst().orElse(-1);

                    Roles roles = new Roles(id,roletxt.getText(),desctxt.getText(), (byte) getConditionCheckbox());

                    boolean confirmed = showConfirmDialog("ရာထူး","အချက်အလက်","ရာထူးပြုပြင်ခြင်း ပြုလုပ်မှာသေချာပြီလား");

                    if (testRoleValidate(roles) && confirmed){

                            boolean isRoleAdded = roleService.updateRole(roles);

                            if (isRoleAdded) {
                                showInformationDialog("ရာထူး", "အောင်မြင်သည်။", "ရာထူးပြုပြင်ခြင်း အောင်မြင်သည်။");

                                setClear();
                            } else {
                                showErrorDialog("ရာထူး", "မအောင်မြင်ပါ။", "ရာထူးပြုပြင်ခြင်း  မအောင်မြင်ပါ။");
                            }

                    } else {
                        showErrorDialog("ရာထူး", "မအောင်မြင်ပါ။", "ရာထူးပြုပြင်ခြင်း အချက်အလက် မမှန်ကန်ပါ။");
                    }





                });


             }

        });





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

    private void getLoadRowData() {

        rolesList = roleService.getRoleAllData();

       List<Roles>roleList = roleService.getRoleAllData().stream()
                .map(roles -> {

                    String status = (roles.getIs_active() == 1) ? "သုံးနေဆဲ" : "မသုံးတော့ပါ။";

                    return new Roles(roles.getRole_name(), roles.getDescription(), status);

                })
                .collect(Collectors.toList());

        ObservableList<Roles> data = FXCollections.observableArrayList(roleList);

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

    private void setClear(){

        roletxt.setText("");
        desctxt.setText("");
        enablecheckbox.setSelected(false);
        disablecheckbox.setSelected(false);

        getLoadRowData();


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
