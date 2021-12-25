# Tic-Tac-Toe

Este es un juego de tres en raya que utiliza un árbol de decisión para que la
computadora decida la jugada apropiada en cada turno. La métrica que se
utiliza para determinar la mejor jugada es la _utilidad del tablero_, que está
definida como el número de filas o columnas que un jugador tiene disponible
para sí menos aquellas del oponente.

Se calculan las utilidades de dos generaciones de tableros, la del jugador
actual y la del oponente luego de cada posible jugada del jugador actual.
Luego se escoge la utilidad mínima del oponente y entre ellas se escoge la que
genera la utilidad máxima para el jugador actual.

