package grupo1.tresenraya.modelo;

public enum Jugador {
    EQUIS, CIRCULO;

    public Jugador getOponente() {
        if (this == EQUIS) return CIRCULO;
        return EQUIS;
    }
}
