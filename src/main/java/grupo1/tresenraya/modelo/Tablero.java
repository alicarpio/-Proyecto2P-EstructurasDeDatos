package grupo1.tresenraya.modelo;

import java.util.List;
import java.util.ArrayList;

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

    public List<Tablero> sgteGeneracion(Jugador jugador) {
        List<Tablero> tableros = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tablero tbl = copy();
                if (tbl.isEmpty(i, j)) {
                    tbl.get(i, j).setJugador(jugador);
                    tbl.mark(i, j);
                    tableros.add(tbl);
                }
            }
        }
        return tableros;
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
        get(row, col).setMarked(true);
    }

    public boolean isEmpty(int row, int col) {
        return !get(row, col).isMarked();
    }

    public boolean jugadorMatches(int row, int col, Jugador jugador) {
        return get(row, col).getJugador() == jugador;
    }

    public boolean won(Jugador jugador) {
        return checkCols(jugador) || checkRows(jugador) || checkDiagonals(jugador);
    }

    public boolean checkCols(Jugador jugador) {
        return (jugadorMatches(0, 0, jugador) && jugadorMatches(0, 1, jugador) && jugadorMatches(0, 2, jugador))
                || (jugadorMatches(1, 0, jugador) && jugadorMatches(1, 1, jugador) && jugadorMatches(1, 2, jugador))
                || (jugadorMatches(2, 0, jugador) && jugadorMatches(2, 1, jugador) && jugadorMatches(2, 2, jugador));
    }

    public boolean checkRows(Jugador jugador) {
        return (jugadorMatches(0, 0, jugador) && jugadorMatches(1, 0, jugador) && jugadorMatches(2, 0, jugador))
                || (jugadorMatches(0, 1, jugador) && jugadorMatches(1, 1, jugador) && jugadorMatches(2, 1, jugador))
                || (jugadorMatches(0, 2, jugador) && jugadorMatches(1, 2, jugador) && jugadorMatches(2, 2, jugador));
    }

    public boolean checkDiagonals(Jugador jugador) {
        return (jugadorMatches(0, 0, jugador) && jugadorMatches(1, 1, jugador) && jugadorMatches(2, 2, jugador))
                || (jugadorMatches(0, 2, jugador) && jugadorMatches(1, 1, jugador) && jugadorMatches(2, 0, jugador));
    }

    public int getUtilidad(Jugador jugador) {
        Jugador oponente = jugador.getOponente();
        if (won(jugador)) {
            return -10;
        } else if (won(oponente)) {
            return -9;
        } else {
            return P(jugador) - P(oponente);
        }
    }

    public boolean tableroLleno() {
        boolean result = true;
        for (Cell[] arr : tablero) {
            for (Cell cell : arr) {
                if (!cell.isMarked()) {
                    result = false;
                }
            }
        }
        return result;
    }

    public int P(Jugador jugador) {
        int p = 0;
        for (int i = 0; i < 3; i++) {
            if ((jugadorMatches(i, 0, jugador) || isEmpty(i, 0))
                    && (jugadorMatches(i, 1, jugador) || isEmpty(i, 1))
                    && (jugadorMatches(i, 2, jugador) || isEmpty(i, 2))) {
                p++;
            }
            if ((jugadorMatches(0, i, jugador) || isEmpty(0, i))
                    && (jugadorMatches(1, i, jugador) || isEmpty(1, i))
                    && (jugadorMatches(2, i, jugador) || isEmpty(2, i))) {
                p++;
            }
        }
        if ((jugadorMatches(0, 0, jugador) || isEmpty(0, 0))
                && (jugadorMatches(1, 1, jugador) || isEmpty(1, 1))
                && (jugadorMatches(2, 2, jugador) || isEmpty(2, 2))) {
            p++;
        }
        if ((jugadorMatches(0, 2, jugador) || isEmpty(0, 2))
                && (jugadorMatches(1, 1, jugador) || isEmpty(1, 1))
                && (jugadorMatches(2, 0, jugador) || isEmpty(2, 0))) {
            p++;
        }

        return p;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append("-------\n");
            for (int j = 0; j < 3; j++) {
                if (get(i, j).isMarked()) {
                    if (get(i, j).getJugador() == Jugador.EQUIS) sb.append("|X");
                    else sb.append("|O");
                } else {
                    sb.append("| ");
                }
            }
            sb.append("|\n");
        }
        sb.append("-------\n");
        sb.append("Utilidad Circulo: " + this.getUtilidad(Jugador.CIRCULO));
        return sb.toString();
    }
}
