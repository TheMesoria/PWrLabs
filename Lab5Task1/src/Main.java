import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		SimpleBean simpleBean = new SimpleBean();
		Scene scene = new Scene(new Group());
		primaryStage.setTitle("MainScreen");
		primaryStage.setWidth(300);
		primaryStage.setHeight(300);

		simpleBean.setSpacing(10);
		((Group) scene.getRoot()).getChildren().add(simpleBean);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
