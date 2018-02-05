package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Data access object responsible for getting and saving the {@link Employee} data.
 * <p>
 * Based on the DAO design pattern.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class EmployeeDAO {

    /**
     * Convert the result set employee to an employee object
     *
     * @param resultSet resultSet
     * @return Employee
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
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

    /**
     * Get the user with the following username and password
     *
     * @param username username
     * @param password password
     * @return Employee model
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
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

    /**
     * Get the list of employees
     *
     * @return Employee observable list
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static ObservableList<Employee> getEmployeeObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetEmployees = SQLiteUtil.executeQuery("SELECT * FROM Employee", null);

        return getEmployeeList(resultSetEmployees);
    }

    /**
     * Convert the employee list results set to employee observable list
     *
     * @param resultSet resultSet
     * @return obserable list of employees
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
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
