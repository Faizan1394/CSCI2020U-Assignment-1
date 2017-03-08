package Project;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spam Detector 3000");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);


        // make an instance of the WordCounter class
        WordCounter word = new WordCounter();
        // call the processFile function in WordCounter class and send it the file containing all the data

        File trainPath = new File(mainDirectory+"/train");
        File testPath = new File(mainDirectory+"/test");
        word.traverseDirectory(trainPath);
        word.calculateProbability();
        word.traverseDirectory(testPath);

        //  word.printWordCounts();
        System.out.println(word.calculateAccuracy());
        System.out.println(word.calculatePrecision());


        primaryStage.setScene(new Scene(root, 750, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}