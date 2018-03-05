import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainSceneController
{
	@FXML private Menu help;
	@FXML private Menu language;
	@FXML private MenuItem languageUs;
	@FXML private MenuItem languageEn;
	@FXML private MenuItem languagePl;
	@FXML private FlowPane mainFlowPane;
	@FXML private Button loadButton;
	@FXML private Button saveButton;
	@FXML private Button editButton;

	DataLoader dl = new DataLoader();

@FXML
	public void loadButtonClick()
	{

	}
@FXML
	public void saveButtonClick(ActionEvent actionEvent)
	{

	}
@FXML
	public void editButtonClick(ActionEvent actionEvent)
	{

	}
@FXML
	public void changeLanguagePl(ActionEvent actionEvent)
	{
		LanguageHolder.setResourceBundle(ResourceBundle.getBundle("lang.pl_PL"));
		Locale.setDefault(Locale.GERMAN);
		reload();
	}
@FXML
	public void changeLanguageEn(ActionEvent actionEvent)
	{
		LanguageHolder.setResourceBundle(ResourceBundle.getBundle("lang.en_EN"));
		Locale.setDefault(Locale.US);
		reload();
	}
@FXML
	public void changeLanguageUs(ActionEvent actionEvent)
	{
		LanguageHolder.setResourceBundle(ResourceBundle.getBundle("lang.us_US"));
		Locale.setDefault(Locale.ENGLISH);
		reload();
	}

	public void reload()
	{
		ResourceBundle resources = LanguageHolder.getBundle();

		System.out.println(resources.getString("main-scene.languageUS"));

		editButton.setText(resources.getString("main-scene.edit"));
		loadButton.setText(resources.getString("main-scene.load"));
		saveButton.setText(resources.getString("main-scene.save"));
		language.setText(resources.getString("main-scene.language"));
		languagePl.setText(resources.getString("main-scene.languagePl"));
		languageEn.setText(resources.getString("main-scene.languageEn"));
		languageUs.setText(resources.getString("main-scene.languageUS"));
		help.setText(resources.getString("main-scene.help"));
	}
}
