package org.erd.paymentoptions.controller

import com.jfoenix.controls.JFXCheckBox
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import org.erd.paymentoptions.model.Payment
import org.erd.paymentoptions.service.PaymentService
import org.erd.paymentoptions.views.PaymentView
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.*

@Controller
class PaymentMethodController (private val paymentService: PaymentService) :Initializable {

    @FXML
    private lateinit var codeCol: TableColumn<PaymentView, String>

    @FXML
    private lateinit var disablecheckbox: JFXCheckBox

    @FXML
    private lateinit var editbtn: Button

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
       // paymenttable.items = getLoadPaymentData()

        paymentService.getAllPayment()

    }

    private fun getLoadPaymentData(): ObservableList<PaymentView> {
        return paymentService.getAllPayment()
            .map { payment ->
                val digitalStatus = if (payment.isdigital.toInt() ==1) "Online" else "Offline"
                val activeStatus = if (payment.isactive.toInt() ==1) "Active" else "Inactive"
                PaymentView(payment.paymentId, payment.methodCode, payment.methodName, digitalStatus, activeStatus)
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


}