import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.util.*;

public class PreviewItem
{
	@FXML public ImageView image;
	@FXML public Label price;
	@FXML public Label date;
	private int value = 0;
	private Calendar dateValue;

	public void init(Calendar date, int value, Image image)
	{
		this.image.setImage(image);
		this.image.setFitHeight(100);
		this.image.setFitWidth(100);
		this.dateValue=date;
		this.value=value;
	}
	void update()
	{
		if(LanguageHolder.currency.getDisplayName().equals("British Pound"))
		{
			price.setText(Integer.toString(value / 4) + LanguageHolder.currency.getSymbol());
			date.setText(
							String.valueOf(dateValue.get(Calendar.DAY_OF_WEEK)) + "/" +
							String.valueOf(dateValue.get(Calendar.MONTH)) + "/" +
							String.valueOf(dateValue.get(Calendar.YEAR))
						);
		}
		if(LanguageHolder.currency.getDisplayName().equals("US Dollar"))
		{
			price.setText(Integer.toString(value/3)+ LanguageHolder.currency.getSymbol());
			date.setText(
							String.valueOf(dateValue.get(Calendar.MONTH)) + "/" +
							String.valueOf(dateValue.get(Calendar.DAY_OF_WEEK)) + "/" +
							String.valueOf(dateValue.get(Calendar.YEAR))
						);
		}
		if(LanguageHolder.currency.getDisplayName().equals("日本円"))
		{
			price.setText(Integer.toString(value) + "zł");
			date.setText(
							String.valueOf(dateValue.get(Calendar.DAY_OF_WEEK)) + "/" +
							String.valueOf(dateValue.get(Calendar.MONTH)) + "/" +
							String.valueOf(dateValue.get(Calendar.YEAR))
						);
		}
	}
}
