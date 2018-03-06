import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class DataLoader
{
	public static FXMLLoader previewItemFactory(File file)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(DataLoader.class.getResource("view/Item.fxml"));

		LinkedList<String> data = new LinkedList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			fxmlLoader.load();
			String line;
			while ((line = br.readLine()) != null)
			{
				data.add(line);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		File fileI = new File(data.get(0));
		Image image = new Image(fileI.toURI().toString());

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date(
				Integer.parseInt(data.get(1)),
				Integer.parseInt(data.get(2)),
				Integer.parseInt(data.get(3))
				));
		((PreviewItem)fxmlLoader.getController()).init(gc,Integer.parseInt(data.get(4)),image);

		return fxmlLoader;
	}
}
