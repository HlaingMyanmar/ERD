package org.erd.chartofaccountoptions.controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class ChartOfAccountsController implements Initializable {

    @FXML
    private TableView<?> accounttable;

    @FXML
    private TextField acnametxt;

    @FXML
    private TextField actypetxt;

    @FXML
    private TableColumn<?, ?> codeCol;

    @FXML
    private JFXCheckBox disablecheckbox;

    @FXML
    private Button editbtn;

    @FXML
    private JFXCheckBox enablecheckbox;

    @FXML
    private Button insertbtn;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
