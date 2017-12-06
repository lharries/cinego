package application;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import models.Film;


public class MoviesController implements Initializable{

    @FXML
    public AnchorPane moviesAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMovies();

    }

    public void initMovies(){
        System.out.println(moviesAnchorPane.getChildren());

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
