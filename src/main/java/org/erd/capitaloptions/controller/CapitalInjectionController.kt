package org.erd.capitaloptions.controller

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
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
import org.erd.paymentoptions.service.PaymentService
import org.springframework.stereotype.Controller
import java.net.URL
import java.sql.Date
import java.time.LocalDate
import java.util.ResourceBundle
import java.util.stream.Collectors


@Controller
class CapitalInjectionController(private val paymentService: PaymentService , private val capitalService: CaptialService) : Initializable {

    @FXML
    private lateinit var addbtn : Button

    @FXML
    private lateinit var amounttxt: TextField

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



}