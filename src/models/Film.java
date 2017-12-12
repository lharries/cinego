package models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Source:
 * - https://stackoverflow.com/questions/3395286/remove-last-character-of-a-stringbuilder
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


//    //DELETED IN LUKE's BOOKING BRANCH
//    //TODO: added constructor to test populating employee movies list
//    public Film (Integer id, String title, String imagePath, String description){
//        this.id = new SimpleIntegerProperty(id);
//        this.title = new SimpleStringProperty(title);
//        this.imagePath = new SimpleStringProperty(imagePath);
//        this.description = new SimpleStringProperty(description);

    public void print() {
        System.out.println(this.id);
        System.out.println(this.title);
//        System.out.println(this.imagePath);
//        System.out.println(this.description);

    }

    //TODO: missing Trailer link?

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

    public String getImagePath() throws UnsupportedEncodingException {
        File directory = new File(".");
        File moviesDirectory = new File(directory.getAbsolutePath(), "movie-images");
        File newMovie = new File(moviesDirectory, this.getImageName());

        System.out.println("file:" + newMovie.getAbsolutePath());
        System.out.println(getImageName());
        return "file:" + newMovie.getAbsolutePath();
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

    public String getTrailerURL() {
        return trailerURL.get();
    }

    public StringProperty trailerURLProperty() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL.set(trailerURL);
    }
}
