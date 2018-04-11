package simpleClicker;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SimpleClicker implements Initializable
{
	@FXML private Label counterLabel;
	@FXML private Label alarmStatus;
	@FXML private Button clickerButton;
	@FXML private Label triggerStatus;

	SimpleBooleanProperty alarmFire;
	SimpleIntegerProperty count;
	SimpleIntegerProperty limit;

	public SimpleClicker()
	{

	}

	@FXML
	void onMouseClicked(MouseEvent event)
	{
		Integer newVal = count.getValue()+1;
		if(newVal.equals(limit.getValue()))
		{
			if(!alarmFire.getValue())
				alarmFire.set(true);
			return;
		}

		count.setValue(newVal);
	}

	public boolean isAlarmFire()
	{
		return alarmFire.get();
	}
	public SimpleBooleanProperty alarmFireProperty()
	{
		return alarmFire;
	}
	public void setAlarmFire(boolean alarmFire)
	{
		this.alarmFire.set(alarmFire);
	}
	public int getCount()
	{
		return count.get();
	}
	public SimpleIntegerProperty countProperty()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count.set(count);
	}
	public int getLimit()
	{
		return limit.get();
	}
	public SimpleIntegerProperty limitProperty()
	{
		return limit;
	}
	public void setLimit(int limit)
	{
		this.limit.set(limit);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		alarmFire.set(false);
		alarmFire.addListener(e -> alarmStatus.setText("WOAH FIRE!"));
		count.addListener(e->counterLabel.setText(count.getValue().toString()));
		count.set(0);
		limit.set(10);
	}

}
