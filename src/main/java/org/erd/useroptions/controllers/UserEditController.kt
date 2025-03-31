package org.erd.useroptions.controllers

import com.jfoenix.controls.JFXCheckBox
import jakarta.validation.Validator
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.stage.Modality
import org.erd.useroptions.dto.UserDTO
import org.erd.useroptions.models.Users
import org.erd.useroptions.services.UserServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.*


@Controller
class UserEditController : Initializable {

    @Autowired
    private var userServices: UserServices? = null
    @Autowired
    private var validator: Validator? = null





    @FXML
    private var disablecheckbox: JFXCheckBox? = null

    @FXML
    private var enablecheckbox: JFXCheckBox? = null

    @FXML
    private var idlb: Label? = null

    @FXML
    private var  insertbtn: Button? = null

    @FXML
    private var namelb: Label? = null

    @FXML
    private var passtxt: PasswordField? = null

    @FXML
    private var usertxt: TextField? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        getLoadData()
        getConditionCheckbox()

        onClickAction()


    }

    private fun onClickAction() {

        insertbtn?.setOnMouseClicked {


            val id = idlb?.text?.toIntOrNull()
            val name = usertxt?.text ?: ""
            val pass = passtxt?.text ?: ""
            val status = getConditionCheckbox().toByte()

            if (id == null || name.isBlank() || pass.isBlank()) {
                showErrorDialog("အသုံးပြုသူ", "မအောင်မြင်ပါ။", "အသုံးပြုသူအချက်အလက် မဖြည့်သွင်းရသေးပါ။")
                return@setOnMouseClicked
            }


            val user = if (!getConditionCheckboxNoSelection()) {
                Users(id, name, pass)
            } else {
                Users(id, name, pass, status)
            }

            println(user)



            if (testRoleValidate(user)) {

                val isRoleUpdated = userServices?.updateUser(user) ?: false
                if (isRoleUpdated) {
                    showInformationDialog("အသုံးပြုသူ", "အောင်မြင်သည်။", "အသုံးပြုသူ ပြုပြင်ခြင်း အောင်မြင်သည်။")
                    setClear()
                } else {
                    showErrorDialog("အသုံးပြုသူ", "မအောင်မြင်ပါ။", "အသုံးပြုသူ ပြုပြင်ခြင်း မအောင်မြင်ပါ။")
                }
            } else {
                showErrorDialog("အသုံးပြုသူ", "မအောင်မြင်ပါ။", "အသုံးပြုသူအချက်အလက် မမှန်ကန်ပါ။")
            }
        }

    }


    private fun setClear() {
        usertxt!!.text = ""
        passtxt!!.text = ""
        enablecheckbox!!.isSelected = false
        disablecheckbox!!.isSelected = false
    }

    private fun testRoleValidate(user: Users): Boolean {

        val violations = validator!!.validate(user)

        if (violations.isNotEmpty()) {
            val errorMessages = StringBuilder()
            for (violation in violations) {
                errorMessages.append(violation.message).append("\n\n")
            }


            showErrorDialog("ဒေတာဘေစ် အမှား", "ဒေတာ လိုအပ်ချက်များရှိနေသည်။", errorMessages.toString())

            return false
        } else {
            return true
        }
    }

    private fun getConditionCheckboxNoSelection(): Boolean {
        return enablecheckbox!!.isSelected || disablecheckbox!!.isSelected
    }

    private fun getLoadData() {

        val userdto = UserDTO.getInstance()
        val user = userdto.getUser()

        idlb?.text = user?.user_id?.toString() ?: ""

        namelb?.text= user?.user_name?: ""

        usertxt?.text = user?.user_name?: ""

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