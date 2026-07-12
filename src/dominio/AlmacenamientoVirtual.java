package dominio;

public final class AlmacenamientoVirtual {

    private final int capacidadTotal;
    private final int tamanioBloque;

    public AlmacenamientoVirtual(int capacidadTotal, int tamanioBloque) {
        this.capacidadTotal = capacidadTotal;
        this.tamanioBloque = tamanioBloque;
    }

    public int capacidad() {
        return capacidadTotal;
    }

    public int bloquesTotales() {
        return capacidadTotal / tamanioBloque;
    }
}
