import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.text.ZoneView;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainSceneController
{
	@FXML public Label counter;
	@FXML private Menu help;
	@FXML private Menu language;
	@FXML private MenuItem languageUs;
	@FXML private MenuItem languageEn;
	@FXML private MenuItem languagePl;
	@FXML private FlowPane mainFlowPane;
	@FXML private Button loadButton;

	LinkedList<PreviewItem> controllers = new LinkedList<>();

	DataLoader dl = new DataLoader();

@FXML
	public void loadButtonClick()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(LanguageHolder.getBundle().getString("file-chooser.title"));

		FXMLLoader fl = DataLoader.previewItemFactory(fileChooser.showOpenDialog(new Stage()));
		((PreviewItem)fl.getController()).update();
		controllers.add(fl.getController());
		mainFlowPane.getChildren().add(fl.getRoot());
		bumpCounter();
	}
@FXML
	public void changeLanguagePl(ActionEvent actionEvent)
	{
		LanguageHolder.setResourceBundle(ResourceBundle.getBundle("lang.pl_PL"));
		Locale.setDefault(Locale.JAPAN);
		LanguageHolder.currency=Currency.getInstance(Locale.getDefault());
		System.out.println(LanguageHolder.currency.getDisplayName());
		reload();
	}
@FXML
	public void changeLanguageEn(ActionEvent actionEvent)
	{
		LanguageHolder.setResourceBundle(ResourceBundle.getBundle("lang.en_EN"));
		Locale.setDefault(Locale.UK);
		LanguageHolder.currency=Currency.getInstance(Locale.getDefault());
		reload();
	}
@FXML
	public void changeLanguageUs(ActionEvent actionEvent)
	{
		LanguageHolder.setResourceBundle(ResourceBundle.getBundle("lang.us_US"));
		Locale.setDefault(Locale.US);
		LanguageHolder.currency=Currency.getInstance(Locale.getDefault());
		reload();
	}

	public void reload()
	{
		ResourceBundle resources = LanguageHolder.getBundle();
		loadButton.setText(resources.getString("main-scene.load"));
		language.setText(resources.getString("main-scene.language"));
		languagePl.setText(resources.getString("main-scene.languagePl"));
		languageEn.setText(resources.getString("main-scene.languageEn"));
		languageUs.setText(resources.getString("main-scene.languageUS"));
		help.setText(resources.getString("main-scene.help"));

		for(PreviewItem pi : controllers)
			pi.update();

		bumpCounter();
	}

	private void bumpCounter()
	{
		String counterText = Integer.toString(controllers.size()) + " " + LanguageHolder.getBundle().getString("main-scene.object");

		if(Locale.getDefault()!=Locale.JAPAN)
		{
			if(controllers.size()>1)
				counterText+="s";
		}
		else
		{
			if(controllers.size()%10==1 || controllers.size()%10==0 && controllers.size()!=1)
				counterText+="ów";
			else if (controllers.size()>1)
				counterText+="y";

		}

		if(controllers.size()==0)
			counterText="";

		counter.setText(counterText);
	}
}
