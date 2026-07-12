package herramientas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Parseador {

    private Parseador() {}

    public static int aEntero(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Campo vacio detectado.");
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor no numerico: " + texto);
        }
    }

    public static List<Integer> aLista(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("La cadena de referencias esta vacia.");
        }
        List<Integer> resultado = new ArrayList<>();
        for (String tok : texto.trim().split("\\s+")) {
            try {
                resultado.add(Integer.parseInt(tok));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Referencia invalida: " + tok);
            }
        }
        return resultado;
    }
}
