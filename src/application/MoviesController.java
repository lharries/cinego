package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import models.Film;
import models.FilmDAO;
import models.Screening;
import models.ScreeningDAO;

import java.io.IOException;
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

/**
 * Sources:
 * - https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
 * - https://stackoverflow.com/questions/24700765/how-to-hide-a-pannable-scrollbar-in-javafx
 * - https://stackoverflow.com/questions/42542312/javafx-datepicker-color-single-cell
 * - https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate
 */


public class MoviesController implements Initializable {

    private Film selectedFilm;

    private Date selectedDate;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private String searchText = "";

    private ArrayList<Rectangle> rectangleArrayList = new ArrayList<>();


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
    private Group screeningTimes;

    @FXML
    public TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectedFilmGroup.setVisible(false);

        // Hide the horizontal scroll bar
        moviesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Setup the spacing for the vertical list of films
        moviesVBox.setPadding(new Insets(10.0));
        moviesVBox.setSpacing(10.0);

        updateFilmList();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = searchField.getText();
            System.out.println("Key up:");
            System.out.println(searchField.getText());
            updateFilmList();
        });

        try {
            setColorsOfDatePicker();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void updateFilmList() {

        moviesVBox.getChildren().clear();
        // Get the films from the db and add them to the list of films
        try {
            ObservableList<Film> films = FilmDAO.getFilmObservableList();
            for (int i = 0; i < films.size(); i++) {
                // set the first film to be selected
                Film film = films.get(i);
                boolean isSelected = (i == 0);
                if (film.getTitle().toLowerCase().contains(searchText.toLowerCase()) || film.getDescription().toLowerCase().contains(searchText.toLowerCase())) {
                    if (selectedDate == null || film.hasScreeningOnDate(selectedDate)) {
                        addFilmToList(films.get(i), isSelected);
                    }

                }

                System.out.println(films.get(i).getUpcomingScreenings());

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void selectFilm(Film film, Rectangle rectangle) {
        selectedFilm = film;

        selectedFilmTitle.setText(film.getTitle());
        selectedFilmDescription.setText(film.getDescription());

        // Try and get the film if it's found
        try {
            selectedFilmImage.setImage(new Image(film.getImagePath()));
            selectedFilmImage.setVisible(true);
        } catch (IllegalArgumentException e) {
            System.err.println("Cant locate the image: ");
            System.err.println(film.getImagePath());
            selectedFilmImage.setVisible(false);
        }
        // TODO: Set image and set screening times

        for (Rectangle otherRectangles :
                rectangleArrayList) {
            otherRectangles.setStrokeWidth(0.0);
        }

        rectangle.setStrokeWidth(5.0);
        selectedFilmGroup.setVisible(true);

        addScreeningsToView();
    }

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
        rectangleArrayList.add(rectangle);
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Clicked");
                selectFilm(film, rectangle);
            }
        });

        // TODO: split the text over multiple lines
        Label title = new Label(film.getTitle());
        title.setLayoutX(15.0);
        title.setLayoutY(10.0);
        Font titleFont = new Font(25.0);
        title.setFont(titleFont);

        // TODO: Limit the size of the description
        Label description = new Label(film.getDescription());
        description.setWrapText(true);
        description.setMaxWidth(200.0);
        description.setLayoutX(15.0);
        description.setLayoutY(50.0);
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
        } catch (IllegalArgumentException e) {
            System.err.print("Unable to find film");
            System.err.println(film.getImagePath());
        }


        Label screenings = new Label(film.getScreeningsDescription());
        screenings.setLayoutX(15.0);
        screenings.setLayoutY(120.0);
        Font screeningFont = new Font(15.0);
        screenings.setFont(screeningFont);

        // TODO: Remove the other screenings

        if (imageView != null) {
            group.getChildren().addAll(rectangle, title, description, imageView, screenings);
        } else {
            group.getChildren().addAll(rectangle, title, description, screenings);

        }

        moviesVBox.getChildren().add(group);

        if (isSelected) {
            selectFilm(film, rectangle);
        }
    }

    private void createFilm() {

//        Group filmGroup = new Group();
//        filmGroup.setLayoutX(10.0);
//        filmGroup.setLayoutY(15.0);
//        Rectangle background = new Rectangle();
//        background.setArcHeight(5.0);
//        background.setArcWidth(5.0);
//        background.setFill(Color.BLACK);
//        background.setHeight(150.0);
//        background.setWidth(367.0);
//
//        filmGroup.getChildren().add(background);
//
//        filmGroup.toFront();
//
//        moviesAnchorPane.getChildren().add(filmGroup);


//        filmGroup.getChildren().add()

//        <Group layoutX="10.0" layoutY="15.0">
//                     <children>
//                        <Rectangle arcHeight="5.0" arcWidth="5.0" fill="WHITE" height="150.0" stroke="#2f3c4d" strokeType="INSIDE" width="367.0" />
//                        <ImageView fitHeight="216.0" fitWidth="88.0" layoutX="247.0" layoutY="15.0" pickOnBounds="true" preserveRatio="true">
//                           <image>
//                              <Image url="@../resources/TheBrokenPoster.jpg" />
//                           </image>
//                        </ImageView>
//                        <Text layoutX="29.0" layoutY="45.0" strokeType="OUTSIDE" strokeWidth="0.0" text="The Broken">
//                           <font>
//                              <Font size="25.0" />
//                           </font>
//                        </Text>
//                        <Label layoutX="34.0" layoutY="65.0" text="Label" />
//                        <Label layoutX="108.0" layoutY="66.0" text="Label" />
//                        <Label layoutX="33.0" layoutY="102.0" text="Date" />
//                     </children>
//                  </Group>
    }

    private void addScreeningsToView() {
        // TODO: Switch to flowpane or tabs
        double xPosition = 0.0;
        screeningTimes.getChildren().clear();
        for (Screening screening :
                selectedFilm.getUpcomingScreenings()) {
            try {
                Button screeningButton = new Button();
                screeningButton.setText(screening.getShortDate());
                screeningButton.setLayoutX(xPosition);
                screeningButton.setLayoutY(0);
                screeningButton.setOnAction((event) -> {
                    try {
                        CustomerBookingController.selectedScreening = screening;
                        Navigation.loadCustFxml(Navigation.CUST_BOOKING_VIEW);
                    } catch (IOException e) {
                        // TODO: Add loggin LOGGER.logp(Level.WARNING, "CustomerRootController", "openBookingView", "Failed to load CustomerBooking View. See: " + e);
                        System.err.println(e);
                        e.printStackTrace();
                    }
                });

                screeningTimes.getChildren().add(screeningButton);

                xPosition += 100.0;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    public void datePickerHandler(ActionEvent actionEvent) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            if (datePicker.getValue() != null)
                selectedDate = dateFormat.parse(String.valueOf(datePicker.getValue()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        updateFilmList();
    }

    public void showAllFilms(ActionEvent actionEvent) {
        datePicker.setValue(null);
        selectedDate = null;
        searchText = "";
        searchField.setText("");
        updateFilmList();
    }

    public void setColorsOfDatePicker() throws SQLException, ClassNotFoundException {
        ObservableList<Screening> screenings = ScreeningDAO.getScreeningObservableList();

        ArrayList<LocalDate> allScreenDates = new ArrayList<>();

        for (Screening screening :
                screenings) {
            try {
                LocalDate date = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(screening.getDateObject()));
                allScreenDates.add(date);
            } catch (ParseException | NullPointerException e) {
                System.err.println("Unable to parse string");
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
