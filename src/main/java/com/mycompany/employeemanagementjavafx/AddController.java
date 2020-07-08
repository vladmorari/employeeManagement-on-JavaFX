package com.mycompany.employeemanagementjavafx;

import com.company.employeeDAO.EmployeeDao;
import com.mycompany.employeeData.EmployeeData;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vlad Morari
 */
public class AddController implements Initializable {

    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldSurename;
    @FXML
    private TextField fieldIdnp;
    @FXML
    private DatePicker data;

    private ObservableList<EmployeeData> appMainObservableList;
    
    EmployeeDao empDao = new EmployeeDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void addEmployee(ActionEvent event) {
        if (fieldName.getText().trim().isEmpty() || fieldSurename.getText().trim().isEmpty() || fieldIdnp.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty data");
            alert.setContentText("You don't have any imformation");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            EmployeeData emp = new EmployeeData(fieldName.getText().trim(), fieldSurename.getText().trim(), data.getValue(), fieldIdnp.getText().trim());
            empDao.insert(emp);///pot face insert aici sau in MainControler de la listener 
            appMainObservableList.add(emp);
            closeStage(event);
        }
    }

    public void setAppMainObservableList(ObservableList<EmployeeData> tvObservableList) {
        this.appMainObservableList = tvObservableList;
    }

    @FXML
    void cancelAdd(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
