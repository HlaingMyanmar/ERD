package org.erd.paymentoptions.controller

import com.jfoenix.controls.JFXCheckBox
import jakarta.validation.Validator
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.stage.Modality
import org.erd.paymentoptions.model.Payment
import org.erd.paymentoptions.service.PaymentService
import org.erd.paymentoptions.views.PaymentView
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.*

@Controller
class PaymentMethodController (private val paymentService: PaymentService ,private val validator: Validator?) :Initializable {

    @FXML
    private lateinit var codeCol: TableColumn<PaymentView, String>

    @FXML
    private lateinit var disablecheckbox: JFXCheckBox

    @FXML
    private  lateinit var editbtn: Button

    @FXML
    private lateinit var enablecheckbox: JFXCheckBox

    @FXML
    private lateinit var insertbtn: Button

    @FXML
    private lateinit var isactiveCol: TableColumn<PaymentView, String>

    @FXML
    private lateinit var isdigitalCol: TableColumn<PaymentView, String>

    @FXML
    private lateinit var methodCol: TableColumn<PaymentView, String>

    @FXML
    private lateinit var offlinecheckbox: JFXCheckBox

    @FXML
    private lateinit var onlinecheckbox: JFXCheckBox

    @FXML
    private lateinit var paymentcodetxt: TextField

    @FXML
    private lateinit var paymentmethodtxt: TextField

    @FXML
    private lateinit var paymenttable: TableView<PaymentView>



    override fun initialize(location: URL?, resources: ResourceBundle?) {


        tableIni()
        paymenttable.items = getLoadPaymentData()
        onClickAction()
        getConditionCheckbox()
        isDigitalCheckbox()



    }

    private fun onClickAction() {


        insertbtn.setOnMouseClicked {
            val paymentCode = paymentcodetxt.text
            val paymentMethod = paymentmethodtxt.text
            val isDigital = isDigitalCheckbox().toByte()
            val status = getConditionCheckbox().toByte()

            if (paymentCode.isEmpty() || paymentMethod.isEmpty()) {
                showErrorDialog("Payment", "Unsuccessful", "Please refill required Data!!!")
                return@setOnMouseClicked
            }

            val payment = Payment()
            payment.methodCode = paymentCode
            payment.methodName = paymentMethod
            payment.isDigital = isDigital
            payment.isActive= status

            if (testRoleValidate(payment)) {
                val savedPayment = paymentService.save(payment)
                if (savedPayment==true) {
                    showInformationDialog("Payment", "Payment Insert Successful", "Successfully created Payment!")
                    setClear()
                    paymenttable.items = getLoadPaymentData()
                }
            }


        }

        paymenttable.setOnMouseClicked { event ->

            if (event.clickCount == 2) {
                val p: PaymentView? = paymenttable.selectionModel.selectedItem



                paymentcodetxt.text = p?.methodCode ?: ""
                paymentmethodtxt.text = p?.methodName ?: ""

                if (p?.isdigital?.equals("Online") == true) {
                    onlinecheckbox.isSelected = true
                    offlinecheckbox.isSelected = false
                } else {
                    offlinecheckbox.isSelected = true
                    onlinecheckbox.isSelected = false
                }

                if (p?.isactive?.equals("Active") == true) {
                    enablecheckbox.isSelected = true
                    disablecheckbox.isSelected = false
                } else {
                    disablecheckbox.isSelected = true
                    enablecheckbox.isSelected = false
                }

                editbtn.setOnMouseClicked {


                   val paymentCode = paymentcodetxt.text?: p?.methodCode
                   val paymentMethod = paymentmethodtxt.text  ?: p?.methodName
                   val isDigital = isDigitalCheckbox().toByte()
                   val status = getConditionCheckbox().toByte()

                    val payment = Payment()
                    payment.method_Id = p?.method_id
                    payment.methodCode = paymentCode.toString()
                    payment.methodName = paymentMethod.toString()
                    payment.isDigital = isDigital
                    payment.isActive= status

                    if(paymentService.update(payment)==true){

                        showInformationDialog("Payment", "Payment Update Successful", "Successfully Update Payment!")
                        setClear()
                        paymenttable.items = getLoadPaymentData()


                    }





                }



            }


        }

        paymenttable.setOnKeyPressed { event ->

            if(event.code== KeyCode.DELETE){

                val p: PaymentView? = paymenttable.selectionModel.selectedItem

                val result = showConfirmDialog("Payment", "Payment Deletion ", "Are you sure?!")

                if(result==true){
                    paymentService.deleteById( p?.method_id?.toInt())
                    showInformationDialog("Payment", "Payment Delete Successful", "Successfully Delete Payment!")
                    setClear()
                    paymenttable.items = getLoadPaymentData()
                }


            }


        }






    }

    private fun showConfirmDialog(title: String, header: String, content: String): Boolean {
        val alert = Alert(Alert.AlertType.CONFIRMATION)
        alert.title = title
        alert.headerText = header
        alert.contentText = content
        val result = alert.showAndWait()
        return result.isPresent && result.get() == ButtonType.OK
    }

    private fun testRoleValidate(payment: Payment): Boolean {

        val violations = validator!!.validate(payment)

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

    private fun getLoadPaymentData(): ObservableList<PaymentView> {
        return paymentService.getAllPayment()
            .map { payment ->
                val digitalStatus = if (payment.isDigital.toInt() ==1) "Online" else "Offline"
                val activeStatus = if (payment.isActive.toInt() ==1) "Active" else "Inactive"
                PaymentView(payment.method_Id, payment.methodCode, payment.methodName, digitalStatus, activeStatus)
            }
            .let { FXCollections.observableArrayList(it) }
    }


    private fun tableIni() {

        codeCol.setCellValueFactory { cellData ->
            SimpleStringProperty(cellData.value.methodCode)
        }
        methodCol.setCellValueFactory{ cellData ->
            SimpleStringProperty(cellData.value.methodName)
        }
        isdigitalCol.setCellValueFactory { cellData ->
            SimpleStringProperty(cellData.value.isdigital)
        }
        isactiveCol.setCellValueFactory { cellData ->
            SimpleStringProperty(cellData.value.isactive)
        }



    }

    private fun getConditionCheckbox(): Int {



        enablecheckbox.setOnAction {

            enablecheckbox.isSelected = true
            disablecheckbox.isSelected = false



        }
        disablecheckbox.setOnAction {
            disablecheckbox.isSelected = true
            enablecheckbox.isSelected = false


        }

        return if (enablecheckbox.isSelected == true) 1 else 0




    }

    private fun isDigitalCheckbox(): Int {



        onlinecheckbox.setOnAction {

            onlinecheckbox.isSelected = true
            offlinecheckbox.isSelected = false



        }
        offlinecheckbox.setOnAction {
            offlinecheckbox.isSelected = true
            onlinecheckbox.isSelected = false


        }

        return if (onlinecheckbox.isSelected == true) 1 else 0




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

    private  fun setClear(){
        paymentcodetxt.text = null
        paymentmethodtxt.text = null
        enablecheckbox.isSelected = false
        disablecheckbox.isSelected = false
        offlinecheckbox.isSelected = false
        onlinecheckbox.isSelected = false
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