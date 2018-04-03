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
import java.time.format.DateTimeFormatter;


public class CalendarBean extends AnchorPane {
    private Thread timeChecker;
    @FXML
    private CheckBox activeCheckBox;
    @FXML
    private Text hourText;
    @FXML
    private Text dateText;
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
        hourText.setText(getFormattedTime());
        dateText.setText(getFormattedDate());
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
            runAlarm();
        }
    }

    private void runAlarm() {
        timeChecker = new Thread(this::checkNotificationDate);
        timeChecker.start();
    }

    private void checkNotificationDate() {
        while (activeCheckBox.isSelected() && !alarmFired.get()) {
            if (isNotificationTimeNow()) {
                alarmFired.set(true);
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFormattedDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return notificationDateProperty.get().format(dateTimeFormatter);
    }

    private String getFormattedTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return notificationDateProperty.get().format(dateTimeFormatter);
    }

    private boolean isNotificationTimeNow() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime notificationDate = getNotificationDate();
        return currentTime.isEqual(notificationDate) || currentTime.isAfter(notificationDate);
    }


}
