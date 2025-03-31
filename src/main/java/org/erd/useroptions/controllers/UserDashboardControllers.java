package org.erd.useroptions.controllers;

import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.val;
import org.erd.App;
import org.erd.useroptions.dto.UserDTO;
import org.erd.useroptions.models.Users;
import org.erd.useroptions.services.UserServices;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static org.erd.App.context;

@Controller
public class UserDashboardControllers implements Initializable {

    private final UserDTO userDTO;
    @FXML
    private TableColumn<Users, Timestamp> DateCol;

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


    public UserDashboardControllers(UserServices userServices, Validator validator, UserDTO userDTO) {
        this.userServices = userServices;
        this.validator = validator;
        usersList = new ArrayList<>();
        this.userDTO = userDTO;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getLodRowData();

        tableInill();

        actionEvent();


    }

    private void actionEvent() {


        addbtn.setOnAction(e -> {
            FXMLLoader fxmlLoader = new FXMLLoader(UserDashboardControllers.class.getResource("/views/userview/createuserview.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);


            Stage mainStage = (Stage) addbtn.getScene().getWindow();
            stage.initOwner(mainStage);


            stage.setTitle("Create User");
            stage.setScene(scene);
            stage.show();


            stage.setOnCloseRequest(event -> {
                getLodRowData();
            });



        });

        userdbtable.setOnMouseClicked(event -> {


            if(event.getClickCount() == 2) {


                val userDTO =  UserDTO.Companion.getInstance();

                Users user= userdbtable.getSelectionModel().getSelectedItem();

                userDTO.setUser(user);


                FXMLLoader fxmlLoader = new FXMLLoader(UserDashboardControllers.class.getResource("/views/userview/updateuserview.fxml"));
                fxmlLoader.setControllerFactory(context::getBean);
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);


                Stage mainStage = (Stage) addbtn.getScene().getWindow();
                stage.initOwner(mainStage);


                stage.setTitle("Edit User");
                stage.setScene(scene);
                stage.show();


                stage.setOnCloseRequest(e -> {
                    getLodRowData();
                });





            }

        });

        removebtn.setOnAction(e -> {

            int id  = userdbtable.getSelectionModel().getSelectedItem().getUser_id();

            boolean confirmed = showConfirmDialog("အသုံးပြုသူ", "စစ်ဆေးပါ။", "ဤအသုံးပြုသူအား ဖျက်မှာသေချာပြီလား။");


                if(confirmed){

                    userServices.deleteUser(id);
                    getLodRowData();
                }
                else
                    showErrorDialog("အသုံးပြုသူ", "စစ်ဆေးပါ။", "ဤအသုံးပြုသူအားဖျက်၍ မရနိုင်ပါ။");






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

    private void showErrorDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }




    private void tableInill() {

        idCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("activation"));
    }

    private void getLodRowData() {





        ObservableList<Users> userList = FXCollections.observableArrayList(userServices.getAllData().stream()
                .map(users -> {

                    String status = (users.getIs_active()==1)?"active":"inactive";
                    return new Users(users.getUser_id(),users.getUser_name(),users.getPassword(),users.getCreated_at(),status);
                })
                .collect(Collectors.toList()));


        userdbtable.setItems(userList);



    }
}
