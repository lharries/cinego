package models;

public class Employee {

    public Employee() {
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
