package persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class LectorTXT {

    public String[] leerBloqueTraduccion(Path ruta) throws IOException {
        return extraerLineas(ruta, 4);
    }

    public String[] leerBloqueReemplazo(Path ruta) throws IOException {
        return extraerLineas(ruta, 2);
    }

    private String[] extraerLineas(Path ruta, int minimo) throws IOException {
        List<String> utiles = new ArrayList<>();
        try (Stream<String> lineas = Files.lines(ruta)) {
            lineas.map(String::trim)
                  .filter(s -> !s.isEmpty())
                  .limit(minimo)
                  .forEach(utiles::add);
        }
        if (utiles.size() < minimo) {
            throw new IOException("Archivo incompleto: se requieren " + minimo + " lineas.");
        }
        return utiles.toArray(new String[0]);
    }
}
