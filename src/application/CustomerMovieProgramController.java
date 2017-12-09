package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Film;
import models.FilmDAO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static application.Navigation.CUST_BOOKING_VIEW;

/**
 *
 *
 */
public class CustomerMovieProgramController implements Initializable {

    //TODO: render the necessary fields for the Movie Program list view table (Title, time, description, IMDB rating, image, trailer)


    @FXML
    private ImageView backgroundImg;

    @FXML
    private Button toCustProf;

    @FXML
    private TableView<String> tableView;


    /**
     *
     * @param location
     * @param resources
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        initTable();
    }


    private void initTable() {
        try {
            // Create the Lists for the ListViews
            TableColumn name = new TableColumn("name");
            TableColumn screenings = new TableColumn("screenings");
            TableColumn misc = new TableColumn("misc");

            tableView.getColumns().addAll(name, screenings, misc);

//            tableView.setItems(FilmDAO.getFilmObservableList());
//            System.out.print(listView);
        } catch (Exception e) {
            System.out.print(e);
        }
    }


    @FXML
    private void sortMoviesByDate() {
        //TODO: add ability to query movies from database based on the selected date
        //TODO: return list view to only include movie screenings on selected date
    }

    @FXML
    private void sortMoviesByTime() {
        //TODO: add ability to query movies from database based on the selected date
        //TODO: return list view to only include movie screenings on selected date
    }

    @FXML
    private void filterMoviesBySearch() {
        //TODO: add movie filter based on the input search string
        //TODO: ensure that search queries all attributes of table (title, description, etc.) and returns selection live while typing
    }

    @FXML
    private void openBookingView() throws IOException {
        CustomerRootController controller = new CustomerRootController();
        controller.openBookingView();
    }


}
