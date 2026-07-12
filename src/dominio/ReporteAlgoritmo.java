package dominio;

public final class ReporteAlgoritmo {

    private final String algoritmo;
    private final String traza;
    private final int fallas;
    private final int impactos;

    public ReporteAlgoritmo(String algoritmo, String traza, int fallas, int impactos) {
        this.algoritmo = algoritmo;
        this.traza = traza;
        this.fallas = fallas;
        this.impactos = impactos;
    }

    public String nombre() {
        return algoritmo;
    }

    public String traza() {
        return traza;
    }

    public int fallas() {
        return fallas;
    }

    public int impactos() {
        return impactos;
    }
}
