package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import mo.User;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ResourcePreviewController
{

	@FXML
	private JFXButton loadUsersButton;

	@FXML
	private JFXButton loadOtherButton;

	@FXML
	private JFXButton importDataButton;

	@FXML
	private JFXButton exportDataButton;

	@FXML
	private TableView<User> tableView;

	@FXML
	private TableColumn<User, Integer> idColumn;

	@FXML
	private TableColumn<User, String> usernameColumn;

	@FXML
	private TableColumn<User, String> saltColumn;

	@FXML
	private TableColumn<User, String> passwordColumn;

	ObservableList<User> observableList = FXCollections.observableArrayList();

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

	}

	@FXML
	void loadUsersClick(MouseEvent event)
	{

	}

	@FXML
	void onKeyPressed(KeyEvent event)
	{

	}

	public void start()
	{
		try
		{
			System.out.println("Lets go");
			ResultSet rs = DataBaseController
					.getInstance()
					.getConnection_()
					.prepareStatement("SELECT * FROM lab.app_user")
					.executeQuery();
			System.out.println("1) READY");
			System.out.println(rs.toString());
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
		System.out.println("2) READY");

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		saltColumn.setCellValueFactory(new PropertyValueFactory<>("salt"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

		tableView.setItems(observableList);
	}
}
