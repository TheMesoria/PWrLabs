import controller.DataBaseController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.ControllerManager;

import java.sql.*;

public class DataBaseApplication extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception
	{
		Scene loginScene = new Scene(ControllerManager.getInstance().getMainScreen());
		primaryStage.setScene(loginScene);
		primaryStage.show();
	}
}
