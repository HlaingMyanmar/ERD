package org.erd.loginoptions

import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.PasswordField
import javafx.scene.input.KeyCode
import javafx.stage.Modality
import javafx.stage.Stage
import org.erd.App
import org.erd.App.context
import org.erd.useroptions.services.UserServices
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.ResourceBundle
import java.util.stream.Collectors


@Controller
class LoginFormController (private val userServices: UserServices) : Initializable {

    companion object {
        var userName : String = ""
    }



    @FXML
    private lateinit var editbtn : Button

    @FXML
    private lateinit var  passwordtxt : PasswordField

    @FXML
    private lateinit var usernamecb : ComboBox<String>


    private val allUsernames = mutableListOf<String>()





    override fun initialize(location: URL?, resources: ResourceBundle?) {


        allUsernames.addAll(userServices

            .allData.stream()
            .map { user -> user.user_name }
            .collect(Collectors.toList())

        )

        usernamecb.items = FXCollections.observableArrayList(allUsernames)




        // Add listener for autocomplete functionality
        usernamecb.editor.textProperty().addListener { _, _, newValue ->
            if (newValue.isEmpty()) {
                usernamecb.items = FXCollections.observableArrayList(allUsernames)
            } else {
                val filteredList = allUsernames.filter { it.contains(newValue, ignoreCase = true) }
                usernamecb.items = FXCollections.observableArrayList(filteredList)
                usernamecb.show() // Show dropdown with suggestions
            }
        }

        // Handle Enter key to select suggestion
        usernamecb.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER && usernamecb.selectionModel.selectedItem != null) {
                usernamecb.editor.text = usernamecb.selectionModel.selectedItem
                usernamecb.hide() // Hide dropdown after selection
            }
        }

        // Ensure dropdown shows when typing starts
        usernamecb.setOnMouseClicked {
            if (usernamecb.editor.text.isEmpty()) {
                usernamecb.items = FXCollections.observableArrayList(allUsernames)
                usernamecb.show()
            }
        }




        editbtn.setOnMouseClicked {



            if (usernamecb.editor.text.isEmpty()  ) {

                showErrorDialog("Username cannot be blank", "Please enter a valid username", "Please enter a username")

            } else if ( passwordtxt.text.isEmpty()){
                showErrorDialog("User password cannot be blank", "Please enter a valid password", "Please enter password")
            }

            else{

                val selectedUsername = usernamecb.editor.text
                val password = passwordtxt.text

                userName =selectedUsername

                if(isTrue(selectedUsername,password)){


                    val stage = Stage()
                    val mainStage: Stage = editbtn.scene.window as Stage
                    val fxmlLoader = FXMLLoader(App::class.java.getResource("/views/dashboardviews/maindashboardview.fxml"))
                    fxmlLoader.setControllerFactory { context.getBean(it) }
                    val scene = Scene(fxmlLoader.load())
                    stage.title = "dashboard"
                    mainStage.close()
                    stage.scene = scene
                    stage.show()

                }
                else{
                    showErrorDialog("User", "Not Success", "Wrong Password")
                }
            }
        }



    }

    private fun isTrue(name: String, password: String): Boolean {
        val user = userServices.allData.find { it.user_name == name && it.password == password }
        return user != null && user.password == password
    }



    private fun showErrorDialog(title: String, header: String, content: String) {
        Platform.runLater {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.title = title
            alert.headerText = header
            alert.contentText = content
            alert.showAndWait()
        }
    }

    private fun showInformationDialog(title: String, header: String, content: String) {
        Platform.runLater {
            val alert = Alert(Alert.AlertType.INFORMATION)
            alert.initModality(Modality.APPLICATION_MODAL)
            alert.title = title
            alert.headerText = header
            alert.contentText = content
            alert.showAndWait()
        }
    }


}