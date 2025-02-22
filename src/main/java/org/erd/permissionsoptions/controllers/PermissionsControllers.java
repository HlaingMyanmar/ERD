package org.erd.permissionsoptions.controllers;

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
import org.erd.permissionsoptions.models.Permissions;
import org.erd.permissionsoptions.services.PermissionsService;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class PermissionsControllers implements Initializable {

    private final Permissions permissions;
    @FXML
    private TableColumn<Permissions, String> desCol;

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
    private TableColumn<Permissions, String> perCol;

    @FXML
    private TableView<Permissions> pertable;

    @FXML
    private TextField pertxt;

    @FXML
    private TableColumn<Permissions, Byte> statusCol;

    private final PermissionsService permissionsService;

    private final Validator validator;

    private List<Permissions> permissionsList;

    public PermissionsControllers(PermissionsService permissionsService, Validator validator, Permissions permissions) {
        this.permissionsService = permissionsService;
        this.validator = validator;
        permissionsList = new ArrayList<>();
        this.permissions = permissions;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableIni();
        getLoadRowData();

        actionEvent();

    }

    private void actionEvent() {

    getConnectionCheckBox();

        insertbtn.setOnAction(event -> {

          String pername = pertxt.getText();
          String desc = desctxt.getText();
          byte status = (byte) getConnectionCheckBox();

          Permissions permissions;

          if(!getConditionaCheckBoxNoSelection()){

              permissions = new Permissions(pername, desc);

          }else{

              permissions = new Permissions(pername, desc, status);
          }

          if(testPerssionsValidate(permissions)) {

              boolean isPermissionadd = permissionsService.addPermissions(permissions);

              if (isPermissionadd) {

                  showInformationDialog("ရာထူးခွင့်ပြုချက်", "အောင်မြင်သည်", "ရာထူးခွင့်ပြုချက်အောင်မြင်သည်။");

                  getLoadRowData();setClear();



              } else {
                  showErrorDialog("ရာထူးခွင့်ပြုချက်", "မမှန်ကန်ပါ", "ရာထူးခွင့်ပြုချက် မအောင်မြင်ပါ။");
              }
          }
          else {
                  showErrorDialog("ရာထူးခွင့်ပြုချက်", "မမှန်ကန်ပါ", "ရာထူးခွင့်ပြုချက် မအောင်မြင်ပါ။");
              }

        });

        pertable.setOnKeyReleased(event -> {

            if (event.getCode() == KeyCode.DELETE) {

                Permissions pe = pertable.getSelectionModel().getSelectedItem();

                int id = permissionsList.stream()
                        .filter(permissions1 -> permissions1.getPermission_name().equals(pe.getPermission_name()))
                        .map(Permissions::getPermission_id)
                        .findFirst().orElse(-1);


                boolean cofirmed = showConfirmDialog("ခွင့်ပြုချက်ပုံစံ","အချက်အလက်","အိုင်ဒီ" + id + "\n"+ "ခွင့်ပြုချက်ပုံစံ"+pe.getPermission_name()+"ကိုဖျက်မှာသေချာပြီလား");

                if (cofirmed) {

                    permissionsService.deleteByID(id);

                    showInformationDialog("ခွင့်ပြုချက်ပုံစံ","အောင်မြင်သည်","ဖျက်ခြင်းအောင်မြင်သည်");

                    setClear();
                }else{

                    showErrorDialog("ခွင့်ပြုချက်ပုံစံ","မအောင်မြင်ပါ။","ခွင့်ပြုချက်ပုံစံ ဖျက်ခြင်း မအောင်မြင်ပါ။");

                }


            }






        });


        pertable.setOnMouseClicked(event -> {

            Permissions pe = pertable.getSelectionModel().getSelectedItem();

            if(event.getClickCount() == 2){

                pertxt.setText(pe.getPermission_name());
                desctxt.setText(pe.getDescription());

                if(pe.getActivation().equals("သုံးနေဆဲ")){

                    enablecheckbox.setSelected(true);
                    disablecheckbox.setSelected(false);

                }else {

                    enablecheckbox.setSelected(false);
                    disablecheckbox.setSelected(true);
                }

                editbtn.setOnAction(event1 -> {

                    int id = permissionsList.stream()
                            .filter(permissions -> permissions.getPermission_name().equals(pe.getPermission_name()))
                            .map(Permissions::getPermission_id)
                            .findFirst().orElse(-1);

                    Permissions permissions = new Permissions(id,pertxt.getText(),desctxt.getText(),(byte)getConnectionCheckBox());

                    boolean cofirmed = showConfirmDialog("ခွင့်ပြုချက်ပုံစံ","ရာထူးခွင့်ပြူချက်ပုံစံ","ခွင့်ပြုချက်ပုံစံပြုပြင်ခြင်း ပြုပြင်မှာသေချာပြီလား");

                    if(testPerssionsValidate(permissions) && cofirmed) {

                        boolean isPermissionadd = permissionsService.updatePermissions(permissions);

                        if (isPermissionadd) {

                            showInformationDialog("ခွင့်ပြုချက်ပုံစံ", "အောင်မြင်သည်", "ခွင့်ပြုချက်ပုံစံပြုပြင်ခြင်း အောင်မြင်သည်");

                            setClear();

                        } else {

                            showErrorDialog("ခွင့်ပြုချက်ပုံစံ", "မအောင်မြင်ပါ။", "ခွင့်ပြုချက်ပုံစံပြုပြင်ခြင်း မအောင်မြင်ပါ။");

                        }

                    }else{

                        showErrorDialog("ခွင့်ပြုချက်ပုံစ","မအောင်မြင်ပါ။","ခွင့်ပြုချက်ပုံစံပြုပြင်ခြင်း အချက်အလက် မမှန်ကန်ပါ။");


                    }


                });


            }

        });



    }

    private boolean getConditionaCheckBoxNoSelection(){

        return enablecheckbox.isSelected() || disablecheckbox.isSelected();
    }

    private int getConnectionCheckBox() {

        enablecheckbox.setOnAction(event -> {

            enablecheckbox.setSelected(true);
            disablecheckbox.setSelected(false);

        });

        disablecheckbox.setOnAction(event -> {

            enablecheckbox.setSelected(false);
            disablecheckbox.setSelected(true);

        });
        return enablecheckbox.isSelected() ? 1 : 0;

    }

    private void getLoadRowData() {

        permissionsList = permissionsService.getAllData();
        List<Permissions> permissionsList = permissionsService.getAllData().stream()
                .map(permissions -> {

            String status = (permissions.getIs_active() ==1) ? " သုံးနေဆဲ ": " မသုံးတော့ပါ။";

            return new Permissions(permissions.getPermission_name(),permissions.getDescription(),status);

        })
                .collect(Collectors.toList());
        ObservableList<Permissions> observableList = FXCollections.observableList(permissionsList);
        pertable.setItems(observableList);

    }

    private void tableIni() {
        perCol.setCellValueFactory(new PropertyValueFactory<>("permission_name"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("activation"));
        
    }

    private boolean testPerssionsValidate(Permissions permissions) {

        Set<ConstraintViolation<Permissions>>  valation = validator.validate(permissions);

        if(!valation.isEmpty()){

            StringBuilder errormessage = new StringBuilder();
            for(ConstraintViolation<Permissions> violation : valation){

                errormessage.append(violation.getMessage());


            }

            showErrorDialog("ဒေတာဘေ့စ်အမှား", "ဒေတာလိုအပ်ချက်များရှိနေသည်",errormessage.toString());

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
    private void setClear(){

        pertxt.setText("");
        desctxt.setText("");
        enablecheckbox.setSelected(false);
        disablecheckbox.setSelected(false);

        getLoadRowData();


    }

    }
