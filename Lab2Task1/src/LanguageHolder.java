import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageHolder
{
	static ResourceBundle resourceBundle = ResourceBundle.getBundle("lang.en_EN");
	static public Currency currency = Currency.getInstance(Locale.UK);
	/** Get active language bundle
	 *
	 * @return actual language bundle
	 * @throws NullPointerException throwed when resource bundle have been not initialised
	 */
	static ResourceBundle getBundle() throws NullPointerException
	{
		if (resourceBundle==null) throw new NullPointerException();
		return resourceBundle;
	}
	static void setResourceBundle(ResourceBundle rb)
	{
		if(rb==null) return;

		resourceBundle=rb;
	}
	static DateFormat getDateFormat()
	{
		return DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
	}
	static NumberFormat getNumberFormat()
	{
		return NumberFormat.getNumberInstance(Locale.getDefault());
	}
	static DecimalFormat getDecimalFormat()
	{
		return (DecimalFormat)getNumberFormat();
	}
}
