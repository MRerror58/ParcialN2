package coordinador;

import dominio.ReporteAlgoritmo;
import dominio.ReporteTraduccion;
import herramientas.Parseador;
import interfaz.PanelPrincipal;
import java.io.File;
import java.util.List;
import javafx.stage.FileChooser;
import logica.PoliticaFIFO;
import logica.PoliticaLRU;
import logica.PoliticaOPT;
import logica.Traductor;
import persistencia.LectorTXT;

public final class Coordinador {

    private final PanelPrincipal panel;
    private final LectorTXT lector;

    public Coordinador(PanelPrincipal panel) {
        this.panel = panel;
        this.lector = new LectorTXT();
    }

    public void procesarTraduccion() {
        try {
            int cv = Parseador.aEntero(panel.capVirtual());
            int cf = Parseador.aEntero(panel.capFisica());
            int tp = Parseador.aEntero(panel.tamanioPag());
            int dv = Parseador.aEntero(panel.dirVirtual());

            Traductor t = new Traductor();
            ReporteTraduccion r = t.resolver(cv, cf, tp, dv);
            panel.publicarTraduccion(r.texto());
        } catch (RuntimeException e) {
            panel.publicarTraduccion("ERROR: " + e.getMessage());
        }
    }

    public void procesarReemplazo() {
        try {
            int casillas = Parseador.aEntero(panel.casillas());
            if (casillas <= 0) {
                throw new IllegalArgumentException("La cantidad de casillas debe ser > 0.");
            }
            List<Integer> refs = Parseador.aLista(panel.cadenaRefs());

            ReporteAlgoritmo f = new PoliticaFIFO().correr(casillas, refs);
            ReporteAlgoritmo l = new PoliticaLRU().correr(casillas, refs);
            ReporteAlgoritmo o = new PoliticaOPT().correr(casillas, refs);

            panel.publicarReemplazo(combinar(f, l, o));
        } catch (RuntimeException e) {
            panel.publicarReemplazo("ERROR: " + e.getMessage());
        }
    }

    public void importarTraduccion() {
        try {
            File f = elegirArchivo();
            if (f == null) return;
            String[] d = lector.leerBloqueTraduccion(f.toPath());
            panel.setDatosTraduccion(d[0], d[1], d[2], d[3]);
            procesarTraduccion();
        } catch (Exception e) {
            panel.publicarTraduccion("ERROR al leer archivo: " + e.getMessage());
        }
    }

    public void importarReemplazo() {
        try {
            File f = elegirArchivo();
            if (f == null) return;
            String[] d = lector.leerBloqueReemplazo(f.toPath());
            panel.setDatosReemplazo(d[0], d[1]);
            procesarReemplazo();
        } catch (Exception e) {
            panel.publicarReemplazo("ERROR al leer archivo: " + e.getMessage());
        }
    }

    private File elegirArchivo() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccione un .txt");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texto", "*.txt"));
        return fc.showOpenDialog(null);
    }

    private String combinar(ReporteAlgoritmo a, ReporteAlgoritmo b, ReporteAlgoritmo c) {
        StringBuilder sb = new StringBuilder();
        sb.append(a.traza()).append("\n\n");
        sb.append(b.traza()).append("\n\n");
        sb.append(c.traza()).append("\n\n");
        sb.append("=== RESUMEN COMPARATIVO ===\n");
        sb.append(a.nombre()).append(" -> fallas: ").append(a.fallas())
          .append(" | impactos: ").append(a.impactos()).append('\n');
        sb.append(b.nombre()).append(" -> fallas: ").append(b.fallas())
          .append(" | impactos: ").append(b.impactos()).append('\n');
        sb.append(c.nombre()).append(" -> fallas: ").append(c.fallas())
          .append(" | impactos: ").append(c.impactos()).append('\n');
        return sb.toString();
    }
}
