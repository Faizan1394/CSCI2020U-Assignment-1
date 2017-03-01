package Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by miral on 19/02/17.
 */
public class EmailList {


    // maps to hold the ham and spam word frequency
    private Map<String,Integer> testingHamFreq;
    private Map<String,Integer> testingSpamFreq;

    public static ObservableList<TestFile> getEmail(File file) throws IOException{
        ObservableList<TestFile> emails = FXCollections.observableArrayList();
        //if the file is a directory
        if (file.isDirectory()) {
            // for directories, recursively call
            File[] filesInDir = file.listFiles();
            for (int i = 0; i < filesInDir.length; i++) {
                getEmail(filesInDir[i]);
            }
        } else {
            // if the current file is in the training ham folder
            if(file.getAbsolutePath().contains("test/ham")) {
                for(int i = 0; i < file.length();i++) {
                    emails.add(new TestFile(file.getName(),0.00, "Ham"));
                }
            }

            // if the current file is in the training spam folder
            else if(file.getAbsolutePath().contains("test/spam")) {
                for(int i = 0; i < file.length();i++) {
                    emails.add(new TestFile(file.getName(),0.00, "Spam"));
                }
            }


        }

        return emails;
    }
}
