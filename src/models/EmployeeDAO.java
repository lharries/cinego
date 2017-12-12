package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class EmployeeDAO {

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

    public static Employee login(String username, String password) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                username,
                password
        };

        ResultSet results = SQLiteUtil.executeQuery("SELECT * FROM Employee WHERE username=? AND password=?", preparedStatementArgs);
        if (results != null) {
            return getEmployeeFromResultSet(results);
        } else {
            return null;
        }
    }

    public static ObservableList<Employee> getEmployeeObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetEmployees = SQLiteUtil.executeQuery("SELECT * FROM Employee", null);

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
