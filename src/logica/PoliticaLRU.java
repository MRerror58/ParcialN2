package logica;

import dominio.ReporteAlgoritmo;
import java.util.ArrayList;
import java.util.List;

public final class PoliticaLRU {

    public ReporteAlgoritmo correr(int casillas, List<Integer> referencias) {
        List<Integer> marcos = new ArrayList<>();
        int fallas = 0, impactos = 0;
        StringBuilder log = new StringBuilder(">> LRU <<\n");

        for (int i = 0; i < referencias.size(); i++) {
            int ref = referencias.get(i);
            int pos = marcos.indexOf(ref);
            if (pos >= 0) {
                impactos++;
                log.append("ref ").append(ref).append(" -> ").append(serializar(marcos))
                   .append(" hit\n");
            } else {
                fallas++;
                String nota = "";
                if (marcos.size() < casillas) {
                    marcos.add(ref);
                } else {
                    int victima = menosReciente(marcos, referencias, i);
                    int sale = marcos.set(victima, ref);
                    nota = " (sustituye a " + sale + ")";
                }
                log.append("ref ").append(ref).append(" -> ").append(serializar(marcos))
                   .append(" miss").append(nota).append('\n');
            }
        }
        log.append("Fallas: ").append(fallas).append(" | Impactos: ").append(impactos);
        return new ReporteAlgoritmo("LRU", log.toString(), fallas, impactos);
    }

    private int menosReciente(List<Integer> marcos, List<Integer> refs, int actual) {
        int victima = 0;
        int marca = -1;
        for (int i = 0; i < marcos.size(); i++) {
            int ultimo = ultimaAparicion(marcos.get(i), refs, actual - 1);
            if (ultimo > marca) {
                marca = ultimo;
                victima = i;
            }
        }
        return victima;
    }

    private int ultimaAparicion(int pag, List<Integer> refs, int desde) {
        for (int i = desde; i >= 0; i--) {
            if (refs.get(i) == pag) return i;
        }
        return -1;
    }

    private String serializar(List<Integer> marcos) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < marcos.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(marcos.get(i));
        }
        return sb.append(']').toString();
    }
}
