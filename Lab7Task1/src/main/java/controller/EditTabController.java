package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import manager.ControllerManager;

import java.sql.Connection;
import java.sql.SQLException;

public class EditTabController
{

	@FXML private JFXTextField idArea;
	@FXML private JFXTextField nameArea;
	@FXML private JFXTextField ramArea;
	@FXML private JFXTextField vendorIdArea;
	@FXML private JFXButton updateButton;
	@FXML private JFXButton insertButton;

	@FXML
	void onMouseInsertClick(MouseEvent event)
	{
		if (
				idArea.getText().compareTo("") == 0 &&
						nameArea.getText().compareTo("") == 0 &&
						ramArea.getText().compareTo("") == 0 &&
						vendorIdArea.getText().compareTo("") == 0
				)
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Missing data.");
			alert.setContentText("You have to fill all data to insert into data base.");
			alert.showAndWait();
			return;
		}

		Connection conn = DataBaseController.getInstance().getConnection_();
		try
		{
			conn.prepareStatement(
					"INSERT INTO lab.pc (name,ram,vendor_id)" +
							"VALUES ('" +
							nameArea.getText() + "', '" +
							ramArea.getText() + "', '" +
							vendorIdArea.getText() +
							"')"
			).execute();
		} catch (SQLException e)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Problem with insert");
			alert.setHeaderText("Are you certain, you have given correct data?");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			return;
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Success.");
		alert.setContentText("Insert have been successful.");
		alert.showAndWait();
		ControllerManager
				.getInstance()
				.getResourceScreenController()
				.loadAdvancedClick(null);
	}

	@FXML
	void onMouseUpdateClick(MouseEvent event)
	{
		if (idArea.getText().compareTo("") == 0)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Missing id.");
			alert.setContentText("You have to fill id, to allow a data edit");
			alert.showAndWait();
			return;
		}

		try
		{
			Connection conn = DataBaseController.getInstance().getConnection_();

			if (nameArea.getText().compareTo("") != 0)
			{
				System.out.println("UPDATE lab.pc " +
						"SET lab.pc.name=`" + nameArea.getText() +
						"` WHERE id=" +idArea.getText());
				conn.prepareStatement(
						"UPDATE lab.pc " +
								"SET name='" + nameArea.getText() +
								"' WHERE id=" +idArea.getText()
				).execute();
			}
			if (ramArea.getText().compareTo("") != 0)
			{
				System.out.println("UPDATE lab.pc " +
								"SET lab.pc.ram=" + ramArea.getText() +
								" WHERE id=" +idArea.getText());
				conn.prepareStatement(
						"UPDATE lab.pc " +
								"SET ram='" + ramArea.getText() +
								"' WHERE id=" +idArea.getText()
				).execute();
			}
			if (vendorIdArea.getText().compareTo("") != 0)
			{
				System.out.println("UPDATE lab.pc " +
						"SET lab.pc.vendor_id=" + vendorIdArea.getText() +
						" WHERE id=" +idArea.getText());
				conn.prepareStatement(
						"UPDATE lab.pc " +
								"SET vendor_id='" + vendorIdArea.getText() +
								"' WHERE id=" +idArea.getText()
				).execute();
			}
		} catch (SQLException e)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("SQL hates me.");
			alert.showAndWait();
			return;
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("SUCCESS");
		alert.setHeaderText("UPDATE succeeded");
		alert.showAndWait();
		ControllerManager
				.getInstance()
				.getResourceScreenController()
				.loadAdvancedClick(null);
	}

}
