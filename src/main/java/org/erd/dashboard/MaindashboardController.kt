package org.erd.dashboard

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import org.erd.loginoptions.LoginFormController
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.*


@Controller
class MaindashboardController : Initializable {

    @FXML
    private lateinit var userlb: Label



    override fun initialize(location: URL?, resources: ResourceBundle?) {

        userlb.text = LoginFormController.userName

    }


}