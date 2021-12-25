package grupo1.tresenraya.modelo;

public class Tablero {
    private Cell[][] tablero;

    public Tablero() {
        tablero = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = new Cell(new Position(i, j));
            }
        }
    }

    public Tablero copy() {
        Tablero newTablero = new Tablero();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j].isMarked()) {
                    newTablero.get(i, j).setMarked(true);
                    newTablero.get(i, j).setJugador(tablero[i][j].getJugador());
                }
            }
        }
        return newTablero;
    }

    public Cell get(int row, int col) {
        return tablero[row][col];
    }

    public void mark(int row, int col) {
        tablero[row][col].setMarked(true);
    }

    public boolean isEmpty(int row, int col) {
        return !tablero[row][col].isMarked();
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

    public int getUtilidad(Jugador jugador) {
        switch (jugador) {
        case EQUIS: return P(Jugador.EQUIS) - P(Jugador.CIRCULO);
        case CIRCULO: return P(Jugador.CIRCULO) - P(Jugador.EQUIS);
        default: throw new RuntimeException("Jugador invalido " + jugador);
        }
    }

    public int P(Jugador jugador) {
        int p = 0;
        for (int i = 0; i < 3; i++) {
            if ((tablero[i][0].getJugador() == jugador || !tablero[i][0].isMarked())
                    && (tablero[i][1].getJugador() == jugador || !tablero[i][1].isMarked())
                    && (tablero[0][2].getJugador() == jugador || !tablero[i][2].isMarked())) {
                p++;
            }
            if ((tablero[0][i].getJugador() == jugador || !tablero[0][i].isMarked())
                    && (tablero[1][i].getJugador() == jugador || !tablero[1][i].isMarked())
                    && (tablero[2][i].getJugador() == jugador || !tablero[2][i].isMarked())) {
                p++;
            }
        }
        if ((tablero[0][0].getJugador() == jugador || !tablero[0][0].isMarked())
                && (tablero[1][1].getJugador() == jugador || !tablero[1][1].isMarked())
                && (tablero[2][2].getJugador() == jugador || !tablero[2][2].isMarked())) {
            p++;
        }
        if ((tablero[0][2].getJugador() == jugador || !tablero[0][2].isMarked())
                && (tablero[1][1].getJugador() == jugador || !tablero[1][1].isMarked())
                && (tablero[2][0].getJugador() == jugador || !tablero[2][0].isMarked())) {
            p++;
        }
        return p;
    }
}
