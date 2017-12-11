package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Defines the structure used for the {@link Customer} and {@link Employee} models.
 * <p>
 * Contains the properties corresponding to the columns in the database for the {@link Customer} and {@link Employee} models.
 * Has getter and setter functions to store data retrieved from the DAO class.
 *
 * @author lukeharries kaiklasen
 */
public class User {

    private IntegerProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty email;
    private StringProperty username;
    private StringProperty password;

    public User() {
        this.id = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return this.firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return this.lastName;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return this.email;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return this.username;
    }
}
