package grupo1.tresenraya.controlador;

import java.io.IOException;

import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;
import jfxtras.styles.jmetro.*;

public class ControladorInicio {
    @FXML
    private ToggleGroup modoJuego;
    @FXML
    private VBox rootPane;

    private String getModoJuego() {
        RadioButton selectedRadioButton = (RadioButton)modoJuego.getSelectedToggle();
        return selectedRadioButton.getText();
    }

    @FXML
    private void onJugar(ActionEvent e) throws IOException {
        String modoJuego = getModoJuego();
        ControladorJuego controladorJuego = new ControladorJuego(modoJuego);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/juego.fxml"));
        fxmlLoader.setController(controladorJuego);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 650);
        Stage stage = (Stage)this.rootPane.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
    }
}
