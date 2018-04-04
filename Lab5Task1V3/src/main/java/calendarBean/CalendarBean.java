package calendarBean;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;

public class CalendarBean extends AnchorPane {
    private Thread timeChecker;
    @FXML
    private CheckBox activeCheckBox;

    private SimpleObjectProperty<LocalDateTime> notificationDateProperty;
    private SimpleBooleanProperty alarmFired;

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
        notificationDateProperty = new SimpleObjectProperty<>(this, "notificationDateProperty");
        alarmFired = new SimpleBooleanProperty(false);
        notificationDateProperty.set(LocalDateTime.now());
        updateTime();
    }

    private void updateTime() {
    }

    public SimpleObjectProperty<LocalDateTime> notificationDateProperty() {
        return notificationDateProperty;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDateProperty.get();
    }

    public void setNotificationDate(LocalDateTime newNotificationDate) throws DateTimeException {
        LocalDateTime currentTime = LocalDateTime.now();
        if (newNotificationDate.isBefore(currentTime)) {
            throw new DateTimeException("Invalid date!");
        } else {
            notificationDateProperty.set(newNotificationDate);
            updateTime();
        }
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

    public boolean getAlarmFired() {
        return alarmFired.get();
    }

    public BooleanProperty alarmFiredProperty() {
        return alarmFired;
    }

    public void statusCheckBoxPressed(ActionEvent actionEvent) {
        if (activeCheckBox.isSelected()) {
        }
    }
}
