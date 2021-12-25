package grupo1.tresenraya.modelo;

public class Cell {
    private Jugador jugador;
    private boolean marked;
    private Position position;

    public Cell(Position position) {
        this.position = position;
        marked = false;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public boolean isMarked() {
        return marked;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return String.format("Cell{%d, %d}", position.getX(), position.getY());
    }
}
