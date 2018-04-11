package simpleClicker;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SimpleClicker extends AnchorPane implements Initializable, Serializable
{
	@FXML private Label counterLabel;
	@FXML private Label alarmStatus;
	@FXML private Button clickerButton;
	@FXML private Label triggerStatus;

	private SimpleBooleanProperty alarmFire;
	private SimpleIntegerProperty count;
	private SimpleIntegerProperty limit;

	public SimpleClicker()
	{
		alarmFire=new SimpleBooleanProperty();
		count=new SimpleIntegerProperty();
		limit=new SimpleIntegerProperty();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SimpleClicker.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try
		{
			fxmlLoader.load();
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}

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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SimpleClicker that = (SimpleClicker) o;
		return Objects.equals(counterLabel, that.counterLabel) &&
				Objects.equals(alarmStatus, that.alarmStatus) &&
				Objects.equals(clickerButton, that.clickerButton) &&
				Objects.equals(triggerStatus, that.triggerStatus) &&
				Objects.equals(alarmFire, that.alarmFire) &&
				Objects.equals(count, that.count) &&
				Objects.equals(limit, that.limit);
	}
	@Override
	public int hashCode()
	{

		return Objects.hash(counterLabel, alarmStatus, clickerButton, triggerStatus, alarmFire, count, limit);
	}
}
