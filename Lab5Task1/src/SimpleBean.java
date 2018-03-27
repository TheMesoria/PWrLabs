import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class SimpleBean
		extends VBox
			implements java.io.Serializable
{
	private String textProperty; // Simple Property
	private boolean initialisedProperty; // Connected Property
	private SimpleBean simpleBeanProperty; // Simple Property, being controlled be Connected Property
	private int sizeProperty; // Limited Property - cannot be bigger than this

	public SimpleBean()
	{
		this("Initialised",false,null,30);
		TextField tft = new TextField(getTextProperty());
		TextField tfc = new TextField(Integer.toString(getSizeProperty()));
		Label enabledLabel = new Label(isInitialisedProperty()?"True":"False");
		Button enableButton = new Button("Change status");
		Button inputButton = new Button("Change values");
		this.getChildren().addAll(tft,tfc,enabledLabel,enableButton,inputButton);
	}
	private SimpleBean(String textProperty, boolean initialisedProperty, SimpleBean simpleBeanProperty, int sizeProperty)
	{
		this.initialisedProperty = initialisedProperty;
		this.simpleBeanProperty = simpleBeanProperty;
		this.sizeProperty = sizeProperty;
		this.textProperty = textProperty;
	}

	public String getTextProperty()
	{
		return textProperty;
	}
	public void setTextProperty(String textProperty) throws Exception
	{
		if(textProperty.length()!=sizeProperty) throw new Exception("Yo'vdone it wrong'rodda.");
		this.textProperty = textProperty;
	}
	public boolean isInitialisedProperty()
	{
		return initialisedProperty;
	}
	public void setInitialisedProperty(boolean initialisedProperty)
	{
		if(initialisedProperty) setSimpleBeanProperty(new SimpleBean());
		else setSimpleBeanProperty(null);
		this.initialisedProperty = initialisedProperty;
	}
	public SimpleBean getSimpleBeanProperty()
	{
		return simpleBeanProperty;
	}
	public void setSimpleBeanProperty(SimpleBean simpleBeanProperty)
	{
		this.simpleBeanProperty = simpleBeanProperty;
	}
	public int getSizeProperty()
	{
		return sizeProperty;
	}
	public void setSizeProperty(int sizeProperty)
	{
		this.sizeProperty = sizeProperty;
	}
}