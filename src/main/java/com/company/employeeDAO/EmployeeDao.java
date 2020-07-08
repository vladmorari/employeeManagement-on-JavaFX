package com.company.employeeDAO;

import com.mycompany.employeeData.EmployeeData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Vlad Morari
 */
public class EmployeeDao {

    Connection initConnection() {
        String url = "jdbc:postgresql://127.0.0.1:5430/employeeFX";
        String user = "postgres";
        String password = "vlad";
        try {
            System.out.println("\nConnecting...");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println("ERROR! Can't connect to db. " + ex.getMessage());
            return null;
        }

    }

    public int checkId(String idnp) {
        int count = 0;
        String sql = "SELECT COUNT (idnp) as numar FROM empdate.employee where idnp='" + idnp + "'";
        Connection conn = initConnection();
        Statement st = null;
        try {
            if (conn != null) {
                st = conn.createStatement();
                ResultSet resultSet = st.executeQuery(sql);
                while (resultSet.next()) {
                    count = resultSet.getInt("numar");
                    System.out.print("sunt :  ");
                    System.out.print(count);
                }
            } else {
                System.out.println("Select failed");
            }

        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        } finally {
            try {
                conn.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return count;
    }

    public void insert(EmployeeData emp) {

        String sql = "INSERT INTO public.employeedata(name,surename,idnp,birthday) VALUES('" + emp.getName() + "','" + emp.getSurename() + "','" + emp.getIdnp() + "','" + emp.getBirthday() + "')";
        Connection conn = initConnection();
        Statement st = null;
        try {
            if (conn != null) {
                st = conn.createStatement();
                int rows = st.executeUpdate(sql);
                System.out.print("inserat in baza rinduri :" + rows); //////rows intoarce nr de rinduri inserate
            } else {
                System.out.println("Insert failed, something wrong with connection");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR! Insert failed. SQL Request error" + ex.getMessage());
        } finally {
            try {
                conn.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ArrayList<EmployeeData> select(String sql) {

        ArrayList<EmployeeData> emp = new ArrayList<>();

        // sql = "SELECT name,surename,idnp,gen FROM empdate.employee ORDER BY id DESC";///select sortat dupa nume
        //SELECT column1, column2, ...FROM table_name ORDER BY column1, column2, ... ASC
        Connection conn = initConnection();
        Statement st = null;
        try {
            if (conn != null) {
                st = conn.createStatement();
                ResultSet resultSet = st.executeQuery(sql);

                while (resultSet.next()) {
                    EmployeeData selectEmp = new EmployeeData(resultSet.getString("name"), resultSet.getString("surename"), resultSet.getDate("birthday").toLocalDate(), resultSet.getString("idnp"));
                    emp.add(selectEmp);
                }
            } else {
                System.out.println("Select failed");
            }

        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        } finally {
            try {
                conn.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return emp;
    }

    public void delete(String idnp) {
        String sql = "DELETE FROM public.employeedata WHERE idnp='" + idnp + "'";
        Connection conn = initConnection();
        Statement st = null;
        try {
            if (conn != null) {
                st = conn.createStatement();
                int rows = st.executeUpdate(sql);
                System.out.print("sterse din baza " + rows);
            } else {
                System.out.println("Insert failed");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR! Insert failed. " + ex.getMessage());
        } finally {
            try {
                conn.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void update(EmployeeData emp, String idnp) {

        String sql = "   UPDATE public.employeedata SET name ='" + emp.getName() + "',surename = '" + emp.getSurename() + "',idnp='" + emp.getIdnp() + "',birthday='" + emp.getBirthday() + "'  WHERE idnp='" + idnp + "'";
        Connection conn = initConnection();
        Statement st = null;
        if (conn != null) {
            try {
                st = conn.createStatement();
                st.executeUpdate(sql);
                System.out.println("Record updated successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                    st.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
