package grupo1.tresenraya.controlador;

import grupo1.tresenraya.App;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    private Computador computadora;

    final private double CELL_WIDTH = 360 / 3;

    public ControladorJuego(String modoJuego) {
        if (modoJuego.equals("Computadora")) {
            this.modoJuego = ModoJuego.COMPUTADORA;
            computadora = new Computador(Jugador.CIRCULO);
        }
        tablero = new Tablero();
        gameState = new GameState(Jugador.EQUIS);
    }

    @FXML
    private void initialize() {
        tableroJuego.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        tableroJuego.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        tableroJuego.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));

        tableroJuego.getRowConstraints().add(new RowConstraints(CELL_WIDTH));
        tableroJuego.getRowConstraints().add(new RowConstraints(CELL_WIDTH));
        tableroJuego.getRowConstraints().add(new RowConstraints(CELL_WIDTH));

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
            checkVictory();
            empate();
        }
        if (modoJuego == ModoJuego.COMPUTADORA && !tablero.tableroLleno() && !tablero.won(cell.getJugador())) {
            turnoComputadora();
            actualizarTablero();
            checkVictory();
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
                anadirEquis(cell, st);
                break;
            case CIRCULO:
                anadirCirculo(cell, st);
                break;
            default:
                throw new RuntimeException("Invalid Jugador " + cell.getJugador());
        }
    }

    private void anadirEquis(Cell cell, StackPane st) {
        Rectangle rect1 = new Rectangle(CELL_WIDTH / 8, CELL_WIDTH / 1.2, Color.CADETBLUE);
        Rectangle rect2 = new Rectangle(CELL_WIDTH / 8, CELL_WIDTH / 1.2, Color.CADETBLUE);
        rect1.setRotate(45);
        rect2.setRotate(-45);
        st.getChildren().addAll(rect1, rect2);
    }

    private void anadirCirculo(Cell cell, StackPane st) {
        Circle circle1 = new Circle(CELL_WIDTH / 2.5, Color.CORAL);
        Circle circle2 = new Circle(CELL_WIDTH / 3.5, Color.WHITE);
        st.getChildren().addAll(circle1, circle2);
    }
    private void turnoComputadora() {
        gameState.marcarCelda(Computador.decidirJugada(tablero, computadora.getJugador()));
    }

    private void checkVictory() {
        if (tablero.won(gameState.getJugador())) {
            System.out.println("Gano!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "GANA "+gameState.getJugador());
            alert.showAndWait();
        }
        else if (tablero.won(gameState.getJugador().getOponente())) {
            System.out.println("Gano!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "GANA "+gameState.getJugador().getOponente());
            alert.showAndWait();
            cambiarAVistaInicio();
        }
    }

    private void empate() {
        if (tablero.tableroLleno()) {
            System.out.println("Empate!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "EMPATE!! ");
            alert.showAndWait();
            cambiarAVistaInicio();
        }
    }

    private void cambiarAVistaInicio() {
        Stage stage = (Stage) this.tableroJuego.getScene().getWindow();
        App.setScene("inicio");
        stage.setScene(App.scene);
    }
}
