package lk.ijse.gdse72.swiftsts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(load);
        stage.setTitle("Swift STS");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/images/icons8-bus-stop-64.png")));
//        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();


    }
}
