package com.mycompany.employeemanagementjavafx;

import com.company.employeeDAO.EmployeeDao;
import com.mycompany.employeeData.EmployeeData;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vlad Morari
 */
public class EditController implements Initializable {

    @FXML
    private TextField editName;
    @FXML
    private TextField editSurename;
    @FXML
    private TextField editId;
    @FXML
    private DatePicker editData;
    private Integer editedIndex;
    private ObservableList<EmployeeData> appMainObservableList;
    String idnp;
    EmployeeDao empDao = new EmployeeDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void saveEditClicked(ActionEvent event) {
        String name = editName.getText().trim();
        String surename = editSurename.getText().trim();
        String id = editId.getText().trim();
        LocalDate data = editData.getValue();
        EmployeeData emp = new EmployeeData(name, surename, data, id);
        empDao.update(emp,this.idnp);
        System.out.println("Idnp:: "+this.idnp);
        appMainObservableList.set(this.editedIndex, emp);
        closeStage(event);
    }

    @FXML
    void cancelEdit(ActionEvent event) {
        closeStage(event);
    }

    void setAppMainObservableList(ObservableList<EmployeeData> tvObservableList, int selectedRow,String idnp) {
        
        //System.out.println("Idnp:: "+idnp);
        this.idnp=idnp;
        
        this.appMainObservableList = tvObservableList;
        this.editedIndex = selectedRow;
        this.editId.setText(appMainObservableList.get(editedIndex).getIdnp());
        this.editName.setText(appMainObservableList.get(editedIndex).getName());
        this.editSurename.setText(appMainObservableList.get(editedIndex).getSurename());
        this.editData.setValue(appMainObservableList.get(editedIndex).getBirthday());
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
