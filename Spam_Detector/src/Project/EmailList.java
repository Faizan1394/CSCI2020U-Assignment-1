package Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import javax.swing.text.TabableView;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by miral on 19/02/17.
 */
public class EmailList {

    private static ObservableList<TestFile> emails = FXCollections.observableArrayList();

    public static void setEmail(String file_name, double spam_prob, String actual_class) {
        emails.add(new TestFile(file_name,spam_prob,actual_class));
    }

    public static ObservableList getEmails(){return emails;}
}
