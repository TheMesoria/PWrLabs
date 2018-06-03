package controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private JFXCheckBox checkBoxColor;
    @FXML private AnchorPane mainAnchorPane;
    @FXML private JFXToggleButton toggleButton;
    @FXML private VBox toggleVBox;
    @FXML private JFXColorPicker colorPicker;
    @FXML private MenuItem menuItemDeception;
    @FXML private MenuItem menuItemTruth;
    @FXML private AnchorPane toggleAnchor;
    @FXML private ComboBox<String> comboBox;
    @FXML private JFXListView<String> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //SetColorPicker to some value
        colorPicker.setValue(Color.CORAL);

        // Disable panes on start
        toggleAnchor.setDisable(true);
        toggleVBox.setDisable(true);

        // Load combo Box
        comboBox.getItems().addAll(
                "Ugh",
                "Ugly",
                "Ok",
                "Nice"
        );

        //Load List View
        listView.getItems().addAll(
          "Choice 1",
          "Choice 2",
          "Choice 3",
          "Choice 4",
          "Choice 5"
        );
    }

    @FXML
    void changeColorToBlack(ActionEvent event) {
        mainAnchorPane.setStyle("-fx-background-color: black");
    }

    @FXML
    void changeColorToBlue(ActionEvent event) {
        mainAnchorPane.setStyle("-fx-background-color: blue");
    }

    @FXML
    void changeColorToRed(ActionEvent event) {
        mainAnchorPane.setStyle("-fx-background-color: red");
    }

    @FXML
    void toggleFunctionality(MouseEvent event) {
        if ( toggleButton.isSelected() )
        {
            toggleAnchor.setDisable(false);
            toggleVBox.setDisable(false);
        }
        else
        {
            toggleAnchor.setDisable(true);
            toggleVBox.setDisable(true);
        }
    }

    public void changeBackgroundColor(ActionEvent actionEvent) {
        if ( checkBoxColor.isSelected() )
        {
            mainAnchorPane.setStyle("-fx-background-color: #" + colorPicker.getValue().toString().substring(2));
        }
        else
        {
            mainAnchorPane.setStyle(null);
        }
    }

    public void menuItemDeceptionChoice(ActionEvent actionEvent) {
        checkBoxColor.setCheckedColor(Color.LIME);
        checkBoxColor.setUnCheckedColor(Color.RED);
    }

    public void menuItemTruthChoice(ActionEvent actionEvent) {
        checkBoxColor.setStyle(null);
        checkBoxColor.setCheckedColor(Color.GREEN);
        checkBoxColor.setUnCheckedColor(Color.BLACK);
    }

    public void menuClose(ActionEvent actionEvent) {
        Stage stage = (Stage) toggleVBox.getScene().getWindow();
        stage.close();
    }

    public void menuDelete(ActionEvent actionEvent) {
        toggleVBox.setDisable(!toggleVBox.isDisabled());
    }

    public void menuAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("It's all about nothing.");
        alert.setContentText("Would say Nietzsche");
        alert.showAndWait();
    }
}
