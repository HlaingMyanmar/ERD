package org.erd.categoryoptions.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import org.erd.categoryoptions.model.Category;
import org.erd.categoryoptions.service.CategoryService;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

@Controller
public class CategoryController implements Initializable {

    private final Category category;
    @FXML
    private TableView<Category> categorytable;

    @FXML
    private TableColumn<Category, String> descCol;

    @FXML
    private TextArea desctxt;

    @FXML
    private Button editbtn;

    @FXML
    private Button insertbtn;

    @FXML
    private TableColumn<Category, String> nameCol;

    @FXML
    private TextField nametxt;

    private final CategoryService categoryService;

    private final Validator validator;

    public CategoryController(CategoryService categoryService, Validator validator, Category category) {
        this.categoryService = categoryService;
        this.validator = validator;
        this.category = category;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        categoryTableIni();
        getCategoryLoadData();


        insertbtn.setOnAction(event -> {

            String categoryName = nametxt.getText();
            String desc = desctxt.getText();

            Category category = new Category(categoryName, desc);


            if(testRoleValidate(category)){


                boolean result = categoryService.addCategory(category);

                if(result){
                    showInformationDialog("အမျိုးအစား", "အောင်မြင်သည်။", "အမျိုးအစား အောင်မြင်သည်။");
                    setClear();
                }
                else{

                    showErrorDialog("အမျိုးအစား", "မအောင်မြင်ပါ။", "အမျိုးအစားထည့်ခြင်း မအောင်မြင်ပါ။");

                }


            }



        });

        categorytable.setOnMouseClicked(event -> {

            if(event.getClickCount() == 2){

                Category index = categorytable.getSelectionModel().getSelectedItem();

                nametxt.setText(index.getCategory_name());
                desctxt.setText(index.getDescription());

                int id = getIDFromCategorydb(index.getCategory_name(),index.getDescription());

                editbtn.setOnAction(event1 -> {

                    String categoryName = nametxt.getText();
                    String desc = desctxt.getText();

                    Category category = new Category(id,categoryName, desc);

                    if(testRoleValidate(category)){


                        boolean result = categoryService.updateCategory(category);
                        if(result){

                            showInformationDialog("အမျိုးအစား", "အောင်မြင်သည်။", "အမျိုးအစား ပြုပြင်ခြင်းအောင်မြင်သည်။");
                            setClear();
                        }
                        else{
                            showErrorDialog("အမျိုးအစား", "မအောင်မြင်ပါ။", "အမျိုးအစားပြုပြင်ခြင်း မအောင်မြင်ပါ။");

                        }
                    }

                });

            }

        });

        categorytable.setOnKeyPressed(event -> {


            if(event.getCode() == KeyCode.DELETE){

                Category index = categorytable.getSelectionModel().getSelectedItem();

                int id = getIDFromCategorydb(index.getCategory_name(),index.getDescription());

                if(showConfirmDialog("အမျိးအစား", "ဖျက်မည်။", "ဤ အမျိးအစားကို ဖျက်မှာသေချာပြီလား")){

                    boolean result = categoryService.deleteCategory(id);

                    if(result){

                        showInformationDialog("အမျိုးအစား", "အောင်မြင်သည်။", "အမျိုးအစား ဖျက်ခြင်းအောင်မြင်သည်။");
                        setClear();
                    }
                    else{
                        showErrorDialog("အမျိုးအစား", "မအောင်မြင်ပါ။", "အမျိုးအစားးဖျက်ခြင်းမအောင်မြင်ပါ။");

                    }
                }

            }
        });


    }

    private int getIDFromCategorydb(String name, String desc){

        return categoryService.getCategories()
                .stream()
                .filter(category1 -> category1.getCategory_name().equals(name) && category1.getDescription().equals(desc))
                .map(Category::getCategory_id).findFirst().orElse(-1);

    }

    private void getCategoryLoadData() {

        ObservableList<Category> categories = FXCollections.observableArrayList(categoryService.getCategories());

        categorytable.setItems(categories);

    }

    private void categoryTableIni() {


        nameCol.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    private boolean testRoleValidate(Category category) {

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        if(!violations.isEmpty()){

            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Category> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n\n");

            }


            showErrorDialog("ဒေတာဘေစ် အမှား","ဒေတာ လိုအပ်ချက်များရှိနေသည်။",errorMessages.toString());

            return false;


        }
        else {

            return true;

        }



    }

    private void setClear(){

        nametxt.setText("");
        desctxt.setText("");

        getCategoryLoadData();


    }

    private void showErrorDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private void showInformationDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private boolean showConfirmDialog(String title, String header, String content) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;


    }


}
