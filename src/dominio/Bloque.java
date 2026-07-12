package dominio;

public final class Bloque {

    private final int identificador;

    public Bloque(int identificador) {
        this.identificador = identificador;
    }

    public int obtenerId() {
        return identificador;
    }
}
