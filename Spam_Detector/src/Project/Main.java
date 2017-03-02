package Project;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @FXML private TableView table;
    @FXML private TableColumn file_column , actual_class, spam_prob;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spam Detector 3000");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        /*

        // make an instance of the WordCounter class
        WordCounter word = new WordCounter();
        // call the processFile function in WordCounter class and send it the file containing all the data
        word.processFile(mainDirectory);
        word.calculateProbability();

        //print out the word counts in console
        //word.printWordCounts();

        */

        ObservableList emails = EmailList.getEmail(mainDirectory);
        table.setItems(emails);
        table.getColumns().addAll(file_column,spam_prob,actual_class);



        primaryStage.setScene(new Scene(root, 750, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}