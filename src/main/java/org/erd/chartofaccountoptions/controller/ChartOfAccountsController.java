package org.erd.chartofaccountoptions.controller;

import com.jfoenix.controls.JFXCheckBox;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import org.erd.chartofaccountoptions.model.ChartOfAccounts;
import org.erd.chartofaccountoptions.service.ChartOfAccountsService;
import org.erd.roleoptions.models.Roles;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

@Controller
public class ChartOfAccountsController implements Initializable {

    @FXML
    private TableView<ChartOfAccounts> accounttable;

    @FXML
    private TextField acnametxt;

    @FXML
    private TextField actypetxt;

    @FXML
    private TableColumn<ChartOfAccounts, Integer> codeCol;

    @FXML
    private JFXCheckBox disablecheckbox;

    @FXML
    private Button editbtn;

    @FXML
    private JFXCheckBox enablecheckbox;

    @FXML
    private Button insertbtn;

    @FXML
    private TableColumn<ChartOfAccounts, String> nameCol;

    @FXML
    private TableColumn<ChartOfAccounts, Byte> statusCol;

    @FXML
    private TableColumn<ChartOfAccounts, String> typeCol;

    private final Validator validator;

    private ChartOfAccountsService chartOfAccountsService;

    public ChartOfAccountsController(Validator validator, ChartOfAccountsService chartOfAccountsService) {
        this.validator = validator;
        this.chartOfAccountsService = chartOfAccountsService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getConditionCheckbox();
        tableIni();

        actionEvent();

        getLoadData();



    }

    private void getLoadData() {




    }

    private void actionEvent() {

        insertbtn.setOnAction(event -> {


            String accountName =  acnametxt.getText();
            String accountType =  actypetxt.getText();
            byte status = (byte) getConditionCheckbox();

            ChartOfAccounts chartOfAccounts;

            if (!getConditionCheckboxNoSelection()) {
                chartOfAccounts = new ChartOfAccounts(accountName,accountType);
            } else {
                chartOfAccounts = new ChartOfAccounts(accountName,accountType,status);
            }

            if (testRoleValidate(chartOfAccounts)){
                boolean isRoleAdded = chartOfAccountsService.addChartOfAccounts(chartOfAccounts);
                if (isRoleAdded) {
                    showInformationDialog("Account", "အောင်မြင်သည်။", "Account ထည့်ခြင်း အောင်မြင်သည်။");

                    setClear();
                } else {
                    showErrorDialog("Account", "မအောင်မြင်ပါ။", "Account ထည့်ခြင်း မအောင်မြင်ပါ။");
                }
            } else {
                showErrorDialog("Account", "မအောင်မြင်ပါ။", "Account အချက်အလက် မမှန်ကန်ပါ။");
            }


        });


    }

    private void tableIni() {


        codeCol.setCellValueFactory(new PropertyValueFactory<>("account_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("account_name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("account_type"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("activation"));

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

    private boolean testRoleValidate(ChartOfAccounts account) {

        Set<ConstraintViolation<ChartOfAccounts>> violations = validator.validate(account);

        if(!violations.isEmpty()){

            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<ChartOfAccounts> violation : violations) {
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

        acnametxt.setText("");
        actypetxt.setText("");
        enablecheckbox.setSelected(false);
        disablecheckbox.setSelected(false);




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
