package dominio;

public final class Casilla {

    private final int indice;
    private boolean enUso;

    public Casilla(int indice) {
        this.indice = indice;
        this.enUso = false;
    }

    public int obtenerIndice() {
        return indice;
    }

    public boolean ocupado() {
        return enUso;
    }

    public void cambiarEstado(boolean nuevoEstado) {
        this.enUso = nuevoEstado;
    }
}
