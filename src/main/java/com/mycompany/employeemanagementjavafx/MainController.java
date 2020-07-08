package com.mycompany.employeemanagementjavafx;

import com.company.employeeDAO.EmployeeDao;
import com.mycompany.employeeData.EmployeeData;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;//ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private TableColumn columnEmployeeName;
    @FXML
    private TableColumn columnEmployeeSurename;
    @FXML
    private TableColumn columnEmployeeIdnp;
    @FXML
    private TableColumn columnEmployeeByrthday;
    @FXML
    private TableView<EmployeeData> table;

    private ObservableList<EmployeeData> observableList = FXCollections.observableArrayList();

    ArrayList<EmployeeData> list = new ArrayList<EmployeeData>();

    EmployeeDao empDao = new EmployeeDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addFromDao();//incarc tabelul cand pornesc aplicatia
        columnEmployeeName.setCellValueFactory(new PropertyValueFactory("name"));
        columnEmployeeSurename.setCellValueFactory(new PropertyValueFactory("surename"));
        columnEmployeeByrthday.setCellValueFactory(new PropertyValueFactory("birthday"));
        columnEmployeeIdnp.setCellValueFactory(new PropertyValueFactory("idnp"));
        table.setItems(observableList);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        observableList.addListener((ListChangeListener) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    System.out.println("Elemente adaugate: " + observableList.get(change.getFrom()).getName());
                    //empDao.insert(observableList.get(change.getFrom()));
                } else if (change.wasRemoved()) {/*
                    change.getRemoved().forEach((remItem)->{
                        System.out.println("sters: "+ remItem.);
                   });
                    EmployeeData person = table.getSelectionModel().getSelectedItem();
                    System.out.println(person.getName());                     */
                }
            }
        });
    }

    /*Select from dao*/
    private void addFromDao() {
        String sql = "SELECT name,surename,birthday,idnp FROM public.employeedata ORDER BY name ASC";
        list = empDao.select(sql);
        observableList = FXCollections.observableArrayList(list);
    }

    @FXML
    void onDeleteEmployee() {
        int selectedRow = this.table.getSelectionModel().getSelectedIndex();
        if (selectedRow != -1) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "This action cannot be undone");
            alert.setTitle("Please confirm");
            alert.setHeaderText("Are you sure you want to delete this user?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                EmployeeData person = table.getSelectionModel().getSelectedItem();
                System.out.println(person.getIdnp());
                empDao.delete(person.getIdnp());
                this.observableList.remove(selectedRow);
            }
        }
    }

    @FXML//metoda ce deschide fereastra add
    void addButton(ActionEvent event) throws IOException {
        //.1 initializam fereastra de adaugare
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Add.fxml"));
        Parent parent = fxmlLoader.load();
        //creez obiect de tip AddControler
        AddController dialogController = (AddController) fxmlLoader.getController();
        //transmit observabelist la add controler in metoda setApp...  
        dialogController.setAppMainObservableList(observableList);
        //2.cream scena pentru fereastra 
        Scene scene = new Scene(parent, 380, 290);
        //3. Afisam fereastra
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);//ne blocheaza accesul la alte ferestree
        stage.setTitle("Add employee");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void editRightClick(ActionEvent event) throws IOException {
        String idnp;
        int selectedRow = this.table.getSelectionModel().getSelectedIndex();
        if (selectedRow != -1) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
            Parent parent = fxmlLoader.load();
            EditController editController = (EditController) fxmlLoader.getController();

            EmployeeData person = table.getSelectionModel().getSelectedItem();
            System.out.println(person.getIdnp());
            idnp = person.getIdnp();
            
            editController.setAppMainObservableList(observableList, selectedRow,idnp);
            Scene scene = new Scene(parent, 355, 280);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Employee");

            stage.setScene(scene);
            stage.showAndWait();
        }

    }

}
