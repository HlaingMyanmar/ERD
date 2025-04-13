package org.erd.capitaloptions.controller

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import org.erd.paymentoptions.service.PaymentService
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.ResourceBundle
import java.util.stream.Collectors


@Controller
class CapitalInjectionController(private val paymentService: PaymentService) : Initializable {

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

    private val paymentLists = mutableListOf<String>()

    override fun initialize(location: URL?, resources: ResourceBundle?) {

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
                paymentcb.hide() // Hide dropdown after selection
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
}