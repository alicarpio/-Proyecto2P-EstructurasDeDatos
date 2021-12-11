package grupo1.tresenraya.controlador;

import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.geometry.Insets;
import javafx.fxml.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

import grupo1.tresenraya.modelo.*;

public class ControladorJuego {
    enum ModoJuego {
        JUGADOR, COMPUTADORA
    }


    @FXML
    private GridPane tableroJuego;

    private GameState gameState;
    private Tablero tablero;
    private ModoJuego modoJuego;

    final private double CELL_WIDTH = 100;

    public ControladorJuego(String modoJuego) {
        if (modoJuego.equals("Computadora"))
            this.modoJuego = ModoJuego.COMPUTADORA;
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
        actualizarTablero();
    }

    private void actualizarTablero() {
        tableroJuego.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = tablero.get(i, j);
                StackPane st = new StackPane();
                if (cell.isMarked()) {
                    anadirMarca(cell, st);
                    checkVictory(cell.getJugador());
                }
                st.setOnMouseClicked(e -> {
                    if (gameState.marcarCelda(cell))
                        actualizarTablero();
                    if (modoJuego == ModoJuego.COMPUTADORA) {
                        turnoComputadora();
                        actualizarTablero();
                    }
                });
                tableroJuego.add(st, j, i);
            }
        }
        tableroJuego.setGridLinesVisible(true);
    }

    private void anadirMarca(Cell cell, StackPane st) {
        switch (cell.getJugador()) {
        case EQUIS: anadirEquis(cell, st); break;
        case CIRCULO: anadirCirculo(cell, st); break;
        default: throw new RuntimeException("Invalid Jugador " + cell.getJugador());
        }
    }

    private void anadirEquis(Cell cell, StackPane st) {
        Rectangle rect1 = new Rectangle(CELL_WIDTH/8, CELL_WIDTH/1.2, Color.CADETBLUE);
        Rectangle rect2 = new Rectangle(CELL_WIDTH/8, CELL_WIDTH/1.2, Color.CADETBLUE);
        rect1.setRotate(45);
        rect2.setRotate(-45);
        st.getChildren().addAll(rect1, rect2);
    }

    private void anadirCirculo(Cell cell, StackPane st) {
        Circle circle1 = new Circle(CELL_WIDTH/2.5, Color.CORAL);
        Circle circle2 = new Circle(CELL_WIDTH/3.5, Color.WHITE);
        st.getChildren().addAll(circle1, circle2);
    }

    private void turnoComputadora() {
        gameState.marcarCelda(tablero.get(1, 1));
    }

    private void checkVictory(Jugador jugador) {
        if (tablero.won(jugador)) {
            System.out.println("Gano!");
            Platform.exit();
        }
    }
}
