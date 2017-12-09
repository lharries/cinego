package models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Source:
 * - https://stackoverflow.com/questions/3395286/remove-last-character-of-a-stringbuilder
 */
public class Film {
    private IntegerProperty id;
    private StringProperty title;
    private StringProperty imagePath;
    private StringProperty description;
    private ListProperty<Screening> screenings;


    public Film() {
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.imagePath = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.screenings = new SimpleListProperty<Screening>();
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

    public String getImagePath() {
        return this.imagePath.get();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public StringProperty imagePathProperty() {
        return this.imagePath;
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
        return screenings.get();
    }

    public ListProperty<Screening> screeningsProperty() {
        return screenings;
    }

    public void setScreenings(ObservableList<Screening> screenings) {
        this.screenings.set(screenings);
    }

    public String getScreeningsDescription() {

        int maxNumberOfScreenings = 4;

        ObservableList<Screening> screenings = getScreenings();

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

    @Override
    public String toString() {
        return getTitle();
    }
}
