package com.mycompany.employeeData;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Vlad Morari
 */
public class EmployeeData {

    private SimpleStringProperty name;
    private SimpleStringProperty surename;
    private SimpleObjectProperty<LocalDate> birthday;
    private SimpleStringProperty idnp;

    public EmployeeData(String name, String sureName, LocalDate data, String idnp) {
        this.name = new SimpleStringProperty(name);
        this.surename = new SimpleStringProperty(sureName);
        this.birthday = new SimpleObjectProperty<>(data);
        this.idnp = new SimpleStringProperty(idnp);
    }

    public String getName() {
        return name.get();
    }

    public String getSurename() {
        return surename.get();
    }

    public String getIdnp() {
        return idnp.get();
    }

    public LocalDate getBirthday() {
        return birthday.get();

    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = new SimpleObjectProperty<>(birthday);
    }

    public void setIdnp(String idnp) {
        this.idnp = new SimpleStringProperty(idnp);
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public void setSurename(String surename) {
        this.surename =new SimpleStringProperty(surename);
    }


}
