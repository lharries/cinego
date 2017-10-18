package main;

import java.util.ArrayList;

public class Cinema {
    public static ArrayList<Film> films = new ArrayList<Film>();

    public Cinema() {
    }

    public static ArrayList<Film> getFilms() {
        return films;
    }

    static boolean createFilm() {
        return false;
    }

    static boolean deleteFilm() {
        return false;
    }

    static boolean createScreening() {
        return false;
    }

    static boolean deleteScreening() {
        return false;
    }

    static void exportFilms() {
        // TODO: Open the CSV
    }
}
