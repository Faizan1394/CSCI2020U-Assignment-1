package Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by miral on 19/02/17.
 * Used to add values to an ObservableList and returns that ObservableList
 */

public class EmailList {

    private static ObservableList<TestFile> emails = FXCollections.observableArrayList();

    public static void setEmail(String file_name, double spam_prob, String actual_class) {
        emails.add(new TestFile(file_name,spam_prob,actual_class));
    }

    public static ObservableList getEmails(){return emails;}
}
