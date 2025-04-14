package org.erd.capitaloptions.controller

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import org.erd.capitaloptions.model.CapitalInjection
import org.erd.capitaloptions.service.CaptialService
import org.erd.capitaloptions.views.CapitalInjectionDTO
import org.erd.chartofaccountoptions.model.ChartOfAccounts
import org.erd.chartofaccountoptions.service.ChartOfAccountsService
import org.erd.paymentoptions.model.Payment
import org.erd.paymentoptions.service.PaymentService
import org.erd.transactionoptions.model.Transaction
import org.springframework.stereotype.Controller
import java.math.BigDecimal
import java.net.URL
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.util.ResourceBundle
import java.util.UUID
import java.util.stream.Collectors


@Controller
class CapitalInjectionController(
    private val paymentService: PaymentService ,
    private val capitalService: CaptialService,
    private val chartOfAccountsService: ChartOfAccountsService



) : Initializable {

    @FXML
    private lateinit var addbtn : Button

    @FXML
    private lateinit var amountxt: TextField

    @FXML
    private lateinit var datep:DatePicker

    @FXML
    private lateinit var descriptiontxt: TextArea

    @FXML
    private lateinit var paymentcb : ComboBox<String>

    @FXML
    private lateinit var amountCol:TableColumn<CapitalInjectionDTO,Double>

    @FXML
    private lateinit var dateCol:TableColumn<CapitalInjectionDTO, LocalDate>

    @FXML
    private lateinit var  paymentCol : TableColumn<CapitalInjectionDTO, String>

    @FXML
    private lateinit var tcodeCol:TableColumn<CapitalInjectionDTO, String>

    @FXML
    private lateinit var textCol:TableColumn<CapitalInjectionDTO, String>

    @FXML
    private lateinit var captialtable:TableView<CapitalInjectionDTO>

    private val paymentLists = mutableListOf<String>()

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        datep.value = LocalDate.now()

        ComboxBoxData()

        tableIni()

        captialtable.items = getLoadCapitalInjection()

        addbtn.setOnAction {

            val amountText = amountxt.text.trim()
            if (amountText.isEmpty()) {
                showError("Amount is required")
                return@setOnAction
            }
            val amount = BigDecimal(amountText).takeIf { it > BigDecimal.ZERO } ?: run {
                showError("Amount must be greater than zero")
                return@setOnAction
            }

            val injectionDate = datep.value?.let { Date.valueOf(it) } ?: run {
                showError("Date is required")
                return@setOnAction
            }

            val description = descriptiontxt.text.trim().ifEmpty { null }
            val paymentMethodName = paymentcb.editor.text.takeIf { it.isNotEmpty() }
            val paymentID = paymentMethodName?.let { paymentMethodName ->
                paymentService.getAllPayment().find { it.methodName == paymentMethodName }?.method_Id?.toInt()
            }

            var id =  chartOfAccountsService.allData.find { it.account_name=="Owner’s Capital" }?.account_id ?.toInt()


            val transaction = Transaction()
            val payment: Payment? = Payment()

            payment?.method_Id = paymentID


            // Create Transactions
            transaction.transaction_id = "TXN-${UUID.randomUUID().toString().substring(0, 8)}"
            transaction.transaction_date = Timestamp(System.currentTimeMillis())
            transaction.reference_no =  "REF-${UUID.randomUUID().toString().substring(0, 8)}"
            transaction.total_amount =  amount
            transaction.paid_amount =  amount
            transaction.statu = "Paid"
            transaction.notes = description
            transaction.account_id= ChartOfAccounts(id as Int)
            transaction.payment =payment

            // Create CapitalInjections
            val capitalInjection = CapitalInjection()
            capitalInjection.injection_date = injectionDate
            capitalInjection.amount = amount.toDouble()
            capitalInjection.description = description
            capitalInjection.transaction = transaction













        }





    }



    private fun ComboxBoxData(){
        paymentLists.addAll(paymentService.getAllPayment()

            .stream()
            .map { payment->payment.methodName }
            .collect(Collectors.toList())

        )

        paymentcb.items = FXCollections.observableArrayList(paymentLists)




        // Add listener for autocomplete functionality
        paymentcb.editor.textProperty().addListener { _, _, newValue ->
            if (newValue.isEmpty()) {
                paymentcb.items = FXCollections.observableArrayList(paymentLists)
            } else {
                val filteredList = paymentLists.filter { it.contains(newValue, ignoreCase = true) }
                paymentcb.items = FXCollections.observableArrayList(filteredList)
                paymentcb.show() // Show dropdown with suggestions
            }
        }

        // Handle Enter key to select suggestion
        paymentcb.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER && paymentcb.selectionModel.selectedItem != null) {
                paymentcb.editor.text = paymentcb.selectionModel.selectedItem
                paymentcb.hide()
            }
        }

        // Ensure dropdown shows when typing starts
        paymentcb.setOnMouseClicked {
            if (paymentcb.editor.text.isEmpty()) {
                paymentcb.items = FXCollections.observableArrayList(paymentLists)
                paymentcb.show()
            }
        }
    }

    private fun getLoadCapitalInjection(): ObservableList<CapitalInjectionDTO> {
        return FXCollections.observableArrayList(capitalService.getCaptialInjectdataView())
    }

    private fun tableIni(){


        amountCol.setCellValueFactory { SimpleObjectProperty(it.value.amount) }
        dateCol.setCellValueFactory { SimpleObjectProperty(it.value.capitalDate) }
        paymentCol.setCellValueFactory { SimpleStringProperty(it.value.paymentMethodName) }
        tcodeCol.setCellValueFactory { SimpleStringProperty(it.value.transactionCode) }
        textCol.setCellValueFactory { SimpleStringProperty(it.value.description) }
    }


    private fun showError(message: String) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = "Error"
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }

    private fun showInfo(message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "Success"
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}