package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import manager.ControllerManager;
import mo.ComboPc;
import mo.PC;
import mo.PcVendor;
import mo.User;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ResourcePreviewController
{
	Stage stage = new Stage();
	Scene scene = null;

	@FXML private JFXButton loadUsersButton;
	@FXML private JFXButton loadOtherButton;
	@FXML private JFXButton importDataButton;
	@FXML private JFXButton exportDataButton;

	@FXML public TableView<ComboPc> tableViewCombo;
	@FXML public TableColumn<PC, Integer> idColumnC;
	@FXML public TableColumn<PC, String> pcName;
	@FXML public TableColumn<PC, String> pcRam;
	@FXML public TableColumn<PcVendor, String> pcVendorName;
	@FXML public TableColumn<PcVendor, Integer> pcVendorNip;

	@FXML private TableView<User> tableView;
	@FXML private TableColumn<User, Integer> idColumn;
	@FXML private TableColumn<User, String> usernameColumn;
	@FXML private TableColumn<User, String> saltColumn;
	@FXML private TableColumn<User, String> passwordColumn;

	private ObservableList<User> observableList = FXCollections.observableArrayList();
	private ObservableList<ComboPc> observableListCombo = FXCollections.observableArrayList();

	@FXML
	void exportDataClick(MouseEvent event)
	{

	}

	@FXML
	void importDataClick(MouseEvent event)
	{

	}

	@FXML
	void loadAdvancedClick(MouseEvent event)
	{
		tableView.setVisible(false);
		tableViewCombo.setVisible(true);
		observableListCombo.clear();

		try
		{
			ResultSet rs = DataBaseController
					.getInstance()
					.getConnection_()
					.prepareStatement("SELECT pc.id, pc.name, pc.ram, vendor.name AS vendor, vendor.nip " +
							"FROM lab.pc INNER JOIN lab.vendor ON pc.vendor_id = vendor.id")
					.executeQuery();

			while (rs.next())
			{
				observableListCombo.add(
						new ComboPc(
								new PC(
										rs.getInt("id"),
										-1,
										rs.getString("name"),
										rs.getString("ram")
								),
								new PcVendor(
										-1,
										rs.getInt("nip"),
										rs.getString("vendor")
								)
						)
				);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		idColumnC.setCellValueFactory(new PropertyValueFactory<>("id"));
		pcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		pcRam.setCellValueFactory(new PropertyValueFactory<>("ram"));
		pcVendorName.setCellValueFactory(new PropertyValueFactory<>("vendorName"));
		pcVendorNip.setCellValueFactory(new PropertyValueFactory<>("nip"));

		tableViewCombo.setItems(observableListCombo);
	}

	@FXML
	void loadUsersClick(MouseEvent event)
	{
		tableViewCombo.setVisible(false);
		tableView.setVisible(true);
		observableList.clear();

		try
		{
			ResultSet rs = DataBaseController
					.getInstance()
					.getConnection_()
					.prepareStatement("SELECT * FROM lab.app_user")
					.executeQuery();

			while (rs.next())
			{
				observableList.add(
						new User(rs.getInt("id"),
								rs.getString("username"),
								rs.getString("salt"),
								rs.getString("password"))
				);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		saltColumn.setCellValueFactory(new PropertyValueFactory<>("salt"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

		tableView.setItems(observableList);
	}

	@FXML
	void onKeyPressed(KeyEvent event)
	{

	}
	public void onKeyPressedCombo(KeyEvent keyEvent)
	{
	}
	@FXML
	public void onMouseClickOpenEditWindow(MouseEvent mouseEvent)
	{
		//stage.setScene(scene);
		if (scene == null)
		{
			scene = new Scene(ControllerManager.getInstance().getEditTabScreen());
			stage.setScene(scene);
		}

		tableViewCombo.setDisable(true);
		stage.showAndWait();
		tableViewCombo.setDisable(false);
	}
}
