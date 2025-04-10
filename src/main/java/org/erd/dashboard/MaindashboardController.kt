package org.erd.dashboard

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.stage.Modality
import javafx.stage.Stage
import org.erd.App
import org.erd.App.context
import org.erd.loginoptions.LoginFormController
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.*


@Controller
class MaindashboardController() : Initializable {

    @FXML
    private lateinit var userlb: Label

    @FXML
    private lateinit var capitalbtn :Button





    override fun initialize(location: URL?, resources: ResourceBundle?) {

        userlb.text = LoginFormController.userName


        capitalbtn.setOnMouseClicked {

            val stage = Stage()
            val mainStage: Stage = capitalbtn.scene.window as Stage
            val fxmlLoader = FXMLLoader(App::class.java.getResource("/views/capitalview/injectionsview.fxml"))
            fxmlLoader.setControllerFactory { context.getBean(it) }
            val scene = Scene(fxmlLoader.load())
            stage.title = "CapitalView"
            stage.initOwner(mainStage)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.scene = scene
            stage.show()


        }





    }




}