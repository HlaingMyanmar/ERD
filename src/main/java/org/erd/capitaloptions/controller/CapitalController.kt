package org.erd.capitaloptions.controller

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import org.springframework.stereotype.Controller
import java.net.URL
import java.util.*

@Controller
class CapitalController() : Initializable {

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


//        paymentLists.addAll(paymentService.getAllPayment()
//
//            .stream()
//            .map { payment->payment.methodName }
//            .collect(Collectors.toList())
//
//        )
//
//        paymentcb.items = FXCollections.observableArrayList(paymentLists)
//
//
//
//
//        // Add listener for autocomplete functionality
//        paymentcb.editor.textProperty().addListener { _, _, newValue ->
//            if (newValue.isEmpty()) {
//                paymentcb.items = FXCollections.observableArrayList(paymentLists)
//            } else {
//                val filteredList = paymentLists.filter { it.contains(newValue, ignoreCase = true) }
//                paymentcb.items = FXCollections.observableArrayList(filteredList)
//                paymentcb.show() // Show dropdown with suggestions
//            }
//        }
//
//        // Handle Enter key to select suggestion
//        paymentcb.setOnKeyPressed { event ->
//            if (event.code == KeyCode.ENTER && paymentcb.selectionModel.selectedItem != null) {
//                paymentcb.editor.text = paymentcb.selectionModel.selectedItem
//                paymentcb.hide() // Hide dropdown after selection
//            }
//        }
//
//        // Ensure dropdown shows when typing starts
//        paymentcb.setOnMouseClicked {
//            if (paymentcb.editor.text.isEmpty()) {
//                paymentcb.items = FXCollections.observableArrayList(paymentLists)
//                paymentcb.show()
//            }
//        }


    }

}