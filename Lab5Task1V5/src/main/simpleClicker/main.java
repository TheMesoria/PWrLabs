import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class main extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
//		File file = new File(getClass().getResource(""));
		System.out.println(getClass().getResource("/SimpleClicker.fxml").toString());
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SimpleClicker.fxml"));
		fxmlLoader.load();
		Scene scene = new Scene(fxmlLoader.getRoot());
		primaryStage.setScene(scene);
		primaryStage.showAndWait();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
