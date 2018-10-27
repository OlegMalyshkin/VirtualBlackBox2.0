import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Virtual Black Box is a special program which implements new method of
 * saving an important information from aircraft which is called flight data.
 * A flight recorder is also known like a black box is an electronic recording
 * device placed in an aircraft for the purpose of facilitating the investigation
 * of aviation accidents and incidents.
 * This program imitates two processes:
 * 1st - recording flight data to cloud storage.
 * Flight parameters comes from Flight Gear (flight simulator) and recording in database.
 * 2nd - reading parameters from cloud database.
 * This parameters display in listViews and plotting graphs for each.
 * All processes occur in real time.
 *
 * @author Oleg Malyshkin
 * @version 2.0
 */
public class VirtualBlackBox extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Virtual Black Box");
        Parent root = FXMLLoader.load(
                getClass().getResource("/javafx/view/virtualBlackBox.fxml")
        );
        Scene scene = new Scene(root);
        scene.getStylesheets().add("javafx/style/style.css");
        primaryStage.setOnCloseRequest((windowEvent) -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
