import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
	@Override
	public void start(Stage stage) throws
						IOException
	{
		//LanguageHolder.setResourceBundle(ResourceBundle.getBundle("lang.pl_PL"));
		FXMLLoader loader= new FXMLLoader(getClass().getResource("view/MainScene.fxml"));
		loader.load();
		Parent root = loader.getRoot();
		MainSceneController msc = loader.getController();
		msc.reload();

		stage.getIcons().add(new Image(getClass().getResource("image.png").toExternalForm()));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Test");
		stage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
