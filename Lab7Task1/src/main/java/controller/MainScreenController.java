package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import manager.ControllerManager;

import javax.xml.crypto.Data;

public class MainScreenController
{
	DataBaseController dataBaseController = DataBaseController.getInstance();
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Label infoLabel;
	@FXML
	private JFXTextField userNameTextField;
	@FXML
	private JFXPasswordField passwordField;
	@FXML
	private Label connectionStatusLabel;

	@FXML
	void keyPressed(KeyEvent event)
	{
		updateStatus();

		if (event.getCode().equals(KeyCode.ENTER))
		{
			if (dataBaseController.isConnectionReady())
			{
				if (dataBaseController.tryToLogin(
						userNameTextField.getText(),
						userNameTextField.getText()))
				{
					Stage stage = new Stage();
					Scene scene = new Scene(ControllerManager.getInstance().getResourceScreen());
					stage.setScene(scene);
					stage.showAndWait();
					mainPane.setVisible(false);
					System.exit(0);
				} else
				{
					infoLabel.setText("Login failed.");
					infoLabel.setTextFill(Color.RED);
				}
			} else
			{
				if (dataBaseController
						.connect(
								userNameTextField.getText(),
								passwordField.getText()
						))
				{
					userNameTextField.requestFocus();
					userNameTextField.setText("");
					passwordField.setText("");
					updateStatus();
				}
			}
		}
	}

	private void updateStatus()
	{
		if (!dataBaseController.isConnectionReady())
		{
			infoLabel.setText("Login to database.");
			infoLabel.setTextFill(Color.BLACK);
			connectionStatusLabel.setTextFill(Color.RED);
			connectionStatusLabel.setText("CONNECTION FAILED");
		} else
		{
			infoLabel.setText("Login to app.");
			infoLabel.setTextFill(Color.BLACK);
			connectionStatusLabel.setTextFill(Color.GREEN);
			connectionStatusLabel.setText("READY");
		}
	}
}
