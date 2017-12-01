package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;


public class EmployeeDAO {

    public static void main(String[] args) {


        // Example of how to use the login
        try {
            Employee employee = login("employee", "employeepassword");
            if (employee != null) {
                System.out.println(employee.getFirstName());
                System.out.println(employee.getLastName());
                System.out.println(employee.getEmail());
                System.out.println(employee.getUsername());
            } else {
                System.out.println("Unable to login");

            }
        } catch (Exception e) {
            System.out.println("Printing the error");
            System.err.print(e);
        }
    }


    private static Employee getEmployeeFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setFirstName(resultSet.getString("firstName"));
            employee.setLastName(resultSet.getString("lastName"));
            employee.setEmail(resultSet.getString("email"));
            employee.setUsername(resultSet.getString("username"));
            return employee;
        } else {
            return null;
        }
    }

    private static Employee login(String username, String password) throws SQLException, ClassNotFoundException {
        // TODO: Switch to prepared statements?
        ResultSet results = SQLiteConnection.executeQuery("SELECT * FROM Employee WHERE username=\"" + username + "\" AND password=\"" + password + "\"");
        if (results != null) {
            return getEmployeeFromResultSet(results);
        } else {
            return null;
        }
    }

    private static ObservableList<Employee> getEmployeeObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetEmployees = SQLiteConnection.executeQuery("SELECT * FROM Employee");

        return getEmployeeList(resultSetEmployees);
    }

    private static ObservableList<Employee> getEmployeeList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setFirstName(resultSet.getString("firstName"));
            employee.setLastName(resultSet.getString("lastName"));
            employee.setEmail(resultSet.getString("email"));
            employee.setUsername(resultSet.getString("username"));
            employeeList.add(employee);
        }

        return employeeList;
    }
}
