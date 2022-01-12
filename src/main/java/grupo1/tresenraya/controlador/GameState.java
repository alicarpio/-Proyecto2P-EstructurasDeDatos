package grupo1.tresenraya.controlador;

import grupo1.tresenraya.modelo.*;

public class GameState {
    private Jugador jugador;

    public GameState(Jugador jugador) {
        this.jugador = jugador;
    }

    public boolean marcarCelda(Cell cell) {
        if (!cell.isMarked()) {
            cell.setJugador(jugador);
            cell.setMarked(true);
            changeJugador();
            return true;
        }
        return false;
    }

    public void changeJugador() {
        jugador = jugador.getOponente();
    }

    public Jugador getJugador() {
        return jugador;
    }
}
