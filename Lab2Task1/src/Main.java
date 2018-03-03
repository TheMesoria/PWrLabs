import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class Main
{
	public void start() throws
						IOException
	{
		ResourceBundle rb = ResourceBundle.getBundle("lang.pl_PL");
		System.out.println(rb.getString("name"));
		Parent root = FXMLLoader.load(getClass().getResource("view/MainScene.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Test");
		stage.show();
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		try
		{
			main.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
