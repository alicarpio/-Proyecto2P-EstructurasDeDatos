package grupo1.tresenraya;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.fxml.*;
import jfxtras.styles.jmetro.*;

import java.io.IOException;

public class App extends Application {

    public static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        App.setScene("inicio");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("images/xo.png"));
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.show();
    }

    public static void setScene(String fxml) {
        try {
            scene = new Scene(loadFXML(fxml), 1000, 650);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/"+fxml+".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
