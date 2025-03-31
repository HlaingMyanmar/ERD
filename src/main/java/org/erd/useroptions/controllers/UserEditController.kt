package org.erd.useroptions.controllers

import com.jfoenix.controls.JFXCheckBox
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.erd.useroptions.dto.UserDTO
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.*


@Controller
class UserEditController : Initializable {


    @FXML
    private var disablecheckbox: JFXCheckBox? = null

    @FXML
    private var enablecheckbox: JFXCheckBox? = null

    @FXML
    private var idlb: Label? = null

    @FXML
    private val insertbtn: Button? = null

    @FXML
    private var namelb: Label? = null

    @FXML
    private var passtxt: PasswordField? = null

    @FXML
    private var usertxt: TextField? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {


        getLoadData()
        getConditionCheckbox()


    }

    private fun getLoadData() {

        val userdto = UserDTO.getInstance()
        val user = userdto.getUser()

        idlb?.text = user?.user_id?.toString() ?: ""

        namelb?.text= user?.user_name ?: ""

        usertxt?.text = user?.user_name ?: ""

        passtxt?.text = user?.password?:""


        when (user?.activation) {

                "inactive" -> {

                    disablecheckbox?.isSelected = true
                }

                "active" -> {

                    enablecheckbox?.isSelected = true

                }

        }





    }

    private fun getConditionCheckbox(): Int {



        enablecheckbox?.setOnAction {

               enablecheckbox?.isSelected = true
               disablecheckbox?.isSelected = false



        }
        disablecheckbox?.setOnAction {
            disablecheckbox?.isSelected = true
            enablecheckbox?.isSelected = false


        }

        return if (enablecheckbox?.isSelected == true) 1 else 0




    }


}