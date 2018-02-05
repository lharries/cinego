package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The Data access object responsible for getting and saving the {@link Screening} data.
 * <p>
 * Based on the DAO design pattern.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class ScreeningDAO {

    /**
     * convert the screenings result set to a screening
     *
     * @param resultSet resultSet
     * @return the screening object
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     */
    private static Screening getScreeningFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Screening screening = new Screening();
            screening.setId(resultSet.getInt("id"));
            screening.setFilmId(resultSet.getInt("filmId"));
            screening.setDate(resultSet.getString("date"));
            return screening;
        } else {
            return null;
        }
    }

    /**
     * get the observable list of screenings
     *
     * @return screenings observable list
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     */
    public static ObservableList<Screening> getScreeningObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetScreenings = SQLiteUtil.executeQuery("SELECT * FROM Screening", null);

        return getScreeningList(resultSetScreenings);
    }


    /**
     * get the screening dates as an observable list
     *
     * @return observable list of dates
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     * @throws ParseException         ParseException invalid date in the db
     */
    public static ObservableList<Date> getScreeningDatesObservableList() throws SQLException, ClassNotFoundException, ParseException {
        ResultSet resultSetScreenings = SQLiteUtil.executeQuery("SELECT date FROM Screening", null);
        return getScreeningDateList(resultSetScreenings);
    }

    /**
     * convert the screening dates result list to an observable list
     *
     * @param resultSet resultSet the screening dates
     * @return the observable list of screening dates
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     * @throws ParseException         ParseException invalid date in the db
     */
    private static ObservableList<Date> getScreeningDateList(ResultSet resultSet) throws SQLException, ClassNotFoundException, ParseException {
        ObservableList<Date> screeningDateList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            DateFormat longFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            String screeningDateString = resultSet.getString("date");
            Date screeningDate = longFormat.parse(screeningDateString);

            screeningDateList.add(screeningDate);
        }
        return screeningDateList;
    }


    /**
     * convert the screenings result set into an observable list
     *
     * @param resultSet the screenings result set
     * @return screenings observable list
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     */
    private static ObservableList<Screening> getScreeningList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Screening> screeningList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Screening screening = new Screening();
            screening.setId(resultSet.getInt("id"));
            screening.setFilmId(resultSet.getInt("filmId"));
            screening.setDate(resultSet.getString("date"));
            //added
            screeningList.add(screening);
        }

        return screeningList;
    }

    /**
     * get the screenings for a particular film
     *
     * @param filmId filmId
     * @return screenings observable list
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     */
    public static ObservableList<Screening> getScreeningObservableListByFilmId(int filmId) throws SQLException, ClassNotFoundException {
        ResultSet resultSetScreenings = SQLiteUtil.executeQuery(
                "SELECT * FROM Screening\n" +
                        "WHERE filmId = ?", new Object[]{
                        filmId
                });

        return getScreeningList(resultSetScreenings);
    }

    /**
     * insert a new screening into the db
     *
     * @param filmId film id
     * @param date   date
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     */
    public static void insertScreening(int filmId, String date) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                filmId,
                date
        };

        SQLiteUtil.execute(
                "INSERT INTO Screening\n" +
                        "(filmId, date)\n" +
                        "VALUES\n" +
                        "(?, ?);",
                preparedStatementArgs
        );
    }

    /**
     * delete a screening from the database
     *
     * @param id the screening id
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     */
    public static void deleteScreening(int id) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                id
        };

        SQLiteUtil.execute(
                "DELETE FROM Screening\n" +
                        "WHERE id = ?",
                preparedStatementArgs
        );
    }

    /**
     * get a screening by the id
     *
     * @param id id
     * @return screening
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     */
    public static Screening getScreeningById(int id) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                id
        };

        ResultSet resultSet = SQLiteUtil.executeQuery("SELECT * FROM Screening\n" +
                "WHERE id = ?", preparedStatementArgs);

        if (resultSet.wasNull() || !resultSet.next()) {
            return null;
        }
        Screening screening = new Screening();

        screening.setId(resultSet.getInt("id"));
        screening.setFilmId(resultSet.getInt("filmId"));
        screening.setDate(resultSet.getString("date"));

        return screening;

    }

}
