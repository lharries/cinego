package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Film;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EmployeeHomeController implements Initializable {

    //TODO: add tooltips to buttons in order to convey additional information w.r.t. their functionality
    //TODO: source: https://stackoverflow.com/questions/25338873/is-there-a-simple-way-to-display-hint-texts-in-javafx

    @FXML
    private Button CreateMovieButton;

    @FXML
    private ImageView backgroundImg;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private TableView ScreeningsTable;

    @FXML
    private TableView<Film> table;

    @FXML
    private ObservableList<Film> data;

    @FXML
    private HBox hBox;

    @FXML
    private TextField addID,addTitle, addImagePath, addDescription;



    @Override
    public void initialize(URL location, ResourceBundle resources) {


//        Stage stage = new Stage();
//        Scene scene = new Scene(new Group());
//        stage.setTitle("Table View Sample");
//        stage.setWidth(450);
//        stage.setHeight(500);
//
//        final Label label = new Label("Address Book");
//        label.setFont(new Font("Arial", 20));

        table = new TableView<Film>();
        data = FXCollections.observableArrayList(
                        new Film(1, "Smith", "jacob.smith@example.com", "das"),
                        new Film(2, "Smith", "aksjdaskjdhaskdaksjdh", "das"),
                        new Film(3, "Smith", "hello world #3", "test")
                );


        table.setEditable(true);

//        TableColumn idCol = new TableColumn("ID");
//        TableColumn titleCol = new TableColumn("Title");
//        TableColumn URLCol = new TableColumn("Image URL");
//        TableColumn descriptCol = new TableColumn("Description");

        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<Film, Integer>("id"));

        TableColumn titleCol = new TableColumn("Title");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<Film, String>("title"));

        TableColumn URLCol = new TableColumn("Image URL");
        URLCol.setMinWidth(100);
        URLCol.setCellValueFactory(
                new PropertyValueFactory<Film, String>("imagePath"));

        TableColumn descriptCol = new TableColumn("Description");
        descriptCol.setMinWidth(200);
        descriptCol.setCellValueFactory(
                new PropertyValueFactory<Film, String>("description"));

        this.table.setItems(data);
        this.table.getColumns().addAll(idCol,titleCol,URLCol,descriptCol);

//        hBox.getChildren().addAll(addID,addTitle,addImagePath,addDescription);

        this.AnchorPane.getChildren().addAll(table);
//

//
//        final VBox vbox = new VBox();
//        vbox.setSpacing(5);
//        vbox.setPadding(new Insets(10, 0, 0, 10));
//        vbox.getChildren().addAll(label, table);

        //trouble maker: but I need this (below)?
//        ((Group) Main.scene.getRoot()).getChildren().addAll(AnchorPane);
//        ((Group) scene.getRoot()).getChildren().addAll(vbox);
//
//        stage.setScene(scene);
//        stage.show();



        //render background image
        BufferedImage bufferedBackground = null;
        try {
            bufferedBackground = ImageIO.read(new File("src/resources/cinWallpaper.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
        this.backgroundImg.setImage(background);

        //Tooltip feature
        this.CreateMovieButton.setTooltip(
                new Tooltip("Button of doom")
        );
    }


    /**
     *
     * Purpose: allows user to also change to movie creation view from within his scene
     */
    @FXML
    private void createMovie(){

        //adds user's input to table
        data.add(new Film(
                Integer.valueOf(addID.getText()),
                addTitle.getText(),
                addImagePath.getText(),
                addDescription.getText()));
        addID.clear();
        addTitle.clear();
        addImagePath.clear();
        addDescription.clear();


        //takes user to new view but possibly do without it
//        EmployeeRootController emplRootController = new EmployeeRootController();
//        emplRootController.openMovieFormView(event);
    }

    @FXML
    private void exportToCSV(){
        //TODO: add exporting to CSV functionality (Button triggers downloading all current movies including titles, seats booked etc.)
    }

    @FXML
    private void openSeatsBooked(ActionEvent event) {
        //TODO: open a movie's specific "seats booked overview" +

        EmployeeRootController emplRootController = new EmployeeRootController();
        emplRootController.openBookingView(event);
    }
}
