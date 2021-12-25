package grupo1.tresenraya.modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import grupo1.tresenraya.DS.RoseTree;

public class Computador {
    private Jugador jugador;

    public Computador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Jugador getOponente() {
        return jugador == Jugador.EQUIS ? Jugador.CIRCULO : Jugador.EQUIS;
    }

    public RoseTree<Tablero> generarTableros(Tablero actual) {
        Comparator<Tablero> cmp = (t1, t2) ->
            t2.getUtilidad(jugador) - t1.getUtilidad(jugador);
        Comparator<Tablero> cmpOp = (t1, t2) ->
            t1.getUtilidad(getOponente()) - t2.getUtilidad(getOponente());
        // Primera generacion: comparamos de acuerdo a la utilidad de nuestras
        // posibles jugadas, maxheap
        RoseTree<Tablero> t = new RoseTree<>(actual, cmp);
        // Segunda generacion: comparamos de acuerdo a la utilidad de las
        // jugadas del oponente, minheap
        t.addChildren(sgteGeneracion(actual).stream()
                .map(tbl -> new RoseTree<>(tbl, cmpOp))
                .toArray(RoseTree[]::new));
        t.getChildren().forEach(tree -> {
            tree.addChildren(sgteGeneracion(tree.getContent()).stream()
                    .map(tbl -> new RoseTree<>(tbl, cmpOp))
                    .toArray(RoseTree[]::new));
        });

        return t;
    }

    public List<Tablero> sgteGeneracion(Tablero tablero) {
        List<Tablero> tableros = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tablero tbl = tablero.copy();
                if (tbl.isEmpty(i, j)) {
                    tbl.mark(i, j);
                    tableros.add(tbl);
                }
            }
        }
        return tableros;
    }
}
