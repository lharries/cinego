package main;

public class Employee extends User {

    public Employee(String firstName, String lastName, String email, String password, String username) {
        super(firstName, lastName, email, password, username);
    }
    
    public boolean createFilm() {
        return Cinema.createFilm();
    }

    public boolean deleteFilm() {
        return Cinema.deleteFilm();
    }

    public boolean createScreening() {
        return Cinema.createScreening();
    }

    public boolean deleteScreening() {
        return Cinema.deleteScreening();
    }

    public void exportFilms() {
        Cinema.exportFilms();
    }
}
