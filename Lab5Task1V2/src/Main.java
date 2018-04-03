import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		System.out.println("Intro");
		Parent parent = FXMLLoader.load(getClass().getResource("NormalBean.fxml"));
		Scene scene = new Scene(parent);
		System.out.println("It's ready");
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println("If you do not see, you failed...");
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
