package logica;

import dominio.ReporteAlgoritmo;
import java.util.ArrayList;
import java.util.List;

public final class PoliticaOPT {

    public ReporteAlgoritmo correr(int casillas, List<Integer> referencias) {
        List<Integer> marcos = new ArrayList<>();
        int fallas = 0, impactos = 0;
        StringBuilder log = new StringBuilder(">> OPT <<\n");

        for (int i = 0; i < referencias.size(); i++) {
            int ref = referencias.get(i);
            if (marcos.contains(ref)) {
                impactos++;
                log.append("ref ").append(ref).append(" -> ").append(serializar(marcos))
                   .append(" hit\n");
            } else {
                fallas++;
                String nota = "";
                if (marcos.size() < casillas) {
                    marcos.add(ref);
                } else {
                    int victima = optima(marcos, referencias, i + 1);
                    int sale = marcos.set(victima, ref);
                    nota = " (sustituye a " + sale + ")";
                }
                log.append("ref ").append(ref).append(" -> ").append(serializar(marcos))
                   .append(" miss").append(nota).append('\n');
            }
        }
        log.append("Fallas: ").append(fallas).append(" | Impactos: ").append(impactos);
        return new ReporteAlgoritmo("OPT", log.toString(), fallas, impactos);
    }

    private int optima(List<Integer> marcos, List<Integer> refs, int desde) {
        int victima = 0;
        int marca = -1;
        for (int i = 0; i < marcos.size(); i++) {
            int proximo = proximaAparicion(marcos.get(i), refs, desde);
            if (proximo == -1) return i;
            if (proximo > marca) {
                marca = proximo;
                victima = i;
            }
        }
        return victima;
    }

    private int proximaAparicion(int pag, List<Integer> refs, int desde) {
        for (int i = desde; i < refs.size(); i++) {
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
