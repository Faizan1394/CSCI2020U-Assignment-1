package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

/**
 * Created by miral on 19/02/17.
 */
public class EmailList {
    public static ObservableList<TestFile> getEmail(File directory){
        ObservableList<TestFile> emails = FXCollections.observableArrayList();

        return emails;
    }
}
