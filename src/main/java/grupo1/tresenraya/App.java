package grupo1.tresenraya;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.fxml.*;
import jfxtras.styles.jmetro.*;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/inicio.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        root.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        Scene scene = new Scene(root, 640, 480);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
