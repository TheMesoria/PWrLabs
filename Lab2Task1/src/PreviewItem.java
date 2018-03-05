import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.text.DateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class PreviewItem
{
	@FXML public ImageView image;
	@FXML public Label price;
	@FXML public Label date;
	private int value = 0;
	private Date dateValue;

	public void init(Date date, int value)
	{
		this.dateValue=date;
		this.value=value;
	}
	void update()
	{
		if(LanguageHolder.currency.getDisplayName().equals("British Pound"))
		{
			price.setText(Integer.toString(value / 4) + LanguageHolder.currency.getSymbol());
			DateFormat df =  LanguageHolder.getDateFormat();
			date.setText();
		}
		if(LanguageHolder.currency.getDisplayName().equals("US Dollar"))
		{
			price.setText(Integer.toString(value/3)+ LanguageHolder.currency.getSymbol());
		}
		if(LanguageHolder.currency.getDisplayName().equals("Japanese Yen"))
		{
			price.setText(Integer.toString(value) + LanguageHolder.currency.getSymbol());
		}
	}
}
