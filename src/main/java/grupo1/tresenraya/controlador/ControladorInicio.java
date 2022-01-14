package grupo1.tresenraya.controlador;

import java.io.IOException;

import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

public class ControladorInicio {
    @FXML
    private ToggleGroup modoJuego;
    @FXML
    private VBox rootPane;
    @FXML
    private ToggleGroup modoJuego1;
    @FXML
    private ToggleGroup pieza;

    private String getModoJuego() {
        RadioButton selectedRadioButton = (RadioButton)modoJuego.getSelectedToggle();
        return selectedRadioButton.getText();
    }

    private String getModoJuego1() {
        RadioButton selectedRadioButton = (RadioButton)modoJuego1.getSelectedToggle();
        return selectedRadioButton.getText();
    }

    private String getPieza() {
        RadioButton selectedRadioButton = (RadioButton)pieza.getSelectedToggle();
        return selectedRadioButton.getText();
    }


    @FXML
    private void onJugar(ActionEvent e) throws IOException {

        String modoJuego = getModoJuego();
        String modoJuego1 = getModoJuego1();
        String pieza = getPieza();

        ControladorJuego controladorJuego = new ControladorJuego(modoJuego, modoJuego1, pieza);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/juego.fxml"));
        fxmlLoader.setController(controladorJuego);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 650);
        Stage stage = (Stage)this.rootPane.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
    }
}
