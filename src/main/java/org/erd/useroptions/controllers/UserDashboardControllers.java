package org.erd.useroptions.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import org.springframework.stereotype.Controller;

@Controller
public class UserDashboardControllers {

    @FXML
    private TableColumn<?, ?> DateCol;

    @FXML
    private Button addbtn;

    @FXML
    private Button editbtn;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> passCol;

    @FXML
    private Button removebtn;

    @FXML
    private Button setrolebtn;

    @FXML
    private TableColumn<?, ?> statusCol;




}
