package grupo1.tresenraya.controlador;

import grupo1.tresenraya.App;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.geometry.Insets;
import javafx.fxml.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;

import grupo1.tresenraya.modelo.*;

import java.util.concurrent.atomic.AtomicReference;

public class ControladorJuego {
    enum ModoJuego {
        JUGADOR, COMPUTADORA
    }

    @FXML
    private GridPane tableroJuego;
    @FXML
    private Button btnAyuda;

    private GameState gameState;
    private Tablero tablero;
    private ModoJuego modoJuego;
    private ModoJuego modoJuego1;
    private Computador computadora;
    private Computador computadora1;

    final private double CELL_WIDTH = 360 / 3;

    public ControladorJuego(String modoJuego, String modoJuego1, String pieza) {
        tablero = new Tablero();
        if (modoJuego.equals("Computer")) {
            this.modoJuego = ModoJuego.COMPUTADORA;
            computadora = new Computador(pieza.equals("X") ? Jugador.EQUIS : Jugador.CIRCULO);
        }
        if (modoJuego1.equals("Computer")) {
            this.modoJuego1 = ModoJuego.COMPUTADORA;
            computadora1 = new Computador(pieza.equals("X") ? Jugador.CIRCULO : Jugador.EQUIS);
        }
        gameState = new GameState(pieza.equals("X") ? Jugador.EQUIS : Jugador.CIRCULO);
    }

    @FXML
    private void initialize() {
        tableroJuego.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        tableroJuego.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        tableroJuego.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));

        tableroJuego.getRowConstraints().add(new RowConstraints(CELL_WIDTH));
        tableroJuego.getRowConstraints().add(new RowConstraints(CELL_WIDTH));
        tableroJuego.getRowConstraints().add(new RowConstraints(CELL_WIDTH));

        if (modoJuego.equals(ModoJuego.COMPUTADORA)) {
            Cell cCell = Computador.decidirJugada(tablero, gameState.getJugador());
            gameState.marcarCelda(cCell);
        }

        btnAyuda.setOnAction(this::showHint);

        actualizarTablero();
    }

    private void showHint(ActionEvent e) {
        Cell hint = Computador.decidirJugada(tablero, gameState.getJugador());
        AtomicReference<StackPane> st = new AtomicReference<>();

        for (Node node : tableroJuego.getChildren()) {
            if (GridPane.getColumnIndex(node) == hint.getPosition().getY()
                    && GridPane.getRowIndex(node) == hint.getPosition().getX()) {
                st.set((StackPane) node);
            }
        }

        new Thread(() -> {
            Platform.runLater(() -> st.get().setBackground(new Background(
                    new BackgroundFill(Color.web("0xffff00", 0.5),
                            new CornerRadii(50, true), Insets.EMPTY))));
            try {
                Thread.sleep(400);
            } catch (Exception ex) {
            }
            Platform.runLater(() -> st.get().setBackground(Background.EMPTY));
        }).start();
    }

    private void hacerJugada(Cell  cell) {
        if (gameState.marcarCelda(cell)) {
            actualizarTablero();
            checkVictory(cell);
            empate();
        }
        if (modoJuego == ModoJuego.COMPUTADORA && !tablero.tableroLleno() && !tablero.won(cell.getJugador())) {
            Cell cCell = Computador.decidirJugada(tablero, computadora.getJugador());
            gameState.marcarCelda(cCell);
            actualizarTablero();
            checkVictory(cCell);
            empate();
        }
    }

    private void actualizarTablero() {
        tableroJuego.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = tablero.get(i, j);
                StackPane st = new StackPane();
                st.getStyleClass().add("grid-cell");
                if (cell.isMarked()) {
                    anadirMarca(cell, st);
                }
                st.setOnMouseClicked(e -> {
                    hacerJugada(cell);
                });
                tableroJuego.add(st, j, i);
            }
        }
    }

    private void anadirMarca(Cell cell, StackPane st) {
        switch (cell.getJugador()) {
            case EQUIS:
                anadirEquis(st);
                break;
            case CIRCULO:
                anadirCirculo(st);
                break;
            default:
                throw new RuntimeException("Invalid Jugador " + cell.getJugador());
        }
    }

    private void anadirEquis( StackPane st) {
        st.getChildren().add(new ImageView(new Image("images/x-red.png", CELL_WIDTH / 2, CELL_WIDTH / 2, true, true)));
    }

    private void anadirCirculo(StackPane st) {
        st.getChildren().add(new ImageView(new Image("images/o-blue.png", CELL_WIDTH / 2, CELL_WIDTH / 2, true, true)));
    }

    private void checkVictory(Cell cell) {
        if (tablero.won(cell.getJugador())) {
            String name = cell.getJugador().toString();
            System.out.println(name);
            crearAlerta("GANADOR!!", name.equals("EQUIS") ? "x-red"  : "o-blue");
        }
    }

    private void empate() {
        if (tablero.tableroLleno()) {
            System.out.println("Empate!");
            crearAlerta("EMPATE!!", "xo");
        }
    }

    private void crearAlerta(String msg, String img) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Image imagen = new Image("images/"+img+".png", 200.0, 200.0, true, true);
        alert.setGraphic(new ImageView(imagen));
        alert.setHeaderText(msg);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("images/xo.png"));
        stage.setTitle("RESULTADO");
        alert.showAndWait();
        cambiarAVistaInicio();
    }

    private void cambiarAVistaInicio() {
        Stage stage = (Stage) this.tableroJuego.getScene().getWindow();
        App.setScene("inicio");
        stage.setScene(App.scene);
    }
}
