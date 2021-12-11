package grupo1.tresenraya.modelo;

public class Tablero {
    private Cell[][] tablero;
    private int utilidad;

    public Tablero() {
        tablero = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = new Cell(new Position(i, j));
            }
        }
    }

    public Cell get(int row, int col) {
        return tablero[row][col];
    }

    public boolean won(Jugador jugador) {
        return checkCols(jugador) || checkRows(jugador) || checkDiagonals(jugador);
    }

    public boolean checkCols(Jugador jugador) {
        return (tablero[0][0].getJugador() == jugador
                && tablero[0][1].getJugador() == jugador
                && tablero[0][2].getJugador() == jugador) ||
            (tablero[1][0].getJugador() == jugador
             && tablero[1][1].getJugador() == jugador
             && tablero[1][2].getJugador() == jugador) ||
            (tablero[2][0].getJugador() == jugador
             && tablero[2][1].getJugador() == jugador
             && tablero[2][2].getJugador() == jugador);
    }

    public boolean checkRows(Jugador jugador) {
        return (tablero[0][0].getJugador() == jugador
                && tablero[1][0].getJugador() == jugador
                && tablero[2][0].getJugador() == jugador) ||
            (tablero[0][1].getJugador() == jugador
             && tablero[1][1].getJugador() == jugador
             && tablero[2][1].getJugador() == jugador) ||
            (tablero[0][2].getJugador() == jugador
             && tablero[1][2].getJugador() == jugador
             && tablero[2][2].getJugador() == jugador);
    }

    public boolean checkDiagonals(Jugador jugador) {
        return (tablero[0][0].getJugador() == jugador
                && tablero[1][1].getJugador() == jugador
                && tablero[2][2].getJugador() == jugador) ||
            (tablero[0][2].getJugador() == jugador
             && tablero[1][1].getJugador() == jugador
             && tablero[2][0].getJugador() == jugador);
    }
}
