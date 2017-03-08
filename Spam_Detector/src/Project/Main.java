package Project;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
/**
 * Created by miral on 19/02/17.
 */
public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{

        // load the fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        // get an instance of the controller
        Controller controller = loader.getController();

        primaryStage.setTitle("Spam Detector 3000");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);
        File trainPath = new File(mainDirectory+"/train");
        File testPath = new File(mainDirectory+"/test");

        // make an instance of the WordCounter class
        WordCounter word = new WordCounter();
        word.traverseDirectory(trainPath);
        word.calculateProbability();
        word.traverseDirectory(testPath);

        controller.setAccuracy_field(Double.toString(word.calculateAccuracy()));
        controller.setPrecision_field(Double.toString(word.calculatePrecision()));

        primaryStage.setScene(new Scene(root, 750, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}