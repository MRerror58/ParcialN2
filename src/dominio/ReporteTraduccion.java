package dominio;

public final class ReporteTraduccion {

    private final String contenido;

    public ReporteTraduccion(String contenido) {
        this.contenido = contenido;
    }

    public String texto() {
        return contenido;
    }
}
