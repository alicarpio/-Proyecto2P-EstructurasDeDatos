package grupo1.tresenraya.modelo;

import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

import grupo1.tresenraya.DS.RoseTree;
import grupo1.tresenraya.DS.Heap;

public class Computador {
    private Jugador jugador;

    public Computador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public static Cell seleccionarCelda(Tablero actual, Tablero nuevo, Jugador jugador) {
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

    public static Cell seleccionarCeldaOP(Tablero actual, Tablero nuevo, Jugador jugador) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (nuevo.get(i, j).isMarked() && !actual.get(i, j).isMarked()
                        && !nuevo.get(i, j).getJugador().equals(jugador)) {
                    return actual.get(i, j);
                }
            }
        return null;
    }

    public static Cell decidirJugada(Tablero actual, Jugador jugador) {
        RoseTree<Tablero> gameTree = Computador.generarTableros(actual, jugador);

        Comparator<RoseTree<Tablero>> cmp = (rt1, rt2) -> {
            int sg1Best = rt1.getContent().getUtilidad(jugador);
            int sg2Best = rt2.getContent().getUtilidad(jugador);
            return sg1Best - sg2Best;
        };

        Heap<RoseTree<Tablero>> bestOps1 = new Heap<>(cmp);
        Heap<RoseTree<Tablero>> bestOps2 = new Heap<>(cmp.reversed());

        for (RoseTree<Tablero> rt : gameTree.getChildren()) {
            bestOps1.insert(rt.getChildren().peek());
            bestOps2.insert(rt.getChildren().peek());
        }

        RoseTree<Tablero> jugada1 = bestOps1.peek();
        RoseTree<Tablero> jugada2 = bestOps2.peek();

        Tablero tablero1 = jugada1.getContent();
        Tablero tablero2 = jugada2.getContent();

//        bestOps1.print();
//        System.out.println("\n\n");
//        bestOps2.print();
//        System.out.println("\n\n");

//        System.out.println(tablero1.toString()+"Jugada 1 Seleccionada");
//        System.out.println("**************"+tablero1.getUtilidad(jugador));

//        System.out.println(tablero2.toString()+"Jugada 2 Seleccionada");
//        System.out.println("**************"+tablero2.getUtilidad(jugador));

//        System.out.println("\n\n\n\n");

        Cell cell = tablero2.getUtilidad(jugador) == -10 ? seleccionarCelda(actual, tablero2, jugador)
                : tablero2.getUtilidad(jugador) == -9 ? seleccionarCeldaOP(actual, tablero2, jugador)
                : seleccionarCelda(actual, tablero1, jugador);

//        System.out.println(cell);
        return cell;

    }

    public static RoseTree<Tablero> generarTableros(Tablero actual, Jugador jugador) {
        Jugador oponente = jugador.getOponente();
        ToIntFunction<Tablero> getUtilidad = t -> t.getUtilidad(jugador);
        Comparator<Tablero> cmpP = Comparator.comparingInt(getUtilidad).reversed();

        RoseTree<Tablero> tree = new RoseTree<>(actual, cmpP);
        List<Tablero> primeraGeneracion = actual.sgteGeneracion(jugador);

        for (Tablero t1 : primeraGeneracion) {
            RoseTree<Tablero> treePrimeraG = new RoseTree<>(t1, cmpP);
            List<Tablero> segundaGeneracion = t1.sgteGeneracion(oponente);
            for (Tablero t2 : segundaGeneracion) {
                treePrimeraG.addChildren(new RoseTree<>(t2, cmpP));
            }
            tree.addChildren(treePrimeraG);
        }
        return tree;
    }
}
