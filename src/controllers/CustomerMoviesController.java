package controllers;

import application.Navigation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import models.Film;
import models.FilmDAO;
import models.Screening;
import models.ScreeningDAO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Allows the customer to browse through a list of films and select the films by date
 * <p>
 * Design Pattern: MVC
 * <p>
 * Sources:
 * - https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
 * - https://stackoverflow.com/questions/24700765/how-to-hide-a-pannable-scrollbar-in-javafx
 * - https://stackoverflow.com/questions/42542312/javafx-datepicker-color-single-cell
 * - https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate
 */
public class CustomerMoviesController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());

    /**
     * The film which has been selected and therefore displayed in the main panel
     */
    private Film selectedFilm;

    private Date selectedDate;

    private String searchText = "";

    private ArrayList<Rectangle> movieRectanglesArrayList = new ArrayList<>();

    @FXML
    public ScrollPane moviesScrollPane;

    @FXML
    private DatePicker datePicker;

    @FXML
    public VBox moviesVBox;

    @FXML
    private Group selectedFilmGroup;

    @FXML
    private ImageView selectedFilmImage;

    @FXML
    private Text selectedFilmTitle;

    @FXML
    private Label selectedFilmDescription;

    @FXML
    private StackPane selectedTrailerPane;

    @FXML
    private Group screeningTimes;

    @FXML
    public TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectedFilmGroup.setVisible(false);

        // Hide the horizontal scroll bar of the movies list
        moviesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Setup the spacing for the vertical list of films
        moviesVBox.setPadding(new Insets(10.0));
        moviesVBox.setSpacing(10.0);

        updateFilmList();

        // listener for changing the search text
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = searchField.getText();
            updateFilmList();
        });

        try {
            setColorsOfDatePicker();
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.logp(Level.WARNING, "CustomerMoviesController", "initialize", "Failed to load setColorsofDatePicker. See: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Update the sidebar showing the list of films according to the filers
     */
    private void updateFilmList() {

        moviesVBox.getChildren().clear();
        // Get the films from the db and add them to the list of films
        try {
            boolean isSelected = true;
            ObservableList<Film> films = FilmDAO.getFilmObservableList();
            for (int i = 0; i < films.size(); i++) {
                // set the first film to be selected
                Film film = films.get(i);

                // filter by title, description and date
                if (film.getTitle().toLowerCase().contains(searchText.toLowerCase()) || film.getDescription().toLowerCase().contains(searchText.toLowerCase())) {
                    if (selectedDate == null || film.hasScreeningOnDate(selectedDate)) {
                        addFilmToList(films.get(i), isSelected);
                        isSelected = false;
                    }

                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.logp(Level.WARNING, "CustomerMoviesController", "updateFilmList",
                    "Unable to get films" + e);
            e.printStackTrace();
        }
    }

    /**
     * Display the film in the main panel
     *
     * @param film      the film which has been select
     * @param rectangle the rectangle containing the film, used to alter the border
     */
    private void selectFilm(Film film, Rectangle rectangle) {

        selectedFilm = film;
        selectedFilmTitle.setText(film.getTitle());
        selectedFilmDescription.setText(film.getDescription());
        selectedFilmDescription.setWrapText(true);
        selectedFilmGroup.setVisible(true);

        String trailerURLString = film.getTrailerURL();
        //loads trailer into view
        if (trailerURLString != null) {
            WebView webview = new WebView();
            String youtubeUrl = trailerURLString.replace("watch?v=", "embed/");
            webview.getEngine().load(youtubeUrl);
            selectedTrailerPane.getChildren().setAll(webview);
        } else {
            Label errorTrailer = new Label("Sorry, this movie trailer is currently unavailable");
            errorTrailer.setWrapText(true);
            selectedTrailerPane.getChildren().addAll(errorTrailer);
        }

        // Try and get the film if it's found
        try {
            selectedFilmImage.setImage(new Image(film.getImagePath()));
            selectedFilmImage.setVisible(true);
        } catch (IllegalArgumentException e) {
            LOGGER.logp(Level.WARNING, "CustomerMoviesController", "selectFilm", "Failed to locate the image. See: " + e);
            selectedFilmImage.setVisible(false);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            LOGGER.logp(Level.WARNING, "CustomerMoviesController", "selectFilm", "Failed to find film. See: " + e);
            e.printStackTrace();
        }

        // hide the borders of all films
        for (Rectangle otherRectangles : movieRectanglesArrayList) {
            otherRectangles.setStrokeWidth(0.0);
        }

        // show the gold border of the selected film
        rectangle.setStrokeWidth(5.0);

        // add the screenings to the view
        addScreeningsToView();

    }

    /**
     * Create the film object to be displayed and add it to the list of films
     *
     * @param film       the film
     * @param isSelected whether the film has been selected, used to set the first film as selected
     */
    private void addFilmToList(Film film, boolean isSelected) {

        Group group = new Group();

        Rectangle rectangle = new Rectangle(10.0, 10.0, 371.0, 177.0);
        rectangle.setFill(Color.WHITE);
        rectangle.setArcHeight(5.0);
        rectangle.setArcWidth(5.0);
        rectangle.setStyle("-fx-padding: 5 5 5 5;");

        // The border of the rectangle which appears when the film is selected
        rectangle.setStroke(Color.GOLD);
        rectangle.setStrokeType(StrokeType.CENTERED);
        rectangle.setStrokeWidth(0.0);

        // To enable turning off the selection color
        movieRectanglesArrayList.add(rectangle);
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectFilm(film, rectangle);
            }
        });

        Label title = new Label(film.getTitle());
        title.setWrapText(true);
        title.setMaxWidth(200.0);
        title.setMaxHeight(60.0);
        title.setLayoutX(15.0);
        title.setLayoutY(20.0);
        Font titleFont = new Font(25.0);
        title.setFont(titleFont);

        Label description = new Label(film.getDescription());
        description.setMaxWidth(200.0);
        description.setMaxHeight(80.0);
        description.setWrapText(true);
        description.setLayoutX(15.0);
        description.setLayoutY(80.0);
        Font descriptionFont = new Font(15.0);
        description.setFont(descriptionFont);

        ImageView imageView = null;
        try {
            Image image = new Image(film.getImagePath());
            imageView = new ImageView(image);
            imageView.setX(230.0);
            imageView.setY(30.0);
            imageView.setFitHeight(145.0);
            imageView.setFitWidth(134.0);
            imageView.setCache(true);
            imageView.setCacheHint(CacheHint.SPEED);
        } catch (IllegalArgumentException e) {
            LOGGER.logp(Level.WARNING, "CustomerMoviesController", "addFilmToList", "Failed to find film. See: " + e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "CustomerMoviesController", "addFilmToList", "Failed to encode. See: " + e);
        } catch (NullPointerException e) {
            LOGGER.logp(Level.WARNING, "CustomerMoviesController", "addFilmToList", "Nullpointer. See: " + e);
            e.printStackTrace();
        }

        if (imageView != null) {
            group.getChildren().addAll(rectangle, title, description, imageView);
        } else {
            group.getChildren().addAll(rectangle, title, description);
        }

        moviesVBox.getChildren().add(group);

        if (isSelected) {
            selectFilm(film, rectangle);
        }

    }

    /**
     * Add the screenings button to the view
     */
    private void addScreeningsToView() {

        if (selectedDate != null) {

            double xPosition = 0.0;
            screeningTimes.getChildren().clear();
            for (Screening screening :
                    selectedFilm.getScreeningsByDate(selectedDate)) {
                try {
                    Button screeningButton = new Button();
                    screeningButton.setText(screening.getTime());
                    screeningButton.setLayoutX(xPosition);
                    screeningButton.setLayoutY(0);
                    screeningButton.setOnAction((event) -> {
                        try {
                            CustomerBookingController.selectedScreening = screening;
                            Navigation.loadCustFxml(Navigation.CUST_BOOKING_VIEW);
                        } catch (IOException e) {
                            LOGGER.logp(Level.WARNING, "CustomerMoviesController",
                                    "addScreeningsToView", "Unable to load customer booking view" + e);
                            e.printStackTrace();
                        }
                    });

                    screeningTimes.getChildren().add(screeningButton);

                    xPosition += 130.0;

                } catch (ParseException e) {
                    LOGGER.logp(Level.WARNING, "CustomerMoviesController", "addScreeningsToView", "Failed to parse data. See: " + e);
                    e.printStackTrace();
                }
            }
        } else {
            screeningTimes.getChildren().clear();
            Label label = new Label("Please select a date to view screenings");
            label.setFont(Font.font(25));
            screeningTimes.getChildren().add(label);
        }
    }


    /**
     * Handle the date picker being used
     *
     * @param actionEvent actionEvent
     */
    public void datePickerHandler(ActionEvent actionEvent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            if (datePicker.getValue() != null)
                selectedDate = dateFormat.parse(String.valueOf(datePicker.getValue()));

        } catch (ParseException e) {
            LOGGER.logp(Level.WARNING, "CustomerMoviesController", "datePickerHandler", "Failed to parse date data. See: " + e);
            e.printStackTrace();
        }
        updateFilmList();
    }

    /**
     * Clear all the filters and show all the films
     *
     * @param actionEvent actionEvent
     */
    public void showAllFilms(ActionEvent actionEvent) {
        datePicker.setValue(null);
        selectedDate = null;
        addScreeningsToView();
        searchText = "";
        searchField.setText("");
        updateFilmList();
    }

    /**
     * Change the date picker colors to show which dates have films showing on that date
     *
     * @throws SQLException SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     */
    public void setColorsOfDatePicker() throws SQLException, ClassNotFoundException {
        ObservableList<Screening> screenings = ScreeningDAO.getScreeningObservableList();

        ArrayList<LocalDate> allScreenDates = new ArrayList<>();

        for (Screening screening :
                screenings) {
            try {
                LocalDate date = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(screening.getDateObject()));
                allScreenDates.add(date);
            } catch (ParseException | NullPointerException e) {
                LOGGER.logp(Level.WARNING, "CustomerMoviesController", "setColorsOfDatePicker", "Failed to parse String. See: " + e);
                e.printStackTrace();
            }
        }

        Callback<DatePicker, DateCell> cellColorFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);

                        if (date.compareTo(LocalDate.now()) < 0) {
                            setDisable(true);
                        } else if (allScreenDates.contains(date)) {
                            setStyle("-fx-background-color: #00FF00;");
                        }


                    }
                };
            }
        };

        datePicker.setDayCellFactory(cellColorFactory);
    }
}
