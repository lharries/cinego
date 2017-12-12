package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Contains the properties corresponding to the columns in the database for the film.
 * Has getter and setter functions to store data retrieved from the {@link FilmDAO} class.
 * <p>
 * References:
 * - https://stackoverflow.com/questions/3395286/remove-last-character-of-a-stringbuilder
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class Film {
    private IntegerProperty id;
    private StringProperty title;
    private StringProperty imageName;
    private StringProperty description;
    private StringProperty trailerURL;


    public Film() {
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.imageName = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.trailerURL = new SimpleStringProperty();
    }

    public Integer getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return this.title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return this.title;
    }

    public String getImageName() {
        return this.imageName.get();
    }

    public void setImageName(String imageName) {
        this.imageName.set(imageName);
    }

    public StringProperty imageNameProperty() {
        return this.imageName;
    }

    public String getImagePath() throws UnsupportedEncodingException {
        File directory = new File(".");
        File moviesDirectory = new File(directory.getAbsolutePath(), "movie-images");
        File newMovie = new File(moviesDirectory, this.getImageName());

        return "file:" + newMovie.getAbsolutePath();
    }

    public String getDescription() {
        return this.description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return this.description;
    }

    public ObservableList<Screening> getScreenings() {
        try {
            return ScreeningDAO.getScreeningObservableListByFilmId(this.getId());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getScreeningsDescription() {

        int maxNumberOfScreenings = 4;

        FilteredList<Screening> screenings = getUpcomingScreenings();

        int numberOfScreeningsToShow = (screenings.size() < maxNumberOfScreenings ? screenings.size() : maxNumberOfScreenings);

        StringBuilder screeningsDescription = new StringBuilder();

        if (screenings.size() == 0) {
            screeningsDescription.append("No upcoming screenings");
        } else {

            for (int screeningIndex = 0; screeningIndex < numberOfScreeningsToShow; screeningIndex++) {
                try {
                    screeningsDescription.append(screenings.get(screeningIndex).getShortDate());
                    screeningsDescription.append(", ");
                } catch (ParseException e) {
                    System.err.println("Unable to get screening description");
                    e.printStackTrace();
                }
            }

            // remove the trailing ", "
            screeningsDescription.setLength(screeningsDescription.length() - 2);
        }

        return screeningsDescription.toString();


    }

    public boolean hasScreeningOnDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        for (Screening screening :
                getScreenings()) {
            try {
                if (Objects.equals(dateFormat.format(screening.getDateObject()), dateFormat.format(date))) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public String getTrailerURL() {
        return trailerURL.get();
    }

    public StringProperty trailerURLProperty() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL.set(trailerURL);
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public FilteredList<Screening> getUpcomingScreenings() {
        return getScreenings().filtered(screening -> {
            try {
                Date today = new Date();
                Date screeningDate = screening.getDateObject();
                return today.compareTo(screeningDate) <= 0;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    /**
     * Reference:
     * - https://stackoverflow.com/questions/2517709/comparing-two-java-util-dates-to-see-if-they-are-in-the-same-day
     *
     * @param selectedDate
     * @return
     */
    public FilteredList<Screening> getScreeningsByDate(Date selectedDate) {
        return getScreenings().filtered(screening -> {
            try {
                Calendar selectedDay = Calendar.getInstance();
                selectedDay.setTime(selectedDate);

                Calendar filmDay = Calendar.getInstance();
                filmDay.setTime(screening.getDateObject());
                return selectedDay.get(Calendar.DAY_OF_YEAR) == filmDay.get(Calendar.DAY_OF_YEAR)
                        && selectedDay.get(Calendar.YEAR) == filmDay.get(Calendar.YEAR);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        });
    }


}
