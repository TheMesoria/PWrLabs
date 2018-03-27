import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

public class SomeKindOfAwesomeBean extends VBox implements Serializable,
														   PropertyChangeListener
{
	private int number; /*Yes it is fully random*/

	public SomeKindOfAwesomeBean(){}
	public int getNumber()
	{
		return number;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
	}

	@Override
	public Node getStyleableNode()
	{
		return null;
	}
}
