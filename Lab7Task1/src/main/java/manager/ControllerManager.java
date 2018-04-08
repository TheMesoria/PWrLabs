package manager;

import controller.MainScreenController;
import controller.ResourcePreviewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ControllerManager
{
	private static ControllerManager controllerManagerInstance_;
	public static ControllerManager getInstance() {return controllerManagerInstance_;}

	static {controllerManagerInstance_ = new ControllerManager();}
	private FXMLLoader mainScreenHolder_;
	private FXMLLoader resourceScreenHolder_;

	private ControllerManager()
	{
		try
		{
			mainScreenHolder_ = new FXMLLoader(getClass().getResource("/MainView.fxml"));
			resourceScreenHolder_ = new FXMLLoader(getClass().getResource("/ResourcePreview.fxml"));
			mainScreenHolder_.load();
			resourceScreenHolder_.load();
		} catch (Exception e)
		{
			System.err.println("Load unsuccessful, shutting.");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public MainScreenController getMainScreenController() {return mainScreenHolder_.getController();}
	public Parent getMainScreen() {return mainScreenHolder_.getRoot();}
	public ResourcePreviewController getResourceScreenController() {return resourceScreenHolder_.getController();}
	public Parent getResourceScreen() {return resourceScreenHolder_.getRoot();}
}
