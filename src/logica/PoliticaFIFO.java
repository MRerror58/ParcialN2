package logica;

import dominio.ReporteAlgoritmo;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public final class PoliticaFIFO {

    public ReporteAlgoritmo correr(int casillas, List<Integer> referencias) {
        Deque<Integer> cola = new ArrayDeque<>();
        int fallas = 0, impactos = 0;
        StringBuilder log = new StringBuilder(">> FIFO <<\n");

        for (int ref : referencias) {
            if (cola.contains(ref)) {
                impactos++;
                log.append("ref ").append(ref).append(" -> ").append(serializar(cola))
                   .append(" hit\n");
            } else {
                fallas++;
                String nota = "";
                if (cola.size() < casillas) {
                    cola.addLast(ref);
                } else {
                    int sale = cola.removeFirst();
                    cola.addLast(ref);
                    nota = " (sustituye a " + sale + ")";
                }
                log.append("ref ").append(ref).append(" -> ").append(serializar(cola))
                   .append(" miss").append(nota).append('\n');
            }
        }
        log.append("Fallas: ").append(fallas).append(" | Impactos: ").append(impactos);
        return new ReporteAlgoritmo("FIFO", log.toString(), fallas, impactos);
    }

    private String serializar(Deque<Integer> cola) {
        StringBuilder sb = new StringBuilder("[");
        boolean primero = true;
        for (Integer v : cola) {
            if (!primero) sb.append(", ");
            sb.append(v);
            primero = false;
        }
        return sb.append(']').toString();
    }
}
