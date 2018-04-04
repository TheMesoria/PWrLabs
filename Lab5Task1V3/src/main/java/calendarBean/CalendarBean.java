package calendarBean;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;

public class CalendarBean extends AnchorPane {
    @FXML
    private CheckBox activeCheckBox;
    @FXML
    private Button butonButon;
    @FXML
    private Label label;

    private SimpleIntegerProperty clickLimit;
    private SimpleIntegerProperty clicks;

    public int getClickLimit()
    {
        return clickLimit.get();
    }

    public SimpleIntegerProperty clickLimitProperty()
    {
        return clickLimit;
    }

    public void setClickLimit(int clickLimit)
    {
        this.clickLimit.set(clickLimit);
    }

    public int getClicks()
    {
        return clicks.get();
    }

    public SimpleIntegerProperty clicksProperty()
    {
        return clicks;
    }

    public void setClicks(int clicks)
    {
        this.clicks.set(clicks);
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        if(activeCheckBox.isSelected() && getClickLimit() > getClicks()){
            setClicks(clicks.get()+1);
            label.setText(Integer.toString(getClicks()));
        }
    }

    public CalendarBean() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/CalendarBean.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize() {
    }



    public void setActive(boolean isActive){
        activeCheckBox.selectedProperty().setValue(isActive);
    }
    public boolean getActive(){
        return activeCheckBox.selectedProperty().get();
    }
    public BooleanProperty activeProperty(){
        return activeCheckBox.selectedProperty();
    }

    public void statusCheckBoxPressed(ActionEvent actionEvent) {
        if (activeCheckBox.isSelected()) {
        }
    }
}
