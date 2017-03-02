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

        File [] filesInDir = file.listFiles();
        for (int i = 0; i < filesInDir.length; i++) {
            System.out.println(filesInDir[i]);
            if(filesInDir[i].isDirectory()) {
                for (int j = 0; j < filesInDir.length; j++) {
                    getEmail(filesInDir[j]);
                }
            }

            else if (filesInDir[i].getAbsolutePath().contains("test/ham")) {
                emails.add(new TestFile(filesInDir[i].getName(),0.00, "Ham"));
            }
            else if (filesInDir[i].getAbsolutePath().contains("test/spam")){
                emails.add(new TestFile(filesInDir[i].getName(),0.00, "Spam"));
            }
        }
        return emails;
    }
}
