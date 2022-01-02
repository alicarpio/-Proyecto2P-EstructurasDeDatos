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

    public static Jugador getOponente(Jugador jugador) {
        return jugador == Jugador.EQUIS ? Jugador.CIRCULO : Jugador.EQUIS;
    }

    public Cell seleccionarCelda(Tablero actual, Tablero nuevo, Jugador jugador) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (nuevo.get(i, j).isMarked() 
                        && !actual.get(i, j).isMarked()
                        && nuevo.get(i, j).getJugador().equals(jugador)) {
                    return actual.get(i, j);
                }
            }
        return null;
    }

    public Cell decidirJugada(Tablero actual) {
        RoseTree<Tablero> gameTree = generarTableros(actual, jugador);

        for (RoseTree<Tablero> t1 : gameTree.getChildren()) {
            if (t1.getContent().won(jugador)) {
                return seleccionarCelda(actual, t1.getContent(), jugador);
            }
        }

        for (RoseTree<Tablero> t1 : gameTree.getChildren()) {
            for (RoseTree<Tablero> t2 : t1.getChildren()) {
                if (t2.getContent().won(getOponente(jugador)))
                    return seleccionarCelda(actual, t2.getContent(), getOponente(jugador));
            }
            t1.setContent(t1.getChildren().pop().getContent());
        }

        // Now calculate the maximum utility for the computer
        Heap<Tablero> maxHeap = new Heap<>((t1, t2) ->
                t2.getUtilidad(jugador) - t1.getUtilidad(jugador));
        gameTree.getChildren().forEach(t -> maxHeap.insert(t.getContent()));
        return seleccionarCelda(actual, maxHeap.pop(), jugador);
    }

    public static RoseTree<Tablero> generarTableros(Tablero actual, Jugador jugador) {
        Comparator<Tablero> cmp = (t1, t2) -> t1.getUtilidad(jugador) - t2.getUtilidad(jugador);

        RoseTree<Tablero> tree = new RoseTree<>(actual, cmp);
        for (Tablero t : actual.sgteGeneracion(jugador)) {
            tree.addChildren(new RoseTree<>(t, cmp));
        }

        tree.getChildren()
            .forEach(t1 -> t1.addChildren(t1.getContent().sgteGeneracion(getOponente(jugador))
                        .stream()
                        .map(tbl -> new RoseTree<>(tbl, cmp))
                        .toArray(RoseTree[]::new)));

        return tree;
    }
}
