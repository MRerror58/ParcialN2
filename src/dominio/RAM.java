package dominio;

public final class RAM {

    private final int capacidadTotal;
    private final int tamanioBloque;

    public RAM(int capacidadTotal, int tamanioBloque) {
        this.capacidadTotal = capacidadTotal;
        this.tamanioBloque = tamanioBloque;
    }

    public int capacidad() {
        return capacidadTotal;
    }

    public int casillasDisponibles() {
        return capacidadTotal / tamanioBloque;
    }
}
