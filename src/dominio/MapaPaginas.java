package dominio;

import java.util.HashMap;
import java.util.Map;

public final class MapaPaginas {

    private static final int SIN_ASIGNAR = -1;
    private final Map<Integer, Integer> asignaciones;

    public MapaPaginas(int cantidadPaginas) {
        this.asignaciones = new HashMap<>();
        for (int i = 0; i < cantidadPaginas; i++) {
            asignaciones.put(i, SIN_ASIGNAR);
        }
    }

    public void registrar(int pagina, int casilla) {
        asignaciones.put(pagina, casilla);
    }

    public int consultar(int pagina) {
        Integer valor = asignaciones.get(pagina);
        return valor == null ? SIN_ASIGNAR : valor;
    }

    public int totalPaginas() {
        return asignaciones.size();
    }

    public String renderizar() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer> e : asignaciones.entrySet()) {
            if (e.getValue() == SIN_ASIGNAR) {
                sb.append("Pag ").append(e.getKey()).append(" -> fuera de RAM\n");
            } else {
                sb.append("Pag ").append(e.getKey()).append(" -> Casilla ")
                  .append(e.getValue()).append('\n');
            }
        }
        return sb.toString();
    }
}
