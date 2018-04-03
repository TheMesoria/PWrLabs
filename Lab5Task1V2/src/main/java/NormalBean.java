package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class NormalBean extends BorderPane{
	public NormalBean()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/NormalBean.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException var3) {
			throw new RuntimeException(var3);
		}
	}

	@FXML
	private BorderPane MainPane;

	@FXML
	private CheckBox lockCheckBox;

	@FXML
	private Button changeButton;

	@FXML
	private Label labelStandardText;

	@FXML
	void onButtonMouseClick(MouseEvent event) {
		if(lockCheckBox.isSelected()) return;

		if(labelStandardText.getText().compareTo("Changed")==0)
			labelStandardText.setText("This is standard Text");
		else
			labelStandardText.setText("Changed");

	}

	public BorderPane getMainPane()
	{
		return MainPane;
	}

	public void setMainPane(BorderPane mainPane)
	{
		MainPane = mainPane;
	}

	public CheckBox getLockCheckBox()
	{
		return lockCheckBox;
	}

	public void setLockCheckBox(CheckBox lockCheckBox)
	{
		this.lockCheckBox = lockCheckBox;
	}

	public Button getChangeButton()
	{
		return changeButton;
	}

	public void setChangeButton(Button changeButton)
	{
		this.changeButton = changeButton;
	}

	public Label getLabelStandardText()
	{
		return labelStandardText;
	}

	public void setLabelStandardText(Label labelStandardText)
	{
		this.labelStandardText = labelStandardText;
	}
}
