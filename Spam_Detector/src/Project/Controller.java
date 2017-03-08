package Project;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by miral on 05/03/17.
 */
public class Controller implements Initializable{

    @FXML
    private TableView<TestFile> table;
    @FXML private TableColumn<TestFile,String> file_column;
    @FXML private TableColumn<TestFile, String> actual_class;
    @FXML private TableColumn<TestFile, String> spam_prob;
    @FXML private TextField accuracy_field;
    @FXML private TextField precision_field;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList emails = EmailList.getEmails();

        file_column.setCellValueFactory(new PropertyValueFactory<TestFile, String>("Filename"));
        actual_class.setCellValueFactory(new PropertyValueFactory<TestFile, String>("ActualClass"));
        spam_prob.setCellValueFactory(new PropertyValueFactory<TestFile, String>("SpamProbRounded"));
        accuracy_field.setText("Accuracy");
        precision_field.setText("Precision");
        table.setItems(emails);
    }

    public void setAccuracy_field(String accuracy) {
        this.accuracy_field.setText(accuracy);
    }

    public void setPrecision_field(String precision) {
        this.precision_field.setText(precision);
    }
}
