package logica;

import dominio.AlmacenamientoVirtual;
import dominio.MapaPaginas;
import dominio.RAM;
import dominio.ReporteTraduccion;

public final class Traductor {

    public ReporteTraduccion resolver(int capVirtual, int capFisica, int tamanioPagina,
                                       int direccionVirtual) {
        validar(capVirtual, capFisica, tamanioPagina, direccionVirtual);

        AlmacenamientoVirtual disco = new AlmacenamientoVirtual(capVirtual, tamanioPagina);
        RAM ram = new RAM(capFisica, tamanioPagina);
        MapaPaginas mapa = poblarMapa(disco, ram);

        int pagina = direccionVirtual / tamanioPagina;
        int offset = direccionVirtual % tamanioPagina;
        int casilla = mapa.consultar(pagina);

        StringBuilder out = new StringBuilder();
        out.append("=== TRADUCCION DE DIRECCIONES ===\n\n");
        out.append("Cap. virtual  : ").append(capVirtual).append('\n');
        out.append("Cap. RAM      : ").append(capFisica).append('\n');
        out.append("Tamanio pag.  : ").append(tamanioPagina).append('\n');
        out.append("Bloques virt. : ").append(disco.bloquesTotales()).append('\n');
        out.append("Casillas RAM  : ").append(ram.casillasDisponibles()).append("\n\n");
        out.append("--- MAPA DE PAGINAS ---\n");
        out.append(mapa.renderizar()).append('\n');
        out.append("--- PROCEDIMIENTO ---\n");
        out.append("Dir. virtual ingresada: ").append(direccionVirtual).append('\n');
        out.append("Pagina       = ").append(direccionVirtual).append(" / ")
           .append(tamanioPagina).append(" = ").append(pagina).append('\n');
        out.append("Offset       = ").append(direccionVirtual).append(" % ")
           .append(tamanioPagina).append(" = ").append(offset).append('\n');

        if (casilla == -1) {
            out.append("La pagina ").append(pagina).append(" no esta cargada en RAM.\n");
            out.append("Resultado    : FALLO de pagina.\n");
        } else {
            int fisica = casilla * tamanioPagina + offset;
            out.append("La pagina ").append(pagina).append(" esta en la casilla ")
               .append(casilla).append(".\n");
            out.append("Dir. fisica  = ").append(casilla).append(" * ")
               .append(tamanioPagina).append(" + ").append(offset).append('\n');
            out.append("Dir. fisica  = ").append(fisica).append('\n');
        }
        return new ReporteTraduccion(out.toString());
    }

    private MapaPaginas poblarMapa(AlmacenamientoVirtual disco, RAM ram) {
        MapaPaginas mapa = new MapaPaginas(disco.bloquesTotales());
        int carga = Math.min(ram.casillasDisponibles(), disco.bloquesTotales());
        for (int i = 0; i < carga; i++) {
            mapa.registrar(i, i);
        }
        return mapa;
    }

    private void validar(int capVirtual, int capFisica, int tamPag, int dirVirtual) {
        if (capVirtual <= 0 || capFisica <= 0 || tamPag <= 0) {
            throw new IllegalArgumentException("Las capacidades y el tamano de pagina deben ser > 0.");
        }
        if (capVirtual % tamPag != 0 || capFisica % tamPag != 0) {
            throw new IllegalArgumentException("Las memorias deben ser multiplos del tamano de pagina.");
        }
        if (dirVirtual < 0 || dirVirtual >= capVirtual) {
            throw new IllegalArgumentException("La direccion virtual excede la memoria virtual.");
        }
    }
}
