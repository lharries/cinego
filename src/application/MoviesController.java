package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import models.Film;

/**
 * Sources:
 * - https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
 * - https://stackoverflow.com/questions/24700765/how-to-hide-a-pannable-scrollbar-in-javafx
 */


public class MoviesController implements Initializable {

    @FXML
    public ScrollPane moviesScrollPane;

    @FXML
    public AnchorPane moviesAnchorPane;

    @FXML
    public VBox moviesVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        moviesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        moviesVBox.setPadding(new Insets(10.0));
        moviesVBox.setSpacing(10.0);

        initMovies("Title","Description","Screening","/resources/TheBrokenPoster.jpg");
        initMovies("Title","Description","Screening","/resources/TheBrokenPoster.jpg");
        initMovies("Title","Description","Screening","/resources/TheBrokenPoster.jpg");
        initMovies("Title","Description","Screening","/resources/TheBrokenPoster.jpg");
        initMovies("Title","Description","Screening","/resources/TheBrokenPoster.jpg");
        initMovies("Title","Description","Screening","/resources/TheBrokenPoster.jpg");

    }

    public void initMovies(String filmTitle, String filmDescription, String filmScreening, String filmImagePath) {

        Group group = new Group();

        Rectangle rectangle = new Rectangle(10.0, 10.0, 371.0, 177.0);
        rectangle.setFill(Color.WHITE);
        rectangle.setArcHeight(5.0);
        rectangle.setArcWidth(5.0);
        rectangle.setStyle("-fx-padding: 5 5 5 5;");

        Label title = new Label(filmTitle);
        title.setLayoutX(15.0);
        title.setLayoutY(10.0);
        Font titleFont = new Font(44.0);
        title.setFont(titleFont);

        // TODO: Limit the size of the description
        Label description = new Label(filmDescription);
        description.setLayoutX(15.0);
        description.setLayoutY(70.0);
        Font descriptionFont = new Font(15.0);
        description.setFont(descriptionFont);

        Label screening = new Label(filmScreening);
        screening.setLayoutX(15.0);
        screening.setLayoutY(120.0);
        Font screeningFont = new Font(15.0);
        screening.setFont(screeningFont);

                Image image = new Image(filmImagePath);
        ImageView imageView = new ImageView(image);
        imageView.setX(230.0);
        imageView.setY(30.0);
        imageView.setFitHeight(145.0);
        imageView.setFitWidth(134.0);

        group.getChildren().addAll(rectangle, title, description, screening, imageView);

        moviesVBox.getChildren().add(group);

//        <Rectangle arcHeight="5.0" arcWidth="5.0" fill="WHITE" height="177.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" style="padding: 10px; margin: 5px;" width="371.0" />
//      <ImageView fitHeight="145.0" fitWidth="134.0" layoutX="253.0" layoutY="16.0" pickOnBounds="true" preserveRatio="true">
//         <image>
//            <Image url="@../resources/TheBrokenPoster.jpg" />
//         </image>
//      </ImageView>
//      <Label layoutX="34.0" layoutY="109.0" text="17:30 29/11" />
//      <Label layoutX="34.0" layoutY="26.0" text="Title">
//         <font>
//            <Font size="44.0" />
//         </font>
//      </Label>
//      <Label layoutX="114.0" layoutY="109.0" text="19:00 30/11" />
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


}
