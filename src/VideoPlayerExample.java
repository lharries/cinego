import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/**
 * References:
 * - https://stackoverflow.com/questions/18758041/play-a-youtube-video-using-javafx
 * - https://docs.oracle.com/javafx/2/api/javafx/scene/web/WebView.html
 *
 */


public class VideoPlayerExample extends Application {
    public static void main(String[] args) { launch(args); }

    @Override public void start(Stage stage) throws Exception {
        WebView webview = new WebView();
        webview.getEngine().load(

                //adjust the Ytube link to "embed/" -> shows only the embedded movie view
//                "https://www.youtube.com/embed/6AgjRgr65BQ"
                "http://www.imdb.com/list/ls053181649/videoplayer/vi3114711065?ref_=hm_hp_i_1"
        );
        webview.setPrefSize(640, 390);

        stage.setScene(new Scene(webview));
        stage.show();
    }
}