package grupo1.tresenraya.modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import grupo1.tresenraya.DS.RoseTree;
import grupo1.tresenraya.DS.Heap;

public class Computador {
    private Jugador jugador;

    public Computador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Jugador getOponente() {
        return jugador == Jugador.EQUIS ? Jugador.CIRCULO : Jugador.EQUIS;
    }

    public Cell decidirJugada(Tablero actual) {
        RoseTree<Tablero> gameTree = generarTableros(actual);
        for (RoseTree<Tablero> tbl : gameTree.getChildren()) {
            // Associate the minimum utility board with its parent
            tbl.setContent(tbl.getChildren().pop().getContent());
        }
        // Now calculate the maximum utility for the computer
        Heap<Tablero> maxHeap = new Heap<>((t1, t2) -> {
            return t2.getUtilidad(jugador) - t1.getUtilidad(jugador);
        });
        gameTree.getChildren().forEach(t -> maxHeap.insert(t.getContent()));
        Tablero best = maxHeap.pop();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (best.get(i, j).isMarked() && !actual.get(i, j).isMarked())
                    return actual.get(i, j);
            }
        return null;
    }

    public RoseTree<Tablero> generarTableros(Tablero actual) {
        // Este no lo usamos en realidad
        Comparator<Tablero> cmp = (t1, t2) -> t2.getUtilidad(jugador) - t1.getUtilidad(jugador);
        // Este si importa, ordena segun la minima utilidad los tableros del
        // oponente
        Comparator<Tablero> cmpOp = (t1, t2) -> t1.getUtilidad(getOponente()) - t2.getUtilidad(getOponente());
        RoseTree<Tablero> t = new RoseTree<>(actual, cmp);
        t.addChildren(sgteGeneracion(actual).stream()
                .map(tbl -> new RoseTree<>(tbl, cmpOp)).toArray(RoseTree[]::new));
        t.getChildren().forEach(tree -> 
                tree.addChildren(sgteGeneracion(tree.getContent()).stream()
                    .map(tbl -> new RoseTree<>(tbl, cmp)).toArray(RoseTree[]::new)));
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
