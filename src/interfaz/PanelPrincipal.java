package interfaz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import coordinador.Coordinador;

public final class PanelPrincipal extends Application {

    private static final String FONDO      = "#F4F1EC";
    private static final String TARJETA    = "#FFFFFF";
    private static final String ACENTO     = "#3B5BDB";
    private static final String ACENTO_SUAV= "#E7ECFD";
    private static final String TEXTO      = "#1F2937";
    private static final String TEXTO_SUAV = "#6B7280";
    private static final String BORDE      = "#E5E7EB";
    private static final String BOTON_SEC  = "#EEF2F7";
    private static final String RESULTADO  = "#0F172A";

    private final TextField inCapVirtual = new TextField();
    private final TextField inCapFisica  = new TextField();
    private final TextField inTamanioPag = new TextField();
    private final TextField inDirVirtual = new TextField();
    private final TextArea  outTraduccion = new TextArea();

    private final TextField inCasillas   = new TextField();
    private final TextField inCadena     = new TextField();
    private final TextArea  outReemplazo = new TextArea();

    private Coordinador coordinador;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage escenario) {
        coordinador = new Coordinador(this);
        outTraduccion.setEditable(false);
        outReemplazo.setEditable(false);

        estiloGeneral(outTraduccion);
        estiloGeneral(outReemplazo);
        estiloCampo(inCapVirtual); estiloCampo(inCapFisica);
        estiloCampo(inTamanioPag); estiloCampo(inDirVirtual);
        estiloCampo(inCasillas);   estiloCampo(inCadena);

        TabPane raiz = new TabPane();
        raiz.getTabs().add(armarPestanaTraduccion());
        raiz.getTabs().add(armarPestanaReemplazo());
        raiz.setStyle("-fx-background-color: " + FONDO + "; -fx-border-color: transparent;");

        Scene escena = new Scene(raiz, 940, 660);
        escena.setFill(javafx.scene.paint.Color.web(FONDO));
        escenario.setTitle("Simulador de Gestion de Memoria");
        escenario.setScene(escena);
        escenario.show();
    }

    private Tab armarPestanaTraduccion() {
        Tab t = new Tab("Traduccion de direcciones");
        t.setClosable(false);

        VBox cont = new VBox(18);
        cont.setPadding(new Insets(22));
        cont.setAlignment(Pos.TOP_CENTER);
        cont.setStyle("-fx-background-color: " + FONDO + ";");

        cont.getChildren().add(encabezado(
                "Traduccion de direcciones",
                "Convierte una direccion virtual a fisica usando paginacion simple."));

        cont.getChildren().add(tarjeta(formularioTraduccion()));

        cont.getChildren().add(botones(
                new String[]{"Resolver", "Cargar TXT"},
                new Runnable[]{coordinador::procesarTraduccion, coordinador::importarTraduccion}));

        cont.getChildren().add(areaResultado("Resultado", outTraduccion));
        VBox.setVgrow(outTraduccion, Priority.ALWAYS);

        t.setContent(cont);
        return t;
    }

    private Tab armarPestanaReemplazo() {
        Tab t = new Tab("Reemplazo de paginas");
        t.setClosable(false);

        VBox cont = new VBox(18);
        cont.setPadding(new Insets(22));
        cont.setAlignment(Pos.TOP_CENTER);
        cont.setStyle("-fx-background-color: " + FONDO + ";");

        cont.getChildren().add(encabezado(
                "Reemplazo de paginas",
                "Simula las politicas FIFO, LRU y OPT sobre una cadena de referencias."));

        cont.getChildren().add(tarjeta(formularioReemplazo()));

        cont.getChildren().add(botones(
                new String[]{"Ejecutar politicas", "Cargar TXT"},
                new Runnable[]{coordinador::procesarReemplazo, coordinador::importarReemplazo}));

        cont.getChildren().add(areaResultado("Resultado", outReemplazo));
        VBox.setVgrow(outReemplazo, Priority.ALWAYS);

        t.setContent(cont);
        return t;
    }

    private VBox encabezado(String titulo, String subtitulo) {
        VBox box = new VBox(4);
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(720);
        Label t = new Label(titulo);
        t.setAlignment(Pos.CENTER);
        t.setStyle("-fx-font-size: 22px; -fx-font-weight: 700; -fx-text-fill: " + TEXTO + ";");
        Label s = new Label(subtitulo);
        s.setAlignment(Pos.CENTER);
        s.setWrapText(true);
        s.setStyle("-fx-font-size: 13px; -fx-text-fill: " + TEXTO_SUAV + ";");
        box.getChildren().addAll(t, s);
        return box;
    }

    private VBox tarjeta(javafx.scene.Node contenido) {
        VBox card = new VBox(contenido);
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(720);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: " + TARJETA + ";" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: " + BORDE + ";" +
                "-fx-border-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.06), 10, 0, 0, 2);");
        return card;
    }

    private GridPane formularioTraduccion() {
        GridPane g = rejilla();
        g.add(etiqueta("Memoria virtual (bytes)"), 0, 0);
        g.add(inCapVirtual, 1, 0);
        g.add(etiqueta("Memoria fisica (bytes)"),  0, 1);
        g.add(inCapFisica, 1, 1);
        g.add(etiqueta("Tamano de pagina (bytes)"), 0, 2);
        g.add(inTamanioPag, 1, 2);
        g.add(etiqueta("Direccion virtual"),         0, 3);
        g.add(inDirVirtual, 1, 3);
        return g;
    }

    private GridPane formularioReemplazo() {
        GridPane g = rejilla();
        g.add(etiqueta("Numero de casillas"),  0, 0);
        g.add(inCasillas, 1, 0);
        g.add(etiqueta("Cadena de referencias"), 0, 1);
        g.add(inCadena, 1, 1);
        inCadena.setPrefWidth(520);
        return g;
    }

    private GridPane rejilla() {
        GridPane g = new GridPane();
        g.setHgap(14);
        g.setVgap(12);
        g.setAlignment(Pos.CENTER);
        return g;
    }

    private Label etiqueta(String txt) {
        Label l = new Label(txt);
        l.setAlignment(Pos.CENTER_RIGHT);
        l.setStyle("-fx-font-size: 13px; -fx-text-fill: " + TEXTO + "; -fx-font-weight: 500;");
        return l;
    }

    private HBox botones(String[] textos, Runnable[] acciones) {
        HBox fila = new HBox(10);
        fila.setAlignment(Pos.CENTER);
        fila.setMaxWidth(720);
        for (int i = 0; i < textos.length; i++) {
            final boolean primario = (i == 0);
            final String texto = textos[i];
            final Runnable accion = acciones[i];
            Button b = new Button(texto);
            b.setStyle(primario ? estiloBotonPrimario() : estiloBotonSecundario());
            b.setPrefHeight(34);
            b.setPrefWidth(primario ? 150 : 130);
            b.setCursor(javafx.scene.Cursor.HAND);
            b.setOnAction(e -> accion.run());
            fila.getChildren().add(b);
        }
        return fila;
    }

    private VBox areaResultado(String titulo, TextArea area) {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(820);
        Label t = new Label(titulo);
        t.setAlignment(Pos.CENTER);
        t.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXTO_SUAV
                + "; -fx-font-weight: 600; -fx-letter-spacing: 1px;");
        area.setPrefHeight(560);
        area.setMaxHeight(1.7976931348623157E308);
        box.getChildren().addAll(t, area);
        VBox.setVgrow(area, Priority.ALWAYS);
        VBox.setVgrow(box, Priority.ALWAYS);
        return box;
    }

    private void estiloCampo(TextField tf) {
        tf.setStyle(
                "-fx-background-color: " + TARJETA + ";" +
                "-fx-border-color: " + BORDE + ";" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 10 8 10;" +
                "-fx-font-size: 13px; -fx-text-fill: " + TEXTO + ";");
        tf.setPrefHeight(34);
    }

    private void estiloGeneral(TextArea ta) {
        ta.setStyle(
                "-fx-background-color: " + RESULTADO + ";" +
                "-fx-control-inner-background: " + RESULTADO + ";" +
                "-fx-text-fill: #E5E7EB;" +
                "-fx-font-family: 'Consolas', 'Monaco', monospace;" +
                "-fx-font-size: 12.5px;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-padding: 12;");
        ta.setWrapText(false);
    }

    private String estiloBotonPrimario() {
        return
            "-fx-background-color: " + ACENTO + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px; -fx-font-weight: 600;" +
            "-fx-background-radius: 8;" +
            "-fx-border-radius: 8;" +
            "-fx-cursor: hand;";
    }

    private String estiloBotonSecundario() {
        return
            "-fx-background-color: " + BOTON_SEC + ";" +
            "-fx-text-fill: " + TEXTO + ";" +
            "-fx-font-size: 13px; -fx-font-weight: 500;" +
            "-fx-background-radius: 8;" +
            "-fx-border-radius: 8;" +
            "-fx-cursor: hand;";
    }

    public String capVirtual() { return inCapVirtual.getText(); }
    public String capFisica()  { return inCapFisica.getText(); }
    public String tamanioPag() { return inTamanioPag.getText(); }
    public String dirVirtual() { return inDirVirtual.getText(); }
    public String casillas()   { return inCasillas.getText(); }
    public String cadenaRefs() { return inCadena.getText(); }

    public void setDatosTraduccion(String a, String b, String c, String d) {
        inCapVirtual.setText(a);
        inCapFisica.setText(b);
        inTamanioPag.setText(c);
        inDirVirtual.setText(d);
    }

    public void setDatosReemplazo(String casillas, String refs) {
        inCasillas.setText(casillas);
        inCadena.setText(refs);
    }

    public void publicarTraduccion(String t) { outTraduccion.setText(t); }
    public void publicarReemplazo(String t)  { outReemplazo.setText(t); }
}
