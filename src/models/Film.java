package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Film {
    private IntegerProperty id;
    private StringProperty title;
    private StringProperty imagePath;
    private StringProperty description;

    public Film() {
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.imagePath = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
    }

    //added constructor to test populating employee movies list
    public Film (Integer id, String title, String imagePath, String description){
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.imagePath = new SimpleStringProperty(imagePath);
        this.description = new SimpleStringProperty(description);
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
        this.title.set(imagePath);
    }

    public StringProperty imagePathProperty() {
        return this.imagePath;
    }

    public String getDescription() {
        return this.description.get();
    }

    public void setDescription(String description) {
        this.title.set(description);
    }

    public StringProperty descriptionProperty() {
        return this.description;
    }

}
