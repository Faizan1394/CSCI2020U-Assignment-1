package Project;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
        */

        try {
            ObservableList emails = EmailList.getEmails();
            table.setItems(emails);
        }
        catch (Exception e) { e.printStackTrace(); }

        /*
        file_column.setCellValueFactory(new PropertyValueFactory<TestFile, String>("Filename"));
        spam_prob.setCellValueFactory(new PropertyValueFactory<TestFile, String>("SpamProbRounded"));
        actual_class.setCellValueFactory(new PropertyValueFactory<TestFile, String>("ActualClass"));
        */

        primaryStage.setScene(new Scene(root, 750, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}